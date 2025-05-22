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
    private Scanner sc;
    private ArrayList<NaryTree> trees;
    private HashMap<String, ArrayList<String>> table;
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
            for (char ch : chars) avaChars.add(ch);  
        }

        while (sc.hasNext()) {
            String line = sc.next();
            boolean isValid = true;

            for (char ch : line.toCharArray()) {
                if (! avaChars.contains(ch)) {
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
     * Finds the words that have consecutive letters and removes them. Uses whatArray method to find the side each character is one to make sure
     * it is valid
     */
    private void refineWords() {
        Iterator<String> it = words.iterator();

        while (it.hasNext()) {
            String word = it.next();

            for (int i = 0; i < word.length() - 1; i++) {
                if (whatArray(word.charAt(i)) == whatArray(word.charAt(i + 1))) {
                    it.remove();
                    break;
                }
            }
        }
    }
    /**
     * Finds the array of the letter of the word and returns the array that it is in. Works like a contain method but outputs the side.
     * 
     * @return the index of the side it is found in or -1 if not found
     * @param character the character to find the side it is on
     */
    private int whatArray(char character) {
        //4 sides
        for (int p = 0; p < dimension.length; p++) {
            //3 characters per side
            for (int k = 0; k < dimension[p].length; k++) {

                if (character == dimension[p][k]) {
                    return p;  
                }
            }
        }
        return -1; //Not in word
    }
    /**
     * Sorts the words in the list by how many special characters each word has. Change to Insertion later, currently bubble
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

            String temp = list.get(i); 
            list.set(i, list.get(greatestIndex)); 
            list.set(greatestIndex, temp);
        }
        return list;
    }
    /**
     * Find words that fit the criteria of the next word after a word is played
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
     * Removes all the character in the last plauyed word from the needed character list
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
     * Amount of new characters in a specified word.
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
        } else System.out.println(words);
    }
    /**
     * Main method to run
     * 
     * @args I don't know what it does it is just part of the unique syntax of a main method
     */
    public static void main(String[] args) {
        char[] row1 = new char[] {'W','G','N'};
        char[] row2 = new char[] {'H','A','I'};
        char[] row3 = new char[] {'K','L','O'};
        char[] row4 = new char[] {'E','T','B'};
        @SuppressWarnings("unused") //I don't know what this does VS just told me to add it. I think it 
        LetterBoxSolver lbs = new LetterBoxSolver(row1, row2, row3, row4);
    }
}                                      