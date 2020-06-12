package KevAndNicGUIApp;
//Library that is Imported
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.Window;
import java.io.*;
import java.net.URL;
import java.time.LocalDate;
import java.util.*;
import java.util.List;

//Declare class controller that controls every element inside the KevAndNic_Gui_App.fxml
//This Class Implements two Interfaces:
//Initializable is an interface used to override the method initialize to put all data and create column for each attributes of the Product
//functionController is an interface used to declare the blue print of each function in the Controller class
public class Controller implements Initializable, functionController {
    //Declare all the elements that are used in KevAndNic_Gui_App.fxml along with each of their fxid
    public Button addButton, delButton, clearItemsButton, clearItemsDeleted ;
    public TextField tfName, tfPartNumber, tfQuantity, tfPrice, tfSearchField;
    public CheckBox cbMachine, cbBody;
    public DatePicker datePicker;
    public TableView tvAllItems, tvDeletedItems;
    public VBox vbMenu;
    public Menu mFile;
    public MenuItem miSave, miLoad, miExit;
    public MenuBar mBar;

    //Create this Observable List to store all the attributes of the product and put it later on inside the all item table view
    ObservableList<MotorCycleProduct> products = FXCollections.observableArrayList();
    //Create this Observable List to store all the attributes of the deleted product and put it later on inside the deleted product table view
    ObservableList<DeletedMotorCycleProduct> deletedProducts= FXCollections.observableArrayList();




    //Create this method to add product to inside the product Observable List
    //This function is synchronized with the addButton I declared above.
    //So, whenever i click the add button this function/method will be called.
    public void addData() {
        //Conditional Statement to check whether every field has been filled or not
        //if there is an empty field the block of code inside this if statement will run
        if (tfPartNumber.getText().isEmpty() || tfName.getText().isEmpty() || tfQuantity.getText().isEmpty() || tfPrice.getText().isEmpty() || datePicker.getEditor().getText().isEmpty()){
            //Call function with warning type that tells the user all Field must be filled.
            warningBox("Warning !", "All Field Must be Filled !");
        }
        //Conditional Statement if part number, name , quantity and price contains comma
        else if(tfPartNumber.getText().contains(",") && tfName.getText().contains(",") && tfQuantity.getText().contains(",") && tfPrice.getText().contains(","))
        {
            warningBox("Warning !", "Cannot Input Comma in All Fields");
            tfPartNumber.clear();
            tfName.clear();
            tfQuantity.clear();
            tfPrice.clear();
        }
        //Conditional Statement if part number, name , quantity or price contains comma
        else if(tfPartNumber.getText().contains(",") || tfName.getText().contains(",") || tfQuantity.getText().contains(",") || tfPrice.getText().contains(",")) {
            warningBox("Warning !", "Cannot Input Comma in All Fields, Check Each Fields Again ! ");
        }

        //If all field has been filled, the program will go straight to this else statement below.
        else
        {
            //Declare variables to store the user input for the partNumber, name, date and category.
            //The purpose is to make me more easier on validating every input and to add them later on to observable list
            String partNumber = tfPartNumber.getText();
            String name = tfName.getText();

            //uses getEditor() method  because the datepicker in fxml uses text field as the editor.
            String date = datePicker.getEditor().getText();

            //This is variable string i created as an empty value for the category of each product
            // i set it to empty string because i will assign a string whenever the user checks one of the category check box
            String category = "";

            //* BLOCK OF CODE TO VALIDATE QUANTITY AND PRICE FIELD
            // This is a block of conditional statement to check the price and quantity that user input.
            //The first if statement is to check whether the quantity and price that the user input is a number or not.
            // if both of them are not than the program will execute block of code inside it.
            if (!isInt(tfQuantity) && !isInt(tfPrice)) {
                //Calls the Function i created in this file to create a warning box that tells user that they input a non numeric value
                warningBox("Warning Fail to Add Product!", " Error : " + tfQuantity.getText() + " in Quantity Field and " + tfPrice.getText() + " in Price Field are Not Numeric");
                //Clears the quantity field
                tfQuantity.clear();
                //Clear the price field
                tfPrice.clear();
            }
            //The else if here is to check whether the quantity that the user input a number or not.
            else if(!isInt(tfQuantity)) {
                //Calls the Function i created in this file to create a warning box that tells user that they input a non numeric value
                warningBox("Warning Fail to Add Product!", " Error : " + tfQuantity.getText() + " in Quantity Field is Not Numeric");
                //Clears the quantity field
                tfQuantity.clear();
            }
            //The else if here is to check whether the price that the user input a number or not.
            else if (!isInt(tfPrice)) {
                //Calls the Function i created in this file to create a warning box that tells user that they input a non numeric value
                warningBox("Warning Fail to Add Product!",  " Error : " + tfPrice.getText() + " in Price Field is Not Numeric");
                //Clears the price field
                tfPrice.clear();
                }
            //*END OF VALIDATION PROCESS


            //Declare variables to store user input for price and quantity after it is being validated by the block of conditional statement above
            Integer quantity = Integer.parseInt(tfQuantity.getText());
            Double price = Double.parseDouble(tfPrice.getText());

            //* BLOCK OF CODE TO VALIDATE THE CHECK BOX FOR CATEGORY
            //The first if is to check if the check box for machine is selected by the user.
            //If yes, it will assign the category value to Machine.
            if(cbMachine.isSelected())
            {
                category = "Machine";
            }
            //The second if is to check if the check box for Body is selected by the user.
            //If yes, it will assign the category value to Machine.
            if (cbBody.isSelected())
            {
                category = "Body";
            }
            //The third if is to check if neither one of the check box is selected
            //If both of them are not selected, the program will show a warning box
            if (!cbMachine.isSelected() && !cbBody.isSelected()) {
               //Calls the Function i created in this file to create a warning box that tells user that they haven't checked
                warningBox("Warning Fail to Add Product!","Error : Check One of The Category Please !");
            }
            //This else if statement is to check if both of the check box is selected
            else if (cbMachine.isSelected() && cbBody.isSelected()) {
                //Calls the Function i created in this file to create a warning box that tells user that they could only check one
                warningBox("Warning Fail to Add Product!", "Error : Please Choose Only One Category !");
                //clears the tick mark in both of the check box
                cbMachine.setSelected(false);
                cbBody.setSelected(false);
            }
            //*END OF CHECK BOX VALIDATION


            //After all of the field has been validated, the program will go to this block of else statement.
            else {
                //Checks whether user inputs 0 in both quantity and price field
                if(quantity == 0 && price == 0)
                {
                    //Calls the Function i created in this file to create an error box that tells user that they cannot input 0 in those fields.
                    errorBox("Warning Fail to Add Product!", "Error Info : You cannot input 0 in Price  and Quantity Field !");
                    //Clears the price and quantity field
                    tfPrice.clear();
                    tfQuantity.clear();
                }
                //Checks if the user input 0 in quantity field
                else if (quantity == 0) {
                    //Calls the Function i created in this file to create an error box that tells user that they cannot input 0 in quantity fields.
                    errorBox("Warning Fail to Add Product!", "Error Info : You cannot input 0 in Quantity Field !");
                    //Clears the quantity fields
                    tfQuantity.clear();
                }
                //Checks if the user input 0 in price field
                else if (price == 0)
                {
                    //Calls the Function i created in this file to create an error box that tells user that they cannot input 0 in price fields.
                    errorBox("Warning Fail to Add Product!", "Error Info: You cannot input 0 in Price Field !");
                    //Clears the price field
                    tfPrice.clear();
                }

                //If the price and the quantity field has been validated the program runs the else block below
                else
                {
                    //Checks whether the part number already used for other products.
                    //to prevent collision between products part number
                    if(isUnique(tfPartNumber))
                    {
                        //Adds the User input that has been validated to an observable array list named products
                        //Format the price to a string anf uses rupiah at the beginning of every price
                        products.add(new MotorCycleProduct(partNumber, name, quantity, String.valueOf(String.format("Rp. %.2f", price)), category, date));
                        //Clears all field including check box after adding the data
                        tfName.clear();
                        tfPartNumber.clear();
                        tfPrice.clear();
                        tfQuantity.clear();
                        cbBody.setSelected(false);
                        cbMachine.setSelected(false);

                        //Show a Success message that tells the user data has been added to the all items table.
                        Alert addSuccess = new Alert(Alert.AlertType.INFORMATION);
                        addSuccess.setTitle("Success Add Process !");
                        addSuccess.setHeaderText("Product has been Added");
                        addSuccess.setContentText("Product has been successfully Added to All Items Table!");
                        setIcon(addSuccess);
                        addSuccess.show();
                    }
                    //Otherwise, if the part number already exist in the table view
                    //the program will show warning box
                    else
                    {
                        //Calls the Function i created to tell the user that the part number must be unique
                        warningBox("Warning Fail To Add product !", "Error info: Part Number already exist, Part Number Must be Unique!");
                        //Clears the part number field
                        tfPartNumber.clear();
                    }
                }
            }
        }
    }

