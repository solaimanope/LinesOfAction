package ui;

import agents.*;
import agents.heuristics.*;
import game.State;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import referee.Referee;

import java.io.FileInputStream;

public class AgentSelection {
    public static final Font font = Font.font("Helvetica", FontWeight.BOLD, FontPosture.REGULAR, 26);
    private Group root;
    private RadioButton blackHuman, blackAI;
    private ToggleGroup blackToggleGroup;
    private Text blackText;

    private RadioButton whiteHuman, whiteAI;
    private ToggleGroup whiteToggleGroup;
    private Text whiteText;

    private RadioButton dim8Radio, dim6Radio;
    private ToggleGroup dimToggleGroup;
    private Text dimText;

    private final int offsetX = 100;

    private Button startButton;

    public AgentSelection() {
        root = new Group();

        blackText = new Text("Black Player Type");
        blackText.setLayoutX(offsetX);
        blackText.setLayoutY(80);
        blackText.setFont(font);
        root.getChildren().add(blackText);

        blackHuman = new RadioButton("Human");
        blackHuman.setLayoutX(offsetX);
        blackHuman.setLayoutY(100);
        blackHuman.setFont(font);
        blackHuman.setTextFill(Color.BLACK);
        root.getChildren().add(blackHuman);

        blackAI = new RadioButton("AI");
        blackAI.setLayoutX(offsetX);
        blackAI.setLayoutY(150);
        blackAI.setFont(font);
        blackAI.setTextFill(Color.BLACK);
        root.getChildren().add(blackAI);

        blackToggleGroup = new ToggleGroup();
        blackHuman.setToggleGroup(blackToggleGroup);
        blackAI.setToggleGroup(blackToggleGroup);
        blackAI.setSelected(true);
        
        whiteText = new Text("White Player Type");
        whiteText.setLayoutX(offsetX);
        whiteText.setLayoutY(230);
        whiteText.setFont(font);
        root.getChildren().add(whiteText);

        whiteHuman = new RadioButton("Human");
        whiteHuman.setLayoutX(offsetX);
        whiteHuman.setLayoutY(250);
        whiteHuman.setFont(font);
        whiteHuman.setTextFill(Color.BLACK);
        root.getChildren().add(whiteHuman);

        whiteAI = new RadioButton("AI");
        whiteAI.setLayoutX(offsetX);
        whiteAI.setLayoutY(300);
        whiteAI.setFont(font);
        whiteAI.setTextFill(Color.BLACK);
        root.getChildren().add(whiteAI);

        whiteToggleGroup = new ToggleGroup();
        whiteHuman.setToggleGroup(whiteToggleGroup);
        whiteAI.setToggleGroup(whiteToggleGroup);
        whiteAI.setSelected(true);
        
        dimText = new Text("Select dimension");
        dimText.setLayoutX(offsetX);
        dimText.setLayoutY(430);
        dimText.setFont(font);
        root.getChildren().add(dimText);

        dim8Radio = new RadioButton("8x8");
        dim8Radio.setLayoutX(offsetX);
        dim8Radio.setLayoutY(450);
        dim8Radio.setFont(font);
        dim8Radio.setTextFill(Color.BLACK);
        root.getChildren().add(dim8Radio);

        dim6Radio = new RadioButton("6x6");
        dim6Radio.setLayoutX(offsetX+100);
        dim6Radio.setLayoutY(450);
        dim6Radio.setFont(font);
        dim6Radio.setTextFill(Color.BLACK);
        root.getChildren().add(dim6Radio);

        dimToggleGroup = new ToggleGroup();
        dim8Radio.setToggleGroup(dimToggleGroup);
        dim6Radio.setToggleGroup(dimToggleGroup);
        dim8Radio.setSelected(true);

        startButton = new Button("Start Game");
        startButton.setLayoutX(offsetX);
        startButton.setLayoutY(500);
        startButton.setFont(font);
        startButton.setOnMouseClicked(e -> clickStart());
        root.getChildren().add(startButton);
    }

    private void clickStart() {
        int dimension = 8;
        if (dim6Radio.isSelected()) {
            dimension = 6;
        }

        GameUI gameUI = new GameUI(dimension);
        Agent blackAgent;
        if (blackHuman.isSelected()) {
            blackAgent = new HumanUI(State.BLACK, gameUI);
        } else {
            Minimax minimax = new Minimax(State.BLACK);
            minimax.addHeuristics(new MaximizePositionalScore(), 1);
            minimax.addHeuristics(new MaximizeQuadCount(), 0.025);
            minimax.addHeuristics(new MaximizeDensity(), 2);
            minimax.addHeuristics(new MinimumBoundingBox(), 0.2);
            blackAgent = minimax;
        }
        Agent whiteAgent;
        if (whiteHuman.isSelected()) {
            whiteAgent = new HumanUI(State.WHITE, gameUI);
        } else {
            Minimax minimax = new Minimax(State.WHITE);
            minimax.addHeuristics(new MaximizeDensity(), 1);
            whiteAgent = minimax;
        }

        Referee referee = new Referee(dimension, blackAgent, whiteAgent, gameUI);
        gameUI.setReferee(referee);
        referee.initUI();
        Main.window.setScene(new Scene(gameUI.getRoot(), SquareUI.SQUARE_SIZE*dimension, SquareUI.SQUARE_SIZE*dimension+50));

//        referee.conductGame();
    }

    public Group getRoot() {
        return root;
    }
}
