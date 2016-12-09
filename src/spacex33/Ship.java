package spacex33;

import com.sun.javafx.geom.BaseBounds;
import com.sun.javafx.geom.transform.BaseTransform;
import com.sun.javafx.jmx.MXNodeAlgorithm;
import com.sun.javafx.jmx.MXNodeAlgorithmContext;
import com.sun.javafx.sg.prism.NGNode;
import java.applet.AudioClip;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class Ship extends Node{
    final int SHIP_GAP = 10; //gap between lanes of the ship
    final int BOTTOM_GAP = 75;
    private int w_height;
    
    private int shipHP = 3;
    private boolean isHit;
    private int width; //width of the ship img
    private int height; //height of the ship img
    private Image ship_src = new Image("file:resource/Images/ship.png", true);
    private Image ship_hit = new Image("file:resource/Images/ship_inverse.png", true);
    ImageView shipView = new ImageView();
    
    public Ship(){
        super();
        width = 105;
        height = 105;
    }
    
    public Ship(int w, int h){
        super();
        width = w;
        height = h;
    }

    public ImageView initGraphics() {
        shipView.setImage(ship_src);
        shipView.setTranslateY(w_height-width-BOTTOM_GAP); // 900-105-75 to get 720. This give a 75px gap from the bottom of the page
        shipView.setTranslateX(SHIP_GAP); //sets the X coordinate to be 10px away from the edge
        //sets the 
        return shipView;
    }
    
    public void resetLocation(){
        shipView.setTranslateY(w_height-width-BOTTOM_GAP); 
        shipView.setTranslateX(SHIP_GAP); 
    }
    
    public boolean getShipState() {
        return isHit;
        // used to know when you've been hit
    }
    
    public void setShipState() {
        if (isHit == false)
        {
        shipView.setImage(ship_hit);
        isHit = true;
        }
        else {
            shipView.setImage(ship_src);
            isHit = false;
        }
    }
    
    public void checkShipState() {
        if (isHit != false) {
            shipView.setImage(ship_src);
            isHit = false;
        }
    }
    
    public boolean isHit(){
        return isHit;
    }
    
    public int getWidth(){
        return width;
    }
    
    public int getHeight(){
        return height;
    }
    
    public int getGap(){
        return SHIP_GAP; //returns the ship gap
    }
    
    public int getHealth(){
        return shipHP;
    }
        
    public void setWidth(int w){
        width = w;
    }
    
    public void setHeight(int h){
        height = h;
    }
    
    public void setHealth(int health){
        if(health > 5)
            shipHP = 5;
        else
            shipHP = health;
    }
    
    public void setW_Height(int h){
        w_height = h;
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