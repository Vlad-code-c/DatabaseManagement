package main;

import de.jensd.fx.glyphs.GlyphsDude;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import main.anim.PlusBtnAnim;
import main.objects.Table;

import java.io.InputStream;
import java.util.*;

public class Controller {


    @FXML
    private AnchorPane mainAnchorPane;

    @FXML
    private AnchorPane MainMenu;

    @FXML
    private VBox Activity;

    @FXML
    private Button collapseMenu;

    @FXML
    private AnchorPane Properties;

    @FXML
    private Button ShowOptions;

    @FXML
    private VBox ShowOptionsPanel;

    @FXML
    private VBox mainContextMenu;

    @FXML
    private Button addTableBtn;

    @FXML
    private TextField tableName;

    @FXML
    private Button finish;

    @FXML
    private AnchorPane addTableAnchorPane;

    @FXML
    private VBox columnsVBox;

    @FXML
    private HBox hboxTemplate;

    @FXML
    private Button debug;

    @FXML
    private Label tableNameVerified;


    private double event_x=0, event_y=0;
    private ArrayList<String[]> columns = new ArrayList<String[]>();

    public static boolean actTableNameVer = true;
    public static boolean actColNameVer = true;


    @FXML
    void initialize() {
        activityMenuFill();
        addIcons();
        collapse();
        collapseAddMenu();
        initColumns();

        InputStream font = Controller.class.getResourceAsStream("/assets/fonts/fa-solid-900.ttf");
        Font fontAwesome = Font.loadFont(font, 15);


        //Deschide/inchide meniul
        collapseMenu.setOnAction(event -> {
            Configs.isCollapsedMenu = !Configs.isCollapsedMenu;
            collapse();
        });

        ShowOptions.setOnAction(event -> {
            Configs.isCollapsedAddButton = !Configs.isCollapsedAddButton;
            collapseAddMenu();
        });


        //La click-dreapta se va deschide context-meniu-ul personalizat
        mainAnchorPane.setOnMouseClicked(event -> {
            if (event.getButton() == MouseButton.SECONDARY) {
                mainContextMenu.setLayoutX(event.getX());
                mainContextMenu.setLayoutY(event.getY());
                mainContextMenu.setVisible(true);

                contextMenuDisableTimer();
            }

            //La apasarea (in context-menu) butonului <Add Table>
            addTableBtn.setOnMouseClicked(event2 -> {
                if (event2.getButton() == MouseButton.PRIMARY) {
                    mainContextMenu.setVisible(false);
                    addTableAnchorPane.setVisible(true);
                    addTableAnchorPane.toFront();

                    this.event_x = event.getX();
                    this.event_y = event.getY();


                }
            });
        });


        //La apasarea butonului finish in fereastra modala dupa adaugarea coloanelor necesare
        finish.setOnAction(event -> {
            Table table = addTable();
            if(table != null) {
                VBox vBox = table.getFXML();

                mainAnchorPane.getChildren().add(vBox);


                vBox.setLayoutX(event_x);
                vBox.setLayoutY(event_y);
                vBox.setVisible(true);
                vBox.setId(table.getId());


                //Drag tables
                for (Node o : mainAnchorPane.getChildren()) {
                    if (o.getId() != null)
                        if (o.getId().startsWith("table")) {
                            ((VBox) o).getChildren().stream()
                                    .filter(child -> child.getId().startsWith("header"))
                                    .findFirst().get()
                                    .setOnMouseDragged(ev -> {
                                        o.setLayoutX(ev.getSceneX());
                                        o.setLayoutY(ev.getSceneY());
                                    });
                        }
                }
            } else {
                Notify.incorectValues();
            }
        });

        //La apasarea butonului Enter (keyboard), se va adauga coloana respectiva in lista
        for (Node item : columnsVBox.getChildren()) {
            item.addEventHandler(KeyEvent.KEY_PRESSED, event -> {
                if (event.getCode() == KeyCode.ENTER) {

                    //Limitez nr maximal de coloane care pot exista intr-un tabel
                    if(columns.size() >= Configs.maxColumnsNumber) {
                        Notify.reachedMaximumColumnNumber();
                    } else {

                        addTableToArrayList();
                        System.out.println(item.getId());


                        //La modificarea campurilor existente
                        for (Node nod : columnsVBox.getChildren()) {
                            if (nod.getId().equals("hboxTemplate"))
                                continue;

                            //Field 1
                            ((TextField) ((HBox) nod).getChildren().get(0)).textProperty().addListener((observable, oldValue, newValue) -> {
                                if (RestrictionVerify.colNameVerify(newValue)) {
                                    columnsChange(oldValue, newValue, 0);
                                    actColNameVer = true;
                                } else {
                                    Notify.incorectValues();
                                    actColNameVer = false;
                                }
                            });

                            ((ComboBox) ((HBox) nod).getChildren().get(1)).valueProperty().addListener(((observable, oldValue, newValue) -> {
                                columnsChange(oldValue.toString(), newValue.toString(), 1);
                            }));

                            //Field 3
                            ((TextField) ((HBox) nod).getChildren().get(2)).textProperty().addListener((observable, oldValue, newValue) -> {
                                columnsChange(oldValue, newValue, 2);
                            });
                        }

                    }
                }
            });
        }

        tableName.textProperty().addListener((observable, oldValue, newValue) -> {
            tableNameVerified.setFont(fontAwesome);
            if(RestrictionVerify.tableNameVerify(newValue)) {
                tableNameVerified.setText(GlyphsDude.createIcon(FontAwesomeIcon.CHECK_CIRCLE, "70px").getText());
                tableNameVerified.setStyle("-fx-fill: green");

                actTableNameVer = true;
            } else {
                tableNameVerified.setText(GlyphsDude.createIcon(FontAwesomeIcon.TIMES_CIRCLE, "70px").getText());
                tableNameVerified.setStyle("-fx-fill: red");

                actTableNameVer = false;
            }
        });





        ////////
        debug.setOnAction(event -> {
            System.out.println("Debug mode on");
            for(int i = 0; i < columns.size(); i++){
                System.out.println(Arrays.toString(columns.get(i)));
            }
        });
    }

