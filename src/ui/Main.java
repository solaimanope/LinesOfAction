package ui;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;
import javafx.stage.Stage;

import java.io.FileInputStream;
import java.util.Random;
import java.util.Scanner;

public class Main extends Application {
    public static Scanner scanner = new Scanner(System.in);
    public static Random random = new Random(1605012);
    public static Stage window;

    @Override
    public void start(Stage primaryStage) throws Exception{
        window = primaryStage;
        window.setTitle("Lines Of Action");
        window.setResizable(false);
        setAgentSelectionScene();
        window.show();
    }

    public static void main(String[] args) {
        launch(args);
    }

    public static void setAgentSelectionScene() {
        Scene currentScene = new Scene(new AgentSelection().getRoot(), 800, 600);
        try {
            ImagePattern bg = new ImagePattern(new Image(new FileInputStream("background.png")));
            System.out.println("OKAY");
            currentScene.setFill(bg);
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("BACKGROUND IMAGE NOT FOUND");
        }
        window.setScene(currentScene);
    }
}