    //Declare the Function that sync to delete button which Delete the data from the table view
    //And add it inside another table view that contains list of deleted items.
    public void delData() {
        //Conditional statement to check whether the table view is empty.
        if (tvAllItems.getItems().isEmpty()) {
            //If it is empty, the program will show a warning box telling the user that nothing to delete in the table.
            warningBox("Warning !","Nothing to Delete in The Table" );

        }
        else if(tvAllItems.getSelectionModel().getSelectedItem() == null)
        {
            warningBox("Warning !","Please Select One of the Product in the All items table view in order to delete!");
        }
        else {

            //Declare 2 observable list
            //One contains product that the user select (productSelected)
            //Another one contains product all products(allProducts)
            ObservableList<MotorCycleProduct> productSelected, allProducts;
            //Assign the all items table view elements to allProducts observable array list.
            allProducts = tvAllItems.getItems();
            //Assign the product that the user select in one of the row.
            //Takes all the elements inside the selected row and put inside the productSelected observable array list
            productSelected = tvAllItems.getSelectionModel().getSelectedItems();

            //Declare variables to contain the Part number, Name, Quantity, Price, Category and Date of the Product that is going to be delete.
            //Gets the part number, name, etc. from the observable list (productSelected)
            //Gets the index 0 means takes only the first product that is selected (You cannot select more than one).
            String deletedPartNumber = productSelected.get(0).getPartNumber();
            String deletedName = productSelected.get(0).getName();
            //For the Quantity i set to 0 so that later on i will assign based on how much the user want to delete
            Integer deletedQuantity = 0;
            String deletedPrice = productSelected.get(0).getPrice();
            String deletedCategory = productSelected.get(0).getCategory();
            //for the date i set it to the present day or i can say the day the user opens the app
            //assuming they delete the data today
            String deletedDate = LocalDate.now().toString();


            //I create another alert box that asks the user to choose delete all or delete by quantity
            Alert alertToDel = new Alert(Alert.AlertType.CONFIRMATION);
            //Set the alert title, header and the message inside it
            alertToDel.setTitle("Delete Form ");
            alertToDel.setHeaderText("Delete Form Please Choose");
            alertToDel.setContentText("Choose One Of The Option Below.");
            setIcon(alertToDel);
            //Defines a modal window that blocks events from being delivered to any other application window.
            alertToDel.initModality(Modality.APPLICATION_MODAL);

            //Declare Button types for delete by all, delete by quantity and cancel also set the text inside the button
            ButtonType buttonTypeDelALl = new ButtonType("Delete All !");
            ButtonType buttonTypeDelByQty = new ButtonType("Delete by Quantity");
            //Sets the cancel Button type for buttonTypeCancel to a cancel_close type (to close the alert box)
            ButtonType buttonTypeCancel = new ButtonType("Cancel", ButtonBar.ButtonData.CANCEL_CLOSE);
            //Adds the button type to the alert box.
            alertToDel.getButtonTypes().setAll(buttonTypeDelALl, buttonTypeDelByQty, buttonTypeCancel);

            //Created a new Optional container to get the answer of the user if they choose delete all, deletebyquantity or cancel
            //assign the optional container(takes Button types as object) to the alert box i created above
            //set the confirm box to show and wait for the user to choose
            Optional<ButtonType> resultToDel = alertToDel.showAndWait();

            //If the user chooses delete all, this block of code will be executed.
            if (resultToDel.get() == buttonTypeDelALl){
                //Create an alert confirmation box that ask the user are they sure to delete all the products.
                Alert confirmDeleteAll = new Alert(Alert.AlertType.CONFIRMATION);
                //Sets the confirmation box title, header text and message that ask the user are they sure to delete all.
                confirmDeleteAll.setTitle("Confirmation to Delete ");
                confirmDeleteAll.setHeaderText("Confirmation of Deleting All Items");
                confirmDeleteAll.setContentText("Are you sure to delete all items ?");
                setIcon(confirmDeleteAll);
               // Defines a modal window that blocks events from being delivered to any other application window.
                confirmDeleteAll.initModality(Modality.APPLICATION_MODAL);


                //Create another Optional container to get the user answer ok or cancelsp
                //assign the optional container (takes Button types Object) to the confirmation box
                //set the confirmation box to show and wait for the user answer.
                Optional<ButtonType> confirmationByUser = confirmDeleteAll.showAndWait();

                //if the user chooses ok the block of code inside will be executed
                if (confirmationByUser.get() == ButtonType.OK) {
                    //user chose OK
                    //Assign the deleted quantity from 0 to the number of quantity the product has.
                    deletedQuantity = productSelected.get(0).getQuantity();
                    //Do a for each loop inside the observable array list of selected product and delete the item from the all item table
                    productSelected.forEach(allProducts::remove);
                    //Put the deleted product identities to an observable array list containing deleted products
                    deletedProducts.add(new DeletedMotorCycleProduct(deletedPartNumber, deletedName, deletedQuantity, deletedPrice, deletedCategory, deletedDate));
                    //Show another alert box information tells the user the product has been successfully deleted
                    // and put inside the deleted items table view.
                    Alert deleteSuccess = new Alert(Alert.AlertType.INFORMATION);
                    //Sets the title, header and message of the information box
                    deleteSuccess.setTitle("Success Delete Process!");
                    deleteSuccess.setHeaderText("Product Has Been Deleted !");
                    deleteSuccess.setContentText(deletedName + " Has Been Successfully Deleted Permanently! \nYou Can Check The Deleted Items Table !");
                    setIcon(deleteSuccess);
                    deleteSuccess.show();
                }
                //If the user chooses cancel, the confirmation box will be closed immediately
                else {
                        confirmDeleteAll.close();
                        // ... user chose CANCEL or close the dialog
                    }
            //If the user chooses deleteByQuantity
            } else if (resultToDel.get() == buttonTypeDelByQty) {
                //Declare an integer variable and assign the selected product quantity
                int quantityOfProductSelected = productSelected.get(0).getQuantity();
                //Declare an integer variable and set the value as 0
                int newProductQuantity = 0;
                //Declare an array list that contains Integer
                List<Integer> quantityChoices = new ArrayList<>();
                //Do a for loop to store quantities from 1 to the number of quantity a product has.
                for (int i = 1; i < quantityOfProductSelected; i++) {
                    //add all the quantity inside the list
                    quantityChoices.add(i);
                }

                //Add a Choice Dialog and assign the list quantityChoice above to the Choice Dialog also set the default choice to 1
                ChoiceDialog<Integer> deleteDialog = new ChoiceDialog<>(1, quantityChoices);
                //Sets the title, header, message for the choice dialog
                deleteDialog.setTitle("Delete Form (by Quantity)");
                deleteDialog.setHeaderText("Choose how many " + productSelected.get(0).getName() + " you want to remove !");
                deleteDialog.setContentText("Choose how many:");
                setIcon(deleteDialog);
                //Defines a modal window that blocks events from being delivered to any other application window.
                deleteDialog.initModality(Modality.APPLICATION_MODAL);

                //add another optional container that contains the user answer
                //assign the delete dialog(optional dialog) and set to show and wwait for the user answer.
                Optional<Integer> delResult = deleteDialog.showAndWait();
                //if the user clicks ok button, the block of code inside will be executed.
                if (delResult.isPresent()) {
                    //assign the deletedQuantity to the number that the user choose in the choice dialog
                    deletedQuantity = delResult.get();
                    //assign the newProductQuantity to the quantity of the product and the number of quantity the user chooses to delete.
                    newProductQuantity = quantityOfProductSelected - delResult.get();
                    //If the result of the subtraction is - or < 0 the error box will appear on the screen
                    if(newProductQuantity < 0)
                    {
                        errorBox("Warning Fail Delete!", "Error : You only have " + productSelected.get(0).getQuantity());
                    }
                    //Otherwise, this block of code will be executed.
                    else {
                        //set the product selected quantity to a new quantity after being deleted previously
                        productSelected.get(0).setQuantity(newProductQuantity);
                        //adds the part number, name ,etc of the product to an observable array list containing deleted products
                        deletedProducts.add(new DeletedMotorCycleProduct(deletedPartNumber,deletedName,deletedQuantity,deletedPrice,deletedCategory,deletedDate));
                        //Shows an Information dialog says the delete process iss success
                        Alert deleteSuccess = new Alert(Alert.AlertType.INFORMATION);
                        //set the title, header and message for the information box
                        deleteSuccess.setTitle("Success Delete Process!");
                        deleteSuccess.setHeaderText("Product has been Deleted !");
                        deleteSuccess.setContentText(deletedQuantity + " pcs of " + deletedName + " Has Been Successfully Deleted ! \nYou Can Check The Deleted Items Table !");
                        setIcon(deleteSuccess);
                        deleteSuccess.show();
                    }
                }

            }
            //Close the Dialog to delete if the user chooses cancel
            else {
               alertToDel.close();
            }
        }
    }

