package application;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Slider;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.StrokeLineCap;
import javafx.stage.Stage;

public class DrawLines extends Application {

	@Override
	public void start(Stage stage) throws Exception {
		// TODO Auto-generated method stub
		BorderPane root = new BorderPane();
		Scene scene = new Scene(root, 500, 500, Color.AQUA);

		Line lineDash = new Line(10, 10, 200, 10);
		lineDash.setStroke(Color.RED);
		lineDash.setStrokeWidth(10);
		lineDash.setStrokeLineCap(StrokeLineCap.BUTT);
		lineDash.getStrokeDashArray().addAll(100d, 5d, 8d, 20d);
		lineDash.setStrokeDashOffset(0);
		root.getChildren().add(lineDash);
		
		Slider slider = new Slider(0, 100, 0);
		slider.setLayoutX(10);
		slider.setLayoutY(50);
		lineDash.strokeDashOffsetProperty().bind(slider.valueProperty());
		root.getChildren().add(slider);
		
		
		stage.setScene(scene);
		stage.show();
	}
	
	public final static void main(String[] args)
	{
		launch(args);
	}

}
