// -----------------------------------------------------
// Assignment 1
// © Sujith Kumaravel, and Jothi Basu
// Written by: Sujith Kumaravel 40281567, Jothi Basu 40230416
// -----------------------------------------------------

/**
 * The MainClass is the entry point for the Battleship game. It creates an instance of the BattleShipGame class
 * and initiates the game by calling the playGame() method.
 *
 * @author Sujith Kumaravel and Jothi Basu
 * @version 1.0
 * @since 2024-02-15
 */
public class MainClass {
    /**
     * The main method where the execution of the program begins.
     *
     * @param args The command-line arguments passed to the program (unused in this case).
     */
    public static void main(String[] args) {
        BattleShipGame game = new BattleShipGame();
        game.playGame();
    }
}