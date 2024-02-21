// -----------------------------------------------------
// Assignment 1
// © Sujith Kumaravel, and Jothi Basu
// Written by: Sujith Kumaravel 40281567, Jothi Basu 40230416
// -----------------------------------------------------

import java.util.Random;
import java.util.Scanner;
/**
 * The Player class represents a player in the Battleship game. It includes methods for obtaining the player's symbol,
 * retrieving the player's move, checking if the player is human, and generating computer moves.
 *
 * @author Sujith Kumaravel and Jothi Basu
 * @version 1.0
 * @since 2024-02-11
 */
public class Player {
    private String name;
    private boolean isHuman;
    private Scanner scanner;
    private Random rand;
    private static final int BOARD_SIZE = 8; // Define BOARD_SIZE here

    /**
     * Constructs a new Player instance with the specified name and human status.
     *
     * @param name The name of the player.
     * @param isHuman True if the player is human, false if it is a computer player.
     */
    public Player(String name, boolean isHuman) {
        this.name = name;
        this.isHuman = isHuman;
        if (isHuman) {
            scanner = new Scanner(System.in);
        } else {
            rand = new Random();
        }
    }

    /**
     * Gets the symbol associated with the player.
     *
     * @return The player's symbol ("H" for human, "C" for computer).
     */
    public String getSymbol() {
        return isHuman ? "H" : "C";
    }

    /**
     * Retrieves the player's move based on whether it is a human or computer player.
     *
     * @param board The game board represented as a 2D array of strings.
     * @return An array containing the row and column indices of the player's move.
     */
    public int[] getMove(String[][] board) {
        int row, col;
        if (isHuman) {
            String input;
            do {
                System.out.print("Enter the coordinates of your rocket: ");
                input = scanner.next().toUpperCase();
            } while (!isValidInput(input) || board[input.charAt(1) - '1'][input.charAt(0) - 'A'] != "_");

            row = input.charAt(1) - '1';
            col = input.charAt(0) - 'A';
        } else {
            do {
                row = rand.nextInt(BOARD_SIZE);
                col = rand.nextInt(BOARD_SIZE);
            } while (!board[row][col].equals("_"));
            System.out.println("Computer chooses: " + (char) ('A' + col) + (row + 1));
        }
        return new int[]{row, col};
    }

    /**
     * Checks if the player is human.
     *
     * @return True if the player is human, false if it is a computer player.
     */
    public boolean isHuman() {
        return isHuman;
    }

    /**
     * Validates if the input string is a valid board coordinate.
     *
     * @param input The input string to validate.
     * @return true if the input is valid, false otherwise.
     */
    private boolean isValidInput(String input) {
        if (input.length() != 2) return false;
        char col = input.charAt(0);
        char row = input.charAt(1);
        return col >= 'A' && col <= 'H' && row >= '1' && row <= '8';
    }

    /**
     * Generates a random move for the computer player.
     *
     * @return An array containing the row and column indices of the generated move.
     */
    public int[] generateMove() {
        int row = rand.nextInt(BOARD_SIZE);
        int col = rand.nextInt(BOARD_SIZE);
        return new int[]{row, col};
    }
}
