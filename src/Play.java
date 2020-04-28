import java.awt.*;
import java.awt.event.*;
import javax.imageio.ImageIO;
import javax.swing.*;
import java.io.*;
import javax.sound.sampled.*;
import java.util.EmptyStackException;
import java.util.Random;
import java.util.concurrent.*;
class Play extends GUI {
    Play(){
        addMouseListener(new MouseAdapterPlay());
    }
    public Random rend = new Random();
    public int blockDirectionTable[][];
    public char blockTypeTable[][];
    static public boolean winGame = false;
    static public boolean loseGame = false;
    static public boolean messageAppear = false;
    public boolean blockFillWater[][];
    public boolean blockConnectedVis[][];
    public int xOfTable = 400, yOfTable = 130;
    public int cellh, cellw;
    //
    static public int plantWater = 50;
    static public int tankWater = 100;
    static public int decrementWater = 2;
    //
    public void fillDataTable(){
        tankWater = 100;
        loseGame=false;
        messageAppear = false;
        blockTypeTable = LevelsData.blockTypeData.get(Game.levelMumber-1);
        if(blockTypeTable.length==5&&blockTypeTable[0].length==4){
            cellh = super.cellh = 150;  cellw = super.cellw = 150;
            xOfTable = 400; yOfTable = 130;
        } else if(blockTypeTable.length==4&&blockTypeTable[0].length==3){
            cellh = super.cellh = 190; cellw = super.cellw = 190;
            xOfTable = 400; yOfTable = 140;
        } else if(blockTypeTable.length==6&&blockTypeTable[0].length==5){
            cellh = super.cellh = 120; cellw = super.cellw = 120;
            xOfTable = 425; yOfTable = 135;
        }
        plantWater = LevelsData.levelsPlantWater[Game.levelMumber-1];
        blockDirectionTable = new int[blockTypeTable.length][blockTypeTable[0].length];
        blockFillWater = new boolean[blockTypeTable.length][blockTypeTable[0].length];
        blockConnectedVis = new boolean[blockTypeTable.length][blockTypeTable[0].length];
        do{
            winGame=false;
            for(int i = 0; i < blockTypeTable.length; i++){
                for(int j = 0; j < blockTypeTable[0].length; j++){
                    blockDirectionTable[i][j] = rend.nextInt(4);
                    blockFillWater[i][j] = false;
                }
            }
            checkWin(true);
        } while(winGame);
        repaint();
    }
    public void fillWithWater(int x, int y, int parentDirection){
        if(getOpen(blockTypeTable[x][y], blockDirectionTable[x][y], parentDirection)){
            blockFillWater[x][y] = true;
            if(x!=0){
                if(getOpen(blockTypeTable[x][y], blockDirectionTable[x][y], 0)&&!blockFillWater[x-1][y]){
                    fillWithWater(x-1, y, 2);
                }
            }
            if(y!=0){
                if(getOpen(blockTypeTable[x][y], blockDirectionTable[x][y], 1)&&!blockFillWater[x][y-1]){
                    fillWithWater(x, y-1, 3);
                }
            }
            if(x!=blockTypeTable.length-1){
                if(getOpen(blockTypeTable[x][y], blockDirectionTable[x][y], 2)&&!blockFillWater[x+1][y] ){
                    fillWithWater(x+1, y, 0);
                }
            }
            if(y!=blockTypeTable[0].length-1){
                if(getOpen(blockTypeTable[x][y], blockDirectionTable[x][y], 3)&&!blockFillWater[x][y+1] ){
                    fillWithWater(x, y+1, 1);
                }
            }
        }
        return;
    }
    public boolean checkConnected(int x, int y, int parentDirection){
        if(getOpen(blockTypeTable[x][y], blockDirectionTable[x][y], parentDirection)){
            blockConnectedVis[x][y] = true;
            boolean ret = true;
            boolean boolval = getOpen(blockTypeTable[x][y], blockDirectionTable[x][y], 0);
            if(x!=0){
                if(boolval&&!blockConnectedVis[x-1][y]){
                    ret &= checkConnected(x-1, y, 2);
                }
                else if(boolval&&blockConnectedVis[x-1][y])
                    ret &= getOpen(blockTypeTable[x-1][y], blockDirectionTable[x-1][y], 2);
            }

else if(boolval&&y!=blockTypeTable[0].length-1){
                ret = false;
            }
            boolval = getOpen(blockTypeTable[x][y], blockDirectionTable[x][y], 1);
            if(y!=0){
                if(boolval&&!blockConnectedVis[x][y-1]){
                    ret &= checkConnected(x, y-1, 3);
                }
                else if(boolval&&blockConnectedVis[x][y-1])
                    ret &= getOpen(blockTypeTable[x][y-1], blockDirectionTable[x][y-1], 3);
            }
            else if(boolval){
                ret = false;
            }
            boolval = getOpen(blockTypeTable[x][y], blockDirectionTable[x][y], 2);
            if(x!=blockTypeTable.length-1){
                if(boolval&&!blockConnectedVis[x+1][y]){
                    ret &= checkConnected(x+1, y, 0);
                }
                else if(boolval&&blockConnectedVis[x+1][y])
                    ret &= getOpen(blockTypeTable[x+1][y], blockDirectionTable[x+1][y], 0);
            }
            else if(boolval&&y!=0){
                ret = false;
            }
            boolval = getOpen(blockTypeTable[x][y], blockDirectionTable[x][y], 3);
            if(y!=blockTypeTable[0].length-1){
                if(boolval&&!blockConnectedVis[x][y+1]){
                    ret &= checkConnected(x, y+1, 1);
                }
                else if(boolval&&blockConnectedVis[x][y+1])
                    ret &= getOpen(blockTypeTable[x][y+1], blockDirectionTable[x][y+1], 1);
            }
            else if(boolval){
                ret = false;
            }
            return ret;
        }
        else{
            return false;
        }
    }
    public boolean getOpen(char type, int blockDirection, int inDirection){
        if(type=='+'){
            return true;
        } else if(type=='L'){
            boolean open[][] = {
                    {false, true, true, false},
                    {true, true, false, false},
                    {true, false, false, true},
                    {false, false, true, true}
            };
            return open[inDirection][blockDirection];
        } else if(type=='I'){
            boolean open[][] = {
                    {true, false, true, false},
                    {false, true, false, true},
                    {true, false, true, false},
                    {false, true, false, true}
            };
            return open[inDirection][blockDirection];
        } else if(type=='T'){
            boolean open[][] = {
                    {false, true, true, true},
                    {true, true, true, false},
                    {true, true, false, true},
                    {true, false, true, true}
            };
            return open[inDirection][blockDirection];
        }
        return false;
    }
    public void checkLose(){
        if(tankWater<plantWater)
            loseGame=true;
    }
    public void checkWin(boolean fast){
        winGame = tankWater>=plantWater;
        winGame &= checkConnected(blockTypeTable.length-1, 0, 2);
        winGame &= getOpen(blockTypeTable[blockTypeTable.length-1][0], blockDirectionTable[blockTypeTable.length-1][0], 2);
        winGame &= getOpen(blockTypeTable[0][blockTypeTable[0].length-1], blockDirectionTable[0][blockTypeTable[0].length-1], 0);
        winGame &= blockFillWater[0][blockTypeTable[0].length-1];
        if(winGame&&!false){
            MusicStuff.playWinMusic();
            Game.storeLevelsData.doneLevelStore(Game.levelMumber);
            if(Game.levelMumber!=10){
                LevelsData.locked[Game.levelMumber]=true;
            }
        }
    }
    public void setBack(Graphics g){
        Graphics2D g2 = (Graphics2D) g;
        if(blockTypeTable.length==4&&blockTypeTable[0].length==3){
            if(winGame) {
                setBackground(g, "win12.png", 0, 0);
            }
            else if(loseGame) {
                setBackground(g, "lose12.png", 0, 0);
            }
            else
                setBackground(g,"play12.png",0,0);
        }
        else if(blockTypeTable.length==5&&blockTypeTable[0].length==4){

            if(winGame) {
                setBackground(g, "win3456.png", 0, 0);
            }
            else if(loseGame) {
                setBackground(g, "lose3456.png", 0, 0);
            }
            else
                setBackground(g,"play3456.png",0,0);
        }
        else{
            if(winGame) {
                setBackground(g, "win78910.png", 0, 0);
            }
            else if(loseGame) {
                setBackground(g, "lose78910.png", 0, 0);
            }
            else
                setBackground(g,"play78910.png",0,0);
        }
        if (Game.muteb)
            setBackground(g, "speaker.png", 58, 55);
        else{
            setBackground(g, "mute.png", 58, 55);
        }
        setBackground(g,"back.png",17,64);
        setBackground(g,"repeat.png",107,59);
        drawTank(g,tankWater, plantWater);
        g.setColor(shapecolor);
        g.fillRect(xOfTable+(blockTypeTable.length*(super.cellw+5))-5,yOfTable+3*cellh/16,cellw/32,5*cellh/8);
        g2.fillRoundRect(xOfTable+(blockTypeTable.length*(super.cellw+5))-5,   yOfTable+3*cellh/16,   cellw/16,   5*cellh/8,   cellw/8, cellh/8);
    }
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        fillWithWater(blockTypeTable.length-1, 0, 2);
        checkWin(false);
        checkLose();
        setBack(g);
        drawLable(g, "Level "+Game.levelMumber+":");
        for(int i = 0; i < blockTypeTable.length; i++){
            for(int j = 0; j < blockTypeTable[0].length; j++){
                drawShape(g, xOfTable+(i*(super.cellw+5)), yOfTable+(j*(super.cellh+5)), blockTypeTable[i][j], blockDirectionTable[i][j], blockFillWater[i][j]);
            }
        }
        showDialog(g);
    }
    public void showDialog(Graphics g){
        if(winGame&&Game.levelMumber!=10) {
            messageAppear = true;
            showMessage(g, "CONGRATULATIONS!!", "Back", "Next",
                    550,543, 460, 768, 460,dialogcolor,shapecolor);
        }
        else if(winGame&&Game.levelMumber==10) {
            messageAppear = true;
            showSingleMessage(g, "CONGRATULATIONS!!", "Back",
                    550,655, 460, dialogcolor,shapecolor);
        }
        if(loseGame){
            messageAppear = true;
            MusicStuff.playLoseMusic();
            showMessage(g, "HARD LUCK :(", "Back", "Repeat",
                    600, 542, 460, 758, 460,dialogcolor,shapecolor);
        }
    }
    class MouseAdapterPlay extends MouseAdapter {
        public void mousePressed(MouseEvent e) {
            if (winGame&&Game.levelMumber!=10) {
                if (e.getX() >= 480 && e.getX() <= 650 && e.getY() >= 425 && e.getY() <= 475) {
                    MusicStuff.playButtonMusic();
                    Game.gotoLevelsPanel();
                    messageAppear = false;
                    repaint();
                    return ;
                }
                if (e.getX() >= 700 && e.getX() <= 870 && e.getY() >= 425 && e.getY() <= 475) {
                    MusicStuff.playButtonMusic();
                    Game.startAPlay(Game.levelMumber + 1);
                    messageAppear = false;
                    repaint();
                    return ;
                }
            } else if(winGame&&Game.levelMumber==10) {
                if (e.getX() >= 595 && e.getX() <= 775 && e.getY() >= 420 && e.getY() <= 480) {
                    MusicStuff.playButtonMusic();
                    Game.gotoLevelsPanel();
                    messageAppear = false;
                    repaint();
                    return ;
                }
            } else if (loseGame) {
                if (e.getX() >= 480 && e.getX() <= 650 && e.getY() >= 425 && e.getY() <= 475) {
                    MusicStuff.playButtonMusic();
                    Game.gotoLevelsPanel();
                    messageAppear = false;
                    repaint();
                    return ;
                }
                if (e.getX() >= 700 && e.getX() <= 870 && e.getY() >= 425 && e.getY() <= 475){
                    MusicStuff.playButtonMusic();
                    Game.startAPlay(Game.levelMumber);
                    messageAppear = false;
                    repaint();
                    return ;
                }
            }
            if (e.getX() >= 107 && e.getX() <= 152 && e.getY() >= 57 && e.getY() <= 102&&!messageAppear) {
                MusicStuff.playButtonMusic();
                Game.startAPlay(Game.levelMumber);
                repaint();
                return ;
            }
            if (e.getX() >= 61 && e.getX() <= 102 && e.getY() >= 55 && e.getY() <= 110&&!messageAppear) {
                Game.muteb = !Game.muteb;
                if (!Game.muteb)
                    MusicStuff.mainClip.stop();
                else
                    MusicStuff.playMainMusic();
                MusicStuff.playButtonMusic();
                repaint();
            }
            if (e.getX() >= 10 && e.getY() >= 55 && e.getX() <= 60 && e.getY() <= 110&&!messageAppear) {
                MusicStuff.playButtonMusic();
                Game.gotoLevelsPanel();
                repaint();
            }
            boolean changeFound = false;
            for (int j = 0; j < blockTypeTable[0].length; j++) {
                for (int i = 0; i < blockTypeTable.length; i++) {
                    if (xOfTable + (i * (cellw + 5)) <= e.getX() && yOfTable + (j * (cellh + 5)) <= e.getY() && xOfTable + ((i + 1) * (cellw + 5)) >= e.getX() && yOfTable + ((j + 1) * (cellh + 5)) >= e.getY()&&!messageAppear) {
                        MusicStuff.playRotateMusic();
                        blockDirectionTable[i][j] = (blockDirectionTable[i][j] + 1) % 4;
                        //System.out.println(""+i+" "+j+" "+blockDirectionTable[i][j]);
                        tankWater -= decrementWater;
                        changeFound = true;
                    }
                    blockFillWater[i][j] = false;
                    blockConnectedVis[i][j] = false;
                }
            }
            if(changeFound)
                repaint();
        }
    }
}