    void columnsChange(String oldVal, String newVal, int itemIndex){
        if(itemIndex == 0){
            for(String[] str : columns){
                if(str[0].equals(oldVal))
                    str[0] = newVal;
            }
        }
        else if(itemIndex == 1){
            for(String[] str : columns){
                if(str[1].equals(oldVal))
                    str[1] = newVal;
            }
        }
        else if (itemIndex == 2){
            for(String[] str : columns){
                if(str[2].equals(oldVal))
                    str[2] = newVal;
            }
        }
    }

    //La adaugarea unei noi coloane
    void addTableToArrayList(){

        //Citesc textul
        String col_name = ((TextField) hboxTemplate.getChildren().stream()
                .filter(child -> "columnNameTemplate".equals(child.getId()))
                .findFirst().get()).getText();


        if(RestrictionVerify.colNameVerify(col_name)){
            actColNameVer = true;
        } else {
            actColNameVer = false;
            return;
        }


        //Setez textul - ""
        ((TextField) hboxTemplate.getChildren().stream()
                .filter(child -> "columnNameTemplate".equals(child.getId()))
                .findFirst().get()).setText("");

        //Preiau optiunea selectata
        String col_type = ((ComboBox) hboxTemplate.getChildren().stream()
                .filter(child -> "columnTypeTemplate".equals(child.getId()))
                .findFirst().get()).getValue().toString();


        //Citesc textul
        String col_con = ((TextField) hboxTemplate.getChildren().stream()
                .filter(child -> "columnConstaintsTemplate".equals(child.getId()))
                .findFirst().get()).getText();

        //Setez textul - ""
        ((TextField) hboxTemplate.getChildren().stream()
                .filter(child -> "columnConstaintsTemplate".equals(child.getId()))
                .findFirst().get()).setText("");



        columns.add(new String[]{
                col_name, col_type, col_con
        });

            int rand = new Random().nextInt(10000);

            HBox hBox = new HBox();
            hBox.setId("Col" + rand);

            TextField tf_name = new TextField(col_name);
            tf_name.setId("tf_name" + rand);

            ComboBox db = new ComboBox();
            db.setItems(Configs.avaliableTypes);
            db.setValue(col_type);
            db.setId("db" + rand);

            TextField tf_con = new TextField(col_con);
            tf_con.setId("tf_con" + rand);


            hBox.getChildren().addAll(tf_name, db, tf_con);

            columnsVBox.getChildren().add(columnsVBox.getChildrenUnmodifiable().size() - 1, hBox);

    }

    /**
     * Creaza un nou obiect de tipul Table folosind numele si colectia de coloane din fereastra.
     * Elimina toate elementele din colectia columns, si din fereastra columnsVbox.
     * @return Table
     * */
    Table addTable(){
        String name = tableName.getText();
        if(!actTableNameVer)
            return null;
        if(!actColNameVer)
            return null;

        String[] str = checkUreaded();
        if(str != null)
            if(!columns.contains(str))
                columns.add(str);

        Table table = new Table(name, columns);


        columns.removeAll(columns);
        addTableAnchorPane.setVisible(false);


        Iterator<Node> iter = columnsVBox.getChildren().iterator();
        while (iter.hasNext()) {
            if (!((Node) iter.next()).getId().equals("hboxTemplate")) {
                iter.remove();
            }
        }

        hboxTemplate.getChildren().removeAll(hboxTemplate.getChildren());

        initColumns();

        return table;
    }

