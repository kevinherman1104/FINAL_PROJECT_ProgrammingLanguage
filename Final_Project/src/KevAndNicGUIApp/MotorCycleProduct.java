package KevAndNicGUIApp;
//Import Libraries that are going to be used
import javafx.beans.property.SimpleStringProperty;


//Define MotorCycleProduct that extend the Abstract Product Class (Inheritance)
public class MotorCycleProduct extends Product{
    //Declare a new Variable Part Number as private (Encapsulation)
    private SimpleStringProperty partNumber;

    //Declare a constructor for the motorcycleproduct Class which has several parameters inside it
    public MotorCycleProduct(String partNumber, String name, int quantity, String price, String category, String date) {
        //Inherit Variable name, quantity, price, category and date also the method inside (including setter and getter for each variable)
        super(name, quantity, price, category, date);
        //Initialize the PartNumber to a new simple string property object that takes partNumber from the constructor parameter
        this.partNumber = new SimpleStringProperty(partNumber);
    }

// Define Setter Getter for the Part Number variable
    public String getPartNumber() {
        return partNumber.get();
    }

    public SimpleStringProperty partNumberProperty() {
        return partNumber;
    }

    public void setPartNumber(String partNumber) {
        this.partNumber.set(partNumber);
    }
}


