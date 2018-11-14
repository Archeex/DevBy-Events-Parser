package sample;

import javafx.fxml.FXML;
import javafx.scene.Cursor;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import javax.xml.parsers.ParserConfigurationException;
import java.awt.*;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;

public class Controller {

    @FXML
    private Pane pane;
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
    @FXML
    private Button habrButton;
    @FXML
    private Button itprogerButton;
    @FXML
    private Button threeDnewsButton;
    @FXML
    private Label startText;

    private Integer pageNumber = 0;
    private ArticleParse news;
    static String currentSource;
    private static double xOffset = 0;
    private static double yOffset = 0;

    private String productName = "itNotes";
    static String tempPath;

    @FXML
    private void initialize() throws ParserConfigurationException, IOException {
        tempPath = "C:\\Users\\" + System.getProperty("user.name") + "\\AppData\\Local\\Temp\\" + productName;
        imageExit.setImage(new Image("iconExit.png"));
        imageRoll.setImage(new Image("iconRoll.png"));

        startText.setText("Выберите интересующий вас источник. Для работы приложения требуется интернет-соединение.");
        setHandlers();
        setMouseDraggedShow();
    }

    private void initializeEvents(ArticleParse news, Integer pageNumber) {
        eventName.setText(news.articleArrayList.get(pageNumber).articleName);
        eventDate.setText(news.articleArrayList.get(pageNumber).articleDate);
        eventDescription.setText(news.articleArrayList.get(pageNumber).articleDescription);
    }

    private void setHandlers() {
        eventName.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
            if (Desktop.isDesktopSupported()) {
                try {
                    Desktop.getDesktop().browse(news.articleArrayList.get(pageNumber).url.toURI());
                } catch (IOException | URISyntaxException e) {
                    e.printStackTrace();
                }
            }
        });

        nextButton.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
            if(news.articleArrayList.size() == pageNumber + 1)
                return;
            pageNumber++;
            initializeEvents(news, pageNumber);
        });

        prevButton.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
            if(pageNumber > 0) {
                pageNumber--;
                initializeEvents(news, pageNumber);
            }
        });

        habrButton.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
            currentSource = "habr.com";
            try {
                news = new ArticleParse(new URL("https://" + currentSource));
            } catch (IOException e) {
                e.printStackTrace();
            }
            initializeEvents(news, pageNumber);
            startText.setVisible(false);
        });

        itprogerButton.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
            currentSource = "itproger.com";
            try {
                news = new ArticleParse(new URL("https://" + currentSource));
            } catch (IOException e) {
                e.printStackTrace();
            }
            if(pageNumber > 5)
                pageNumber = 5;
            initializeEvents(news, pageNumber);
            startText.setVisible(false);
        });

        threeDnewsButton.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
            currentSource = "3dnews.ru";
            try {
                news = new ArticleParse(new URL("https://" + currentSource));
            } catch (IOException e) {
                e.printStackTrace();
            }
            if(pageNumber > 19)
                pageNumber = 19;
            initializeEvents(news, pageNumber);
            startText.setVisible(false);
        });

        imageExit.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
            Stage stage = (Stage) imageExit.getScene().getWindow();
            stage.close();
        });

        imageRoll.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
            Stage stage = (Stage) imageRoll.getScene().getWindow();
            stage.setIconified(true);
        });
    }

    private void setMouseDraggedShow() {
        eventName.addEventHandler(MouseEvent.MOUSE_ENTERED, event -> {
            Main.getPrimaryStage().getScene().setCursor(Cursor.HAND);
            eventName.setTextFill(Color.web("#994058"));
            });
        eventName.addEventHandler(MouseEvent.MOUSE_EXITED, event -> {
            Main.getPrimaryStage().getScene().setCursor(Cursor.DEFAULT);
            eventName.setTextFill(Color.web("#282828"));
        });

        imageRoll.addEventHandler(MouseEvent.MOUSE_ENTERED, event -> Main.getPrimaryStage().getScene().setCursor(Cursor.HAND));
        imageRoll.addEventHandler(MouseEvent.MOUSE_EXITED, event -> Main.getPrimaryStage().getScene().setCursor(Cursor.DEFAULT));
        imageExit.addEventHandler(MouseEvent.MOUSE_ENTERED, event -> Main.getPrimaryStage().getScene().setCursor(Cursor.HAND));
        imageExit.addEventHandler(MouseEvent.MOUSE_EXITED, event -> Main.getPrimaryStage().getScene().setCursor(Cursor.DEFAULT));

        nextButton.addEventHandler(MouseEvent.MOUSE_ENTERED, event -> Main.getPrimaryStage().getScene().setCursor(Cursor.HAND));
        nextButton.addEventHandler(MouseEvent.MOUSE_EXITED, event -> Main.getPrimaryStage().getScene().setCursor(Cursor.DEFAULT));
        prevButton.addEventHandler(MouseEvent.MOUSE_ENTERED, event -> Main.getPrimaryStage().getScene().setCursor(Cursor.HAND));
        prevButton.addEventHandler(MouseEvent.MOUSE_EXITED, event -> Main.getPrimaryStage().getScene().setCursor(Cursor.DEFAULT));

        habrButton.addEventHandler(MouseEvent.MOUSE_ENTERED, event -> Main.getPrimaryStage().getScene().setCursor(Cursor.HAND));
        habrButton.addEventHandler(MouseEvent.MOUSE_EXITED, event -> Main.getPrimaryStage().getScene().setCursor(Cursor.DEFAULT));
        itprogerButton.addEventHandler(MouseEvent.MOUSE_ENTERED, event -> Main.getPrimaryStage().getScene().setCursor(Cursor.HAND));
        itprogerButton.addEventHandler(MouseEvent.MOUSE_EXITED, event -> Main.getPrimaryStage().getScene().setCursor(Cursor.DEFAULT));
        threeDnewsButton.addEventHandler(MouseEvent.MOUSE_ENTERED, event -> Main.getPrimaryStage().getScene().setCursor(Cursor.HAND));
        threeDnewsButton.addEventHandler(MouseEvent.MOUSE_EXITED, event -> Main.getPrimaryStage().getScene().setCursor(Cursor.DEFAULT));


        pane.setOnMousePressed(event -> {
            xOffset = Main.getPrimaryStage().getX() - event.getScreenX();
            yOffset = Main.getPrimaryStage().getY() - event.getScreenY();
        });

        pane.setOnMouseDragged(event -> {
            Main.getPrimaryStage().setX(event.getScreenX() + xOffset);
            Main.getPrimaryStage().setY(event.getScreenY() + yOffset);
        });
    }
}
