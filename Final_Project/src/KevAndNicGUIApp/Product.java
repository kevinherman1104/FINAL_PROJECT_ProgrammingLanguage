package KevAndNicGUIApp;
//Import Library That are going to be used
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

//Define an abstract class Product
public abstract class Product {
    //Declare variables for each Product and set as private (Encapsulation)
    private SimpleStringProperty name;
    private SimpleIntegerProperty quantity;
    private SimpleStringProperty price;
    private SimpleStringProperty category;
    private SimpleStringProperty date;

    //Create Constructor of the Product class
    public Product(String name, int quantity, String price, String category, String date)
    {
        //Initialize all the variables to a new simple string property object from the parameter in the constructor
        this.name = new SimpleStringProperty(name);
        this.quantity = new SimpleIntegerProperty(quantity);
        this.price = new SimpleStringProperty(price);
        this.category = new SimpleStringProperty(category);
        this.date = new SimpleStringProperty(date);
    }
//Create Setter and Getter for each Variable of Product Abstract Class
    public String getName() {
        return name.get();
    }

    public SimpleStringProperty nameProperty() {
        return name;
    }

    public void setName(String name) {
        this.name.set(name);
    }

    public int getQuantity() {
        return quantity.get();
    }

    public SimpleIntegerProperty quantityProperty() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity.set(quantity);
    }

    public String getPrice() {
        return price.get();
    }

    public SimpleStringProperty priceProperty() {
        return price;
    }

    public void setPrice(String price) {
        this.price.set(price);
    }

    public String getCategory() {
        return category.get();
    }

    public SimpleStringProperty categoryProperty() {
        return category;
    }

    public void setCategory(String category) {
        this.category.set(category);
    }

    public String getDate() {
        return date.get();
    }

    public SimpleStringProperty dateProperty() {
        return date;
    }

    public void setDate(String date) {
        this.date.set(date);
    }
}
