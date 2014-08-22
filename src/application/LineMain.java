package application;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Line;
import javafx.scene.shape.StrokeLineCap;
import javafx.scene.shape.StrokeLineJoin;
import javafx.stage.Stage;

public class LineMain extends Application{

	@Override
	public void start(Stage primaryStage) throws Exception {
		// TODO Auto-generated method stub
		BorderPane root = new BorderPane();
		Scene scene = new Scene(root, 400, 300);
		Line line = new Line();
		line.setStartX(30);
		line.setStartY(30);
		line.setEndX(100);
		line.setEndY(30);
		line.setStroke(Paint.valueOf("red"));
		line.setStrokeLineJoin(StrokeLineJoin.ROUND);
		line.setStrokeLineCap(StrokeLineCap.ROUND);
		line.setStrokeWidth(5);
		
		Line line1 = new Line();
		line1.setStartX(100);
		line1.setStartY(30);
		line1.setEndX(150);
		line1.setEndY(100);
		line1.setStrokeLineJoin(StrokeLineJoin.ROUND);
		line1.setStrokeLineCap(StrokeLineCap.ROUND);
		line1.setStrokeWidth(5);
		line1.setStroke(Paint.valueOf("red"));

		root.getChildren().add(line);
		root.getChildren().add(line1);
		primaryStage.setScene(scene);
		primaryStage.show();
	}
	
	public static void main(String[] args)
	{
		launch(args);
	}

}
