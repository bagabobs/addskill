package application;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.CheckMenuItem;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.RadioMenuItem;
import javafx.scene.control.SeparatorMenuItem;
import javafx.scene.control.ToggleGroup;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class MenuExample extends Application {

	@Override
	public void start(Stage primaryStage) throws Exception {
		// TODO Auto-generated method stub
		primaryStage.setTitle("Menu Example");
		BorderPane root = new BorderPane();
		Scene scene = new Scene(root, 300, 250, Color.WHITE);
		
		MenuBar menuBar = new MenuBar();
		root.setTop(menuBar);
		
		Menu fileMenu = new Menu("_File");
		MenuItem newMenuItem = new MenuItem("New");
		MenuItem saveMenuItem = new MenuItem("_Save");
		saveMenuItem.setMnemonicParsing(true);
		saveMenuItem.setAccelerator(new KeyCodeCombination(KeyCode.S, KeyCombination.SHORTCUT_DOWN));
		MenuItem exitMenuItem = new MenuItem("Exit");
		exitMenuItem.setOnAction(actionEvent -> Platform.exit());
		
		ContextMenu contextFileMenu = new ContextMenu(exitMenuItem);
		
		primaryStage.addEventHandler(MouseEvent.MOUSE_CLICKED, (MouseEvent me) -> {
			if(me.getButton() == MouseButton.SECONDARY || me.isControlDown())
			{
				contextFileMenu.show(root, me.getScreenX(), me.getScreenY());
			}
			else
			{
				contextFileMenu.hide();
			}
		});
		
		fileMenu.getItems().addAll(newMenuItem, saveMenuItem,  new SeparatorMenuItem(),exitMenuItem);
		
		Menu cameraMenu = new Menu("Cameras");
		CheckMenuItem cam1MenuItem = new CheckMenuItem("Show Camera 1");
		cam1MenuItem.setSelected(true);
		cameraMenu.getItems().add(cam1MenuItem);
		
		CheckMenuItem cam2MenuItem = new CheckMenuItem("Show Camera 2");
		cam2MenuItem.setSelected(true);
		cameraMenu.getItems().add(cam2MenuItem);
		
		Menu alarmMenu = new Menu("Alarm");
		ToggleGroup tGroup = new ToggleGroup();
		RadioMenuItem soundAlarmItem = new RadioMenuItem("Sound Alarm");
		soundAlarmItem.setToggleGroup(tGroup);
		
		RadioMenuItem stopAlarmItem = new RadioMenuItem("Alarm Off");
		stopAlarmItem.setSelected(true);
		stopAlarmItem.setToggleGroup(tGroup);
		
		alarmMenu.getItems().addAll(soundAlarmItem, stopAlarmItem);
		Menu contigencyPlans = new Menu("Contigent Plans");
		contigencyPlans.getItems().addAll(new CheckMenuItem("Self Destruct in T minus 50"),
				new CheckMenuItem("Turn Off The Coffe Machine"), new CheckMenuItem("Run For Your Life!"));

		alarmMenu.getItems().add(contigencyPlans);
		menuBar.getMenus().addAll(fileMenu, cameraMenu, alarmMenu);

		primaryStage.setScene(scene);
		primaryStage.show();
	}
	
	public static void main(String[] args)
	{
		launch(args);
	}

}