    //Declare the Function to Update data inside the main table view(All items table view).
    public void updateData() {
        //If the user press the update button with no item inside the table a warning box will appear
        if (tvAllItems.getItems().isEmpty()) {
            warningBox("Warning !","Nothing to Update in The Table" );

        }
        else if(tvAllItems.getSelectionModel().getSelectedItem() == null)
        {
            warningBox("Warning !","Please select one of the product in the All items table in order to update!");
        }
        else {
            //Declare an Observable Array List that contains selected product
            ObservableList<MotorCycleProduct> productSelected;
            productSelected = tvAllItems.getSelectionModel().getSelectedItems();
            // Create the update dialog.
            Dialog<ButtonType> updateDialog = new Dialog<>();
            updateDialog.setTitle("Update Form for " + productSelected.get(0).getName() + " Product");
            updateDialog.setHeaderText("Please Input Fields Below to Update !");
            setIcon(updateDialog);
            //Defines a modal window that blocks events from being delivered to any other application window.
            updateDialog.initModality(Modality.APPLICATION_MODAL);


            // Set the button types.
            ButtonType updateButton = new ButtonType("Update", ButtonBar.ButtonData.OK_DONE);
            updateDialog.getDialogPane().getButtonTypes().addAll(updateButton, ButtonType.CANCEL);


            // Create the a grid pane for the update form.
            GridPane grid = new GridPane();
            grid.setHgap(10);
            grid.setVgap(10);
            grid.setPadding(new Insets(20, 150, 10, 10));

            //Declare Text Field, Date Picker and check box for each Products identity.
            //Sets the every text field, date picker and check box based on the product selected identities.
            TextField newPartNumber = new TextField();
            newPartNumber.setText(productSelected.get(0).getPartNumber());

            TextField newName = new TextField();
            newName.setText(productSelected.get(0).getName());

            TextField newQuantity = new TextField();
            newQuantity.setText(String.valueOf(productSelected.get(0).getQuantity()));

            TextField newPrice = new TextField();
            //set the prompt text of the newPrice
            newPrice.setPromptText(productSelected.get(0).getPrice() + "(Input without commas or dot)");

            DatePicker newDate = new DatePicker();
            newDate.getEditor().setText(productSelected.get(0).getDate());
            //Sets the Datepicker's picker to disable future dates
            newDate.setDayCellFactory(picker -> new DateCell() {
                @Override
                //Overrides the update item from Local Date Class
                //Takes to parameter date and empty
                public void updateItem(LocalDate date, boolean empty) {
                    //Inherits the update item
                    super.updateItem(date, empty);
                    //Set Local date as today
                    LocalDate today = LocalDate.now();
                    //Disables Future Dates from today (in date.compareTo(today) > 0)
                    setDisable(empty || date.compareTo(today) > 0);
                }
            });
            //sets the Editor of the Date picker to uneditable
            newDate.setEditable(false);


            CheckBox cbUpdateBody = new CheckBox();
            //Sets the text of the check box
            cbUpdateBody.setText("Body");
            CheckBox cbUpdateMachine = new CheckBox();
            //Sets the text of the check box
            cbUpdateMachine.setText("Machine");

            //Block of code to Check the Body Check box if the selected product's category equals to Body
            if (productSelected.get(0).getCategory() == "Body") {
                cbUpdateBody.setSelected(true);
            }
            //Otherwise, Check the Machine Check box if the selected product's category equals to Machine
            else if (productSelected.get(0).getCategory() == "Machine") {
                cbUpdateMachine.setSelected(true);
            }

            //adds all the element inside the gridpane
            grid.add(new Label("New Part Number:"), 0, 0);
            grid.add(newPartNumber, 1, 0);

            grid.add(new Label("New Name:"), 0, 1);
            grid.add(newName, 1, 1);

            grid.add(new Label("New Quantity:"), 0, 2);
            grid.add(newQuantity, 1, 2);

            grid.add(new Label("New Price:"), 0, 3);
            grid.add(newPrice, 1, 3);

            grid.add(new Label("New Date:"), 0, 4);
            grid.add(newDate, 1, 4);

            grid.add(new Label("New Category:"), 0, 5);
            grid.add(cbUpdateBody, 1, 5);
            grid.add(cbUpdateMachine, 2, 5);


            // Get the Stage.
            setIcon(updateDialog);

            //set the dialog pane to the grid pane i created above
            updateDialog.getDialogPane().setContent(grid);

            //Another Container to get the user answer from the update dialog
            Optional<ButtonType> resultUpdate = updateDialog.showAndWait();
            if(resultUpdate.isPresent())
            {
                //if the user press the update button
                if(resultUpdate.get() == updateButton)
                {
                    //Conditional Statement to check whether every field has been filled or not
                    //if there is an empty field the block of code inside this if statement will run
                    if (newPartNumber.getText().isEmpty() || newName.getText().isEmpty() || newQuantity.getText().isEmpty() || newPrice.getText().isEmpty() || newDate.getEditor().getText().isEmpty()) {
                        //Call function with warning type that tells the user all Field must be filled.
                        warningBox("Warning Fail Update !", "Error : All Field Must Be Filled !");
                    }
                    //Conditional Statement if part number, name , quantity and price contains comma
                    else if(newPartNumber.getText().contains(",") && newName.getText().contains(",") && newQuantity.getText().contains(",") && newPrice.getText().contains(","))
                    {
                        warningBox("Warning !", "Cannot Input Comma in All Fields");
                        newPartNumber.clear();
                        newName.clear();
                        newQuantity.clear();
                        newPrice.clear();
                    }
                    //Conditional Statement if part number, name , quantity or price contains comma
                    else if(newPartNumber.getText().contains(",") || newName.getText().contains(",") || newQuantity.getText().contains(",") || newPrice.getText().contains(",")) {
                        warningBox("Warning !", "Cannot Input Comma in All Fields, Check Each Fields Again ! ");
                    }
                    //If all field has been filled, the program will go straight to this else statement below.
                    else {
                        //Declare variables to store the user input for the partNumber, name, date and category.
                        //The purpose is to make me more easier on validating every input and to add them later on to the table
                        String updatedPartNumber = newPartNumber.getText();
                        String updatedName = newName.getText();
                        String updatedDate = newDate.getEditor().getText();
                        String updatedCategory = "";

                        //* BLOCK OF CODE TO VALIDATE QUANTITY AND PRICE FIELD
                        // This is a block of conditional statement to check the price and quantity that user input.
                        //The first if statement is to check whether the quantity and price that the user input is a number or not.
                        // if both of them are not than the program will execute block of code inside it.
                        if (!isInt(newQuantity) && !isInt(newPrice)) {
                            //Calls the Function i created in this file to create a warning box that tells user that they input a non numeric value
                            warningBox("Warning Fail Update!", "Error : " + newQuantity.getText() + " in Quantity Field and " + newPrice.getText() + " in Price Field are Not Numeric");
                            //Clears the quantity field
                            newQuantity.clear();
                            //Clear the price field
                            newPrice.clear();
                            //The else if here is to check whether the quantity that the user input a number or not.
                        }
                        //The else if here is to check whether the quantity that the user input a number or not.
                        else if (!isInt(newQuantity)) {
                            //Calls the Function i created in this file to create a warning box that tells user that they input a non numeric value
                            warningBox("Warning Fail Update!", "Error : " + newQuantity.getText() + " in Quantity Field is Not Numeric" );
                            //Clears the quantity field
                            newQuantity.clear();
                        }
                        //The else if here is to check whether the price that the user input a number or not.
                        else if (!isInt(newPrice)) {
                            //Calls the Function i created in this file to create a warning box that tells user that they input a non numeric value
                            warningBox("Warning Fail Update!", "Error : " +newPrice.getText() + " in Price Field is Not Numeric");
                            //Clears the price field
                            newPrice.clear();

                        }
                        //*END OF VALIDATION PROCESS

                        //Declare variables to store user input for price and quantity after it is being validated by the block of conditional statement above
                        Integer updatedQuantity = Integer.parseInt(newQuantity.getText());
                        Double updatedPrice = Double.parseDouble(newPrice.getText());


                        //* BLOCK OF CODE TO VALIDATE THE CHECK BOX FOR CATEGORY
                        //The first if is to check if the check box for machine is selected by the user.
                        //If yes, it will assign the category value to Machine.
                        if (cbUpdateMachine.isSelected()) {
                            updatedCategory = "Machine";
                        }
                        //The second if is to check if the check box for Body is selected by the user.
                        //If yes, it will assign the category value to Machine.
                        if (cbUpdateBody.isSelected()) {
                            updatedCategory = "Body";
                        }

                        //Conditional statement to check whether any of the textfield, datepicker and checkbox is empty or not
                        //If empty the program will show a warning box that tells the user that everything must be filled in order to add.
                        if (!cbUpdateMachine.isSelected() && !cbUpdateBody.isSelected()) {
                            warningBox("Warning Fail Update !", "Error : Check One of The Category Please !");
                        } else if (cbUpdateMachine.isSelected() && cbUpdateBody.isSelected()) {
                            warningBox("Warning Fail Update!", "Error : Please Choose Only One Category !");
                            cbUpdateBody.setSelected(false);
                            cbUpdateMachine.setSelected(false);
                        }
                        //*END OF CHECK BOX VALIDATION

                        //After all of the field has been validated, the program will go to this block of else statement.
                        else {
                            //Checks whether user inputs 0 in both quantity and price field
                            if (updatedQuantity == 0 || updatedPrice == 0) {
                                //Calls the Function i created in this file to create an error box that tells user that they cannot input 0 in those fields.
                                errorBox("Warning Fail Update!", "Error Info: You cannot input 0 in Price and Quantity Field");
                                //sets the price and quantity field to the previous value
                                newPrice.setText(String.valueOf(productSelected.get(0).getPrice()));
                                newQuantity.setText(String.valueOf(productSelected.get(0).getQuantity()));

                            }
                            //Checks if the user input 0 in quantity field
                            else if (updatedQuantity == 0) {
                                //Calls the Function i created in this file to create an error box that tells user that they cannot input 0 in quantity fields.
                                errorBox("Warning Fail Update!", "Error Info: You cannot input 0 in Quantity Field");
                                //sets the quantity field to the previous value
                                newQuantity.setText(String.valueOf(productSelected.get(0).getQuantity()));

                            }
                            //Checks if the user input 0 in price field
                            else if (updatedPrice == 0) {
                                //Calls the Function i created in this file to create an error box that tells user that they cannot input 0 in price fields.
                                errorBox("Warning Fail Update!", "Error Info: You cannot input 0 in Price Field");
                                //sets the price field to the previous value
                                newPrice.setText(String.valueOf(productSelected.get(0).getPrice()));
                            }
                            //If the price and the quantity field has been validated the program runs the else block below
                            else
                            {
                                //Checks whether the part number already used for other products.
                                //to prevent collision between products part number
                                if(newPartNumber.getText().equals(productSelected.get(0).getPartNumber())) {
                                    //sets the User input that has been validated
                                    //Format the price to a string anf uses rupiah at the beginning of every price
                                    setUpdatedProduct(productSelected, updatedPartNumber, updatedName, updatedQuantity, updatedPrice, updatedCategory, updatedDate);

                                    //Show a Success message that tells the user data has been updated.
                                    Alert updateSuccess = new Alert(Alert.AlertType.INFORMATION);
                                    updateSuccess.setTitle("Success Update Process !");
                                    updateSuccess.setHeaderText("Product Information Has Been Updated !");
                                    updateSuccess.setContentText("Product Has Been Successfully Updated ! \nCheck The All Items Table !");
                                    setIcon(updateSuccess);
                                    updateSuccess.show();
                                }
                                else if(!newPartNumber.getText().equals(productSelected.get(0).getPartNumber()))
                                {
                                    if(isUnique(newPartNumber))
                                    {
                                        setUpdatedProduct(productSelected, updatedPartNumber, updatedName, updatedQuantity, updatedPrice, updatedCategory, updatedDate);
                                        //Show a Success message that tells the user data has been updated.
                                        Alert updateSuccess = new Alert(Alert.AlertType.INFORMATION);
                                        updateSuccess.setTitle("Success Update Process !");
                                        updateSuccess.setHeaderText("Product Information Has Been Updated !");
                                        updateSuccess.setContentText("Product Has Been Successfully Updated ! \nCheck The All Items Table !");
                                        setIcon(updateSuccess);
                                        updateSuccess.show();

                                    }
                                    //Otherwise, if the part number already exist in the table view
                                    //the program will show warning box
                                    else {
                                        //Calls the Function i created to tell the user that the part number must be unique
                                        warningBox("Warning Fail Update!", "Error Info: Part Number Already Exist, New Part Number must be Unique");
                                    }

                                }
                            }
                        }
                    }
                }
            }
        }
    }

