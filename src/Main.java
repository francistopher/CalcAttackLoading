import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.control.Label;

import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;

public class Main extends Application {

    // integral size ratio compared to original size
    private double integralSizeRatio = -8.0;
    // label with integral symbol as text
    private final Label integralLabel = new Label("∫");
    // pane housing integral
    private final Pane pane = new Pane(integralLabel);
    // keeps track of integral color
    private boolean integralColorIsBlack = false;
    // applies golden ratio factor to given length
    public double adjustLength(double length, double ratio)
    {
        return length * Math.pow(16.0, ratio) / Math.pow(9.0, ratio);
    }
    // returns center position of axis given parent and child length on that same axis
    public double getCenterPos(double parentLength, double childLength)
    {
        return (parentLength - childLength) * 0.5;
    }
    // returns new colored background
    public Background getNewBackground(Color color)
    {
        return new Background(new BackgroundFill(color, null, null));
    }
    public void start(Stage stage) {
        // get lengths
        Rectangle2D screenBounds = Screen.getPrimary().getBounds();
        double sideLength = screenBounds.getHeight();

        // set integral label
        integralLabel.setAlignment(Pos.CENTER);
        integralLabel.setBackground(getNewBackground(Color.TRANSPARENT));
        integralLabel.setTextFill(Color.BLACK);

        // set pane/scene
        pane.setBackground(getNewBackground(Color.WHITE));
        Scene introScene = new Scene(pane,adjustLength(sideLength, -2), adjustLength(sideLength, -1));

        // set stage
        stage.setScene(introScene);
        double centerX = getCenterPos(screenBounds.getWidth(), adjustLength(sideLength, -2));
        double centerY = getCenterPos(screenBounds.getHeight(), adjustLength(sideLength, -1));
        stage.setX(centerX);
        stage.setY(centerY);
        stage.initStyle(StageStyle.UNDECORATED);
        stage.show();

        // create timeline
        Timeline timeline = new Timeline(
                new KeyFrame(Duration.seconds(0.007), event -> {
                    // load custom font and size
                    Font cambriaMath = Font.loadFont(getClass().getResourceAsStream("./Cambria Math.ttf"), adjustLength(sideLength, integralSizeRatio));
                    // size ratio has been reset
                    if (integralSizeRatio == -8.0)
                    {
                        // prevent flicker
                        pane.setBackground(getNewBackground(integralColorIsBlack ? Color.BLACK : Color.WHITE));
                        integralLabel.setTextFill(integralColorIsBlack ? Color.WHITE : Color.BLACK);
                        integralLabel.setBackground(getNewBackground(integralColorIsBlack ? Color.BLACK : Color.WHITE));
                    }
                    // update integral size, font, position
                    integralLabel.setFont(cambriaMath);
                    integralLabel.setMinSize(adjustLength(sideLength, integralSizeRatio), adjustLength(sideLength, integralSizeRatio + 1));
                    integralLabel.setLayoutX(getCenterPos(introScene.getWidth(), adjustLength(sideLength, integralSizeRatio)));
                    integralLabel.setLayoutY(getCenterPos(introScene.getHeight(), adjustLength(sideLength, integralSizeRatio + 1)));
                    // increase ratio
                    integralSizeRatio += 0.03125;
                    // maximum size has been met
                    if (integralSizeRatio >= 3.5)
                    {
                        // reset ratio
                        integralSizeRatio = -8.0;
                        // update color for next iteration
                        pane.setBackground(getNewBackground(integralColorIsBlack ? Color.BLACK : Color.WHITE));
                        integralLabel.setTextFill(integralColorIsBlack ? Color.WHITE : Color.BLACK);
                        integralLabel.setBackground(getNewBackground(integralColorIsBlack ? Color.BLACK : Color.WHITE));
                        integralColorIsBlack = !integralColorIsBlack;
                    }
                })
        );
        // start animation
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();
    }

    public static void main(String[] args)
    {
        launch(args);
    }
}
