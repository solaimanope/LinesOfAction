package ui;

import agents.HumanUI;
import game.Cell;
import game.Move;
import game.State;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import referee.Referee;

import java.util.Vector;

public class GameUI {
    private Group root;
    private SquareUI[][] squareUI;
    private Cell activeSource;
    private Cell activeDestination;
    private Vector<Cell> availableDestinations;
    private int dimension;
    private Referee referee;
    private Text whoseMove;
    private Text goBack;

    public GameUI(int dimension) {
        this.dimension = dimension;
        root = new Group();

        addSquareUI();

        activeSource = null;
        activeDestination = null;
        availableDestinations = null;

        Rectangle bottom = new Rectangle(SquareUI.SQUARE_SIZE*dimension, 50);
        bottom.setLayoutX(0);
        bottom.setLayoutY(SquareUI.SQUARE_SIZE*dimension);
        bottom.setFill(Color.rgb(222, 214, 201));
        root.getChildren().add(bottom);

        whoseMove = new Text("whose move");
        whoseMove.setLayoutX(15);
        whoseMove.setLayoutY(dimension*SquareUI.SQUARE_SIZE+30);
        whoseMove.setFont(AgentSelection.font);
        root.getChildren().add(whoseMove);

        goBack = new Text("Go Back");
        goBack.setLayoutX(dimension*SquareUI.SQUARE_SIZE-120);
        goBack.setLayoutY(dimension*SquareUI.SQUARE_SIZE+30);;
        goBack.setFont(AgentSelection.font);

        goBack.setOnMouseEntered(e -> {
            goBack.setFill(Color.DARKGREEN);
        });
        goBack.setOnMouseExited(e -> {
            goBack.setFill(Color.BLACK);
        });
        goBack.setOnMouseClicked(e -> {
            Main.setAgentSelectionScene();
        });

        goBack.setVisible(false);

        root.getChildren().add(goBack);
    }

    public void setReferee(Referee referee) {
        this.referee = referee;
    }

    void addSquareUI() {
        squareUI = new SquareUI[dimension][dimension];
        for (int i = 0; i < dimension; i++) {
            for (int j = 0; j < dimension; j++) {
                squareUI[i][j] = new SquareUI(new Cell(i, j), this);
                squareUI[i][j].drawSquare();
            }
        }
    }

    public void showWinUI(String winner) {
        Rectangle rectangle = new Rectangle(SquareUI.SQUARE_SIZE*dimension, SquareUI.SQUARE_SIZE*dimension);
        rectangle.setLayoutX(0);
        rectangle.setLayoutY(0);
        rectangle.setFill(Color.WHITE);
        rectangle.setOpacity(0.5);
        root.getChildren().add(rectangle);

        whoseMove.setText(winner + " WON!");
        goBack.setVisible(true);
    }

    void clickedOnSquare(Cell cell) {
        if (!(referee.currentAgent instanceof HumanUI)) return;

        if (activeSource == null) {
            if (referee.currentState.board[cell.row][cell.column] == referee.currentState.currentPlayer) {
                activeSource = cell;
                availableDestinations = new Vector<>();
                referee.currentState.availableMovesAt(activeSource).forEach(m -> availableDestinations.add(m.destination));
                sourceSelectionUI();
            }
        } else {
            boolean validMove = false;
            for (Cell option : availableDestinations) {
                if (option.equals(cell)) {
                    validMove = true;
                }
            }
            if (validMove) {
                activeDestination = cell;
                moveReady();
            } else {
                sourceDeselectionUI();
                activeSource = null;
                availableDestinations = null;
            }
        }
    }

    private void sourceDeselectionUI() {
        squareUI[activeSource.row][activeSource.column].deactivate();
        availableDestinations.forEach(m -> squareUI[m.row][m.column].deactivate());
    }

    private void sourceSelectionUI() {
        squareUI[activeSource.row][activeSource.column].activateAsSource();
        availableDestinations.forEach(m -> squareUI[m.row][m.column].activateAsDestination());
    }

    public void moveReady() {
        referee.validMoveMade(new Move(activeSource, activeDestination));
        sourceDeselectionUI();
        activeSource = null;
        availableDestinations = null;
        activeDestination = null;
    }

//    public Move getGivenMove() {
//        Move move = new Move(activeSource, activeDestination);
//        activeSource = null;
//        availableDestinations = null;
//        activeDestination = null;
//        return move;
//    }

    public Group getRoot() {
        return root;
    }

    public void currentStateUpdated() {
        whoseMove.setText(referee.currentAgent.designatedColor() + "'s move");

        for (int i = 0; i < referee.currentState.dimension; i++) {
            for (int j = 0; j < referee.currentState.dimension; j++) {
                squareUI[i][j].showImage(referee.currentState.board[i][j]);
            }
        }

//        try {
//            Thread.currentThread().sleep(500);
//        } catch (InterruptedException ie) {
//            Thread.currentThread().interrupt();
//        }
    }
}
