package ui;

import agents.*;
import agents.heuristics.MaximizePositionalScore;
import game.State;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import referee.Referee;

public class AgentSelection {
    private final Font font = Font.font("Helvetica", FontWeight.BOLD, FontPosture.REGULAR, 26);
    private Group root;
    private RadioButton blackHuman, blackAI;
    private ToggleGroup blackToggleGroup;
    private Text blackText;

    private RadioButton whiteHuman, whiteAI;
    private ToggleGroup whiteToggleGroup;
    private Text whiteText;

    private final static int blackX = 50;
    private final static int whiteX = 500;

    private Button startButton;

    public AgentSelection() {
        root = new Group();

        blackText = new Text("Black Player Type");
        blackText.setLayoutX(blackX);
        blackText.setLayoutY(100);
        blackText.setFont(font);
        root.getChildren().add(blackText);

        blackHuman = new RadioButton("Human");
        blackHuman.setLayoutX(blackX);
        blackHuman.setLayoutY(150);
        blackHuman.setFont(font);
        root.getChildren().add(blackHuman);

        blackAI = new RadioButton("AI");
        blackAI.setLayoutX(blackX);
        blackAI.setLayoutY(200);
        blackAI.setFont(font);
        root.getChildren().add(blackAI);

        blackToggleGroup = new ToggleGroup();
        blackHuman.setToggleGroup(blackToggleGroup);
        blackAI.setToggleGroup(blackToggleGroup);
        blackAI.setSelected(true);
        
        whiteText = new Text("White Player Type");
        whiteText.setLayoutX(whiteX);
        whiteText.setLayoutY(100);
        whiteText.setFont(font);
        root.getChildren().add(whiteText);

        whiteHuman = new RadioButton("Human");
        whiteHuman.setLayoutX(whiteX);
        whiteHuman.setLayoutY(150);
        whiteHuman.setFont(font);
        root.getChildren().add(whiteHuman);

        whiteAI = new RadioButton("AI");
        whiteAI.setLayoutX(whiteX);
        whiteAI.setLayoutY(200);
        whiteAI.setFont(font);
        root.getChildren().add(whiteAI);

        whiteToggleGroup = new ToggleGroup();
        whiteHuman.setToggleGroup(whiteToggleGroup);
        whiteAI.setToggleGroup(whiteToggleGroup);
        whiteAI.setSelected(true);

        startButton = new Button("Start Game");
        startButton.setLayoutX(350);
        startButton.setLayoutY(500);
        startButton.setFont(font);
        startButton.setOnMouseClicked(e -> clickStart());
        root.getChildren().add(startButton);
    }

    private void clickStart() {
        GameUI gameUI = new GameUI(8);
        Agent blackAgent;
        if (blackHuman.isSelected()) {
            blackAgent = new HumanUI(State.BLACK, gameUI);
        } else {
            Minimax minimax = new Minimax(State.BLACK);
            minimax.addHeuristics(new MaximizePositionalScore(), 1);
            blackAgent = minimax;
        }
        Agent whiteAgent;
        if (whiteHuman.isSelected()) {
            whiteAgent = new HumanUI(State.WHITE, gameUI);
        } else {
            Minimax minimax = new Minimax(State.WHITE);
            minimax.addHeuristics(new MaximizePositionalScore(), 1);
            whiteAgent = minimax;
        }
//        blackAgent = new Random(State.BLACK);
//        whiteAgent = new Random(State.WHITE);

        Referee referee = new Referee(8, blackAgent, whiteAgent, gameUI);
        gameUI.setReferee(referee);
        referee.init();
        Main.window.setScene(new Scene(gameUI.getRoot(), 640, 640));

//        referee.conductGame();
    }

    public Group getRoot() {
        return root;
    }
}
