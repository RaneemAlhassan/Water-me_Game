import java.io.*;
import java.util.*;
class StoreLevelsData {
    public static String levelsString;
    public static File myFile = new File("levelsDoneData.txt");
    public static void Initialization(){
        try {
            if (myFile.createNewFile()) {
                FileWriter myWriter = new FileWriter(myFile.getName());
                myWriter.write("0000000000");
                myWriter.close();
            }
            Scanner myReader = new Scanner(myFile);
            levelsString = myReader.nextLine();
            myReader.close();
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }
    public static void doneLevelStore(int levelNum){
        try {
            FileWriter myWriter = new FileWriter(myFile.getName());
            levelsString = changeChar(levelsString, levelNum-1, '1');
            myWriter.write(levelsString);
            myWriter.close();
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }
    public static void rerun(){
        LevelsData.locked[0]=true;
        for(int i=1;i<10;i++){
            LevelsData.locked[i]=false;
        }
        try {
            FileWriter myWriter = new FileWriter(myFile.getName());
            levelsString = "0000000000";
            myWriter.write(levelsString);
            myWriter.close();
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }
    public static String changeChar(String s, int ind, char ch){
        char[] arrayOfChars = s.toCharArray();
        arrayOfChars[ind] = ch;
        return String.valueOf(arrayOfChars);
    }
}