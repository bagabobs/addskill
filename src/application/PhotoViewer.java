package application;

import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

import javafx.animation.FadeTransition;
import javafx.animation.SequentialTransition;
import javafx.animation.TranslateTransition;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.ReadOnlyDoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.FlowPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Arc;
import javafx.scene.shape.ArcType;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;

public class PhotoViewer extends Application {
	private final List<String> imageFiles = new ArrayList<>();
	private int currentIndex = -1;
	private enum ButtonMove {NEXT, PREV};
	private ImageView currentImageView;
	private ProgressIndicator progressIndicator;
	private AtomicBoolean loading = new AtomicBoolean();
	
	@Override
	public void start(Stage primaryStage) throws Exception {
		// TODO Auto-generated method stub
		primaryStage.setTitle("Image View");
		Group root = new Group();
		Scene scene = new Scene(root, 551, 400, Color.BLACK);
		
		System.out.println(getClass().getResource("photo-viewer.css").toExternalForm());
		scene.getStylesheets().add(getClass().getResource("photo-viewer.css").toExternalForm());
		primaryStage.setScene(scene);
		
		currentImageView = createImageView(scene.widthProperty());
		
		setupDragNDrop(scene);
		
		Group buttonGroup = createButtonPanel(scene);
		
		progressIndicator = createProgressIndicator(scene);
		
		Group newsTickerGroup = createTickerControl(primaryStage, 78);
		
		root.getChildren().addAll(currentImageView, buttonGroup, progressIndicator, newsTickerGroup);
		primaryStage.show();
	}

	private ProgressIndicator createProgressIndicator(Scene scene) {
		// TODO Auto-generated method stub
		ProgressIndicator progress = new ProgressIndicator(0);
		progress.setVisible(false);
		progress.layoutXProperty().bind(scene.widthProperty().subtract(progress.widthProperty()).divide(2));
		
		progress.layoutYProperty().bind(scene.heightProperty().subtract(progress.heightProperty()).divide(2));
		return progress;
	}

	private Group createButtonPanel(Scene scene) {
		// TODO Auto-generated method stub
		Group buttonGroup = new Group();
		Rectangle buttonArea = new Rectangle(0, 0, 60, 30);
		buttonArea.getStyleClass().add("button-panel");
		buttonGroup.getChildren().add(buttonArea);

		Arc leftButton = new Arc(12, 16, 15, 15, -30, 60);
		leftButton.setType(ArcType.ROUND);
		leftButton.getStyleClass().add("left-arrow");
		
		leftButton.addEventHandler(MouseEvent.MOUSE_PRESSED, (mouseEvent) -> {
			System.out.println("busy loading? " + loading.get());
			if(currentIndex == 0 || loading.get()) return;
			int indx = gotoImageIndex(ButtonMove.PREV);
			if(indx > -1)
			{
				loadImage(imageFiles.get(indx));
			}
		});
		
		Arc rightButton = new Arc(12, 16, 15, 15, 180-30, 60);
		rightButton.setType(ArcType.ROUND);
		rightButton.getStyleClass().add("right-arrow");
		
		rightButton.addEventHandler(MouseEvent.MOUSE_PRESSED, (mouseEvent) -> {
			System.out.println("busy loading? " + loading.get());
			
			if(currentIndex == imageFiles.size() -1 || loading.get()) return;
			int idx = gotoImageIndex(ButtonMove.NEXT);
			if(idx > -1)
			{
				loadImage(imageFiles.get(idx));
			}
		});
		
		buttonGroup.getChildren().addAll(leftButton, rightButton);
		buttonGroup.translateXProperty().bind(scene.widthProperty().subtract(buttonArea.getWidth() + 6));
		buttonGroup.translateYProperty().bind(scene.heightProperty().subtract(buttonArea.getHeight() + 6));
		
		scene.setOnMouseEntered((MouseEvent me) -> {
			FadeTransition fadeButtons = new FadeTransition(Duration.millis(500), buttonGroup);
			fadeButtons.setFromValue(0.0);
			fadeButtons.setToValue(1.0);
			fadeButtons.play();
		});
		
		scene.setOnMouseExited((MouseEvent me) -> {
			FadeTransition fadeButtons = new FadeTransition(Duration.millis(500), buttonGroup);
			fadeButtons.setFromValue(1.0);
			fadeButtons.setToValue(0.0);
			fadeButtons.play();
		});
		
		return buttonGroup;
	}

	private int gotoImageIndex(ButtonMove prev) {
		// TODO Auto-generated method stub
		int size = imageFiles.size();
		if(size == 0)
		{
			currentIndex = -1;
		}
		else if(prev == ButtonMove.NEXT && size > 1 && currentIndex < size - 1)
		{
			currentIndex += 1;
		}
		else if(prev == ButtonMove.PREV && size > 1 && currentIndex > 0)
		{
			currentIndex -= 1;
		}
		return currentIndex;
	}
	
	private Task createWorker(final String url)
	{
		return new Task() {

			@Override
			protected Object call() throws Exception {
				// TODO Auto-generated method stub
				Image image = new Image(url, false);
				Platform.runLater(() -> {
					System.out.println("done loading image " + url);
					SequentialTransition seqTrans = transitionByFading(image, currentImageView);
					seqTrans.play();
					//currentImageView.setImage(image);
					progressIndicator.setVisible(false);
					loading.set(false);
				});
				return true;
			}
			
		};
	}

