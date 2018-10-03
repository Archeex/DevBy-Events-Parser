package sample;

import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import org.w3c.dom.events.EventException;

import javax.xml.parsers.ParserConfigurationException;
import java.awt.*;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;

public class Controller {

    @FXML
    private Pane pane;
    @FXML
    private ImageView imageApp;
    @FXML
    private ImageView imageGit;
    @FXML
    private ImageView imageExit;
    @FXML
    private ImageView imageRoll;

    @FXML
    private Label eventName;
    @FXML
    private Label eventDate;
    @FXML
    private Label eventDescription;

    @FXML
    private Button nextButton;
    @FXML
    private Button prevButton;

    private Integer pageNumber;
    private EventParse news;
    private static double xOffset = 0;
    private static double yOffset = 0;

    @FXML
    private void initialize() throws ParserConfigurationException, IOException {
        imageApp.setImage(new Image("iconApp.png"));
        imageGit.setImage(new Image("iconGit.png"));
        imageExit.setImage(new Image("iconExit.png"));
        imageRoll.setImage(new Image("iconRoll.png"));

        //Parse XML in doc
        news = new EventParse(new URL("https://events.dev.by/"));
        pageNumber = 0;
        initEvents(news, pageNumber);

        nextButton.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
            if(news.eventArrayList.size() == pageNumber + 1)
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

        imageExit.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
            Stage stage = (Stage) imageExit.getScene().getWindow();
            stage.close();
        });

        imageRoll.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
            Stage stage = (Stage) imageRoll.getScene().getWindow();
            stage.setIconified(true);
        });

        pane.setOnMousePressed(event -> {
            xOffset = Main.getPrimaryStage().getX() - event.getScreenX();
            yOffset = Main.getPrimaryStage().getY() - event.getScreenY();
        });

        pane.setOnMouseDragged(event -> {
            Main.getPrimaryStage().setX(event.getScreenX() + xOffset);
            Main.getPrimaryStage().setY(event.getScreenY() + yOffset);
        });

        eventName.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
            if (Desktop.isDesktopSupported()) {
                try {
                    Desktop.getDesktop().browse(news.eventArrayList.get(pageNumber).url.toURI());
                } catch (IOException | URISyntaxException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void initEvents(EventParse news, Integer pageNumber) {
        eventName.setText(news.eventArrayList.get(pageNumber).nameEvent);
        eventDate.setText(news.eventArrayList.get(pageNumber).dateEvent);
        eventDescription.setText(news.eventArrayList.get(pageNumber).descrEvent);
    }
}