    //void function to set update product identity
    public void setUpdatedProduct(ObservableList<MotorCycleProduct> productSelected, String updatedPartNumber,String updatedName, Integer updatedQuantity, Double updatedPrice, String updatedCategory, String updatedDate)
    {
        productSelected.get(0).setPartNumber(updatedPartNumber);
        productSelected.get(0).setName(updatedName);
        productSelected.get(0).setQuantity(updatedQuantity);
        productSelected.get(0).setPrice(String.valueOf(String.format("Rp. %.2f", updatedPrice)));
        productSelected.get(0).setDate(updatedDate);
        productSelected.get(0).setCategory(updatedCategory);
    }
    //Declare a boolean Function to test whether the input is an integer/double.
    //It takes two parameter Textfield and a String.
    public boolean isInt(TextField input) {
        //Using Exception handling try catch to validate the input of a text field
        try {
            Integer.parseInt(input.getText());
            //return true if the parse process is success
            return true;

        } catch (NumberFormatException exception) {
            //return false if there is an error while parsing
            return false;
        }
    }

    public void setIcon(Dialog dialog)
    {
        // Get the Stage.
        Stage stage = (Stage) dialog.getDialogPane().getScene().getWindow();
        // Add a custom icon.
        stage.getIcons().add(new Image(this.getClass().getResource("icon.png").toString()));
    }

