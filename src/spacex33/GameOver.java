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
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.animation.FadeTransition;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.event.EventType;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.util.Duration;
import static spacex33.Screen.WINDOW_HEIGHT;
import static spacex33.Screen.WINDOW_WIDTH;

/**
 *
 * @author asdas
 */
public class GameOver extends Node implements Screen {
    Pane gameOver = new Pane();
    HBox overMessage = new HBox();
    HBox restartMessage = new HBox();
    HBox newHighscore = new HBox();
    HBox initials = new HBox();
    String init = null;
    TextField tf = new TextField();
    int currentScore = 0;
    int minScore = 0;
    
    public GameOver(){
        super();
        gameOver.setBackground(Background.EMPTY);
        overMessage.setAlignment(Pos.CENTER);
        printer("GAME OVER", 70, overMessage);
        
        newHighscore.setAlignment(Pos.CENTER);
        newHighscore.setTranslateY(100);
        printer("NEW HIGHSCORE", 25, newHighscore);
        
        initials.setAlignment(Pos.CENTER);
        initials.setTranslateY(140);
        printer("ENTER YOUR INITIALS", 20, initials);
        
        restartMessage.setAlignment(Pos.CENTER);
        restartMessage.setTranslateY(100);
        printer("PRESS 'R' TO RESET", 20, restartMessage);
        restartMessage.setVisible(false);
        
        tf.setBackground(Background.EMPTY);
        tf.setStyle("-fx-text-inner-color: white; -fx-display-caret: false;");
        tf.setAlignment(Pos.CENTER);
        tf.setMaxWidth(200);
        tf.setTranslateX(250);
        tf.setTranslateY(630);
        tf.setFont(Font.loadFont("file:resource/Fonts/PressStart2P.ttf", 40));
        
        addTextLimiter(tf, 3);

        gameOver.getChildren().add(tf);
        
        tf.setOnKeyPressed(new EventHandler<KeyEvent>(){
            @Override
            public void handle(KeyEvent ke){
                if(ke.getCode().equals(KeyCode.ENTER) && isValid(tf.getText())){
                    init = tf.getText();
                    gameOver.getChildren().remove(initials);
                    gameOver.getChildren().remove(newHighscore);
                    gameOver.getChildren().remove(tf);
                    restartMessage.setVisible(true);
                }
            }
        });
        
    }
    
    public Pane initGameOver(){
        return gameOver;
    }
    
    public void printer(String print, int size, HBox location){
        location.setPrefSize(WINDOW_WIDTH, WINDOW_HEIGHT);
        Text hold_text = new Text(print);
        hold_text.setFont(Font.loadFont("file:resource/Fonts/PressStart2P.ttf", size));
        hold_text.setFill(Color.WHITE);
        location.setAlignment(Pos.CENTER);
        location.getChildren().add(hold_text);
        gameOver.getChildren().add(location);
    }
    
    public void reset(){
        tf.setText("");

        gameOver.getChildren().add(tf);
        gameOver.getChildren().add(initials);
        gameOver.getChildren().add(newHighscore);
        restartMessage.setVisible(false);
    }
    
    public String getInitials(){
        return init;
    }
    
    public void setCurrentScore(int curScore){
        currentScore = curScore;
    }
    
    public void setMinScore(int s){
        minScore = s;
    }
    
    public void restartMessage(){
        restartMessage.setVisible(true);
    }
    
    public void updateGameOver(){
        System.out.println(currentScore + " " + minScore);
        if(currentScore < minScore){
            gameOver.getChildren().remove(initials);
            gameOver.getChildren().remove(newHighscore);
            gameOver.getChildren().remove(tf);
            restartMessage.setVisible(true);
            
        }
    }
    
    public boolean isValid(String s){
        if(s.length() < 3)
            return false;
        
        for(int i = 0; i < s.length(); i++){
            if(!Character.isAlphabetic(s.charAt(i)))
                return false;
        }
        return true;
    }
    
    public static void addTextLimiter(final TextField tf, final int maxLength) {
    tf.textProperty().addListener((final ObservableValue<? extends String> ov, final String oldValue, final String newValue) -> {
        if(tf.getText() != null)
        tf.setText(tf.getText().toUpperCase());
        
        if (tf.getText().length() > maxLength) {
            String s = tf.getText().substring(0, maxLength);
            tf.setText(s);
        }
    });
    }

    @Override
    protected NGNode impl_createPeer() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public BaseBounds impl_computeGeomBounds(BaseBounds bounds, BaseTransform tx) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    protected boolean impl_computeContains(double localX, double localY) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Object impl_processMXNode(MXNodeAlgorithm alg, MXNodeAlgorithmContext ctx) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
