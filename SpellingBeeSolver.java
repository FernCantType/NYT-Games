import java.util.*;
import java.io.*;
/**
 * Solves the NYT Spelling Bee game
 * 
 * @author Lorenzo Canali
 * @version 11/11/2024
 */
public class SpellingBeeSolver {
    private char[] letters;
    private char specL;
    private Scanner sc;
    private ArrayList<String> words;
    private int totalPoints;
    private String playerRank;
    /**
     * Constructs a LetterBoxSolver object. It also fills all the fields through local intialization and 
     * internal method calls. It then starts the recursive nextRound() method.
     * 
     * @param letters the letters that are allowed in the game
     */
    SpellingBeeSolver(char[] letters, char specialLetter) {
        try{
            sc = new Scanner(new File("words.txt"));
            this.letters = letters;
            totalPoints = 0;
            specL = specialLetter;
            playerRank = "Beginner";
            words = new ArrayList<>();
            addWords();
            words = sort(words);
            nextRound(words);
        }
        catch (IOException e) {
            System.out.println("FILE NOT FOUND");
        }

    }
    /**
     * Add the words to the set that only have the letters allowed inside them
     */
    private void addWords() {
        Set<Character> avaChars = new HashSet<>();
        for(char ch : letters) {
            avaChars.add(ch);
        }
        while(sc.hasNext()) {
            String line = sc.next();
            boolean isValid = true;
            if(!avaChars.contains(specL)) {
                isValid = false;
            }
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
     * It finds the worth of the word in points. A four letter word is 1 point and words 5 or more are worth
     * however many letters they have.
     * 
     * @param word the word to check to see how many points it's worth
     * @return the amount of points the word is worth
     */
    private int findPoints(String word) {
        if(word.length() == 4) {
            return 1;
        }
        else {
            return word.length();
        }
    }
    /**
     * Sorts the words in the list by how many points each word is worth
     * 
     * @param list the list of words to be sorted
     * @return the same list but with the words sorted 
     */
    private ArrayList<String> sort(ArrayList<String> list) {
        for (int i = 0; i < list.size(); i++) {
            int greatestIndex = i;
            for (int p = i + 1; p < list.size(); p++) {
                if(findPoints(list.get(p)) < findPoints(list.get(greatestIndex))) {
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
     * Prints all the words that would work for the 
     * 
     * @param words the list of words to be printed
     */
    private void printWords(ArrayList<String> words) {
        System.out.println(words);
    }
    /**
     * Keeps playing until the user gets enough points to win
     * 
     * @param list the list of words to be played with
     */
    private void nextRound(ArrayList<String> list) {
        printWords(list);
        Scanner scanner = new Scanner(System.in);
        System.out.println("Print out the word of your choosing. To close, type nothing: ");
        String wordChoice = scanner.nextLine().toUpperCase();
        if(wordChoice.equals("")) {
            scanner.close();
            System.exit(0);
        }
        list.remove(wordChoice);
        totalPoints += findPoints(wordChoice);
        String oldRank = playerRank;
        findNewRank();
        if(! oldRank.equals(playerRank)) {
            System.out.println("You made it to a new rank! You are now a " + playerRank);
        }
        else{
            System.out.println("You are still a " + playerRank);
        }
        nextRound(list);
    }
    /**
     * Finds the rank of the player based off the rubric on spelling bee nyt
     */
    private void findNewRank() {
        if(totalPoints >= 162) {
            playerRank = "Genius";
        }
        if(totalPoints >= 116) {
            playerRank = "Amazing";
        }
        if(totalPoints >= 92) {
            playerRank = "Great";
        }
        if(totalPoints >= 58) {
            playerRank = "Nice";
        }
        if(totalPoints >= 35) {
            playerRank = "Solid";
        }
        if(totalPoints >= 18) {
            playerRank = "Good";
        }
        if(totalPoints >= 12) {
            playerRank = "Moving Up";
        }
        if(totalPoints >= 5) {
            playerRank = "Good Start";
        }
    }
    /**
     * Main arg
     */
    public static void main(String[] args) {
        char specialLetter = 'M';
        char[] letters = new char[] {specialLetter,
                                    'A', 'T', 'P', 'O', 'E', 'L'};
        @SuppressWarnings("unused")
        SpellingBeeSolver sbs = new SpellingBeeSolver(letters, specialLetter);
    }
}