    //Declare Void Function to Set the Alert box to handle every warning (The purpose of this is to prevent Writing the same code for every warning)
    //It has two parameter the title as the title of the alert box and message as the message inside it to inform warning state to the user.
    public void warningBox(String title, String message) {
        //Declare the Alert and the type
        Alert a = new Alert(Alert.AlertType.WARNING);
        //sets the title of the alert from the parameter passed
        a.setTitle(title);
        //sets the message of the alert from the parameter passed
        a.setContentText(message);
        //call setIcon function to set the icon of the alert box
        setIcon(a);
        a.show();
    }

    //Declare Void Function to Set the Alert box to handle every error while input (The purpose of this is to prevent Writing the same code for every error)
    //It has two parameter the title as the title of the alert box and message as the message inside it to inform error state to the user.
    public void errorBox(String title, String message)
    {
        //Declare the Alert and the type
        Alert a = new Alert(Alert.AlertType.ERROR);
        //sets the title of the alert from the parameter passed
        a.setTitle(title);
        //sets the message of the alert from the parameter passed
        a.setContentText(message);
        //call setIcon function to set the icon of the alert box
        setIcon(a);
        a.show();
    }

    //Declare a void function that syncs with the clear all button which will clear all the data in the all item tableview
    public void clearAllItemsTable()
    {
        Alert confirmClearAllItems = new Alert(Alert.AlertType.CONFIRMATION);
        //Sets the confirmation box title, header text and message that ask the user are they sure to clear all.
        confirmClearAllItems.setTitle("Confirmation to Clear Table");
        confirmClearAllItems.setHeaderText("Confirmation of Clear All Items Table");
        confirmClearAllItems.setContentText("Are you sure to clear all items ? YOU CANNOT UNDO THIS !");
        setIcon(confirmClearAllItems);
        // Defines a modal window that blocks events from being delivered to any other application window.
        confirmClearAllItems.initModality(Modality.APPLICATION_MODAL);


        //Create another Optional container to get the user answer ok or cancel
        //assign the optional container (takes Button types Object) to the confirmation box
        //set the confirmation box to show and wait for the user answer.
        Optional<ButtonType> confirmationByUser = confirmClearAllItems.showAndWait();

        //if the user chooses ok the block of code inside will be executed
        if (confirmationByUser.get() == ButtonType.OK) {
            tvAllItems.getItems().clear();
            Alert deleteSuccess = new Alert(Alert.AlertType.INFORMATION);
            //Sets the title, header and message of the information box
            deleteSuccess.setTitle("Success Clear Process!");
            deleteSuccess.setHeaderText("Deleted Items Table elements Has Been Deleted !");
            deleteSuccess.setContentText("All Products in All Item Table Has Been Successfully Deleted Permanently!");
            setIcon(deleteSuccess);
            deleteSuccess.show();
        }
        //If the user chooses cancel, the confirmation box will be closed immediately
        else {
            confirmClearAllItems.close();
            // ... user chose CANCEL or close the dialog
        }

    }

