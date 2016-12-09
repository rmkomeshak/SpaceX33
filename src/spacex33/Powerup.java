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
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 *
 * @author Ryan
 */
public class Powerup extends Node{
    int width, height, pow_num;
    Image cacheTimeSlow = new Image("file:resource/Images/clock_image.png", true);
    Image cacheHeal = new Image("file:resource/Images/heartImg.png", true);
    Image cacheAmmoDrop = new Image("file:resource/Images/ammo.png", true);
    ImageView powerup = new ImageView();
    final int EDGE_POWERUP_GAP = 15; //the gap on the edge is 5px larger than the ship gap because it takes 5px extra on one side
    final int MID_POWERUP_GAP = EDGE_POWERUP_GAP + 5; //the gaps beween lanes in the middle are 5px bigger because it takes 5 off both sides
    //gaps need to be slightly larger so that the edges of the obstacles don't touch the ship as they pass
    
    public Powerup(){
        super();
        width = 95;
        height = 95;
    }
    
    public ImageView initGraphics(){
        powerup = new ImageView(choosePowerup());
        powerup.setFitWidth(width);
        powerup.setFitHeight(height);
        
        return powerup;
    }
    
    public Image choosePowerup(){
        pow_num = (int)(Math.random() * 100) % 3;
        
        switch(pow_num){
            case 0: 
                return cacheTimeSlow;
            case 1:
                return cacheHeal;
            case 2: 
                return cacheAmmoDrop;
            default:
                break;
        }
        return cacheHeal;
    }
    
    public int getType(){
        return pow_num;
    }
    
    public void setWidth(int w){
        width = w;
    }
    
    public void setHeight(int h){
        height = h;
    }
    
    public int getWidth(){
        return width;
    }
    
    public int getHeight(){
        return height;
    }
    
    public int getMidGap(){
        return MID_POWERUP_GAP;
    }
    
    public int getEdgeGap(){
        return EDGE_POWERUP_GAP;
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