    /**
     * Verifica daca ultima coloana a fost salvata in lista columns
     * @return String[]
     */
    String[] checkUreaded(){
        String[] str = new String[3];

        str[0] = ((TextField) hboxTemplate.getChildren().stream()
                .filter(child -> "columnNameTemplate".equals(child.getId()))
                .findFirst().get()).getText();

       //Preiau optiunea selectata
        str[1] = ((ComboBox) hboxTemplate.getChildren().stream()
                .filter(child -> "columnTypeTemplate".equals(child.getId()))
                .findFirst().get()).getValue().toString();


        //Citesc textul
        str[2] = ((TextField) hboxTemplate.getChildren().stream()
                .filter(child -> "columnConstaintsTemplate".equals(child.getId()))
                .findFirst().get()).getText();


        if(!str[0].isEmpty())
            return str;
        else
            return null;
    }


    /**
     * Initiaza o linie pentru a introduce datele despre o coloana noua
     * */
    void initColumns(){
        TextField tf_name = new TextField();
        tf_name.setId("columnNameTemplate");

        ComboBox db = new ComboBox();
        db.setItems(Configs.avaliableTypes);
        db.setId("columnTypeTemplate");
        db.setValue(Configs.avaliableTypes.get(0));

        TextField tf_con = new TextField();
        tf_con.setId("columnConstaintsTemplate");

        hboxTemplate.getChildren().addAll(tf_name, db, tf_con);
    }



    /**
     * Ascunde contexMenu-ul dupa 4 (0000) secunde.
     * @deprecated
     * */
    void contextMenuDisableTimer(){
        //Disable contextMenu after 4 seconds
        Timer timer = new Timer();

        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                mainContextMenu.setVisible(false);
            }
        }, 400000);  //TODO: Do something with this

    }


        /**
         * Animatia pentru butonul '+'
         * */
    void collapseAddMenu(){
        //TODO: Increase visibility after n miliseconds
        // Timeline: https://stackoverflow.com/a/48309311/11479834

        if(Configs.isCollapsedAddButton){
            ShowOptionsPanel.setVisible(false);
            new PlusBtnAnim(ShowOptions, -180).playAnim();
        } else {
            ShowOptionsPanel.setVisible(true);
            new PlusBtnAnim(ShowOptions, 180).playAnim();
        }
    }


    /**
     * Seteaza icon-urile Home si Plus
     * */
    void addIcons(){
        InputStream font = Controller.class.getResourceAsStream("/assets/fonts/fa-solid-900.ttf");
        Font fontAwesome = Font.loadFont(font, 10);


        ShowOptions.setText(GlyphsDude.createIcon(FontAwesomeIcon.PLUS, "20px").getText());
        ShowOptions.setFont(fontAwesome);

        collapseMenu.setText(GlyphsDude.createIcon(FontAwesomeIcon.HOME, "20px").getText());
        collapseMenu.setFont(fontAwesome);
    }


    /**
     * Micsoreaza dimensiunile meniului
     * */
    void collapse(){
        if(!Configs.isCollapsedMenu) {
            double w = collapseMenu.getWidth();
            Activity.setMaxWidth(w);
            MainMenu.setMaxWidth(w);
        } else {
            double w = collapseMenu.getWidth();
            Activity.setMaxWidth(Configs.activityWidth);
            MainMenu.setMaxWidth(Configs.activityWidth);
        }

        ShowOptionsButtonReposition();
    }


    /**
     * Afiseaza contextMenu-ul pentru butonul '+'
     * */
    void ShowOptionsButtonReposition(){
        if(!Configs.isCollapsedMenu) {
            ShowOptions.setLayoutX(60);
            ShowOptionsPanel.setLayoutX(60);
        } else {
            ShowOptions.setLayoutX(170);
            ShowOptionsPanel.setLayoutX(170);
        }
    }


    /**
     * Adauga cate un button pentru fiecare element din colectia Configs.activity
     * */
    void activityMenuFill(){
        for(int i = 0; i < Configs.activity.size(); i++){

            Button btn = new Button(
                    new Text(String.valueOf(Configs.activity.get(i)[0])).getText(),
                    (Node) Configs.activity.get(i)[1]
            );
            Activity.getChildren().add(btn);
        }
    }

}


