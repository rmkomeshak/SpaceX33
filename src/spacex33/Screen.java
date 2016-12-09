/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package spacex33;

import javafx.scene.layout.HBox;

/**
 *
 * @author asdas
 */
public interface Screen {
    final int WINDOW_WIDTH = 700;
    final int WINDOW_HEIGHT = 900;
    
    void printer(String print, int size, HBox location);
}
