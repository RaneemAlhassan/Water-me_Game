import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.io.*;
import javax.sound.sampled.*;
import java.util.Random;
import java.util.concurrent.*;
class MusicStuff{
    static public Clip mainClip, buttonClip, rotateClip, lockedClip, winClip, loseClip;
    static void playEffect(Clip clip, String s, float vol){
        if(!Game.muteb)
            return ;
        try{
            File musicP = new File(NewGame.path+s);
            if(musicP.exists()){
                AudioInputStream auI = AudioSystem.getAudioInputStream(musicP);
                clip = AudioSystem.getClip();  clip.start();  clip.open(auI);
                FloatControl gainControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
                float range = gainControl.getMaximum() - gainControl.getMinimum();
                float gain = (range * vol) + gainControl.getMinimum();
                gainControl.setValue(gain);
                clip.loop(0);
            } else {
                System.out.println("can't find file");
            }
        } catch(Exception ex){
            ex.printStackTrace();
        }
    }
    static void playMainMusic(){
        if(!Game.muteb)
            return ;
        try{
            File musicP = new File(NewGame.path+"Game music loop.wav");
            if(musicP.exists()){
                AudioInputStream auI = AudioSystem.getAudioInputStream(musicP);
                mainClip = AudioSystem.getClip();  mainClip.start();  mainClip.open(auI);
                FloatControl gainControl = (FloatControl) mainClip.getControl(FloatControl.Type.MASTER_GAIN);
                float range = gainControl.getMaximum() - gainControl.getMinimum();
                float gain = (range * 0.9f) + gainControl.getMinimum();
                gainControl.setValue(gain);
                mainClip.loop(Clip.LOOP_CONTINUOUSLY);
            } else {
                System.out.println("can't find file");
            }
        } catch(Exception ex){
            ex.printStackTrace();
        }
    }
    static void playButtonMusic(){
        playEffect(buttonClip, "button.wav", 0.8f);
    }
    static void playRotateMusic(){
        playEffect(rotateClip, "rotate.wav", 0.85f);
    }
    static void playLockedMusic(){
        playEffect(lockedClip, "locked.wav", 0.9f);
    }
    static void playWinMusic(){
        playEffect(winClip, "Win Tone.wav", 0.85f);
    }
    static void playLoseMusic(){
        playEffect(loseClip, "lose sound.wav", 0.85f);
    }
}