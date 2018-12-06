import java.util.*;
import java.lang.*;

/**
 * AUTHOR: AARON POWELL UPDATED: 24-10-18 TESTER NOTE: You can use Generate full
 * board (option 8) to create monsters when youre bored of creating them
 * individually when testing other methods.
 * 
 * CRITIQUE: please read further comments, especially in board class where I
 * evaluate previous code, and explain how I could improve on other areas.
 * _________________________________________________________________ Since the
 * data pool is small, using a for loop and a single if statement to determine
 * whether the space is empty to place a monster is quite effective. To make the
 * code more scaleable I would need to loop through and add empty coordinates to
 * an array. Thus only having one loop ever.
 * 
 * Using TWO methods with TWO nested for loops each, this algorithm to find a
 * coordinate of a monster, is NOT effective. It works for this program however,
 * with a larger game, I would store the coordinates in the monster class with
 * the getPosition method and then call of that when needed.
 * 
 * My leaderboard was over complicated and simplified by using a single lambda
 * expression, reducting 55 lines of code to 4 lines.
 * 
 * I did use compound && and nested ifs for the validation of inputs to make the
 * code have better correctness rather than efficiency and rely on user input.
 */
public class Board {
    private final int SIZE = 6;
    private char board[][];
    private Random rand;
    private ArrayList<Monster> monsterList;
    private boolean finished = false;

    /**
     * Constructor fills the board initially with * and sets up the surrounding
     * edges / hedge with "-, |"
     */
    public Board() {
        monsterList = new ArrayList<>();
        board = new char[SIZE][SIZE];
        rand = new Random();

        for (int i = 1; i <= SIZE - 1; i++) {
            for (int j = 1; j <= SIZE - 1; j++)
                board[i][j] = '*';
        }

        for (int i = 0; i < SIZE; i++) {
            board[0][i] = '-';
            if (i == 0 || i == SIZE - 1) {
                board[i][0] = '-';
            } else {
                board[i][0] = '|';
            }
            board[i][SIZE - 1] = '|';
            board[SIZE - 1][i] = '-';
            board[0][0] = '\u00A0';
            board[SIZE - 1][SIZE - 1] = '\u00A0';
            board[0][SIZE - 1] = '\u00A0';
            board[SIZE - 1][0] = '\u00A0';
        }
    }

    /**
     * @param: String Checks whether the input of quit is a Y or an N and if it is
     *         quits, or continues. Else it will request a new input and call the
     *         method again with that new input.
     */

    public void endGame(String quit) {
        boolean valid = false;
        if (quit.toUpperCase().equals("Y")) {
            System.out.println("Thank you for playing, the game will now close.");
            finished = true;
            valid = true;
        }
        if (quit.toUpperCase().equals("N")) {
            System.out.println("You've chosen to keep playing!");
            valid = true;
        }
        if (!valid) {
            System.out.println("Please enter either Y or N.");
            Scanner q = new Scanner(System.in);
            String quitting = q.next();
            endGame(quitting);
        }
    }

    /**
     * @return: boolean finished Returns the finished field.
     */

    public boolean getFinished() {
        return finished;
    }

    /**
     * Prints the current board using a nested for loop, although this results in a
     * quadratic algorithm I have chosen method #2 out of the three for the
     * implementation as I beleive it is the most efficient, deepToString is
     * exponentially slower with more data.
     */

