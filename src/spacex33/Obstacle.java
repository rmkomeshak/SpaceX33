package spacex33;

import javafx.scene.image.ImageView;

public interface Obstacle {
    public int getWidth();
    public int getHeight();
    public void setWidth(int w);
    public void setHeight (int h);
    public ImageView initGraphics();
    //will add more to this later, but for now this is all I have
}
