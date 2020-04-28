import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.io.*;
import javax.sound.sampled.*;
import java.util.Random;
import java.util.concurrent.*;
import java.util.ArrayList;
class LevelsData {
    public static ArrayList<char[][]> blockTypeData = new ArrayList<char[][]> ();
    static public boolean locked[]={true,false,false,false,false,false,false,false,false,false};
    static public int levelsPlantWater[]={80, 60, 70, 54, 60, 62, 50, 36, 32, 30};
    static void fillLevelsData(){
        for(int i = 0; i < 9; i++){
            locked[i+1] = (StoreLevelsData.levelsString.charAt(i)=='1');
        }
        char[][] blockType1 = {
                {'I', 'I', 'I'},
                {'L', '+', 'I'},
                {'I', 'L', 'L'},
                {'L', 'L', 'L'},
        };
        char[][] blockType2 = {
                {'L', 'T', 'L'},
                {'I', 'L', 'L'},
                {'T', 'L', 'I'},
                {'I', 'L', 'L'},
        };
        char[][] blockType3 = {
                {'I', 'I', 'L', 'L'},
                {'L', 'I', 'I', 'T'},
                {'I', 'L', 'L', 'L'},
                {'L', 'L', '+', 'L'},
                {'I',  'L', 'T', 'I'}
        };
        char[][] blockType4 = {
                {'L', 'L', 'L', 'T'},
                {'L', '+', 'T', 'L'},
                {'+', 'I', 'I', '+'},
                {'L', '+', 'T', 'L'},
                {'T',  'L', 'L', 'L'}
        };
        char[][] blockType5 = {
                {'I', 'L', 'I', 'L'},
                {'L', 'L', 'L', 'I'},
                {'L', 'L', 'I', 'L'},
                {'I', 'L', 'L', 'I'},
                {'I',  'L', 'I', 'L'}
        };
        char[][] blockType6 = {
                {'L', 'L', 'T', 'I'},
                {'I', 'L', 'L', 'I'},
                {'I', 'L', 'L', 'I'},
                {'I', 'I', 'L', 'L'},
                {'I',  'L', 'L', 'L'}
        };
        char[][] blockType7 = {
                {'L', 'L', 'I', 'I', 'I'},
                {'I', 'I', 'L', 'L', 'L'},
                {'I', 'L', 'I', 'T', 'L'},
                {'L', 'I', 'L', '+', 'I'},
                {'L',  'I', 'T', 'L', 'I'},
                {'I',  'L', 'L', 'I', 'L'}
        };
        char[][] blockType8 = {
                {'L', 'L', 'L', 'L', 'I'},
                {'I', 'L', 'L', 'I', 'I'},
                {'I', 'L', 'L', 'L', 'L'},
                {'L', 'T', 'T', 'I', 'L'},
                {'I',  'L', 'I', 'L', 'I'},
                {'L',  'L', 'I', 'L', 'L'}
        };
        char[][] blockType9 = {
                {'L', 'I', 'L', 'L', 'L'},
                {'L', 'L', 'I', 'I', 'I'},
                {'L', 'L', 'I', 'L', 'L'},
                {'I', 'L', 'L', 'L', 'L'},
                {'I',  'T', 'L', 'I', 'T'},
                {'T',  'L', 'L', 'L', 'L'}
        };
        char[][] blockType10 = {
                {'L', 'L', 'L', 'I', 'I'},
                {'L', 'L', 'I', 'L', 'L'},
                {'I', 'L', 'L', 'L', 'L'},
                {'L', '+', 'I', 'T', 'T'},
                {'L',  'T', 'L', 'L', 'I'},
                {'I',  'L', 'T', 'I', 'L'}
        };
        blockTypeData.add(blockType1);
        blockTypeData.add(blockType2);
        blockTypeData.add(blockType3);
        blockTypeData.add(blockType4);
        blockTypeData.add(blockType5);
        blockTypeData.add(blockType6);
        blockTypeData.add(blockType7);
        blockTypeData.add(blockType8);
        blockTypeData.add(blockType9);
        blockTypeData.add(blockType10);
    }
}