package spacex33;

import com.sun.javafx.geom.BaseBounds;
import com.sun.javafx.geom.transform.BaseTransform;
import com.sun.javafx.jmx.MXNodeAlgorithm;
import com.sun.javafx.jmx.MXNodeAlgorithmContext;
import com.sun.javafx.sg.prism.NGNode;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.shape.Circle;

public class Asteroid extends Node implements Obstacle {
    private int width; //width of the image
    private int height; //height of the image
    private int lane;
    private int guessArt;
    private Image asteroid_image1 = new Image("file:resource/Images/asteroid.png", true);
    private Image asteroid_image2 = new Image("file:resource/Images/asteroid2.png", true); //sets the path of the asteroid image
    private ImageView astImage = new ImageView(asteroid_image1); //sets up the imageviewer for the asteroid image
    private ImageView ast2Image = new ImageView(asteroid_image2);
    
    final int EDGE_OBSTACLE_GAP = 15; //the gap on the edge is 5px larger than the ship gap because it takes 5px extra on one side
    final int MID_OBSTACLE_GAP = EDGE_OBSTACLE_GAP + 5; //the gaps beween lanes in the middle are 5px bigger because it takes 5 off both sides
    //gaps need to be slightly larger so that the edges of the obstacles don't touch the ship as they pass
    
    public Asteroid(){
        super();
        width = 95;
        height = 95;
        //default width and height are 95 because that's what we're using, but this is subject to change
        lane = 0;
    }
    
    public Asteroid(int w, int h){
        super();
        width = w;
        height = h;
        lane = 0;
    }
    
    public ImageView initGraphics(){
        int guessAst = (int) (Math.random() * 100);
        if (guessAst % 2 == 1) {
        astImage.setFitWidth(width);
        astImage.setFitHeight(height);
        //sets the width and height of the image
        return astImage;
        }
        else {
            ast2Image.setFitWidth(width);
            ast2Image.setFitHeight(height);
            return ast2Image;
        }
    }
    
    public int getWidth(){
        return width;
    }
    
    public int getHeight(){
        return height;
    }
    
    public int getEdgeGap(){
        return EDGE_OBSTACLE_GAP;
    }
    
    public int getMidGap(){
        return MID_OBSTACLE_GAP;
    }
    
    public int getLane(){
        return lane;
    }
        
    public void setWidth(int w){
        width = w;
    }
    
    public void setHeight(int h){
        height = h;
    }
    
    public void setLane(int l){
        lane = l;
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