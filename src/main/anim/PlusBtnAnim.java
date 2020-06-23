package main.anim;

import javafx.animation.RotateTransition;
import javafx.scene.Node;
import javafx.util.Duration;


public class PlusBtnAnim {
    private RotateTransition tt;
    private Node node;

    public PlusBtnAnim(Node node, int radius){
        this.node = node;
        tt = new RotateTransition(Duration.millis(300), node);

        //Anim:
        tt.setByAngle(radius);
        tt.setCycleCount(1);
    }

    public void playAnim(){
        tt.playFromStart();
    }
}
