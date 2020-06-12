package KevAndNicGUIApp;
//Import Library That are going to be used
import javafx.beans.property.SimpleStringProperty;


//Declare a new Class for deleted product
public class DeletedMotorCycleProduct extends Product{
    //Create Variables for the deleted items that contains the identity like name, part number, etc and set all as private (Encapsulation)
    private SimpleStringProperty delPartNumber;

    //Create the constructor for the DeletedMotorCycleProduct Class
    public DeletedMotorCycleProduct(String delPartNumber, String name, int quantity, String price, String category, String date) {
        //Inherit Variable name, quantity, price, category and date also the method inside (including setter and getter for each variable)
        super(name, quantity, price, category, date);
        //Initialize the delPartNumber to a new simple string property object that takes partNumber from the constructor parameter
        this.delPartNumber = new SimpleStringProperty(delPartNumber);

    }
//Create the Setter and Getter method for all the variables of DeletedMotorCycleProduct
    public String getDelPartNumber() {
        return delPartNumber.get();
    }

    public SimpleStringProperty delPartNumberProperty() {
        return delPartNumber;
    }

    public void setDelPartNumber(String delPartNumber) {
        this.delPartNumber.set(delPartNumber);
    }

}
