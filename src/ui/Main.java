package ui;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

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
        window.setScene(new Scene(new AgentSelection().getRoot(), 800, 600));
        window.setResizable(false);
        window.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
