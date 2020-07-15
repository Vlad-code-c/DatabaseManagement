package main;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;

public class Notify {
    public static void incorectValues(){
        //System.out.println("Incorect values");


        Configs.modal_text = "Incorect Values";
        Configs.modal_desc = "Incorect Values desc";
        try {
            startModal();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void reachedMaximumColumnNumber(){
        System.out.println("Reached maximum columns");


        Configs.modal_text = "Reached maximum columns";
        Configs.modal_desc = "You can add maximum " + Configs.maxColumnsNumber + " columns in a table.\n This is temporary, and we work at this.\n";
        try {
            startModal();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    static private void startModal() throws IOException {
        Stage stage = new Stage();
        Parent root = FXMLLoader.load(Notify.class.getResource("modal/modal.fxml"));

        stage.setScene(new Scene(root));
        stage.setTitle("Warning");
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.show();
    }

}
