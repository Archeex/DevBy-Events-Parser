package sample;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;

class EventParse {

    ArrayList<Event> eventArrayList = new ArrayList<>();

    EventParse(URL url) throws IOException {
        Document parseDoc = parseURLInDoc(url);
        Elements allEvents = parseDoc.select("div[class=list-item-events list-more]");
        Elements allItems = allEvents.select("div[class=item]");

        for (Element elem : allItems) {
            Event someEvent = new Event();
            someEvent.url = new URL("https://events.dev.by" + (elem.select("div[class=item-body left]").select("a").attr("href")));
            someEvent.nameEvent = elem.select("div[class=item-body left]").select("a").first().text();
            someEvent.descrEvent = parseURLInDoc(someEvent.url).select("div[class=text]").text();
            someEvent.dateEvent = parseURLInDoc(someEvent.url).select("div[class=time]").text();
            eventArrayList.add(someEvent);
        }

    }

    private Document parseURLInDoc(URL url) throws IOException {
        Document parsed = new Document("");
        try {
            parsed = Jsoup.parse(url, 3000);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return parsed;
    }
}
