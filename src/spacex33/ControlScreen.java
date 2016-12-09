/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package spacex33;

import javafx.geometry.Pos;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import static spacex33.Screen.WINDOW_HEIGHT;
import static spacex33.Screen.WINDOW_WIDTH;
/**
 *
 * @author Andrew
 */
public class ControlScreen implements Screen {
 Rectangle bck = new Rectangle();
    HBox pause = new HBox();
    HBox pause2 = new HBox();
    HBox pause3 = new HBox();
    HBox pause4 = new HBox();
    HBox pause5 = new HBox();
    HBox pause6 = new HBox();
    Pane controlScreen = new Pane();
    
    public ControlScreen(){
        super();
        bck.setHeight(WINDOW_HEIGHT + 50);
        bck.setWidth(WINDOW_WIDTH + 50);
        bck.setFill(Color.BLACK);
        controlScreen.getChildren().add(bck);
        pause.setAlignment(Pos.TOP_CENTER);
        pause.setLayoutY(100);
        printer("CONTROLS", 45, pause);
        
        pause2.setAlignment(Pos.TOP_CENTER);
        pause2.setLayoutY(175);
        printer("D or →: MOVE RIGHT", 20, pause2);
        
        pause3.setAlignment(Pos.TOP_CENTER);
        pause3.setLayoutY(215);
        printer("A or ←: MOVE LEFT", 20, pause3);
        
        pause4.setAlignment(Pos.TOP_CENTER);
        pause4.setLayoutY(255);
        printer("TAB: PAUSE/UNPAUSE", 20, pause4);
        
        pause5.setAlignment(Pos.TOP_CENTER);
        pause5.setLayoutY(295);
        printer("SPACE: SHOOT", 20, pause5);
        
        pause6.setLayoutY(800);
        printer("PRESS TAB TO RETURN", 20, pause6);
    }
    
    public Pane initControlScreen(){
        return controlScreen;
    }
    
    public void printer(String print, int size, HBox location) {
        location.setPrefSize(WINDOW_WIDTH, WINDOW_HEIGHT);
        Text hold_text = new Text(print);
        hold_text.setFont(Font.loadFont("file:resource/Fonts/PressStart2P.ttf", size));
        hold_text.setFill(Color.WHITE);
        location.setAlignment(Pos.TOP_CENTER);
        location.getChildren().add(hold_text);
        controlScreen.getChildren().add(location);
    }
} 
