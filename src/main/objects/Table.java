package main.objects;

import de.jensd.fx.glyphs.GlyphsDude;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;

import java.io.InputStream;
import javafx.scene.text.Font;
import sun.rmi.runtime.Log;

import java.util.ArrayList;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Table {
    private String name;

    //column := ["Nume", "int", "unique"]
    private String id;
    private ArrayList<String[]> columns = new ArrayList<String[]>();

    public Table(String name, ArrayList<String[]> columns){
        this.id = "table" + new Random().nextInt((100000 * 1) + 1);
        this.name = name;
        this.columns.addAll(columns);
    }

    public void add(String[] column){
        columns.add(column);
    }
    public void remove(String columnName){
        for(String[] str : columns){
            if(str[0].toLowerCase().equals(columnName.toLowerCase())){
                columns.remove(str);
            }
        }
    }
    public VBox getFXML(){
        VBox vBoxMain = new VBox();

        /**
         * HBox
         **/
            HBox hBox1 = new HBox();
            hBox1.setId("header" + this.id);
            hBox1.setCursor(Cursor.MOVE);

            Label labelName = new Label(this.name);

            //FontAwesome Icon settings
            Label more = new Label(
                    GlyphsDude.createIcon(FontAwesomeIcon.ELLIPSIS_V, "30em").getText()
            );
            InputStream inputStream = Table.class.getResourceAsStream("/assets/fonts/fa-solid-900.ttf");
            Font fontAwesome = Font.loadFont(inputStream, 10);

            more.setFont(fontAwesome);
            more.setAlignment(Pos.CENTER_RIGHT);
            more.setPadding(new Insets(0, 0, 0, 20));
            more.setCursor(Cursor.HAND);

            hBox1.getChildren().addAll(labelName, more);
            hBox1.setStyle("-fx-border-color: black;");


        /**
         * VBox
         **/

            VBox vBox = new VBox();

            for(String[] str : columns){
                HBox hBox = new HBox();
                Label name = new Label(str[0]);
                Label type = new Label(str[1]);
                hBox.getChildren().addAll(name, type);

                vBox.getChildren().add(hBox);
            }


        vBoxMain.getChildren().addAll(hBox1, vBox);
        vBoxMain.setStyle("-fx-border-color: black;");
        vBoxMain.setId(this.id);

        return vBoxMain;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ArrayList<String[]> getColumns() {
        return columns;
    }

    public void setColumns(ArrayList<String[]> columns) {
        this.columns = columns;
    }

    public String getId() {
        return id;
    }
}
