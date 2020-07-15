package main.modal;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import main.Configs;
import sun.security.krb5.Config;

public class ModalController {

    @FXML
    private AnchorPane mainAchorPane;

    @FXML
    private AnchorPane titleAnchor;

    @FXML
    private Text title;

    @FXML
    private TextArea desc;

    @FXML
    private Button buttonOk;

    @FXML
    void initialize(){
        title.setText(Configs.modal_text);
        desc.setText(Configs.modal_desc);

        Configs.modal_text = "Text";
        Configs.modal_desc = "You are not alowed to see this :/";

        mainAchorPane.addEventHandler(KeyEvent.KEY_PRESSED, event -> {
            if(event.getCode() == KeyCode.ESCAPE){
                exitModal(mainAchorPane);
            }
        });

        buttonOk.setOnMouseClicked(event -> {
            exitModal(buttonOk);
        });

    }

    private void exitModal(Node node){
        Stage stage = (Stage) node.getScene().getWindow();
        stage.close();
    }
}
