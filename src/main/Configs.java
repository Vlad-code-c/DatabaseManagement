package main;

import de.jensd.fx.glyphs.GlyphsDude;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;

import java.util.ArrayList;

public class Configs {
    /**
     * Main
     **/
    public static int WIDHT = 1200;
    public static int HEIGHT = 675;

    public static ArrayList<Object[]> activity = new ArrayList<Object[]>();
    public static boolean isCollapsedMenu = false;
    public static boolean isCollapsedAddButton = true;

    public static double activityWidth = 300;

    public static ObservableList<String> avaliableTypes = FXCollections.observableArrayList(
            "Int", "Nvarchar"
    );

    public static int maxColumnsNumber = 6;

    //For modal windows
    public static String modal_text = "Text";
    public static String modal_desc = "You are not alowed to see this :/";


    static {
        activity.add(new Object[]{ "Main", GlyphsDude.createIcon(FontAwesomeIcon.HOME, "20px")});
        activity.add(new Object[]{ "Main", GlyphsDude.createIcon(FontAwesomeIcon.HACKER_NEWS, "20px")});
        activity.add(new Object[]{ "Main", GlyphsDude.createIcon(FontAwesomeIcon.HOME, "20px")});
    }
}
