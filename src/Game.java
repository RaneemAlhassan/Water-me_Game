import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.io.*;
import javax.sound.sampled.*;
import java.util.Random;
import java.util.concurrent.*;
class Game extends JFrame{
    public static CardLayout card;
    public static Container cont;
    public static boolean muteb = true;
    public static int levelMumber;
    public static Start startPanel = new Start();
    public static Levels levelsPanel = new Levels();
    public static Play playPanel = new Play();
    public static StoreLevelsData storeLevelsData;
    Game()
    {
        cont = getContentPane();
        card = new CardLayout();
        cont.setLayout(card);
        cont.add("Start", startPanel);
        cont.add("Levels", levelsPanel);
        cont.add("Play", playPanel);
        card.show(cont, "Start");
        StoreLevelsData.Initialization();
        LevelsData.fillLevelsData();
        Initialization();
    }
    public void Initialization(){
        setSize(1500,800);
        setResizable(false);
        Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
        setLocation((int) ((dimension.getWidth() - 1500) / 2), (int) ((dimension.getHeight() - 800) / 2) - 20);
        setFocusable(true);
        requestFocusInWindow();
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setVisible(true);
    }
    static public void gotoLevelsPanel(){
        levelsPanel.rerunb = false;
        card.show(cont, "Levels");
    }
    static public void startAPlay(int levelMum){
        levelMumber = levelMum;
        playPanel.fillDataTable();
        card.show(cont, "Play");
    }
    static public void gotoStartPanel(){
        card.show(cont, "Start");
    }
}