    //Declare a void function that syncs with the clear all button which will clear all the data in the deleted item tableview
    public void clearDeletedItemsTable()
    {
        Alert confirmClearDeletedItems = new Alert(Alert.AlertType.CONFIRMATION);
        //Sets the confirmation box title, header text and message that ask the user are they sure to clear all.
        confirmClearDeletedItems.setTitle("Confirmation to Clear Table");
        confirmClearDeletedItems.setHeaderText("Confirmation of Clear Deleted Items Table");
        confirmClearDeletedItems.setContentText("Are you sure to clear all products ? YOU CANNOT UNDO THIS !");
        setIcon(confirmClearDeletedItems);
        // Defines a modal window that blocks events from being delivered to any other application window.
        confirmClearDeletedItems.initModality(Modality.APPLICATION_MODAL);


        //Create another Optional container to get the user answer ok or cancel
        //assign the optional container (takes Button types Object) to the confirmation box
        //set the confirmation box to show and wait for the user answer.
        Optional<ButtonType> confirmationByUser = confirmClearDeletedItems.showAndWait();

        //if the user chooses ok the block of code inside will be executed
        if (confirmationByUser.get() == ButtonType.OK) {
            tvDeletedItems.getItems().clear();
            Alert deleteSuccess = new Alert(Alert.AlertType.INFORMATION);
            //Sets the title, header and message of the information box
            deleteSuccess.setTitle("Success Clear Process!");
            deleteSuccess.setHeaderText("Deleted Items Table elements Has Been Deleted !");
            deleteSuccess.setContentText("All Products in Deleted Item Table Has Been Successfully Deleted Permanently!");
            setIcon(deleteSuccess);
            deleteSuccess.show();
        }
        //If the user chooses cancel, the confirmation box will be closed immediately
        else {
            confirmClearDeletedItems.close();
            // ... user chose CANCEL or close the dialog
        }

    }

    //Declare a boolean function to test whether the part number is unique or not.
    //The function takes 1 parameter Text field that check the field for the part number.
    public boolean isUnique(TextField inputPartNumber)
    {
        //For each loop to check every product inside products observable array list.
        for(MotorCycleProduct product:products)
        {
            //if the part number inputted by the user exist in the observable list the function returns false
            if(product.getPartNumber().equals(inputPartNumber.getText()) )
            {
                return false;
            }
        }
        //returns true by default
        return true;
    }

    //Declare a new File chooser object
    FileChooser fileChooser = new FileChooser();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        //sets the file chooser initial directory
        fileChooser.setInitialDirectory(new File("C:\\Users\\ASUS\\IdeaProjects\\Final_Project\\Files"));

        //Set the table view for deleted items to uneditable
        tvDeletedItems.setSelectionModel(null);