    public void viewBoard() {
        /**
         * 1 String formattedString = Arrays.deepToString(board).replace(",",
         * "").replace("[", "\n").replace("]", "") .replace(" ", "").trim();
         * 
         * System.out.println(formattedString);
         */
        // 2
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                System.out.print(board[i][j]);
            }
            System.out.println();
        }
        /**
         * //3 for (char[] row : board) { for (char c : row) { System.out.print(c); }
         * System.out.println(); }
         */
    }

    /**
     * @return: boolean boardFull Checks with a nested for loop if there is a
     *          character * on the entire board, if one is found it will set
     *          boardFull to false as the * character is a free space. Method
     *          returns true or false and is used when adding monsters.
     */
    public boolean isBoardFull() {
        boolean boardFull = true;

        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                if (board[i][j] == '*') {
                    boardFull = false;
                }
            }
        }
        if (boardFull == true) {
            System.out.println("The board is full, you cannot create anymore monsters.");

        }
        return boardFull;
    }

    /**
     * Searched the board using a brute force while loop approach, I decided to use
     * this as we are only using a small data sample, making the code less scalable
     * but shorter in the short term. Sets the monsters character to that position
     * and then sets the mosters position field to its coords.
     * 
     * If there was a larger data sample I could have created an array of all the
     * free spaces and their coordinates and then assigned them to a free position
     * within that array.
     */
    public void findPlaceMonster(Monster c) {
        boolean foundPlace = false;
        while (foundPlace == false) {
            int a = rand.nextInt(SIZE - 2) + 1;
            int b = rand.nextInt(SIZE - 2) + 1;
            if (board[a][b] == '*') {
                board[a][b] = c.getChar();
                c.setMonsterPosition(a, b);
                foundPlace = true;

            }

        }
    }

    /**
     * Takes a user input and then compares it to the ArrayList of all the monsters
     * names, if it finds a match for the input, it will return the current position
     * of that monster. Else it will return the error of no monster found.
     */
    public void getMonsterPosition() {
        System.out.print("Enter the monsters name you're searching for: ");
        Scanner getM = new Scanner(System.in);
        String monster = getM.next().toUpperCase();
        boolean found = false;
        for (int i = 0; i < monsterList.size(); i++) {
            Monster currentMonster = monsterList.get(i);
            if (monster.equals(currentMonster.getName())) {
                System.out
                        .println(currentMonster.getName() + " is positioned at " + currentMonster.getMonsterPosition());
                found = true;
            }
        }
        if (found == false) {
            System.out.println("No monster found by the name: " + monster);
        }
    }

    /**
     * Reshuffles the entire board, even if the board is full, all scores are kept
     * and positions are tracked. I think this method could have been more efficient
     * than looping through the monsterlist twice but it seems to work efficiently
     * for this data sample.
     */
    public void reshuffle() {
        ArrayList<Monster> newList = new ArrayList<>();
        for (Monster monster : monsterList) {
            newList.add(monster);
        }
        for (Monster m : newList) {
            removeMonster(m.getChar());
            monsterList.add(m);
        }
        for (Monster mon : monsterList) {
            findPlaceMonster(mon);
        }
        System.out.println("All monsters shuffled!");
    }

    /**
     * Checks whether the input of a monster name only contains letters A-Z. Used
     * for validation.
     */
    public boolean checkName(String name) {
        char[] chars = name.toCharArray();

        for (char c : chars) {
            if (!Character.isLetter(c)) {
                return false;
            }
        }

        return true;
    }

    /**
     * Uses checkName to validate a monsters name, if true, begins the process of
     * adding the monster. Looping through the current monsterList, comparing the
     * first character to make sure no current monsters begin with the same
     * character.
     * 
     * Returns the respective error messages if needed, else, it will create a new
     * monster, FINDS a place for the monster, if there is a space then it will add
     * it to the list.
     * 
     * Then it will print that the monster has been added etc..
     */

    public void checkValidMonster(String m) {

        if (checkName(m)) {
            char monsterChar = m.toUpperCase().charAt(0);
            m = m.toUpperCase();
            boolean valid = true;
            for (int i = 0; i < (monsterList.size()); i++) {
                if (monsterList.get(i).getChar() == monsterChar) {
                    System.out.println("A monster named " + monsterList.get(i).getName()
                            + " already starts with the same character as " + m + ", try again!");
                    valid = false;
                }
            }
            if (valid) {
                Monster newMonster;
                newMonster = new Monster(m);
                findPlaceMonster(newMonster);
                addToList(newMonster);
                // newMonster.setMonsterPosition(monsterPlace);
                System.out.println("Added " + m + " to the board!");
            }
        } else {
            System.out.println("Please enter a valid monster name with charaters A-z:");
            Scanner newSc = new Scanner(System.in);
            String newM = newSc.next();
            checkValidMonster(newM);

        }
    }

    /**
     * Checks that the list is not empty, then returns the mosters by name.
     */

    public void returnList() {
        if (!monsterList.isEmpty()) {
            System.out.println("Current monsters on the board are: ");
            for (int i = 0; i < monsterList.size(); i++) {
                if (i != (monsterList.size() - 1))
                    System.out.print(monsterList.get(i).getName() + ",");
                else {
                    System.out.println(monsterList.get(i).getName());
                }
            }
        } else {
            System.out.println("There are no active monsters, add some.");
        }
    }

    /**
     * Adds a monster to the list
     */
    public void addToList(Monster monster) {
        monsterList.add(monster);
    }

    /**
     * Loops through the board looking for the character from the parameter, if
     * there is a match it replaces that letter with a *.
     * 
     * Loops through the monster list looking for a character match with the one
     * deleted, once found, it removes it from the list.
     */
    public void removeMonster(char c) {

        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                if (board[i][j] == c) {
                    board[i][j] = '*';
                }
            }
        }
        for (int index = 0; index < monsterList.size(); index++) {
            if (monsterList.get(index).getChar() == c) {
                monsterList.remove(index);
            }
        }
    }

    /**
     * Searches for a character in the board and returns its I index.
     */

    public int findMonsteri(char c) {
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                if (board[i][j] == c) {
                    return i;
                }
            }
        }
        return 0;
    }

    /**
     * Searches for a character on the board and returns its j index;
     */
    public int findMonsterj(char c) {

        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                if (board[i][j] == c) {
                    return j;
                }
            }
        }
        return 0;
    }

    /**
     * Searches one up,right,down,left of the current monsters positions, then adds
     * available monsters to an array.
     * 
     * Old code below showing multiple if statements which was less efficicient and
     * only checked characters, new code checks for monsters so there is no need for
     * validation of other characters.
     */
    public ArrayList<Monster> searchMonsterNearby(int i, int j) {
        ArrayList<Monster> nearBy = new ArrayList<>();
        char north = board[i - 1][j];
        char east = board[i][j + 1];
        char south = board[i + 1][j];
        char west = board[i][j - 1];
        for (Monster m : monsterList) {
            if (m.getChar() == north || m.getChar() == east || m.getChar() == south || m.getChar() == west) {
                nearBy.add(m);
            }
        }
        /**
         * if (north != '*' && north != '-' && north != '|' && north != '\u00A0') {
         * 
         * nearBy.add(north); } if (east != '*' && east != '-' && east != '|' && east !=
         * '\u00A0') {
         * 
         * nearBy.add(east); } if (south != '*' && south != '-' && south != '|' && south
         * != '\u00A0') {
         * 
         * nearBy.add(south); } if (west != '*' && west != '-' && west != '|' && west !=
         * '\u00A0') {
         * 
         * nearBy.add(west); }
         */
        return nearBy;

    }

    /**
     * Launches an attacks from a monster taken by user input and allows the user to
     * select from a list of monsters nearby. Once the monster is selected and the
     * victim has been chosen, the removeMonster method is called and the attackers
     * points are increased.
     * 
     * If there is no monster by the name of the victim then appropriate error
     * messages will show.
     * 
     * Using other methods to validate inputs and remove the monster, the launch
     * attack metehod is quite efficient however, the finding of the attacker and
     * victim could be done easier by implementing a char[][] of monstersposition
     * and call on it instead of searching through the entire board again.
     * 
     * Validation makes the code longer and take more time but sicne there is only
     * ever a small data pool, the code is quite fast and is always correct.
     */
    public void launchAttack() {
        if (!monsterList.isEmpty() && (monsterList.size() > 1)) {
            returnList();
            System.out.println("Choose the Monster you want to attack with: ");
            Scanner scan = new Scanner(System.in);
            char mUse = scan.next().toUpperCase().charAt(0);
            int j = findMonsterj(mUse);
            int i = findMonsteri(mUse);
            Monster attacker = null;
            boolean matched = false;
            for (int index = 0; index < monsterList.size(); index++) {
                if (monsterList.get(index).getChar() == board[i][j]) {
                    attacker = monsterList.get(index);
                }
            }
            if (i == 0 || j == 0) {
                System.out.println("No valid monster found. Try Again");
            } else {
                Scanner sc = new Scanner(System.in);
                ArrayList<Monster> nearBy = searchMonsterNearby(i, j);

                if (!nearBy.isEmpty()) {
                    System.out.print(attacker.getName() + " can attack the following monsters, ");
                    for (int index = 0; index < nearBy.size(); index++) {
                        System.out.print(nearBy.get(index).getName() + ", ");
                    }
                    System.out.println("choose one.");
                    String input = sc.next().toUpperCase();
                    for (Monster monster : nearBy) {
                        if (input.equals(monster.getName())) {
                            matched = true;
                            char c = input.charAt(0);
                            System.out.println(
                                    attacker.getName() + " has killed " + monster.getName() + " and gained 1 point");
                            removeMonster(c);
                            attacker.increaseScore();
                        }
                    }
                    if (matched == false)
                        System.out.println(attacker.getName() + " Cannot attack " + input + ", try again.");

                } else {
                    System.out.println("There are no monsters available to be attacked, create some.");
                }
            }
        } else {
            System.out.println("There are no monsters to be attacked, add some now!");
        }
    }

    /**
     * Searches through the monsterList and calls the getScore method of the monster
     * entered by user input.
     */
    public void retrieveScores() {
        if (!monsterList.isEmpty()) {
            Scanner sc = new Scanner(System.in);
            returnList();
            System.out.print("Which monster's score do you wish to retrieve?: ");
            String input = sc.next();
            boolean found = false;
            for (int i = 0; i < monsterList.size(); i++) {
                if (monsterList.get(i).getName().contains(input.toUpperCase())) {
                    System.out
                            .println(monsterList.get(i).getName() + " has a score of " + monsterList.get(i).getScore());
                    found = true;
                }
            }
            if (!found) {
                System.out.println("There are no monsters of that name");
            }
        } else {
            System.out.println("There are no active monsters to retrieve any scores");
        }
    }

    /**
     * Returns the size of the monster board, not including the border. IE: a SIZE
     * 10 board will produce and 8x8 monster board, allowing for 64 monster
     * positions.
     */
    public int getMonsterSize() {
        int size = (SIZE - 2) * (SIZE - 2);
        return size;
    }

    public void getLeaderboard() {

        /**
         * THIS CODE WAS THE FIRST ATTEMPT BEFORE FURTHER RESEARCH, IT SEEMED TO TAKE AN
         * EXPONENTIAL AMOUNT OF TIME, AS THE DATA INCREASE THE TIME TAKEN TO RUN WAS
         * DOUBLING, WITH A FULL 26 CHARACTER BOARD, THE SEARCH WOULD TAKE 3-5 SECONDS!
         * 
         * 
         * 
         * public void getLeaderboard(){ if(!monsterList.isEmpty()) { int highscore = 0;
         * ArrayList<Monster> leaderboard = new ArrayList<>(); for(int i = 0; i <
         * monsterList.size(); i++) { int score = monsterList.get(i).getScore();
         * if(score > highscore) { highscore = score; } } int value = 0;
         * while(leaderboard.size() != monsterList.size()) { for(int i = 0; i <
         * monsterList.size(); i++) { if(monsterList.get(i).getScore() == highscore -
         * value) { leaderboard.add(monsterList.get(i)); for(int n = 0; n <
         * monsterList.size(); n++) { if(monsterList.get(n).getScore() ==
         * monsterList.get(i).getScore() && monsterList.get(i) != monsterList.get(n)) {
         * leaderboard.add(monsterList.get(n)); } } value++; } else{ value++; }
         * 
         * } } System.out.println("Leaderboard:"); for(Monster m : leaderboard) {
         * System.out.print(m.getName()+"|"+m.getScore()); System.out.println(); } )
         * System.out.println("Leaderboard"); for (int i = 0; i < monsterList.size();
         * i++) { System.out.print(monsterList.get(i).getName()); System.out.print("|");
         * System.out.print(monsterList.get(i).getScore()); System.out.println(""); }
         * 
         * } else{ System.out.println("There are no monsters to list a leaderboard"); }
         * }
         */
        if (!monsterList.isEmpty()) {

            // ArrayList<Monster> temp = (ArrayList<Monster>) monsterList.clone();
            ArrayList<Monster> monstersClone = new ArrayList<>(monsterList);
            // Using a lambda express greatly reduces the time to sort the leaderboard,
            // similar to inserstion sort method.
            Collections.sort(monstersClone, (monster2, monster1) -> monster1.getScore() - monster2.getScore());
            for (Monster m : monstersClone) {
                System.out.println(m.getName() + "|" + m.getScore());
            }
        } else {
            System.out.println("There are no monsters to list a leaderboard");
        }
    }
}