	private void setupDragNDrop(Scene scene) {
		// TODO Auto-generated method stub
		scene.setOnDragOver((DragEvent event) -> {
			Dragboard db = event.getDragboard();
			if(db.hasFiles() || (db.hasUrl() && isValidImageFile(db.getUrl())))
			{
				event.acceptTransferModes(TransferMode.LINK);
			}
			else
			{
				event.consume();
			}
		});
		
		scene.setOnDragDropped((DragEvent event) -> {
			Dragboard db = event.getDragboard();
			if(db.hasFiles() && !db.hasUrl())
			{
				db.getFiles().stream().forEach(file -> {
					try
					{
						addImage(file.toURI().toURL().toString());
					}
					catch(MalformedURLException ex)
					{
						ex.printStackTrace();
					}
				});
			}
			else
			{
				addImage(db.getUrl());
			}
			
			if(currentIndex > -1)
			{
				loadImage(imageFiles.get(currentIndex));
			}
			event.setDropCompleted(true);
			event.consume();
		});
	}

	private void loadImage(String string) {
		// TODO Auto-generated method stub
		if(!loading.getAndSet(true))
		{
			Task loadImage = createWorker(string);
			progressIndicator.setVisible(true);
			progressIndicator.progressProperty().unbind();
			progressIndicator.progressProperty().bind(loadImage.progressProperty());
			new Thread(loadImage).start();
		}
	}

	private void addImage(String string) {
		// TODO Auto-generated method stub
		if(isValidImageFile(string))
		{
			currentIndex += 1;
			imageFiles.add(currentIndex, string);
		}
	}
	
	private SequentialTransition transitionByFading(Image nextImage, ImageView imageView)
	{
		FadeTransition fadeOut = new FadeTransition(Duration.millis(200), imageView);
		fadeOut.setFromValue(1.0);
		fadeOut.setToValue(0.0);
		fadeOut.setOnFinished(actionEvent -> imageView.setImage(nextImage));
		
		FadeTransition fadeIn = new FadeTransition(Duration.millis(200), imageView);
		fadeOut.setFromValue(0.0);
		fadeOut.setToValue(1.0);
		
		SequentialTransition sequentialTransition = new SequentialTransition(fadeOut, fadeIn);
		return sequentialTransition;
	}

	private boolean isValidImageFile(String url) {
		// TODO Auto-generated method stub
		List<String> imgTypes = Arrays.asList(".jpg", ".jpeg", ".png", ".gif", ".bmp");
		return imgTypes.stream().anyMatch(t -> url.endsWith(t));
	}

	private ImageView createImageView(ReadOnlyDoubleProperty widthProperty) {
		// TODO Auto-generated method stub
		ImageView imageView = new ImageView();
		imageView.setPreserveRatio(true);
		imageView.fitWidthProperty().bind(widthProperty);
		return imageView;
	}
	
	private Group createTickerControl(Stage stage, double rightPadding)
	{
		Scene scene = stage.getScene();
		
		Group tickerArea = new Group();
		Rectangle tickerRect = new Rectangle(scene.getWidth(), 30);
		tickerRect.getStyleClass().add("ticker-border");
		
		Rectangle clipRegion = new Rectangle(scene.getWidth(), 30);
		clipRegion.getStyleClass().add("ticker-clip-region");
		tickerArea.setClip(clipRegion);
		
		tickerArea.setTranslateX(6);
		tickerArea.translateYProperty().bind(scene.heightProperty().subtract(tickerRect.getHeight() + 6));
		tickerRect.widthProperty().bind(scene.widthProperty().subtract(rightPadding));
		clipRegion.widthProperty().bind(scene.widthProperty().subtract(rightPadding));
		tickerArea.getChildren().add(tickerRect);
		
		FlowPane tickerContent = new FlowPane();
		
		Text news = new Text();
		news.setText("JavaFX 8.0 News! | 85 and sunny | :)");
		news.setFill(Color.WHITE);
		tickerContent.getChildren().add(news);
		
		DoubleProperty centerContentY = new SimpleDoubleProperty();
		centerContentY.bind(clipRegion.heightProperty().divide(2).subtract(tickerContent.heightProperty().divide(2)));
		tickerContent.translateYProperty().bind(centerContentY);
		tickerArea.getChildren().add(tickerContent);
		
		TranslateTransition tickerScroller = new TranslateTransition();
		tickerScroller.setNode(tickerContent);
		tickerScroller.setDuration(Duration.millis(scene.getWidth() * 40));
		tickerScroller.fromXProperty().bind(scene.widthProperty());
		tickerScroller.toXProperty().bind(scene.widthProperty().negate());
		
		tickerScroller.setOnFinished((ActionEvent ev) -> {
			tickerScroller.stop();
			tickerScroller.setDuration(Duration.millis(scene.getWidth() * 40));
			tickerScroller.playFromStart();
		});
		
		stage.setOnShown(windowEvent -> {
			tickerScroller.play();
		});
		return tickerArea;
	}
	
	public static void main(String[] args)
	{
		launch(args);
	}

}
