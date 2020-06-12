package KevAndNicGUIApp;
//Library that is Imported
import javafx.scene.*;
import javafx.scene.image.Image;
import javafx.stage.*;
import javafx.scene.layout.*;
import javafx.scene.control.*;
import javafx.geometry.*;

//Create a class ConfirmBox to create a dialogue box asking user are they sure to leave the program
public class ConfirmBox {
    //Declare a static boolean named answer
    static boolean answer;
    //Declare a boolean display function that has two parameters 1 as title and the other ass message
    public static boolean display(String title, String message) {
        //Create a new window for the dialogue
        Stage window = new Stage();
        //Set the window modality to application_modal
        //Defines a modal window that blocks events from being delivered to any other application window.
        window.initModality(Modality.APPLICATION_MODAL); // cannot click anything unless do something with the pop up window
        //Set the window title as the title parameter
        window.setTitle(title);
        //Set the width of the window
        window.setMinWidth(250);
        //Declare new Label as the message inside the window
        Label label = new Label();
        //Set the label value as the message from the parameter
        label.setText(message);

        //Button initialize
        //Yes Button
        Button yesButton = new Button("Yes");
        yesButton.setStyle(" -fx-background-color : red;");

        //NO button
        Button noButton = new Button("No");
        noButton.setStyle(" -fx-background-color : deepskyblue;");

        //If the yes button is clicked the events inside will appear (using Lambda Expression)
        yesButton.setOnAction(event -> {
            answer = true;
            window.close();
        });
        //If the no button is clicked the events inside wil appear (using Lambda Expression)
        noButton.setOnAction(event -> {
            answer = false;
            window.close();
        });
        //Create a new Vbox as the layout for the dialogue
        VBox layout = new VBox(10);
        //Add all the children to the Vbox
        layout.getChildren().addAll(label, yesButton, noButton);
        //set the position to center
        layout.setAlignment(Pos.CENTER);

        Scene scene = new Scene(layout, 350,200);
        //Set Scene for the confirmbox to appear
        window.setScene(scene);
        window.getIcons().add(new Image("KevAndNicGUIApp\\icon.png"));
        //Method to show an wait for the user action
        window.showAndWait();
        //returns the true false statement
        return answer;
    }
}


