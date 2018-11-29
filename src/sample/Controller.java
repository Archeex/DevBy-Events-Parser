package sample;

import javafx.fxml.FXML;
import javafx.scene.Cursor;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.awt.*;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Objects;

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
    private TextArea eventDescription;

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
    @FXML
    private Label internetConnection;

    private Integer pageNumber = 0;
    private ArticleParse news;
    static String currentSource;
    private static double xOffset = 0;
    private static double yOffset = 0;

    private final String productName = "itNotes";
    static String tempPath;

    @FXML
    private void initialize() {
        tempPath = "C:\\Users\\" + System.getProperty("user.name") + "\\AppData\\Local\\Temp\\" + productName;
        imageExit.setImage(new Image("iconExit.png"));
        imageRoll.setImage(new Image("iconRoll.png"));

        eventDescription.setVisible(false);
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
            if (Desktop.isDesktopSupported() && Objects.equals(currentSource, "habr.com")) {
                try {
                    Desktop.getDesktop().browse(new URL("https://" + currentSource).toURI());
                } catch (IOException | URISyntaxException e) {
                    e.printStackTrace();
                }
            }
            currentSource = "habr.com";
            try {
                news = new ArticleParse(new URL("https://" + currentSource));
            } catch (IOException e) {
                e.printStackTrace();
            }
            habrButton.setStyle("-fx-background-color: #2F2F2F");
            threeDnewsButton.setStyle("-fx-background-color: #376DF5");
            itprogerButton.setStyle("-fx-background-color: #376DF5");
            initializeEvents(news, pageNumber);
            startText.setVisible(false);
            eventDescription.setVisible(true);
            if(!ArticleParse.parseSuccessful)
                internetConnection.setText("Интернет-соединение отсутствует");
        });

        itprogerButton.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
            if (Desktop.isDesktopSupported() && Objects.equals(currentSource, "itproger.com")) {
                try {
                    Desktop.getDesktop().browse(new URL("https://" + currentSource).toURI());
                } catch (IOException | URISyntaxException e) {
                    e.printStackTrace();
                }
            }
            currentSource = "itproger.com";
            try {
                news = new ArticleParse(new URL("https://" + currentSource));
            } catch (IOException e) {
                e.printStackTrace();
            }
            if(pageNumber > 5)
                pageNumber = 5;
            itprogerButton.setStyle("-fx-background-color: #2F2F2F");
            habrButton.setStyle("-fx-background-color: #376DF5");
            threeDnewsButton.setStyle("-fx-background-color: #376DF5");
            initializeEvents(news, pageNumber);
            startText.setVisible(false);
            eventDescription.setVisible(true);
            if(!ArticleParse.parseSuccessful)
                internetConnection.setText("Интернет-соединение отсутствует");
        });

        threeDnewsButton.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
            if (Desktop.isDesktopSupported() && Objects.equals(currentSource, "3dnews.ru")) {
                try {
                    Desktop.getDesktop().browse(new URL("https://" + currentSource).toURI());
                } catch (IOException | URISyntaxException e) {
                    e.printStackTrace();
                }
            }
            currentSource = "3dnews.ru";
            try {
                news = new ArticleParse(new URL("https://" + currentSource));
            } catch (IOException e) {
                e.printStackTrace();
            }
            if(pageNumber > 19)
                pageNumber = 19;
            threeDnewsButton.setStyle("-fx-background-color: #2F2F2F");
            habrButton.setStyle("-fx-background-color: #376DF5");
            itprogerButton.setStyle("-fx-background-color: #376DF5");
            initializeEvents(news, pageNumber);
            startText.setVisible(false);
            eventDescription.setVisible(true);
            if(!ArticleParse.parseSuccessful)
                internetConnection.setText("Интернет-соединение отсутствует");
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
            eventName.setTextFill(Color.web("#376DF5"));
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
