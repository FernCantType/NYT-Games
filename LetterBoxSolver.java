import java.io.*;
import java.util.*;
/**
 * Solves the NYT Letter Box game
 * 
 * @author Lorenzo Canali
 * @version 11/10/2024
 */
public class LetterBoxSolver extends NYTGames {
    private char[][] dimension;
    private ArrayList<String> words;
    private Scanner sc;
    private ArrayList<Character> charsLeft;
    /**
     * Constructs a LetterBoxSolver object. It also fills all the fields through local intialization and 
     * internal method calls. It then starts the recursive nextRound() method.
     * 
     * @param row1 the first for 
     * @param row2
     * @param row3
     * @param row4
     */
    public LetterBoxSolver(char[] row1, char[] row2, char[] row3, char[] row4) {
        try {
            sc = new Scanner(new File("words.txt"));
            dimension = new char[][] {row1, row2, row3, row4};
            charsLeft = new ArrayList<>();
            for (char[] chars : dimension) {
                for (char ch : chars) {  
                    charsLeft.add(ch);  
                }
            }
            words = new ArrayList<>();
            addWords();
            refineWords();
            words = sort(words);
            nextRound(words);
        }
        catch(IOException e) {
            System.out.println("FILE NOT FOUND");
        }
        
    }
    /**
     * Adds only the neccesary words to the words ArrayList<> by seeing if it contains only the letters of
     * dimensions
     */
    private void addWords() {
        Set<Character> avaChars = new HashSet<>();
        for (char[] chars : dimension) {
            for (char ch : chars) {
                avaChars.add(ch);  
            }
        }
        while (sc.hasNext()) {
            String line = sc.next();
            boolean isValid = true;
            for (char ch : line.toCharArray()) {
                if (!avaChars.contains(ch)) {
                    isValid = false;
                    break;
                }
            }
            if (isValid) {
                words.add(line);
            }
        }
    }
    
    /**
     * Finds the words that have consecutive letters and removes them
     */
    private void refineWords() {
        Iterator<String> it = words.iterator();
        while (it.hasNext()) {
            String word = it.next();
            for (int i = 0; i < word.length() - 1; i++) {
                if (whatArray(word, i) == whatArray(word, i + 1)) {
                    it.remove();
                    break;
                }
            }
        }
    }
    /**
     * Finds the array of the letter of the word and returns the array that it is in
     * 
     * @return the index of the array it is found in
     * @param word the word to search
     * @param indexOfChar the index of the char you want to find
     */
    private int whatArray(String word, int indexOfChar) {
        for (int p = 0; p < dimension.length; p++) {
            for (int k = 0; k < dimension[p].length; k++) {
                if (word.charAt(indexOfChar) == dimension[p][k]) {
                    return p;  
                }
            }
        }
        return -1; 
    }
    /**
     * Sorts the words in the list by how many special characters each word has
     * 
     * @param list the list of words to be sorted
     * @return the same list but with the words sorted 
     */
    protected ArrayList<String> sort(ArrayList<String> list) {
        for (int i = 0; i < list.size(); i++) {
            int greatestIndex = i;
            for (int p = i + 1; p < list.size(); p++) {
                if(numOfSpecChars(list.get(p)) < numOfSpecChars(list.get(greatestIndex))) {
                    greatestIndex = p;
                }
            }
            String change = list.get(i); 
            list.set(i, list.get(greatestIndex)); 
            list.set(greatestIndex, change);
        }
        return list;
    }
    /**
     * Find words that fit the criteria of the next word
     * 
     * @param word
     * @return the words that fit the criteria
     */
    private ArrayList<String> nextWords(String sWord) {
        ArrayList<String> validWords = new ArrayList<>();
        for(String word : words) {
            if(word.charAt(0) == sWord.charAt(sWord.length() -1)) {
                validWords.add(word);
            }
        }
        validWords = sort(validWords);
        return validWords;
    }
    /**
     * Next round of the game then calls itself
     * 
     * @param list the words to be printed every time
     */
    private void nextRound(ArrayList<String> list) {
        printWords(list);
        System.out.println(charsLeft);
        Scanner scanner = new Scanner(System.in);
        System.out.println("Print out the word of your choosing: ");
        String wordChoice = scanner.nextLine().toUpperCase();
        removeChars(wordChoice);
        if(charsLeft.size() == 0) {
            scanner.close();
            System.out.println("YOU WON GOOD JOB");
        }
        else {
            nextRound(sort(nextWords(wordChoice)));
        }
    }
    /**
     * Removes all the character from the needed character spot
     * 
     * @param word the word that is being played so the characters of that word need to be removed
     */
    private void removeChars(String word) {
        Iterator<Character> it = charsLeft.iterator();
        while(it.hasNext()) {
            char specC = it.next();
            if(word.indexOf(specC) >= 0) {
                it.remove();
            }
        }
    }
    /**
     * Amount of new characters hit with the word
     * 
     * @param word the word to see how many characters are hit
     * @return the number of special characters inside
     */
    private int numOfSpecChars(String word) {
        int numOfSpecChars = 0;
        for(char ch : charsLeft) {
            if(word.indexOf(ch) >= 0) {
                numOfSpecChars++;
            }
        }
        return numOfSpecChars;
    }
    /**
     * Prints all the words that would work for the 
     * 
     * @param words the list of words to be printed
     */
    private void printWords(ArrayList<String> words) {
        if(words.isEmpty()) {
            System.out.println("YOU LOST BY TECHNICALITY; NO WORDS APPLICABLE!");
            System.exit(0);
        }
        else {            
            System.out.println(words);
        }
    }
    /**
     * Main method to run
     * 
     * @args I don't know what it does it is just part of the unique syntax of a main method
     */
    public static void main(String[] args) {
        char[] row1 = new char[] {'T','V','M'};
        char[] row2 = new char[] {'D','U','L'};
        char[] row3 = new char[] {'E','I','B'};
        char[] row4 = new char[] {'Y','J','N'};
        @SuppressWarnings("unused") //I don't know what this does VS just told me to add it. I think it 
        LetterBoxSolver lbs = new LetterBoxSolver(row1, row2, row3, row4);
    }
}                                      