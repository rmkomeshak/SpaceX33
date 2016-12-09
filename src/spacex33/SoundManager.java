/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package spacex33;

import javafx.scene.media.AudioClip;

/**
 *
 * @author asdas
 */
public class SoundManager {
    private AudioClip laserSound = new AudioClip("file:resource/Sounds/laser.wav");
    private AudioClip buttonSelect = new AudioClip("file:resource/Sounds/button_select.wav");
    private AudioClip pickUp = new AudioClip("file:resource/Sounds/pick_up.aiff");
    private AudioClip asteroidKill = new AudioClip("file:resource/Sounds/asteroid_kill.aiff");
    private AudioClip shipHit = new AudioClip("file:resource/Sounds/ship_hit.wav");
    private AudioClip gameMusic = new AudioClip("file:resource/Sounds/music_2.mp3");
    private AudioClip startMusic = new AudioClip("file:resource/Sounds/music_3.mp3");
    
    public SoundManager(){
        laserSound.setVolume(0.7);
        pickUp.setVolume(0.3);
        asteroidKill.setVolume(0.3);
        shipHit.setVolume(0.35);
        gameMusic.setVolume(0.2);
        startMusic.setVolume(0.7);
    }
    
    public void playLaserSound(){
        laserSound.play();
    }
    
    public void playButtonSelect(){
        buttonSelect.play();
    }
    
    public void playPickUp(){
        pickUp.play();
    }
    
    public void playAsteroidKill(){
        asteroidKill.play();
    }
    
    public void playShipHit(){
        shipHit.play();
    }
    
    public void playGameMusic(){
        gameMusic.play();
    }
    
    public void loopGameMusic(){
        if(!gameMusic.isPlaying())
            gameMusic.play();
    }
    
    public void loopStartMusic(){
        if(!startMusic.isPlaying())
            startMusic.play();
    }
    
    public void pauseGameMusic(){
        gameMusic.stop();
    }
    
    public void pauseStartMusic(){
        startMusic.stop();
    }
    
    public void stopAllSounds(){
        laserSound.stop();
        buttonSelect.stop();
        pickUp.stop();
        asteroidKill.stop();
        shipHit.stop();
        gameMusic.stop();
        startMusic.stop();
    }
}
