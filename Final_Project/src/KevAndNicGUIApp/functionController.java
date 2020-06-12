package KevAndNicGUIApp;

//Import all the library that are going to be used
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.control.Dialog;
import javafx.scene.control.TextField;
import java.io.File;

// Interface of Controller Class that contains al the function that are going to be used
//This is the blue print of the class Controller
//This is to implement Polymorphism
public interface functionController {
   //Declare the Function that sync to add button to add the data to the main table view (All Items Table View).
   void addData();

   //Declare the Function that sync to delete button which Delete the data from the table view
   //And add it inside another table view that contains list of deleted items.
   void delData();

   //Declare the Function to Update data inside the main table view(All items table view).
   void updateData();

   //Declare a boolean Function to test whether the input is an integer/double.
   //It takes two parameter Textfield and a String.
   boolean isInt(TextField input);

   //Declare Void Function to Set the Alert box to handle every warning (The purpose of this is to prevent Writing the same code for every warning)
   //It has two parameter the title as the title of the alert box and message as the message inside it to inform warning state to the user.
   void warningBox(String title, String message);

   //Declare Void Function to Set the Alert box to handle every error while input (The purpose of this is to prevent Writing the same code for every error)
   //It has two parameter the title as the title of the alert box and message as the message inside it to inform error state to the user.
   void errorBox(String title, String message);

   //Declare a void function that syncs with the clear all button which will clear all the data in the all item tableview;
   void clearAllItemsTable();

   //Declare a void function that syncs with the clear all button which will clear all the data in the deleted item tableview;
   void clearDeletedItemsTable();

   //Declare a boolean function to test whether the part number is unique or not.
   //The function takes 1 parameter Text field that check the field for the part number.
   boolean isUnique(TextField inputPartNumber);

   //Declare a void function for the search bar text field to sort the table list based on the text inside.
   //This helps the user to find a product if there are a lot of products later on.
   //The search is by name then part number (performs sequential search);
   void search();

   //Declare void function that sync to the menu item Save inside File menu too save the data of products.
   void handleSaveClicked(ActionEvent event);

   //Declare void function that sync to the menu item load inside File menu too load/open the data of products.
   void handleLoadClicked(ActionEvent event);

   //Declare a void function that writes all the data of products to csv file or excel.
   void writeExcel(File newFile) throws Exception;

   //Declare a void function that reads all the data of products from csv file or excel.
   void readExcel(File existedFile) throws Exception;

   //Declare a void function sets the icon of every scene or dialog
   void setIcon(Dialog dialog);

   //void function to set update product identity
   void setUpdatedProduct(ObservableList<MotorCycleProduct> productSelected, String updatedPartNumber, String updatedName, Integer updatedQuantity, Double updatedPrice, String updatedCategory, String updatedDate);



}
