package sample;

import org.apache.commons.io.FileUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;

class ArticleParse {

    ArrayList<Article> articleArrayList = new ArrayList<>();
    private boolean parseSuccessful = false;

    ArticleParse(URL url) throws IOException {
        Document parseDoc = parseURLInDoc(url);
        Elements allItems;
        File saveDataItProger = new File(Controller.tempPath + "\\saveDataItProger");
        File saveDataHabr = new File(Controller.tempPath + "\\saveDataHabr");

        if (Controller.currentSource.equals("itproger.com")) {
            if (parseSuccessful) {
                File folder = new File(Controller.tempPath);
                if (!folder.exists())
                    folder.mkdir();

                FileUtils.writeStringToFile(saveDataItProger, parseDoc.outerHtml(), "UTF-8");
                allItems = parseDoc.select("div[class=article]");
            } else {
                Document parseLocalDoc = Jsoup.parse(saveDataItProger, "UTF-8");
                allItems = parseLocalDoc.select("div[class=article]");
            }
            for (Element elem : allItems) {
                Article someArticle = new Article();
                someArticle.url = new URL("https://" + Controller.currentSource + "/" + elem.select("div[class=article]").select("a").attr("href"));
                someArticle.articleName = elem.select("a").attr("title");
                someArticle.articleDescription = elem.select("span").get(1).text();
                someArticle.articleDate = elem.select("div").get(1).text().split("·")[1].substring(1);
                articleArrayList.add(someArticle);
            }
        }

        else {
            if (parseSuccessful) {
                File folder = new File(Controller.tempPath);
                if (!folder.exists())
                    folder.mkdir();

                FileUtils.writeStringToFile(saveDataHabr, parseDoc.outerHtml(), "UTF-8");
                allItems = parseDoc.select("article[class=post post_preview]");
            } else {
                Document parseLocalDoc = Jsoup.parse(saveDataHabr, "UTF-8");
                allItems = parseLocalDoc.select("article[class=post post_preview]");
            }
            int i = 1;
            for (Element elem : allItems) {
                Article someArticle = new Article();
                someArticle.url = new URL(elem.select("h2[class=post__title]").select("a").attr("href"));
                someArticle.articleName = elem.select("h2[class=post__title]").text();
                someArticle.articleDescription = elem.select("div[class=post__text post__text-html js-mediator-article]").text();
                someArticle.articleDate = elem.select("span[class=post__time]").text();
                articleArrayList.add(someArticle);
                if(i++ == 6)
                    break;
            }
        }
    }

    private Document parseURLInDoc(URL url) throws IOException {
        Document parsed = new Document("");
        try {
            parsed = Jsoup.parse(url, 10000);
            parseSuccessful = true;
        } catch (IOException e) {
            e.printStackTrace();
            parseSuccessful = false;
        }
        return parsed;
    }
}
