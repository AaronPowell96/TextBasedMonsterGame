import java.util.*;

public class Game {
    public Scanner sc;
    public Board board;

    /**
     * Initialised the board constructor beginning the game.
     */
    public Game() {
        board = new Board();
    }

    /**
     * A series of print lines to create a text menu with numbers corresponding to what needs to be
     * entered to carry out the action.
     * 
     * Validating the input is an integer and if it isn't then request a new input in a loop
     * until the parameter is satisfied.
     * 
     * Runs for aslong as boards finished field is false.
     */
    public void showMenu() {

        if (!board.getFinished()) {
            //int item = 0;
            System.out.println("________________________________________________________________");
            System.out.println("Choose an option:");
            System.out.println(/*item++ + */"0. Quit the Game");
            System.out.println(/*item++ + */"1. Create a Monster");
            System.out.println(/*item++ + */"2. Launch an Attack");
            System.out.println(/*item++ + */"3. View the board");
            System.out.println(/*item++ + */"4. View Leaderboard");
            System.out.println(/*item++ + */"5. Retrieve a score of a monster");
            System.out.println(/*item++ + */"6. List players");
            System.out.println(/*item++ + */"7. Shuffle the Board");
            System.out.println(/*item++ + */"8. Generate full board of monsters");
            System.out.println(/*item++ + */"9. View a monster's coordinates");
            Integer i = 0;
            sc = new Scanner(System.in);

            if (sc.hasNextInt()) {
                i = sc.nextInt();
            } else {
                boolean numberEntered = false;
                while (!numberEntered) {
                    System.out.print("Please enter a valid number: ");
                    Scanner input = new Scanner(System.in);
                    if (input.hasNextInt()) {
                        i = input.nextInt();
                        numberEntered = true;
                    }
                }
            }
            menuInput(i);
        } else {
            quitGame();
        }

    }

    /**
     * Using a bunch of IF statements to decide what the user had inputted and the carryout the correct
     * method. I decided this was slower and less aesthetic than a switch statement, so I swapped to what 
     * you see below, using the switch depending on what is inputted.
     * 
     * It use to take an item input for the else if which would also slow down its efficiency.
     */
    public void menuInput(int i /*int item*/) {

        switch (i) {
        case 1:
            createMonster();
            showMenu();
            break;
        case 2:
            board.launchAttack();
            showMenu();
            break;
        case 3:
            board.viewBoard();
            showMenu();
            break;
        case 4:
            board.getLeaderboard();
            showMenu();
            break;
        case 5:
            board.retrieveScores();
            showMenu();
            break;
        case 6:
            board.returnList();
            showMenu();
            break;
        case 7:
            board.reshuffle();
            showMenu();
            break;
        case 8:
            generateFullBoard();
            break;
        case 9:
            board.getMonsterPosition();
            showMenu();
        case 0:
            System.out.println("You are about to exit the game, are you sure?");
            System.out.println("Enter Y to quit, or N to keep playing");
            Scanner exit = new Scanner(System.in);
            String quit = exit.next();
            board.endGame(quit);
            showMenu();
        default: System.out.println("Please enter a valid number");
        showMenu();
        }
        /**
         * 
         * IN-EFFICIENT CODE THAT WAS USED BEFORE THE SWITCH STATEMENTS ABOVE.
         * 
        if (i == 0) {
            board.getMonsterPosition();
            showMenu();
        }
        if (i == 1) {
            createMonster();
            showMenu();
        }
        if (i == 2) {
            board.launchAttack();
            showMenu();
        }
        if (i == 3) {
            board.viewBoard();
            showMenu();
        }
        if (i == 4) {
            board.getLeaderboard();
            showMenu();
        }
        if (i == 5) {
            board.retrieveScores();
            showMenu();
        }
        if (i == 6) {
            board.returnList();
            showMenu();
        }
        if (i == 7) {
            board.reshuffle();
            showMenu();
        }
        if (i == 8) {
            generateFullBoard();
        }
        if (i == 9) {
            System.out.println("You are about to exit the game, are you sure?");
            System.out.println("Enter Y to quit, or N to keep playing");
            Scanner exit = new Scanner(System.in);
            String quit = exit.next();
            board.endGame(quit);
            showMenu();
        } else if (i > item) {
            System.out.println("Please enter a valid number");
            showMenu();
        } 
        */
    }
    
    /**
     * Calls upon the isBoardFull method which responds as needed then carrys on with the
     * creation process if the board is not full, otherwise stops the process and isBoardFull will
     * print the error information.
     * 
     * Scanner takes the input of a monster name and then feeds it into the checkValidMonster method.
     */
    public void createMonster() {
        if (!board.isBoardFull()) {
            System.out.print("Enter name: ");
            // String monster = sc.next();
            Scanner cm = new Scanner(System.in);
            String monsterInput = cm.next();
            board.checkValidMonster(monsterInput);
        }
    }

    /** 
     * Used for testing purposes but also useful for a user who is bored of creating monsters,
     * using a name generator(pre defined names going through A-Z of names) the inputting those
     * to the checkValidMonster method which enters them onto the board.
     */
    public void generateFullBoard() {
        for (int i = 0; i < board.getMonsterSize(); i++) {
            NameGenerator ng = new NameGenerator();
            String name = ng.generateName(i);
            board.checkValidMonster(name);
        }
        showMenu();
    }

    /**
     * Quits the game using system exit.
     */
    public void quitGame() {
        System.exit(0);
    }

}