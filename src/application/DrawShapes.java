package application;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.effect.DropShadow;
import javafx.scene.paint.Color;
import javafx.scene.shape.CubicCurve;
import javafx.scene.shape.Ellipse;
import javafx.scene.shape.LineTo;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;
import javafx.scene.shape.QuadCurve;
import javafx.scene.shape.QuadCurveTo;
import javafx.scene.shape.Shape;
import javafx.scene.shape.StrokeType;
import javafx.stage.Stage;

public class DrawShapes extends Application {

	@Override
	public void start(Stage primaryStage) throws Exception {
		// TODO Auto-generated method stub
		Group root = new Group();
		Scene scene = new Scene(root, 360, 550, Color.WHITE);
		
		CubicCurve cubicCurve = new CubicCurve(50, 75, 80, -25, 110, 175, 140, 75);
		cubicCurve.setStrokeType(StrokeType.CENTERED);
		cubicCurve.setStroke(Color.BISQUE);
		cubicCurve.setStrokeWidth(6);
		cubicCurve.setFill(Color.WHITE);
		root.getChildren().add(cubicCurve);
		
		Path path = new Path();
		path.setStrokeWidth(3);
		
		MoveTo moveTo = new MoveTo();
		moveTo.setX(50);
		moveTo.setY(150);
		
		QuadCurveTo quadCurveTo = new QuadCurveTo();
		quadCurveTo.setX(150);
		quadCurveTo.setY(150);
		quadCurveTo.setControlX(100);
		quadCurveTo.setControlY(50);
		
		LineTo lineTo1 = new LineTo();
		lineTo1.setX(50);
		lineTo1.setY(150);
		
		LineTo lineTo2 = new LineTo();
		lineTo2.setX(100);
		lineTo2.setY(275);
		
		LineTo lineTo3 = new LineTo();
		lineTo3.setX(150);
		lineTo3.setY(150);
		
		path.getElements().addAll(moveTo, quadCurveTo, lineTo1, lineTo2, lineTo3);
		path.setTranslateY(30);
		root.getChildren().add(path);
		
		QuadCurve quad = new QuadCurve(50, 20, 125, 150, 150, 50);
		quad.setTranslateY(path.getBoundsInParent().getMaxY());
		quad.setStrokeWidth(3);
		quad.setStroke(Color.BLACK);
		quad.setFill(Color.WHITE);
		
		root.getChildren().add(quad);
		
		Ellipse outer = new Ellipse(100, 100, 50, 75/2);
		outer.setStrokeWidth(3);
		outer.setStroke(Color.BLACK);
		outer.setFill(Color.WHITE);
		
		Ellipse inner = new Ellipse(100, 100, 35/2, 25/2);
		
		Shape donut = Path.subtract(outer, inner);
		donut.setStrokeWidth(1.8);
		donut.setStroke(Color.BLACK);
		
		donut.setFill(Color.rgb(255, 200, 0));
		
		DropShadow dropShadow = new DropShadow(5, 2.0f, 2.0f, Color.rgb(50, 50, 50, .588));
		donut.setEffect(dropShadow);
		donut.setTranslateY(quad.getBoundsInParent().getMinY() + 30);
		root.getChildren().add(donut);
		
		primaryStage.setScene(scene);
		primaryStage.show();
	}
	
	public static void main(String[] args)
	{
		launch(args);
	}

}
