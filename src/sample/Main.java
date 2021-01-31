package sample;


import javafx.application.Application;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;

import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;

public class Main extends Application {

    private final int APP_WIDTH = 591;
    private final int APP_HEIGHT = 224;

    private final String ICON_PATH = "/pics/default.png";
    private final String APP_NAME = "MyCurrency";
    public final String MAIN_FXML_NAME = "main.fxml";


    private double xOffset = 0;
    private double yOffset = 0;


    @Override
    public void start(Stage primaryStage) throws Exception {

        Parent root = FXMLLoader.load(getClass().getResource(MAIN_FXML_NAME));


        primaryStage.setTitle(APP_NAME);
        primaryStage.setResizable(false);


        root.setOnMousePressed(mouseEvent -> {
            xOffset = mouseEvent.getSceneX();
            yOffset = mouseEvent.getSceneY();
        });
        root.setOnMouseDragged(mouseEvent -> {
            primaryStage.setX(mouseEvent.getScreenX() - xOffset);
            primaryStage.setY(mouseEvent.getScreenY() - yOffset);
        });


        Scene scene = new Scene(root, APP_WIDTH, APP_HEIGHT);
        primaryStage.setScene(scene);

        // set app icon
        primaryStage.getIcons().add(new Image(Main.class.getResourceAsStream(ICON_PATH)));

        primaryStage.getScene().getStylesheets().add("sample/styles.css");


        scene.setFill(Color.TRANSPARENT);
        primaryStage.initStyle(StageStyle.TRANSPARENT);

        primaryStage.show();


    }


    public static void main(String[] args) throws IOException {
        launch(args);
    }


}
