/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package spacex33;

import com.sun.deploy.util.StringUtils;
import com.sun.javafx.geom.BaseBounds;
import com.sun.javafx.geom.transform.BaseTransform;
import com.sun.javafx.jmx.MXNodeAlgorithm;
import com.sun.javafx.jmx.MXNodeAlgorithmContext;
import com.sun.javafx.sg.prism.NGNode;

import java.io.File;
import java.util.ArrayList;
import javafx.geometry.Pos;
import javafx.scene.Node;
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
 * @author asdas
 */
public class HighscoreScreen extends Node implements Screen {
    
    Pane highscore = new Pane();
    Rectangle bck = new Rectangle();
    HBox leaderboard = new HBox();
    HBox ret = new HBox();
    HBox no_scores = new HBox();
    ArrayList<HBox> scoreDisplay = new ArrayList<>();
    HBox remove = new HBox();
    int[] scores = new int[20];
    File hs = new File("./resource/Text/highscores.txt");
    String[] initials = new String[20];
    String format = "";
    int length = 0;
    int k = 0;
    int lowestScore = 0;
    
    public HighscoreScreen(){
        super();
        bck.setHeight(WINDOW_HEIGHT + 50);
        bck.setWidth(WINDOW_WIDTH + 50);
        bck.setFill(Color.BLACK);
        highscore.getChildren().add(bck);
        
        leaderboard.setPrefSize(WINDOW_WIDTH, WINDOW_HEIGHT);
        leaderboard.setAlignment(Pos.TOP_CENTER);
        leaderboard.setLayoutY(100);
        Text hold_text = new Text("HIGHSCORES");
        hold_text.setFont(Font.loadFont("file:resource/Fonts/PressStart2P.ttf", 50));
        hold_text.setFill(Color.WHITE);
        leaderboard.getChildren().add(hold_text);
        highscore.getChildren().add(leaderboard);
        
        ret.setPrefSize(WINDOW_WIDTH, WINDOW_HEIGHT);
        ret.setAlignment(Pos.BOTTOM_CENTER);
        ret.setLayoutY(-50);
        Text ht = new Text("PRESS TAB TO RETURN");
        ht.setFont(Font.loadFont("file:resource/Fonts/PressStart2P.ttf", 20));
        ht.setFill(Color.WHITE);
        ret.getChildren().add(ht);
        highscore.getChildren().add(ret);
        
        no_scores.setPrefSize(WINDOW_WIDTH, WINDOW_HEIGHT);
        no_scores.setAlignment(Pos.TOP_CENTER);
        no_scores.setLayoutY(200);
        Text ht2 = new Text("NO HIGHSCORES");
        ht2.setFont(Font.loadFont("file:resource/Fonts/PressStart2P.ttf", 20));
        ht2.setFill(Color.WHITE);
        no_scores.getChildren().add(ht2);
        highscore.getChildren().add(no_scores);
        
        
        
        
        //updateScores();
    }
    
    public void updateScores(){
        clearScores();
        //formats the highscores along with initials
        for(int i = 0; i < length; i++){
            if(initials[i] != null){
                if(no_scores.isVisible())
                    no_scores.setVisible(false);
                HBox h = new HBox();
                h.setAlignment(Pos.TOP_CENTER);
                h.setLayoutY(200 + 30*i);
                if(i >= 9)
                    format = (i+1) + ".  " + String.format("%-12s", initials[i]).replace(' ', '.') + String.format("%-10s", " " + String.valueOf(scores[i]));
                else
                    format = (i+1) + ".   " + String.format("%-12s", initials[i]).replace(' ', '.') + String.format("%-10s", " " + String.valueOf(scores[i]));

                printer(format, 20, h);
            }
                
        }
        
        highscore.getChildren().addAll(scoreDisplay);
    }
    
    //sets the highscores
    public void setHighscores(int[] s, String[] init){
        clearScores();
         for(int i = 0; i < length; i++){
             if(scores[i] != s[i])
                scores[i] = s[i];
             if(initials[i] != init[i])
                 initials[i] = init[i];
         }
         updateScores();
    }
    
    public void clearScores(){
        highscore.getChildren().removeAll(scoreDisplay);
        scoreDisplay.clear();
    }
    
    public Pane initHighscoreScreen(){
        return highscore;
    }
    public void printer(String print, int size, HBox location){
        //standardized printer for adding all the information
        location.setPrefSize(WINDOW_WIDTH, WINDOW_HEIGHT);
        Text hold_text = new Text(print);
        hold_text.setFont(Font.loadFont("file:resource/Fonts/PressStart2P.ttf", size));
        hold_text.setFill(Color.WHITE);
        location.setAlignment(Pos.TOP_CENTER);
        location.getChildren().add(hold_text);
        //highscore.getChildren().add(location);
        scoreDisplay.add(location);
    }
    
    public void setLength(int l){
        if(l < 20)
            length = l;
        else
            length = 20;
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
