package application;

import javafx.application.Application;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.Tooltip;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.util.Callback;

public class TableViewExample extends Application {

	@Override
	public void start(Stage primaryStage) throws Exception {
		// TODO Auto-generated method stub
		primaryStage.setTitle("Bosses & Employees");
		BorderPane root = new BorderPane();
		Scene scene = new Scene(root, 500, 250, Color.WHITE);
		
		GridPane gridPane = new GridPane();
		gridPane.setPadding(new Insets(5));
		gridPane.setHgap(10);
		gridPane.setVgap(10);
		root.setCenter(gridPane);
		
		Label candidatesLbl = new Label("Boss");
		GridPane.setHalignment(candidatesLbl, HPos.CENTER);
		gridPane.add(candidatesLbl, 0, 0);
		
		ObservableList<Person> leaders = getPeople();
		final ListView<Person> leadersListView = new ListView<>(leaders);
		leadersListView.setPrefWidth(150);
		leadersListView.setMaxWidth(Double.MAX_VALUE);
		leadersListView.setPrefHeight(150);
		
		leadersListView.setCellFactory(new Callback<ListView<Person>, ListCell<Person>>() {

			@Override
			public ListCell<Person> call(ListView<Person> param) {
				// TODO Auto-generated method stub
				Label leadLbl = new Label();
				Tooltip toolTip = new Tooltip();
				ListCell<Person> cell = new ListCell<Person>() {
					@Override
					public void updateItem(Person item, boolean empty)
					{
						super.updateItem(item, empty);
						if(item != null)
						{
							leadLbl.setText(item.getAliasName());
							setText(item.getFirstName() + " " + item.getLastName());
							toolTip.setText(item.getAliasName());
							setTooltip(toolTip);
						}
					}
				};
				return cell;
			}
			
		});
		
		gridPane.add(leadersListView, 0, 1);
		Label emplLbl = new Label("Employees");
		gridPane.add(emplLbl, 2, 0);
		GridPane.setHalignment(emplLbl, HPos.CENTER);
		
		final TableView<Person> employeeTableView = new TableView<>();
		employeeTableView.setPrefWidth(300);
		
		final ObservableList<Person> teamMembers = FXCollections.observableArrayList();
		employeeTableView.setItems(teamMembers);
		
		TableColumn<Person, String> aliasNameCol = new TableColumn<>("Alias");
		aliasNameCol.setEditable(true);
		aliasNameCol.setCellValueFactory(new PropertyValueFactory<>("aliasName"));
		aliasNameCol.setPrefWidth(employeeTableView.getPrefWidth() / 3);
		
		TableColumn<Person, String> firstNameCol = new TableColumn<>("First Name");
		firstNameCol.setCellValueFactory(new PropertyValueFactory<>("firstName"));
		firstNameCol.setPrefWidth(employeeTableView.getPrefWidth() / 3);
		
		TableColumn<Person, String> lastNameCol = new TableColumn<>("Last Name");
		lastNameCol.setCellValueFactory(new PropertyValueFactory<>("lastName"));
		lastNameCol.setPrefWidth(employeeTableView.getPrefWidth() / 3);
		
		leadersListView.getSelectionModel().selectedItemProperty().addListener((ObservableValue<? extends Person> observable, 
				Person oldValue, Person newValue) -> {
					if(observable != null && observable.getValue() != null)
					{
						teamMembers.clear();
						teamMembers.addAll(observable.getValue().employeeProperty());
					}
				});
		employeeTableView.getColumns().setAll(aliasNameCol, firstNameCol, lastNameCol);
		gridPane.add(employeeTableView, 2, 1);
		primaryStage.setScene(scene);
		primaryStage.show();
	}

	private ObservableList<Person> getPeople() {
		// TODO Auto-generated method stub
		ObservableList<Person> people = FXCollections.<Person>observableArrayList();
		Person docX = new Person("Professor X", "Charles", "Xavier");
		docX.employeeProperty().add(new Person("Wolverine", "James", "Howlett"));
		docX.employeeProperty().add(new Person("Cyclops", "Scott", "Summers"));
		docX.employeeProperty().add(new Person("Storm", "Ororo", "Munroe"));
		
		Person magneto = new Person("Magneto", "Max", "Eisenhardt");
		magneto.employeeProperty().add(new Person("Jedi", "Baga", "Gusdiana"));
		
		Person yoda = new Person("Elder Jedi", "Yoda", "Wombat");
		yoda.employeeProperty().add(new Person("Sith", "Anakin", "Skywalker"));
		people.addAll(docX, magneto, yoda);
		return people;
	}
	
	public static void main(String[] args)
	{
		launch(args);
	}

}
