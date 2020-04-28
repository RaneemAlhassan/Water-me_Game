import java.awt.*;
import java.awt.event.*;
import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.image.BufferedImage;
import java.io.*;
import javax.sound.sampled.*;
import java.util.EmptyStackException;
import java.util.Random;
import java.util.concurrent.*;
class GUI extends JPanel{

    //coordinates
    public int x=50;
    public int y=50;
    public int p=155;

    //cell's width and height
    public int cellw = 150;
    public int cellh = 150;
    public int borderWidth = 10;
    //colors
    public final Color watercolor = new Color(131,189,225) ;
    public final Color shapecolor = new Color(103, 105, 109);
    public final Color cellcolor = new Color(222, 220, 226);
    public final Color fontcolor = new Color(255, 253, 253);
    public final Color shadow = new Color(144, 149, 157);
    public final Color dialogcolor = new Color(226, 231, 227);
    //image
    public BufferedImage image;

    public int cellx,celly;
    public void drawDoneLevels(Graphics g){
        int ofx = 315;
        int ofy = 300;
        for(int j = 0; j < 2; j++)
        {
            for(int i = 0; i < 5; i++)
            {
                int levelNum = (i+1+(5*j));
                if(StoreLevelsData.levelsString.charAt(levelNum-1)=='1'){
                    g.setColor(new Color(60, 200, 60));
                    g.fillPolygon(new int[]{20+ofx+(i*200), 8+ofx+(i*200), 12+ofx+(i*200), 20+ofx+(i*200), 36+ofx+(i*200), 42+ofx+(i*200)}, new int[]{50+ofy+(j*220), 35+ofy+(j*220), 30+ofy+(j*220), 38+ofy+(j*220), 17+ofy+(j*220), 23+ofy+(j*220)}, 6);
                }
            }
        }
    }
    public void drawLevelsTable(Graphics g){
        Font font = new Font("SansSerif", Font.BOLD, 100);
        g.setFont(font);
        Graphics2D g2 = (Graphics2D) g;
        for(int j = 0; j < 2; j++)
        {
            for(int i = 0; i < 5; i++)
            {
                g.setColor(shadow);
                g2.fillRoundRect(215+(i*200), 210+(j*220), 150, 150,40, 40);
                g.setColor(shapecolor);
                g2.fillRoundRect(220+(i*200), 215+(j*220), 150, 150,40, 40);
                g.setColor(fontcolor);
                int levelNum = (i+1+(5*j));
                if(levelNum!=10){
                    g.drawString(""+levelNum, 265+(i*200), 315+(j*220));
                }
                else {
                    g.drawString("" + levelNum, 230 + (i * 200), 315 + (j * 220));
                }
            }
        }
    }
    public void showMessage(Graphics g,String s,String chose1,String chose2,int sx,int x1,int y1,int x2,int y2
            ,Color base,Color font){

        drawButton(g,400,290,570,220,shadow,new Color(152, 175, 190),false);
        drawButton(g,410,300,550,200,shadow,base,false);
        drawButtonString(g,s,sx,360,font,25);
        drawButton(g,485,420,180,60,shadow,new Color(152, 175, 190),false);
        drawButton(g,490,425,170,50,shadow,base,false);
        drawButton(g,705,420,180,60,shadow,new Color(152, 175, 190),false);
        drawButton(g,710,425,170,50,shadow,base,false);
        drawButtonString(g,chose1,x1,y1,font,25);
        drawButtonString(g,chose2,x2,y2,font,25);
    }
    public void showSingleMessage(Graphics g,String s,String chose,int sx,int x1,int y1
            ,Color base,Color font){

        drawButton(g,400,290,570,220,shadow,new Color(152, 175, 190),false);
        drawButton(g,410,300,550,200,shadow,base,false);
        drawButtonString(g,s,sx,360,font,25);
        drawButton(g,595,420,180,60,shadow,new Color(152, 175, 190),false);
        drawButton(g,600,425,170,50,shadow,base,false);
        drawButtonString(g,chose,x1,y1,font,25);
    }
    public void drawLock(Graphics g){
        int k=0;
        for(int j = 0; j < 2; j++) {
            for (int i = 0; i < 5; i++) {
                cellx = 260 + (i * 200);
                celly = 330 + (j * 220);
                if (LevelsData.locked[k])
                    setBackground(g, "unlock.png", cellx - 35, celly - 15);
                else
                    setBackground(g, "lock.png", cellx - 35, celly - 15);
                k++;
            }
        }
    }
    public void drawButton(Graphics g,int x,int y,int buttonw,int buttonh,Color shadow,Color buttoncolor,boolean sh){
        Graphics2D g2 = (Graphics2D) g;
        if(sh) {
            g.setColor(shadow);
            g2.fillRoundRect(x - 4, y - 4, buttonw, buttonh, buttonw / 5, buttonh / 5);
        }
        g.setColor(buttoncolor);
        g2.fillRoundRect(x, y, buttonw, buttonh,buttonw/5, buttonh/5);
    }
    public void drawTank(Graphics g,int tw, int plantWater){
        g.setColor(watercolor);
        Graphics2D g2 = (Graphics2D) g;
        g2.fillRoundRect(1270,290-(180*tw/100),160,180*tw/100,10,10);
        g.setColor(watercolor);
        drawButtonString(g,tw+"%",1315,100,fontcolor,30);
        g.setColor(new Color(255, 50, 50));
        g.fillPolygon(new int[]{1444, 1414, 1444}, new int[]{(278-(180*plantWater/100)), (292-(180*plantWater/100)), (307-(180*plantWater/100))}, 3);
        g.fillOval(1435, 277-(180*plantWater/100),30, 30);
        drawButtonString(g,""+plantWater,1437, 300-(180*plantWater/100),Color.white,20);
    }
    public void drawButtonString(Graphics g,String s,int x,int y,Color fontcolor,int size){
        g.setColor(fontcolor);
        Font font = new Font("SansSerif", Font.BOLD, size);
        g.setFont(font);
        g.drawString(s,x,y);
    }
    public void setBackground(Graphics g,String imagename,int x,int y) {
        try {
            image = ImageIO.read(new File(NewGame.path+imagename));
            g.drawImage(image, x, y, this);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void drawShape(Graphics g, int x, int y, char type, int direction, boolean water){
        if(type=='I'){
            drawIShape(g, x, y, direction, water);
        }
        else if(type=='+'){
            drawPlusShape(g, x, y, water);
        }
        else if(type=='L'){
            drawLShape(g, x, y, direction, water);
        }
        else if(type=='T'){
            drawTShape(g, x, y, direction, water);
        }
    }
    public void drawIShape(Graphics g, int x, int y, int direction, boolean water){
        g.setColor(cellcolor);
        Graphics2D g2 = (Graphics2D) g;
        g2.fillRoundRect(x, y, cellw, cellh,cellw/5, cellw/5);
        g.setColor(shapecolor);
        if(direction==1||direction==3){
            g.fillRect((int)(x+cellw/4),y,cellw/2,cellh);
        }
        else if(direction==0||direction==2){
            g.fillRect(x,(int)(y+cellh/4),cellw,cellh/2);
        }
        if(water){
            g.setColor(watercolor);
            if(direction==1||direction==3){
                g.fillRect((int)(x+cellw/4+borderWidth),y,cellw/2-(2*borderWidth),cellh);
            }
            else if(direction==0||direction==2){
                g.fillRect(x,(int)(y+cellh/4+borderWidth),cellw,cellh/2-(2*borderWidth));
            }
        }
        g.setColor(shapecolor);
        if(direction==1||direction==3){
            g.fillRect(x+3*cellw/16, y, 5*cellw/8,cellh/32);
            g2.fillRoundRect(x+3*cellw/16, y, 5*cellw/8,cellw/16,cellw/8, cellh/8);
            g.fillRect(x+3*cellw/16, y+31*cellh/32, 5*cellw/8,cellh/32);
            g2.fillRoundRect(x+3*cellw/16, y+15*cellw/16, 5*cellw/8,cellh/16,cellw/8, cellh/8);
        }
        else if(direction==0||direction==2){
            g.fillRect(x+31*cellw/32,y+3*cellh/16,cellw/32,5*cellh/8);
            g2.fillRoundRect(x+15*cellw/16,   y+3*cellh/16,   cellw/16,   5*cellh/8,   cellw/8, cellh/8);
            g.fillRect(x,y+3*cellh/16,cellw/32,5*cellh/8);
            g2.fillRoundRect(x,   y+3*cellh/16,   cellw/16,   5*cellh/8,   cellw/8, cellh/8);
        }
    }
    public void drawPlusShape(Graphics g, int x, int y, boolean water){
        g.setColor(cellcolor);
        Graphics2D g2 = (Graphics2D) g;
        g2.fillRoundRect(x, y, cellw, cellh,cellw/5, cellw/5);
        g.setColor(shapecolor);
        g.fillRect((int)(x+cellw/4),y,cellw/2,cellh);
        g.fillRect(x,(int)(y+cellh/4),cellw,cellh/2);
        if(water){
            g.setColor(watercolor);
            g.fillRect((int)(x+cellw/4+borderWidth),y,cellw/2-(2*borderWidth),cellh);
            g.fillRect(x,(int)(y+cellw/4+borderWidth),cellw,cellh/2-(2*borderWidth));
        }
        g.setColor(shapecolor);
        g.fillRect(x+3*cellw/16, y, 5*cellw/8,cellh/32);
        g2.fillRoundRect(x+3*cellw/16, y, 5*cellw/8,cellw/16,cellw/8, cellh/8);
        g.fillRect(x+31*cellw/32,y+3*cellh/16,cellw/32,5*cellh/8);
        g2.fillRoundRect(x+15*cellw/16,   y+3*cellh/16,   cellw/16,   5*cellh/8,   cellw/8, cellh/8);
        g.fillRect(x+3*cellw/16, y+31*cellh/32, 5*cellw/8,cellh/32);
        g2.fillRoundRect(x+3*cellw/16, y+15*cellw/16, 5*cellw/8,cellh/16,cellw/8, cellh/8);
        g.fillRect(x,y+3*cellh/16,cellw/32,5*cellh/8);
        g2.fillRoundRect(x,   y+3*cellh/16,   cellw/16,   5*cellh/8,   cellw/8, cellh/8);
    }
    public void drawLShape(Graphics g, int x, int y, int direction, boolean water){
        g.setColor(cellcolor);
        Graphics2D g2 = (Graphics2D) g;
        g2.fillRoundRect(x, y, cellw, cellh,cellw/5, cellw/5);
        g.setColor(shapecolor);
        if(direction==0){
            g.fillRect((int)(x+cellw/4), y, cellw/2,cellw*3/4);
            g.fillRect((int)(x+cellw/4),(int)(y+cellh/4),cellw*3/4,cellh/2);
        }
        else if(direction==1){
            g.fillRect((int)(x+cellw/4), y, cellw/2,cellw*3/4);
            g.fillRect(x,(int)(y+cellh/4),cellw*3/4,cellh/2);
        }
        else if(direction==2){
            g.fillRect((int)(x+cellw/4), (int)(y+cellh/4), cellw/2,cellw*3/4);
            g.fillRect(x,(int)(y+cellh/4),cellw*3/4,cellh/2);
        }

else if(direction==3){
            g.fillRect((int)(x+cellw/4), (int)(y+cellh/4), cellw/2,cellw*3/4);
            g.fillRect((int)(x+cellw/4),(int)(y+cellh/4),cellw*3/4,cellh/2);
        }
        if(water){
            g.setColor(watercolor);
            if(direction==0){
                g.fillRect((int)(x+cellw/4+borderWidth), y, cellw/2-(2*borderWidth),cellw*3/4-borderWidth);
                g.fillRect((int)(x+cellw/4+borderWidth),(int)(y+cellh/4+borderWidth),cellw*3/4-borderWidth,cellh/2-(2*borderWidth));
            }
            else if(direction==1){
                g.fillRect((int)(x+cellw/4+borderWidth), y, cellw/2-(2*borderWidth),cellw*3/4-borderWidth);
                g.fillRect(x,(int)(y+cellh/4+borderWidth),cellw*3/4-borderWidth,cellh/2-(2*borderWidth));
            }
            else if(direction==2){
                g.fillRect((int)(x+cellw/4+borderWidth), (int)(y+cellh/4+borderWidth), cellw/2-(2*borderWidth),cellh*3/4-borderWidth);
                g.fillRect(x,(int)(y+cellh/4+borderWidth),cellw*3/4-borderWidth,cellh/2-(2*borderWidth));
            }
            else if(direction==3){
                g.fillRect((int)(x+cellw/4+borderWidth), (int)(y+cellh/4+borderWidth), cellw/2-(2*borderWidth),cellh*3/4-borderWidth);
                g.fillRect((int)(x+cellw/4+borderWidth),(int)(y+cellh/4+borderWidth),cellw*3/4-borderWidth,cellh/2-(2*borderWidth));
            }
        }
        g.setColor(shapecolor);
        if(direction==0){
            g.fillRect(x+3*cellw/16, y, 5*cellw/8,cellh/32);
            g2.fillRoundRect(x+3*cellw/16, y, 5*cellw/8,cellh/16,cellw/8, cellh/8);
            g.fillRect(x+31*cellw/32,y+3*cellh/16,cellw/32,5*cellh/8);
            g2.fillRoundRect(x+15*cellw/16,   y+3*cellh/16,   cellw/16,   5*cellh/8,   cellw/8, cellh/8);
        }
        else if(direction==1){
            g.fillRect(x+3*cellw/16, y, 5*cellw/8,cellh/32);
            g2.fillRoundRect(x+3*cellw/16, y, 5*cellw/8,cellh/16,cellw/8, cellh/8);
            g.fillRect(x,y+3*cellh/16,cellw/32,5*cellh/8);
            g2.fillRoundRect(x,   y+3*cellh/16,   cellw/16,   5*cellh/8,   cellw/8, cellh/8);
        }
        else if(direction==2){
            g.fillRect(x+3*cellw/16, y+31*cellh/32, 5*cellw/8,cellh/32);
            g2.fillRoundRect(x+3*cellw/16, y+15*cellw/16, 5*cellw/8,cellh/16,cellw/8, cellh/8);
            g.fillRect(x,y+3*cellh/16,cellw/32,5*cellh/8);
            g2.fillRoundRect(x,   y+3*cellh/16,   cellw/16,   5*cellh/8,   cellw/8, cellh/8);
        }
        else if(direction==3){
            g.fillRect(x+3*cellw/16, y+31*cellh/32, 5*cellw/8,cellh/32);
            g2.fillRoundRect(x+3*cellw/16, y+15*cellw/16, 5*cellw/8,cellh/16,cellw/8, cellh/8);
            g.fillRect(x+31*cellw/32,y+3*cellh/16,cellw/32,5*cellh/8);
            g2.fillRoundRect(x+15*cellw/16,   y+3*cellh/16,   cellw/16,   5*cellh/8,   cellw/8, cellh/8);
        }
    }
    public void drawTShape(Graphics g, int x, int y, int direction, boolean water){
        g.setColor(cellcolor);
        Graphics2D g2 = (Graphics2D) g;
        g2.fillRoundRect(x, y, cellw, cellh,cellw/5, cellw/5);
        g.setColor(shapecolor);
        if(direction==0){
            g.fillRect((int)(x+cellw/4), y, cellw/2,cellh);
            g.fillRect((int)(x+cellw/4),(int)(y+cellh/4),cellw*3/4,cellh/2);
        }
        else if(direction==1){
            g.fillRect((int)(x+cellw/4), y, cellw/2,cellw*3/4);
            g.fillRect(x,(int)(y+cellh/4),cellw,cellh/2);
        }
        else if(direction==2){
            g.fillRect((int)(x+cellw/4), y, cellw/2,cellh);
            g.fillRect(x,(int)(y+cellh/4),cellw*3/4,cellh/2);
        }
        else if(direction==3){
            g.fillRect((int)(x+cellw/4), (int)(y+cellh/4), cellw/2,cellw*3/4);
            g.fillRect(x,(int)(y+cellh/4),cellw,cellh/2);
        }
        if(water){
            g.setColor(watercolor);
            if(direction==0){
                g.fillRect((int)(x+cellw/4+borderWidth), y, cellw/2-(2*borderWidth),cellh);

                g.fillRect((int)(x+cellw/4+borderWidth),(int)(y+cellh/4+borderWidth),cellw*3/4-borderWidth,cellh/2-(2*borderWidth));
            }
            else if(direction==1){
                g.fillRect((int)(x+cellw/4+borderWidth), y, cellw/2-(2*borderWidth),cellw*3/4-borderWidth);
                g.fillRect(x,(int)(y+cellh/4+borderWidth), cellw, cellh/2-(2*borderWidth));
            }
            else if(direction==2){
                g.fillRect((int)(x+cellw/4+borderWidth), y, cellw/2-(2*borderWidth),cellh);
                g.fillRect(x,(int)(y+cellh/4+borderWidth),cellw*3/4-borderWidth,cellh/2-(2*borderWidth));
            }
            else if(direction==3){
                g.fillRect((int)(x+cellw/4+borderWidth), (int)(y+cellh/4+borderWidth), cellw/2-(2*borderWidth),cellh*3/4-borderWidth);
                g.fillRect(x,(int)(y+cellh/4+borderWidth), cellw, cellh/2-(2*borderWidth));
            }
        }
        g.setColor(shapecolor);
        if(direction==0){
            g.fillRect(x+3*cellw/16, y, 5*cellw/8,cellh/32);
            g2.fillRoundRect(x+3*cellw/16, y, 5*cellw/8,cellw/16,cellw/8, cellh/8);
            g.fillRect(x+31*cellw/32,y+3*cellh/16,cellw/32,5*cellh/8);
            g2.fillRoundRect(x+15*cellw/16,   y+3*cellh/16,   cellw/16,   5*cellh/8,   cellw/8, cellh/8);

            g.fillRect(x+3*cellw/16, y+31*cellh/32, 5*cellw/8,cellh/32);
            g2.fillRoundRect(x+3*cellw/16, y+15*cellw/16, 5*cellw/8,cellh/16,cellw/8, cellh/8);
        }
        else if(direction==1){
            g.fillRect(x+3*cellw/16, y, 5*cellw/8,cellh/32);
            g2.fillRoundRect(x+3*cellw/16, y, 5*cellw/8,cellw/16,cellw/8, cellh/8);
            g.fillRect(x+31*cellw/32,y+3*cellh/16,cellw/32,5*cellh/8);
            g2.fillRoundRect(x+15*cellw/16,   y+3*cellh/16,   cellw/16,   5*cellh/8,   cellw/8, cellh/8);
            g.fillRect(x,y+3*cellh/16,cellw/32,5*cellh/8);
            g2.fillRoundRect(x,   y+3*cellh/16,   cellw/16,   5*cellh/8,   cellw/8, cellh/8);
        }
        else if(direction==2){
            g.fillRect(x+3*cellw/16, y, 5*cellw/8,cellh/32);
            g2.fillRoundRect(x+3*cellw/16, y, 5*cellw/8,cellw/16,cellw/8, cellh/8);
            g.fillRect(x+3*cellw/16, y+31*cellh/32, 5*cellw/8,cellh/32);
            g2.fillRoundRect(x+3*cellw/16, y+15*cellw/16, 5*cellw/8,cellh/16,cellw/8, cellh/8);
            g.fillRect(x,y+3*cellh/16,cellw/32,5*cellh/8);
            g2.fillRoundRect(x,   y+3*cellh/16,   cellw/16,   5*cellh/8,   cellw/8, cellh/8);
        }
        else if(direction==3){
            g.fillRect(x+31*cellw/32,y+3*cellh/16,cellw/32,5*cellh/8);
            g2.fillRoundRect(x+15*cellw/16,   y+3*cellh/16,   cellw/16,   5*cellh/8,   cellw/8, cellh/8);
            g.fillRect(x+3*cellw/16, y+31*cellh/32, 5*cellw/8,cellh/32);
            g2.fillRoundRect(x+3*cellw/16, y+15*cellw/16, 5*cellw/8,cellh/16,cellw/8, cellh/8);
            g.fillRect(x,y+3*cellh/16,cellw/32,5*cellh/8);
            g2.fillRoundRect(x,   y+3*cellh/16,   cellw/16,   5*cellh/8,   cellw/8, cellh/8);
        }
    }
    public void drawLable(Graphics g, String s){
        Font font = new Font("SansSerif", Font.BOLD, 50);
        g.setFont(font);
        g.setColor(shadow);
        g.drawString(s, 472, 97);
        g.setColor(fontcolor);
        g.drawString(s, 475, 100);
    }
}