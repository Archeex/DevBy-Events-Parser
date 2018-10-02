package sample;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.image.ImageView;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;

import javax.xml.parsers.ParserConfigurationException;
import java.awt.*;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;

public class Controller {
    @FXML
    private ImageView imageApp;
    @FXML
    private ImageView imageLogo;
    @FXML
    private ImageView imageGit;

    @FXML
    private Label eventFirst;
    @FXML
    private Label eventSecond;
    @FXML
    private Label eventThird;

    @FXML
    private Label eventDateFirst;
    @FXML
    private Label eventDateSecond;
    @FXML
    private Label eventDateThird;

    @FXML
    private TextArea eventFirstDescription;
    @FXML
    private TextArea eventSecondDescription;
    @FXML
    private TextArea eventThirdDescription;

    @FXML
    private Button nextButton;
    @FXML
    private Button prevButton;

    private Integer pageNumber;
    private EventParse news;

    @FXML
    private void initialize() throws ParserConfigurationException, IOException {
        imageApp.setImage(new Image("iconApp.png"));
        imageLogo.setImage(new Image("iconLogo.png"));
        imageGit.setImage(new Image("iconGit.png"));

        //Parse XML in doc
        news = new EventParse(new URL("https://events.dev.by/"));
        pageNumber = 0;
        initEvents(news, pageNumber);

        nextButton.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
            if(news.eventArrayList.size() == 3 + pageNumber)
                return;
            pageNumber++;
            initEvents(news, pageNumber);
        });

        prevButton.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
            if(pageNumber > 0) {
                pageNumber--;
                initEvents(news, pageNumber);
            }
        });

        imageApp.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
            if (Desktop.isDesktopSupported()) {
                try {
                    Desktop.getDesktop().browse(new URL("https://events.dev.by/").toURI());
                } catch (IOException | URISyntaxException e) {
                    e.printStackTrace();
                }
            }
        });

        imageGit.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
            if (Desktop.isDesktopSupported()) {
                try {
                    Desktop.getDesktop().browse(new URL("https://github.com/Archeex/DevBy-Events-Parser/").toURI());
                } catch (IOException | URISyntaxException e) {
                    e.printStackTrace();
                }
            }
        });

        eventFirst.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
            if (Desktop.isDesktopSupported()) {
                try {
                    Desktop.getDesktop().browse(news.eventArrayList.get(0 + pageNumber).url.toURI());
                } catch (IOException | URISyntaxException e) {
                    e.printStackTrace();
                }
            }
        });

        eventSecond.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
            if (Desktop.isDesktopSupported()) {
                try {
                    Desktop.getDesktop().browse(news.eventArrayList.get(1 + pageNumber).url.toURI());
                } catch (IOException | URISyntaxException e) {
                    e.printStackTrace();
                }
            }
        });

        eventThird.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
            if (Desktop.isDesktopSupported()) {
                try {
                    Desktop.getDesktop().browse(news.eventArrayList.get(2 + pageNumber).url.toURI());
                } catch (IOException | URISyntaxException e) {
                    e.printStackTrace();
                }
            }
        });

    }

    private void initEvents(EventParse news, Integer pageNumber) {
        eventFirst.setText(news.eventArrayList.get(0 + pageNumber).nameEvent);
        eventSecond.setText(news.eventArrayList.get(1 + pageNumber).nameEvent);
        eventThird.setText(news.eventArrayList.get(2 + pageNumber).nameEvent);

        eventDateFirst.setText(news.eventArrayList.get(0 + pageNumber).dateEvent);
        eventDateSecond.setText(news.eventArrayList.get(1 + pageNumber).dateEvent);
        eventDateThird.setText(news.eventArrayList.get(2 + pageNumber).dateEvent);

        eventFirstDescription.setText(news.eventArrayList.get(0 + pageNumber).descrEvent);
        eventFirstDescription.setWrapText(true);
        eventSecondDescription.setText(news.eventArrayList.get(1 + pageNumber).descrEvent);
        eventSecondDescription.setWrapText(true);
        eventThirdDescription.setText(news.eventArrayList.get(2 + pageNumber).descrEvent);
        eventThirdDescription.setWrapText(true);
    }
}
