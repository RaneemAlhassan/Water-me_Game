import java.awt.*;
import java.awt.event.*;
import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.image.BufferedImage;
import java.io.*;
import javax.sound.sampled.*;
import java.util.Random;
import java.util.concurrent.*;
import java.awt.geom.Ellipse2D;

class Start extends GUI {
    Start(){
        MusicStuff.playMainMusic();
        addMouseListener(new MouseAdapterStart());
    }
    public void paintComponent(Graphics g) {
        p = 75;

        cellh = 170;
        cellw = 100;
        borderWidth = 10;
        super.paintComponent(g);
        setBackground(g,"waterme.png",-3,0);
        drawButton(g,1260,580, 2*cellw+20, cellh/3+18,shadow,watercolor,false);
        drawButtonString(g,"PLAY",1275,645,fontcolor,70);
    }
    class MouseAdapterStart extends MouseAdapter{
        public void mousePressed(MouseEvent e) {
            if(e.getX()>=1260&&e.getY()>=580&&e.getX()<=1280+2*cellw&&e.getY()<=598+cellh/3){
                MusicStuff.playButtonMusic();
                Game.gotoLevelsPanel();
            }
        }
    }
}