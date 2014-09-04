package application;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Person {
	private StringProperty aliasName;
	private StringProperty firstName;
	private StringProperty lastName;
	private ObservableList<Person> employees = FXCollections.observableArrayList();
	
	public final void setAliasName(String value)
	{
		aliasNameProperty().set(value);
	}
	
	public final String getAliasName()
	{
		return aliasNameProperty().get();
	}
	
	public StringProperty aliasNameProperty()
	{
		if(aliasName == null)
			aliasName = new SimpleStringProperty();
		return aliasName;
	}
	
	public final void setFirstName(String firstName)
	{
		firstNameProperty().set(firstName);
	}
	
	public final String getFirstName()
	{
		return firstNameProperty().get();
	}
	
	public StringProperty firstNameProperty()
	{
		if(firstName == null)
			firstName = new SimpleStringProperty();
		return firstName;
	}
	
	public final void setLastName(String lastName)
	{
		lastNameProperty().set(lastName);
	}
	
	public final String getLastName()
	{
		return lastNameProperty().get();
	}
	
	public StringProperty lastNameProperty()
	{
		if(lastName == null)
			lastName = new SimpleStringProperty();
		return lastName;
	}
	
	public ObservableList<Person> employeeProperty()
	{
		return employees;
	}
	
	public Person(String aliasName, String firstName, String lastName)
	{
		setAliasName(aliasName);
		setFirstName(firstName);
		setLastName(lastName);
	}

}
