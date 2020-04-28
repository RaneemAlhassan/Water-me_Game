import javax.swing.*;
import java.awt.*;
import java.io.*;
public class NewGame {
    public static String path = "";
    public static void main(String[] args) {
        getPath();
        Game game = new Game();
    }
    public static void getPath(){
        String nameClass = "NewGame.class";
        path = NewGame.class.getClassLoader().getResource(nameClass).getPath();
        if(path.charAt(0)=='f'&&path.charAt(1)=='i')
            path = path.substring(6, path.length()-nameClass.length());
        else
            path= path.substring(1, path.length()-nameClass.length());
        path = path.replace("%20", " ");
        path = path.replace("/", "\\");
        if(path.charAt(path.length()-2)=='!')
            path = path.substring(0, path.length()-nameClass.length());
        path += "Data\\";
        System.out.println(path);
    }
}