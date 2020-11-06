package ui;

import game.Cell;
import game.State;
import javafx.scene.Group;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;

import java.io.FileInputStream;

public class SquareUI {
    public static final int SQUARE_SIZE = 80;
    private static final Color darkColor = Color.rgb(189, 130, 11);
    private static final Color lightColor = Color.rgb(224, 172, 67);
    private static final Color sourceColor = Color.rgb(17, 89, 179);
    private static final Color destinationColor = Color.rgb(37, 161, 87);
    private Color defaultColor;

    private ImageView currentImageView;
//    private Circle currentCircle;
    private Rectangle rectangle;
    public final double baseX;
    public final double baseY;

    GameUI gameUI;
    Cell position;

    public SquareUI(Cell position, GameUI gameUI) {
        this.position = position;
        this.gameUI = gameUI;
        currentImageView = null;
        baseX = position.column*SQUARE_SIZE;
        baseY = position.row*SQUARE_SIZE;
//        currentCircle = null;

        if ((position.row+position.column)%2 == 1) {
            defaultColor = darkColor;
        } else {
            defaultColor = lightColor;
        }
    }

    public void showImage(int color) {
        clearImage();

        if (color == State.BLACK) {
            try {
                currentImageView = new ImageView(new Image(new FileInputStream("black.png")));
            } catch (Exception e) {
                System.out.println(e);
                System.out.println("BLACK IMAGE NOT FOUND");
            }
        } else if (color == State.WHITE) {
            try {
                currentImageView = new ImageView(new Image(new FileInputStream("white.png")));
            } catch (Exception e) {
                System.out.println(e);
                System.out.println("WHITE IMAGE NOT FOUND");
            }
        }
        if (currentImageView != null) {
            currentImageView.setLayoutX(baseX);
            currentImageView.setLayoutY(baseY);
            currentImageView.setOnMouseClicked(e -> whenClicked());
            gameUI.getRoot().getChildren().add(currentImageView);
        }
    }

//    public void showCircle(int color) {
//        if (currentCircle != null) {
//            gameUI.getRoot().getChildren().remove(currentCircle);
//            currentCircle = null;
//        }
//
//        if (color == State.BLACK) {
//            currentCircle = new Circle();
//            currentCircle.setRadius(40);
//            currentCircle.setCenterX(position.column*SQUARE_SIZE+SQUARE_SIZE/2);
//            currentCircle.setCenterY(position.row*SQUARE_SIZE+SQUARE_SIZE/2);
//            currentCircle.setFill(Color.BLACK);
//        } else if (color == State.WHITE) {
//            currentCircle = new Circle();
//            currentCircle.setRadius(40);
//            currentCircle.setCenterX(position.column*SQUARE_SIZE+SQUARE_SIZE/2);
//            currentCircle.setCenterY(position.row*SQUARE_SIZE+SQUARE_SIZE/2);
//            currentCircle.setFill(Color.WHITE);
//        }
//        if (currentCircle != null) {
//            currentCircle.setOnMouseClicked(e -> whenClicked());
//            gameUI.getRoot().getChildren().add(currentCircle);
//        }
//    }

    public void drawSquare() {
        rectangle = new Rectangle(SQUARE_SIZE, SQUARE_SIZE);
        rectangle.setLayoutX(baseX);
        rectangle.setLayoutY(baseY);
        rectangle.setFill(defaultColor);
        rectangle.setOnMouseClicked(e -> whenClicked());
        gameUI.getRoot().getChildren().add(rectangle);
    }


    void deactivate() {
        rectangle.setFill(defaultColor);
    }
    void activateAsDestination() {
        rectangle.setFill(destinationColor);
    }
    void activateAsSource() {
        rectangle.setFill(sourceColor);
    }

    private void whenClicked() {
        System.out.println("Clicked cell " + position.row + ", " + position.column);
        gameUI.clickedOnSquare(position);
    }

    public ImageView getCurrentImageView() {
        return currentImageView;
    }

    public void clearImage() {
        if (currentImageView != null) {
            gameUI.getRoot().getChildren().remove(currentImageView);
            currentImageView = null;
        }
    }
}