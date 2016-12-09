package spacex33;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.util.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.animation.TranslateTransition;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.shape.Rectangle;

public class SpaceX33 extends Application {   
    private Scene gameScene;
    private Scene startScene;
    private Scene hudScene;
    private Scene pauseScene;
    private Scene gameOverScene;
    private Scene controlScene;
    private Scene highscoreScene;
    
    private Button startGame;
    
    private AnimationTimer timer; 
    private Pane root; //declare the root pane
    private List<Node> asteroids = new ArrayList<>(); //array list of asteroids
    private List<ImageView> powerups = new ArrayList<>(); //array list of powerups
    private List<Node> background = new ArrayList<>(); //array of 3 background images used for cycling
    private List<Node> farbackground = new ArrayList<>();
    private List<ImageView> shots = new ArrayList<>();
    private double rand_hold = 0;
    private Node shipDisplay = new Ship(); //create the display node for the ship
    private ImageView astImg = new ImageView(); //create the imageviewer for the asteroids
    //private ImageView slowImg = new ImageView();
    private ImageView powerupImage = new ImageView();
    private final Ship shipObj = new Ship();
    private HeadsUpDisplay HUD = new HeadsUpDisplay();   
    private StartScreen startScreen = new StartScreen();
    private PauseScreen pauseScreen = new PauseScreen();
    private GameOver gameOver = new GameOver();
    private ControlScreen controlScreen = new ControlScreen();
    private HighscoreScreen highscoreScreen = new HighscoreScreen();
    File highscores = new File("./resource/Text/highscores.txt");
    File initials = new File("./resource/Text/initials.txt");
    
    private Image bck = new Image("file:resource/Images/space_background1.png",true);
    private ImageView b1, b2;
    private ImageView far_b1, far_b2;
    
    private Image start = new Image("file:resource/Images/start_game.png", true);
    private Image laser = new Image("file:resource/Images/laser.png", true);
    private SoundManager sounds = new SoundManager();
    
    //private final SlowTime slowObj = new SlowTime();
    
    private boolean enableShip = false;
    private boolean enableSlow = true;
    private int start_pause_none = 0;
    
    Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    final int NUM_LANES = 6; //number of lanes for obstacles to spawn
    private int window_width = 700;
    private int window_height = 900;
    private double speed = 7; //speed of asteroids descending toward ship
    private int spawnCount = 0; //counts amount of asteroids spawned in
    private double speedAdd = 0.1; //used in algorithm to calculate difficulty. speedAdd = 1/10 of spawnCount
    private double spawnRate = 0.3; //spawn in speed of asteroids
    private double spawnAdd = 0.05; //adds onto the spawn rate
    private int iframes = 0; //iframes and istart are for invincibility frames
    private long istart = 0;
    private double powerSpawn = 0.002; //spawn rate of the powerups
    private double powerSpawnHold = 0; //used to hold the spawn rate when time is slowed
    private boolean pickedUp = false; //used to know if the time slow powerup is picked up
    private Node toRemAst = new Asteroid(); //asteroid object used for removing
    private Node toRemPower = new Powerup(); //powerup object used for removing
    private Node toRemShot = new ImageView(); //imageview object used for removing shots
    private int shotsLeft = 5; //number of shots remaining
    
    Timeline POWER_UP_TIME; //used later for powerup timer
    Duration time_hold; //holds the time for pausing
    int[] scores = new int[21]; //holds the scores
    String[] initial = new String[21]; //holds the corresponding initials
    
    private boolean newChunkSpawned = false; //checks to see if a new chunk has been spawned
    private PathFinder pf = new PathFinder(shipObj, NUM_LANES); //pathfinder so the game is not impossible to beat
    
    private STATE state = STATE.START; //starts the game in the start state

