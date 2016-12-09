/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package spacex33;

import com.sun.javafx.geom.BaseBounds;
import com.sun.javafx.geom.transform.BaseTransform;
import com.sun.javafx.jmx.MXNodeAlgorithm;
import com.sun.javafx.jmx.MXNodeAlgorithmContext;
import com.sun.javafx.sg.prism.NGNode;
import java.util.ArrayList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.Border;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import spacex33.SpaceX33.STATE;

/**
 *
 * @author asdas
 */
public class StartScreen implements Screen{
    private Image start = new Image("file:resource/Images/start_game.png", true);
    
    private boolean isActive = true;
    Button startGame = new Button();
    Pane startScreen = new Pane();
    HBox startMessage = new HBox();
    HBox starter = new HBox();
    HBox starter2 = new HBox();
    HBox starter3 = new HBox();
    HBox starter4 = new HBox();
    STATE s = STATE.START;
    
    public StartScreen(){
        startScreen.setBackground(Background.EMPTY);
        
        //startGame = createButton(175, 300, start);
        
        startMessage.setAlignment(Pos.TOP_CENTER);
        startMessage.setLayoutY(200);
        printer("SPACEX33", 70, startMessage);
        
        starter.setAlignment(Pos.TOP_CENTER);
        starter.setLayoutY(350);
        printer("PRESS 'SPACE' TO START", 25, starter);
        
        starter2.setAlignment(Pos.TOP_CENTER);
        starter2.setLayoutY(400);
        printer("PRESS 'ESCAPE' TO QUIT", 25, starter2);
        
        starter3.setAlignment(Pos.TOP_CENTER);
        starter3.setLayoutY(450);
        printer("PRESS 'C' FOR CONTROLS", 25, starter3);
        
        starter4.setAlignment(Pos.TOP_CENTER);
        starter4.setLayoutY(500);
        printer("PRESS 'H' FOR HIGHSCORES", 25, starter4);
        

    }
    
    public Pane initStartScreen(){
        return startScreen;
    }
    
    public STATE getState(){
        return s;
    }
    
    public void setState(STATE st){
        s = st;
    }
    
    public Button createButton(int x, int y, Image img){
        Button b = new Button();
        b.setBackground(Background.EMPTY);
        b.setVisible(true);
        b.setLayoutX(x);
        b.setLayoutY(y);
        b.setGraphic(new ImageView(img));
        startScreen.getChildren().add(b);
        return b;
    }
    
    

    public void printer(String print, int size, HBox location) {
        location.setPrefSize(WINDOW_WIDTH, WINDOW_HEIGHT);
        Text hold_text = new Text(print);
        hold_text.setFont(Font.loadFont("file:resource/Fonts/PressStart2P.ttf", size));
        hold_text.setFill(Color.WHITE);
        location.setAlignment(Pos.TOP_CENTER);
        location.getChildren().add(hold_text);
        startScreen.getChildren().add(location);
    }
    
    public Button getStartButton(){
        return startGame;
    }
}