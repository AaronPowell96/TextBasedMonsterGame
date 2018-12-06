public class Assess {

    /**
     * Prints the beginning statements of the game and then calls the menu
     * which will continue to loop until endGame is called.
     */
    public static void main(String[] args) {
        Game game = new Game();       
        System.out.println("Welcome to Monster Manic!");
        System.out.println("As a text based game we require you to choose your actions via the menu.");
        System.out.println("You can only attack adjacent monsters.");
        System.out.println("_____________________________________________________");       
        game.showMenu();      
    }
}