    private Parent createContent(){
        root = new Pane(); //initialize the pane
        initBackground(); //initializes the background
        root.setId("pane"); //set the ID of the pane for the CSS file
        HUD.setShots(shotsLeft); //sets the number of shots
        HUD.updateAmmo(); //updates the number of shots in the hud
        
        //reduces the window height is the resolution is too low
        if(screenSize.getHeight() <= 950)
            window_height = 800;
        shipObj.setW_Height(window_height);
        HUD.setHeight(window_height);
        
        shipObj.setW_Height(window_height);
        
        root.setPrefSize(window_width, window_height); //set the size of the pane
        
        shipDisplay = shipObj.initGraphics(); //calls the initShip method to load in the graphics for the ship
        
        //adds all of the background images to the pane
        root.getChildren().add(b1);
        root.getChildren().add(b2);
        root.getChildren().add(far_b1);
        root.getChildren().add(far_b2);
        root.getChildren().add(shipDisplay); //adds the shipDisplay to the root so it can be displayed

        //root.getChildren().add(left);
        
        timer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                onUpdate();
            }
        };
        timer.start();
        return root;  
    }
    
    //the following classes are just for initializing the different screens
    private Parent createHUD(){
        return HUD.initHUD();
    }
    
    private Parent createStartScreen(){
        return startScreen.initStartScreen();
    }
    
    private Parent createPauseScreen(){
        return pauseScreen.initPauseScreen();
    }
    
    private Parent createGameOverScreen(){
        return gameOver.initGameOver();
    }
    
    private Parent createControlScreen() {
        return controlScreen.initControlScreen();
    }
    
    private Parent createHighscoreScreen(){
        return highscoreScreen.initHighscoreScreen();
    }
    
    private void initBackground(){
        //sets the scaling and location of each background image
        b1 = new ImageView(bck);
        b1.setScaleY(1.5);
        b1.setScaleX(1.5);
        background.add(b1);
        b2 = new ImageView(bck);
        b2.setScaleX(1.5);
        b2.setScaleY(1.5);
        b2.setTranslateY(window_height);
        background.add(b2);
        
        far_b1 = new ImageView(bck);
        far_b1.setScaleY(1.1);
        far_b1.setScaleX(1.1);
        far_b1.setRotate(180);
        farbackground.add(far_b1);
        far_b2 = new ImageView(bck);
        far_b2.setScaleX(1.1);
        far_b2.setScaleY(1.1);
        far_b2.setRotate(180);
        far_b2.setTranslateY(window_height);
        farbackground.add(far_b2);
        
    }
    
    //spawns the asteroid using the pathfinder class
    private void spawnAsteroid(boolean[][] grid) {
        for(int i = 0; i < pf.getY(); i++) {
            for(int j = 0; j < pf.getX(); j++) {
                if(grid[j][i] == false && Math.random() < spawnRate) {
                    Asteroid ast = new Asteroid();
                    astImg = ast.initGraphics(); //gets the graphic loaded in the Asteroid class

                    int lane = j; //gets a lane number between 0-5

                    int obs_location = ast.getEdgeGap() + lane * (ast.getWidth() + ast.getMidGap()); //formula for calculating the X coordinate of the obstacle
                    astImg.setTranslateX(obs_location); //sets the x coordinate of the obstacle to obs_location
                    astImg.setTranslateY(-50 - (i + 1) * ast.getWidth() * 2);
                    root.getChildren().add(astImg); //adds the graphic for the asteroid to the root
                    asteroids.add(astImg);
                }
            }
        }
        newChunkSpawned = true;
    }
    
    private void spawnShot() {
        if(shotsLeft > 0) {
            sounds.playLaserSound();
            ImageView shot = new ImageView(laser); 
            shots.add(shot); 
            shot.setTranslateX(shipDisplay.getTranslateX() + 45); //sets the x position fo the shot right on the nose of the ship
            shot.setTranslateY(shipDisplay.getTranslateY()); //sets the y to the y of the ship
            root.getChildren().add(shot); //adds the shot to the display
            shotsLeft--;
            HUD.setShots(shotsLeft);
            HUD.updateAmmo();
        }
    }
    
    
    private ImageView spawnPowerup(){
        Powerup powerup = new Powerup();
        powerupImage = powerup.initGraphics();
        powerupImage.setX(powerup.getType());
        int plane = getLane();
        int p_location = powerup.getEdgeGap() + plane * (powerup.getWidth() + powerup.getMidGap()); //chooses the coordinates based on the lane
        powerupImage.setTranslateY(-window_height); //sets it to spawn way off screen so there's no visual bugs
        powerupImage.setTranslateX(p_location); 
        root.getChildren().add(powerupImage); //adds it to the root
        
        return powerupImage;
    }
    
    private void refreshHUD(){
        hudScene.getRoot().toFront(); //makes sure the HUD is always on top of the obstacles/ship
    }
    
    
    private int getLane(){
        return (int)((Math.random() * 100) % NUM_LANES); //returns a number between 0-5 to decide which lane to spawn the asteroids
    }
    
    private void onUpdate() {
        //update the background if it's NOT in the pause state
        if(state != STATE.PAUSE){
            backgroundUpdate();
        }
        
        //if it's in the game state, spawn all the asteroids and enable all the game echanics
        if(state == STATE.GAME){
            enableShip = true;
            shipDisplay.setVisible(true);
            asteroidUpdate();
            powerupUpdate();
            spawnUpdate();
            shotUpdate();
            refreshHUD();

            rand_hold = Math.random(); 
            
            //once the asteroid arraylist is less than 5, it will spawn a new chunk of asteroids
            if(asteroids.size() <= 5)
                newChunkSpawned = false;
            
            //spawn a new chunk if one isn't spawned
            if(newChunkSpawned == false) {
                    spawnAsteroid(pf.pathGen());
                    newChunkSpawned = true;
            }
            
            //spawn powerup if random number is below powerspawn
            if(rand_hold < powerSpawn && enableSlow == true){
                powerups.add(spawnPowerup());
            }
            
            //loop the game music if rand_hold < 0.5... this is just to make it update less often but this is still sufficient
            if(rand_hold < 0.5){
                sounds.loopGameMusic();
            }
            checkState();
        }

        if(state == STATE.PAUSE){
            enableShip = false;
        }
        
        if(state == STATE.START){
            sounds.loopStartMusic();
            shipDisplay.setVisible(false);
        }
        
    }
    
    private void asteroidUpdate(){
        for (Node asteroid : asteroids){
            asteroid.setTranslateY(asteroid.getTranslateY() + speed); //translates the asteroid down 12 pixels every update
            if(asteroid.getTranslateY() > window_height){
                root.getChildren().remove(asteroid);
                HUD.setScore(HUD.getScore() + 50);
                toRemAst = asteroid;
                spawnCount++;
            }
        }
        asteroids.remove(toRemAst);
    }
    
    private void powerupUpdate(){
        for (ImageView power : powerups){
            power.setTranslateY(power.getTranslateY() + speed); //translates the asteroid down 12 pixels every update
            if(power.getTranslateY() > window_height){
                toRemPower = power;
                root.getChildren().remove(power);
            }
        }
        powerups.remove(toRemPower);
    }
    
    private void backgroundUpdate(){
        //loops the background images
        for(Node bg : background){
            bg.setTranslateY(bg.getTranslateY() + speed/3); //moves faster because it's the "foreground"
            
            if(bg.getTranslateY() > window_height + 200)
                bg.setTranslateY(0-window_height);
        }
        
        for(Node bg : farbackground){
            bg.setTranslateY(bg.getTranslateY() + speed/4); //moves slower to give the perception of being far away
            
            if(bg.getTranslateY() > window_height + 90)
                bg.setTranslateY(0-window_height);
        }
    }
    
     private void shotUpdate() {
         //update the shot, moving it 15 px up everytime this method is called
        for(ImageView shot : shots) {
            shot.setTranslateY(shot.getTranslateY() - 15);
            if(shot.getTranslateY() < -50) {
                toRemShot = shot;
                root.getChildren().remove(shot);
            }
        }
        shots.remove(toRemShot);
    } 
    
    //update the spawn rate and speed if a certain number of asteroids has been spawned
    private void spawnUpdate(){
        if (speed <= 20) {//sets limit on spawning
            if (spawnCount >= 35) { //initial condition to add to spawnRate
                spawnRate += spawnAdd; //add to spawnRate if conditions are met
                spawnCount = 0; // reset spawn counter
                speed += speedAdd;
                System.out.println(speed);
            }   
        }
    }
    
    private void checkState(){
        HUD.updateScore();
        checkAsteroidState();
        checkPowerupState();
        checkShotState();
    }
    
    //check for collisions with the ship
    private void checkAsteroidState(){
        for (Node asteroid : asteroids) {
            if (System.currentTimeMillis() - istart > iframes) {
                shipObj.checkShipState();
                iframes = 0;
                if (isCollision(asteroid, shipDisplay)) { //checks for an intersection between the asteroid and the ship 
                    iframes = 2000;
                    istart = System.currentTimeMillis();
                    root.getChildren().remove(asteroid);
                    sounds.playShipHit();

                    shipObj.setShipState();
              
                    HUD.hasHit();
                    if(HUD.numHearts() <= 0){
                        state = STATE.OVER;
                        enableShip = false;
                        gameOver.setCurrentScore(HUD.getScore());
                        gameOver.updateGameOver();
                        timer.stop(); //stops the timer so asteroids no longer spawn
                        //saveScore();
                        gameOverScene.getRoot().setVisible(true);
                        gameOverScene.getRoot().toFront();
                    }
                } 
            }
            else {
                iframes--;
            }
        }
    }
    
    //check for collisions with the ship
    private void checkPowerupState(){
        for(ImageView power : powerups){
            if(isCollision(power, shipDisplay)){
                sounds.playPickUp();
                toRemPower = power;
                root.getChildren().remove(power);
                
                switch((int)power.getX()){
                    case 0: //slow time powerup
                        if(!pickedUp){
                        pickedUp = true;
                        HUD.hasSlowPower();
                        enableSlow = false;
                        speed = speed / 2;
                        spawnRate = spawnRate / 2;
                        powerSpawnHold = powerSpawn;
                        powerSpawn = 0;
                        timer();
                        }
                        break;
                    case 1: //heart powerup
                        if(HUD.numHearts() < 5)
                        HUD.hasHeartPower();
                        break;
                    case 2: //ammo drop powerup
                        if(shotsLeft < 9999)
                            shotsLeft += 5;
                        if(shotsLeft >= 9999)
                            shotsLeft = 9999;
                        HUD.setShots(shotsLeft);   
                        HUD.updateAmmo();
                        break;
                    default: break;
                }
            }
            
            //makes sure it's not overlapping an asteroid
            for(Node asteroid: asteroids){
                if(isCollision(power, asteroid)){
                    toRemAst = asteroid;
                    root.getChildren().remove(asteroid);
                }
            }
            asteroids.remove(toRemAst);
        }
        powerups.remove(toRemPower);
    }
    
    //checks for collisions with asteroids
    private void checkShotState(){
        
        for(ImageView shot : shots) {
            for(Node ast:asteroids){
                if(isCollision(shot, ast)){
                    sounds.playAsteroidKill();
                    toRemShot = shot;
                    toRemAst = ast;
                    HUD.setScore(HUD.getScore() + 100);
                    root.getChildren().remove(shot);
                    root.getChildren().remove(ast);
                }
            }
        }
        asteroids.remove(toRemAst);
        shots.remove(toRemShot);
    }
    
    private boolean isCollision(Node obstacle1, Node obstacle2){
        return obstacle1.getBoundsInParent().intersects(obstacle2.getBoundsInParent()); //returns whether or not the bounds intersect
    }
    
    
    
    int i;
    int k;
    
    //loads in the scores from the file
    private void loadScore(){
        int store = 0;
        String store2 = "";
        
        BufferedReader br;
        BufferedReader br2;
        i = 1;
        k = 1;
        scores[0] = HUD.getScore(); //sets the first initial and score slot to the new score/initial
        initial[0] = gameOver.getInitials();
        try {
            br = new BufferedReader(new FileReader(highscores));
            br2 = new BufferedReader(new FileReader(initials));
            String line = null;
            String line2 = null;
            try {
                //reads in all the data from the scores file
                while((line = br.readLine()) != null){
                    scores[i] = Integer.valueOf(line);
                    gameOver.setMinScore(scores[i]);
                    if((line.equals("")) == false);
                    i++; 
                }
                
                //does the same thing but with initials 
                while((line2 = br2.readLine()) != null){
                    initial[k] = line2;
                    if((line2.equals("")) == false);
                    k++;
                }
                
                //if i is less than 20 (# of highscores) then to get a highscore it needs to be greater than zero
                if(i < 20)
                    gameOver.setMinScore(0);
                
                highscoreScreen.setLength(i);
                if(i > 20){
                    i--; //i counts an extra time when leaving loop
                    k--;
                }
                br.close();
                br2.close();
            } catch (IOException ex) {
                Logger.getLogger(SpaceX33.class.getName()).log(Level.SEVERE, null, ex);
            }
        } catch (FileNotFoundException ex) {
            Logger.getLogger(SpaceX33.class.getName()).log(Level.SEVERE, null, ex);
        }
        //sorts the scores
        if(scores.length > 1){
        for(int j = 0; j < i; j++){
            for(int k = 0; k < i; k++){
                if(scores[k+1] > scores[k]){
                    store = scores[k];
                    store2 = initial[k];
                    
                    scores[k] = scores[k+1];
                    initial[k] = initial[k+1];
                    
                    scores[k+1] = store;
                    initial[k+1] = store2;
                }
            }
        }
        }
    }

    private void saveScore(){
        loadScore();
        
        FileWriter writer;
        FileWriter writer2;
        try {
            //writes the scores and initials to the file
            writer = new FileWriter(highscores, false);
            writer2 = new FileWriter(initials, false);
            for(int k = 0; k < i; k++){
                writer.write(scores[k] + "\n");
                writer2.write(initial[k] + "\n");
            }
            writer.close();
            writer2.close();
        } catch (IOException ex) {
            Logger.getLogger(SpaceX33.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    //sets the highscores for the highscore screen
    private void setHighscores(){
        highscoreScreen.setHighscores(scores, initial);
    }

    //resets all the variables when the game is reset
    private void reset(){
        for(Node asteroid : asteroids){
            root.getChildren().remove(asteroid);
        }
        for(Node powerup: powerups){
            root.getChildren().remove(powerup);
        }
        
        for(ImageView shot : shots)
            root.getChildren().remove(shot);
        
        asteroids.removeAll(asteroids);
        powerups.removeAll(powerups);
        root.getChildren().remove(HUD);
        speed = 7;
        spawnCount = 0;
        shotsLeft = 5;
        speedAdd = 0.1;
        spawnAdd = 0.05;
        spawnRate = 0.3; 
        
        newChunkSpawned = false;
        iframes = 0;
        istart = 0;
        time_hold = null;
        enableSlow = true;
        shipDisplay.setTranslateX(shipObj.getGap());
        if(shipObj.isHit())
        shipObj.setShipState();
        shipDisplay.setVisible(false);
        HUD.setShots(shotsLeft);
        HUD.reset();
        highscoreScreen.setHighscores(scores, initial);
    }
    
    //5 second timer for the slow time powerup
    private void timer() {
        POWER_UP_TIME = new Timeline (new KeyFrame(
        Duration.millis(5000),
        ae -> slowThings()));
        POWER_UP_TIME.play();
    }
    
    //reverts the speed once the timer runs out
    private void slowThings() {
        speed = speed * 2;
        spawnRate = spawnRate * 2;
        enableSlow = true;
        powerSpawn = powerSpawnHold;
        pickedUp = false;
    }

    
    @Override
    public void start(Stage stage) throws Exception {
        loadScore();
        setHighscores();
        stage.getIcons().add(new Image("file:resource/Images/ship.png"));
        stage.setTitle("SPACEX33");
        //initializes all the scenes
        gameScene = new Scene(createContent()); //creates the game scene
        hudScene = new Scene(createHUD()); //creates the HUD overlay
        startScene = new Scene(createStartScreen());
        pauseScene = new Scene(createPauseScreen());
        gameOverScene = new Scene(createGameOverScreen());
        controlScene = new Scene(createControlScreen());
        highscoreScene = new Scene(createHighscoreScreen());

        //adds all of them to the root
        ((Pane)gameScene.getRoot()).getChildren().add(startScene.getRoot()); //adds the start scene
        ((Pane)gameScene.getRoot()).getChildren().add(hudScene.getRoot());
        ((Pane)gameScene.getRoot()).getChildren().add(pauseScene.getRoot());
        ((Pane)gameScene.getRoot()).getChildren().add(gameOverScene.getRoot());
        ((Pane)gameScene.getRoot()).getChildren().add(controlScene.getRoot());
        ((Pane)gameScene.getRoot()).getChildren().add(highscoreScene.getRoot());
        
        //the only scene that is visible to begin with is the start scene
        hudScene.getRoot().setVisible(false);
        pauseScene.getRoot().setVisible(false);
        gameOverScene.getRoot().setVisible(false);
        controlScene.getRoot().setVisible(false);
        highscoreScene.getRoot().setVisible(false);
        
        gameScene.getStylesheets().addAll(this.getClass().getResource("style.css").toExternalForm()); //sets the stylesheet of the scene (used for the background image)

        stage.setScene(gameScene); 
        stage.setResizable(false); //makes it not possible to resize the window since this breaks the game
        
        if(state == STATE.OVER)
            stage.showAndWait();
        
        TranslateTransition shipSlide = new TranslateTransition(Duration.millis(100), shipDisplay); //sets up the ship to slide over the course of 100ms (0.1s). Change the Duration.millis to make the animation slower or faster
        //these are all the button inputs
        stage.getScene().setOnKeyPressed(event -> {
            switch (event.getCode()) {
                case LEFT:
                case A:
                    if(shipDisplay.getTranslateX() != shipObj.getGap() && enableShip){ //checks if the ship is not at the edge (10px away)
                        shipSlide.setByX(-(shipObj.getWidth() + shipObj.getGap())); //formula to calculate position the ship should slide to (negative becuse moving to the left)
                        shipSlide.play(); //plays animation
                    }
                    break;
                case RIGHT:
                case D:
                    if(shipDisplay.getTranslateX() != (window_width-shipObj.getGap()-shipObj.getWidth()) && enableShip){ //700 (total window size) - one gap at the furthermost edge - size of one ship
                        shipSlide.setByX(shipObj.getWidth() + shipObj.getGap()); //same formula as previously but positive
                        shipSlide.play(); //plays animation
                    }
                    break;
                case SPACE:
                    if(state == STATE.START)
                        start_to_game();
                    else if(state == STATE.GAME)
                        spawnShot();
                    break;
                case TAB:
                    if(state == STATE.GAME)
                        game_to_pause();
                    else if(state == STATE.SCORE)
                        exit_highscores();
                    else if(state == STATE.CONTROL)
                        exit_control();
                        
                    else if(state == STATE.PAUSE)
                            pause_to_game();
                    break;
                case R:
                    if(state == STATE.PAUSE)
                        pause_to_start();  
                    
                    if(state == STATE.OVER){
                        over_to_start();
                    }
                    break;
                case C:
                    if(state == STATE.START || state == STATE.PAUSE)
                        to_control();
                    break;
                case H:
                    if(state == STATE.START)
                        to_highscores();
                    break;
                case ESCAPE:
                    if(state == STATE.START || state == STATE.PAUSE){
                        sounds.stopAllSounds();
                        System.exit(3);
                    }
                    break;
                default:
                    break;
            }
        });
        stage.show();
    }
    
    public static enum STATE{
        START,
        GAME,
        PAUSE,
        OVER,
        CONTROL,
        SCORE
    };
    
    public static void main(String[] args) {
        launch(args);
    }
    
    
    
    //the methods from this point are used for when hotkeys/buttons are pressed
    
    //for going from the start scene to the game scene 
    private void start_to_game(){
        sounds.playButtonSelect();
        sounds.pauseStartMusic();
        sounds.playGameMusic();
        startScene.getRoot().setVisible(false);
        hudScene.getRoot().setVisible(true);
        //PathFinder pf = new PathFinder(shipObj, NUM_LANES);
        //pf.pathGen();
        state = STATE.GAME;
        start_pause_none = -1;
    }
    
    //for going from the game scene to the pause scene
    private void game_to_pause(){
        state = STATE.PAUSE;
        sounds.playButtonSelect();
        sounds.pauseGameMusic();
        start_pause_none = 1;
        
        pauseScene.getRoot().setVisible(true);
        pauseScene.getRoot().toFront();
        if(!enableSlow){
            time_hold = POWER_UP_TIME.getCurrentTime();
            POWER_UP_TIME.stop();
        }
    }
    
    //for going between the pause scene and the game scene
    private void pause_to_game(){
        state = STATE.GAME;
        start_pause_none = -1;
        pauseScene.getRoot().setVisible(false);
        controlScene.getRoot().setVisible(false);
        if(!enableSlow)
            POWER_UP_TIME.playFrom(time_hold);
    }
    
    //for going between the pause scene and the start scene
    private void pause_to_start(){
        reset();
        sounds.playButtonSelect();
        pauseScene.getRoot().setVisible(false);
        hudScene.getRoot().setVisible(false);
        startScene.getRoot().setVisible(true);
        state = STATE.START;
        start_pause_none = 0;
        if(!enableSlow)
            time_hold = null;
    }
    
    //for going from the over scene to the start scene
    private void over_to_start(){
        saveScore();
        reset();
        sounds.playButtonSelect();
        sounds.pauseGameMusic();
        gameOverScene.getRoot().setVisible(false);
        hudScene.getRoot().setVisible(false);
        state = STATE.START;
        start_pause_none = 0;
        gameOver.reset();
        startScene.getRoot().setVisible(true);
        timer.start();
    }
    
    //for going to the control scene
    private void to_control(){
        sounds.playButtonSelect();
        controlScene.getRoot().setVisible(true);
        controlScene.getRoot().toFront();
        state = STATE.CONTROL;
    }
    
    //for exiting the control scene
    private void exit_control(){
        controlScene.getRoot().setVisible(false);
        if(start_pause_none == 0)
            state = STATE.START;
        if(start_pause_none == 1)
            state = STATE.PAUSE;
    }
    
    //for going to the highscore scene
    private void to_highscores(){
        sounds.playButtonSelect();
        highscoreScene.getRoot().setVisible(true);
        state = STATE.SCORE;
    }
    
    //for exiting the highscore scene
    private void exit_highscores(){
        highscoreScene.getRoot().setVisible(false);
        state = STATE.START;
    }
}