        //Set the all item table view selection mode to only single row each time it is clicked.
        //cannot select multiple rows.
        tvAllItems.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);

        //Sets the date picker's picker disable for future dates.
        datePicker.setDayCellFactory(picker -> new DateCell()
        {
            @Override
            public void updateItem(LocalDate date, boolean empty) {
                super.updateItem(date, empty);
                LocalDate today = LocalDate.now();

                setDisable(empty || date.compareTo(today) > 0);
            }
        });

        //Creates column for each identity of the products
        //sets the name, column width, and where to take the data from
        TableColumn partNumberCol = new TableColumn("Part Number");
        partNumberCol.setMinWidth(50);
        partNumberCol.setCellValueFactory(
                //sets from what class to take the data from and what is the type of data
                new PropertyValueFactory<MotorCycleProduct, String>("partNumber"));

        TableColumn nameCol = new TableColumn("Product Name");
        nameCol.setMinWidth(100);
        nameCol.setCellValueFactory(
                //sets from what class to take the data from and what is the type of data
                new PropertyValueFactory<MotorCycleProduct, String>("name"));

        TableColumn quantityCol = new TableColumn("Quantity (pcs)");
        quantityCol.setMinWidth(50);
        quantityCol.setCellValueFactory(
                //sets from what class to take the data from and what is the type of data
                new PropertyValueFactory<MotorCycleProduct, Integer>("quantity"));

        TableColumn priceCol = new TableColumn("Price (in Rp)");
        priceCol.setMinWidth(60);
        priceCol.setCellValueFactory(
                //sets from what class to take the data from and what is the type of data
                new PropertyValueFactory<MotorCycleProduct, Double>("price"));

        TableColumn categoryCol = new TableColumn("Category");
        categoryCol.setMinWidth(100);
        categoryCol.setCellValueFactory(
                //sets from what class to take the data from and what is the type of data
                new PropertyValueFactory<MotorCycleProduct, String>("category"));

        TableColumn dateCol = new TableColumn("Date");
        dateCol.setMinWidth(100);
        dateCol.setCellValueFactory(
                //sets from what class to take the data from and what is the type of data
                new PropertyValueFactory<MotorCycleProduct, String>("date"));

        //sets the item of tvAllItems table view from the observable array list products
        tvAllItems.setItems(products);

        //gets all the columns of each products identity that we created above.
        tvAllItems.getColumns().addAll(partNumberCol, nameCol, quantityCol, priceCol, categoryCol, dateCol);



        //Creates column for each identity of the deleted products
        //sets the name, column width, and where to take the data from
        TableColumn delPartNumberCol = new TableColumn("Part Number");
        delPartNumberCol.setMinWidth(100);
        delPartNumberCol.setCellValueFactory(
                //sets from what class to take the data from and what is the type of data
                new PropertyValueFactory<DeletedMotorCycleProduct, String>("delPartNumber"));

        TableColumn delNameCol = new TableColumn("Product Name");
        delNameCol.setMinWidth(100);
        delNameCol.setCellValueFactory(
                //sets from what class to take the data from and what is the type of data
                new PropertyValueFactory<DeletedMotorCycleProduct, String>("name"));

        TableColumn delQuantityCol = new TableColumn("Quantity (pcs)");
        delQuantityCol.setMinWidth(100);
        delQuantityCol.setCellValueFactory(
                //sets from what class to take the data from and what is the type of data
                new PropertyValueFactory<DeletedMotorCycleProduct, Integer>("quantity"));

        TableColumn delPriceCol = new TableColumn("Price (in Rp)");
        delPriceCol.setMinWidth(100);
        delPriceCol.setCellValueFactory(
                //sets from what class to take the data from and what is the type of data
                new PropertyValueFactory<DeletedMotorCycleProduct, Double>("price"));

        TableColumn delCategoryCol = new TableColumn("Category");
        delCategoryCol.setMinWidth(100);
        delCategoryCol.setCellValueFactory(
                //sets from what class to take the data from and what is the type of data
                new PropertyValueFactory<DeletedMotorCycleProduct, String>("category"));

        TableColumn delDateCol = new TableColumn("Date Deleted");
        delDateCol.setMinWidth(100);
        delDateCol.setCellValueFactory(
                //sets from what class to take the data from and what is the type of data
                new PropertyValueFactory<DeletedMotorCycleProduct, String>("date"));

        //sets the item of tvDeletedItems table view from the observable array list deletedProdcuts
        tvDeletedItems.setItems(deletedProducts);

        //gets all the columns of each deleted products identity that we created above.
        tvDeletedItems.getColumns().addAll(delPartNumberCol, delNameCol, delQuantityCol, delPriceCol, delCategoryCol, delDateCol);
    }


    // Wrap the ObservableList products in a FilteredList (initially display all data).
    FilteredList<MotorCycleProduct> productFilteredList = new FilteredList<>(products, b -> true);
    //Declare a void function for the search bar text field to sort the table list based on the text inside.
    //This helps the user to find a product if there are a lot of products later on.
    //The search is by name
    public void search()
    {
        //gets the search field and add listener
        tfSearchField.textProperty().addListener((observable, oldValue, newValue) -> {
            //Set the filter Predicate whenever the filter changes.
            productFilteredList.setPredicate(product -> {

                // If filter text is empty, display all products
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }

                // set the search box input to lowercase
                String lowerCaseFilter = newValue.toLowerCase();

                if (product.getName().toLowerCase().indexOf(lowerCaseFilter) != -1 ) {
                    return true; // Filter matches product's name.
                }
                else
                    return false; // Does not match.
                   });
        });
        // Wrap the FilteredList in a SortedList.
        SortedList<MotorCycleProduct> sortedData = new SortedList<>(productFilteredList);

        // Bind the SortedList comparator to the TableView comparator.
        // Otherwise, sorting the TableView would have no effect.
        sortedData.comparatorProperty().bind(tvAllItems.comparatorProperty());

        // Add sorted (and filtered) data to the table.
        tvAllItems.setItems(sortedData);
    }


    //Declare void function that sync to the menu item Save inside File menu to save the data of products.
    public void handleSaveClicked(ActionEvent event) {
        //create a new window object which contains the vbox of the main app interface
        Window stage = vbMenu.getScene().getWindow();
        //set the title of the filechooser
        fileChooser.setTitle("Save Dialog");
        //set the initial name every time the user wants to save a file.
        fileChooser.setInitialFileName("Untitled-1");
        //set the extension only for csv files.
        fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("csv file", "*.csv")
        );
        //use another handling exception try catch to save the file,so whenever there is an error it catches the error and prints it in the compiler
        try {
            //Declare a new object file to show the save dialog in the window.
            //It takes stage that i created before as the parameter.
            File file  = fileChooser.showSaveDialog(stage);
            //calls the writeExcel function i created below to write the files in csv file.
            writeExcel(file);

        }
        catch (Exception ex) {
            //prints if there is an exception while saving the file, keeps the program working even there is an error
            ex.printStackTrace();
        }
    }

    //Declare void function that sync to the menu item load inside File menu too load/open the data of products.
    public void handleLoadClicked(ActionEvent event)
    {
        //create a new window object which contains the vbox of the main app interface
        Window stage = vbMenu.getScene().getWindow();
        //set the title of the filechooser
        fileChooser.setTitle("Load Dialog");
        //set the extension only for csv files.
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("csv", "*.csv")
        );
        //use another exception handling try catch to open a file, so when there is an error while opening the file
        //the program will still run
        try {
            //Declare a new object file to show the save dialog in the window.
            //It takes stage that i created before as the parameter.
            File file  = fileChooser.showOpenDialog(stage);
            //calls the writeExcel function i created below tor read the csv file that the user choose to open
            readExcel(file);

        }
        catch (FileNotFoundException e) {
            //prints the file not found exception in the compiler, if there is an error if the user inputs the wrong csv file name.
            e.printStackTrace();
        }
        catch (Exception ex) {
            //prints another exception if there is any other error while loading the file
            ex.printStackTrace();

        }
    }

    //Declare a void function that writes all the data of products to csv file or excel.
    public void writeExcel(File newFile) throws Exception {
        //Declare a new object of a Writer class
        Writer writer = null;
        //Use another Exception handling try catch to write the file inside a csv document.
        //To make the program still run if there is an error while writing the file
        try {
            //assign the writer object to a new object of a buffered writer that takes a new object filewriter with a newFile as parameter
            writer = new BufferedWriter(new FileWriter(newFile));
            //Do a for each loop to write the identity of each product separated with comma
            for (MotorCycleProduct product : products) {
                //New string variable for each products
                String eachProducts = product.getPartNumber() + "," + product.getName() + "," + product.getQuantity() + "," + product.getPrice()  + "," + product.getCategory() + "," + product.getDate() + "\n";
                //writes the eachProducts variable inside the csv file.
                writer.write(eachProducts);
            }
            //Do a for each loop to write the identity of each deleted product separated with comma
            for(DeletedMotorCycleProduct delproduct : deletedProducts)
            {
                //If condition to check if the deleted products observable array list is empty.
                //If its empty break the for each loop.
                if (deletedProducts.isEmpty())
                {
                    break;
                }
                //Otherwise, Write each deleted products identity to the csv file
                else
                {
                    //New string variable for each deleted products starts with empty space " " to make it different with the existing item
                    String eachDeletedProducts = " " + delproduct.getDelPartNumber() + "," + delproduct.getName() + "," + delproduct.getQuantity() + "," + delproduct.getPrice() + "," + delproduct.getCategory() + "," + delproduct.getDate() + "\n";
                    //writes the eachDeletedProducts variable inside the csv file.
                    writer.write(eachDeletedProducts);
                }
            }

        }
        catch (Exception ex) {
            //prints the exception in compiler if there is an error while writing the file
            ex.printStackTrace();
        }
        finally {
            //block of code to Close thw writer
            if(writer != null)
            {
                //try catch to catch error whilw flushing and closing the writer
                try {
                    //flushes the writer
                    writer.flush();
                    //closes the writer
                    writer.close();
                }
                catch(IOException e)
                {
                    e.printStackTrace();
                }
            }
        }
    }


    //Declare a void function that reads all the data of products from csv file or excel.
    public void readExcel(File existedFile) throws Exception
    {
        //Create observable array lists to store all the loadProducts and deleted load products
        ObservableList<MotorCycleProduct> loadedProducts = FXCollections.observableArrayList();
        ObservableList<DeletedMotorCycleProduct> delLoadedProducts = FXCollections.observableArrayList();
        //Declares the new line delimiter which is enter "\n"
        String newLine = "\n";
        //Declare the delimiter or separator for each identity as comma
        String csvSplitBy = ",";

        //Use another try catch, so if an error occures while the program runs, it will not terminate the program.
        //Tries the Declaration of Buffered Reader as the file reader
        try (BufferedReader br = new BufferedReader(new FileReader(existedFile))) {

            //Do a while loop with the condition where the new line is not null
            while ((newLine = br.readLine()) != null) {
                //Declare an array of strings that contain each identity of product and split them by commas to a -1 limit.
                //-1 limit means split them every time the reader reads comma and put them in the productsInFile array.
                String[] productsInFile = newLine.split(csvSplitBy, -1);

                //If the new line starts with an empty space add the product read by the reader to the deleted item table
                if(newLine.startsWith(" "))
                {
                    //creates a new object of deleted products.
                    DeletedMotorCycleProduct loadDelProduct =  new DeletedMotorCycleProduct(productsInFile[0],productsInFile[1], Integer.parseInt(productsInFile[2]),
                            productsInFile[3], productsInFile[4], productsInFile[5]);
                    //add every object to the observable array list delLoadedProducts
                    delLoadedProducts.add(loadDelProduct);
                    //set the deleted item table view element to observable array list containing deleted products identities
                    tvDeletedItems.setItems(delLoadedProducts);

                }
                //Otherwise, add the products read by the reader to the all items table
                else {
                    //creates a new object of products.
                    MotorCycleProduct loadProduct = new MotorCycleProduct(productsInFile[0], productsInFile[1], Integer.parseInt(productsInFile[2]),
                            productsInFile[3], productsInFile[4], productsInFile[5]);
                    //add every object to the observable array list loadedProducts
                    loadedProducts.add(loadProduct);
                    //set the all item table view element to observable array list containing existing product identities
                    tvAllItems.setItems(loadedProducts);
                }
            }
        }
        catch (IOException e)
        {
            //Prints the exception in the compiler if there is an error while reading the file.
            //Doesn;t terminates the program
            e.printStackTrace();
        }
    }
}
