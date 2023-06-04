import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.text.Font;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.awt.*;

public class Main extends Application {

    public void start(Stage stage) {
        // get lengths
        Rectangle2D screenBounds = Screen.getPrimary().getBounds();
        double sideLength = screenBounds.getHeight() * 9 / 16;

        // set integral1
        Label integral1 = new Label("∫");
        integral1.setLayoutY(sideLength * 0.25 / 8.0);
        integral1.setAlignment(Pos.CENTER);
        integral1.setFont(new Font("Times New Roman", sideLength * 0.5));
        integral1.setMinSize(sideLength * 9 / 16, sideLength);

        // set pane/scene
        Pane pane = new Pane(integral1);
        Scene introScene = new Scene(pane,sideLength * 9 / 16, sideLength);

        // set stage
        stage.setScene(introScene);
        double centerX = (screenBounds.getWidth() - (sideLength * 9 / 16)) / 2;
        double centerY = (screenBounds.getHeight() - sideLength) / 2;
        stage.setX(centerX);
        stage.setY(centerY);
        stage.initStyle(StageStyle.UNDECORATED);
        stage.show();
    }

    public static void main(String[] args)
    {
        launch(args);
    }
}
