import java.awt.*;
import java.awt.event.*;
import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.image.BufferedImage;
import java.io.*;
import javax.sound.sampled.*;
import java.util.Random;
import java.util.Scanner;
import java.util.concurrent.*;
import java.awt.geom.Ellipse2D;

public class Levels extends GUI {
    Levels() {
        addMouseListener(new MouseAdapterLevels());
    }
    public boolean rerunb=false;
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        setBackground(g, "levels.png", 0, 0);
        if (Game.muteb)
            setBackground(g, "speaker.png", 58, 55);
        else
            setBackground(g, "mute.png", 58, 55);
        setBackground(g,"back.png",17,64);
        setBackground(g,"rerun.png",115,67);
        drawLable(g, "Levels:");
        drawLevelsTable(g);
        drawLock(g);
        drawDoneLevels(g);
        if(rerunb){
            showMessage(g,"Are you sure you want to start a new game?","Yes","Cancel"
                    ,422 ,555,460,758,460,dialogcolor,shapecolor);
        }
    }
    static public int levelNum;
    class MouseAdapterLevels extends MouseAdapter {
        public void mousePressed(MouseEvent e) {
            if (e.getX() >= 115 && e.getX() <= 140 && e.getY() >= 67 && e.getY() <= 92) {
                MusicStuff.playButtonMusic();
                rerunb=true;
                repaint();
            }
            if(e.getX() >= 480 && e.getX() <= 650 && e.getY() >= 425 && e.getY() <= 475&&rerunb){
                MusicStuff.playButtonMusic();
                StoreLevelsData.rerun();
                rerunb=false;
                repaint();
                return ;
            }
            if(e.getX() >= 700 && e.getX() <= 870 && e.getY() >= 425 && e.getY() <= 475&&rerunb){
                MusicStuff.playButtonMusic();
                rerunb=false;
                repaint();
                return ;
            }
            if (e.getX() >= 58 && e.getX() <= 108 && e.getY() >= 55 && e.getY() <= 110&&!rerunb) {
                Game.muteb = !Game.muteb;
                if(!Game.muteb)
                    MusicStuff.mainClip.stop();
                else
                    MusicStuff.playMainMusic();
                MusicStuff.playButtonMusic();
                repaint();
            }
            if(e.getX()>=5 && e.getY()>= 55 &&e.getX()<=55 && e.getY()<=110&&!rerunb){
                MusicStuff.playButtonMusic();
                Game.gotoStartPanel();
            }
            for (int j = 0; j < 2; j++) {
                for (int i = 0; i < 5; i++) {
                    if (220 + (i * 200) <= e.getX() && 215 + (j * 220) <= e.getY() && 370 + (i * 200) >= e.getX() && 365 + (j * 220) >= e.getY()&&!rerunb) {
                        levelNum = (i + 1 + (5 * j));
                        if(levelNum==1){
                            MusicStuff.playButtonMusic();
                            Game.startAPlay(levelNum);
                        } else{
                            if(LevelsData.locked[levelNum-1]) {
                                MusicStuff.playButtonMusic();
                                Game.startAPlay(levelNum);
                            }else
                                MusicStuff.playLockedMusic();
                        }
                    }
                }
            }
        }
    }
}