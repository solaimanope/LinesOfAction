package ui;

import javafx.animation.Interpolator;
import javafx.animation.Transition;
import javafx.scene.image.ImageView;
import javafx.util.Duration;

public class PieceMoveAnimation extends Transition {
    public static final int DURATION_PER_UNIT = 400;
    private ImageView imageView;
//    private final int animationDuration = 900;

    private double dx;
    private double dy;

    private double x;
    private double y;

    public PieceMoveAnimation(ImageView imageView, double dx, double dy, double duration) {
        this.imageView = imageView;
        this.dx = dx;
        this.dy = dy;

        x = imageView.getLayoutX();
        y = imageView.getLayoutY();

        setInterpolator(Interpolator.LINEAR);
        setCycleDuration(Duration.millis(duration));
    }

    @Override
    protected void interpolate(double fraction) {
        imageView.setLayoutX(x + fraction * dx);
        imageView.setLayoutY(y + fraction * dy);
    }
}