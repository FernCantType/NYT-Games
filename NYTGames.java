import java.io.*;
import java.util.*;

/**
 * A super class that contains the words so I only need to read the file once while storing the
 * words for all the classes
 * 
 * @author Lorenzo Canali
 * @version 4/20/2025
 * @updated
 */
public abstract class NYTGames {
    private static ArrayList<String> allWords;

    //Constructor method
    public NYTGames () {
        readFile();
    }

    //words.txt file read in method
    private void readFile() {

    }

    //Sort abstract method
    protected  abstract ArrayList<String> sort(ArrayList<String> list);

    //Menu method
    private void menu() {
        
    }

    public static void main(String args) {

    }
}
