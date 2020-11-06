package ui;

import javafx.animation.Interpolator;
import javafx.animation.Transition;
import javafx.scene.image.ImageView;
import javafx.util.Duration;

public class PieceCaptureAnimation extends Transition {
    public static final int DURATION = 800;
    private ImageView imageView;
    private double x;
    private double y;

    public PieceCaptureAnimation(ImageView imageView) {
        this.imageView = imageView;

        x = imageView.getLayoutX();
        y = imageView.getLayoutY();
//        System.out.println("x = " + x + " y = " + y);

        setInterpolator(Interpolator.LINEAR);
        setCycleDuration(Duration.millis(DURATION));
    }

    @Override
    protected void interpolate(double fraction) {
        imageView.setLayoutX(x + fraction * (SquareUI.SQUARE_SIZE/2));
        imageView.setLayoutY(y + fraction * (SquareUI.SQUARE_SIZE/2));
        imageView.setFitHeight(SquareUI.SQUARE_SIZE - fraction * SquareUI.SQUARE_SIZE);
        imageView.setFitWidth(SquareUI.SQUARE_SIZE - fraction * SquareUI.SQUARE_SIZE);
    }
}
