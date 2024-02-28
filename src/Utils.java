import java.util.Random;
import java.util.Scanner;
/**
 * The Utils class provides utility methods for a Battleship game, including input validation, random coordinate generation,
 * and conversion of coordinates from string format to array indices.
 *
 * @author Sujith Kumaravel and Jothi Basu
 * @version 1.0
 * @since 2024-02-15
 */
public class Utils {
    // Constants for the game's board size, adjust as necessary
    private static final int BOARD_SIZE = 8;

    /**
     * Validates if the input string is a valid board coordinate.
     *
     * @param input The input string to validate.
     * @return true if the input is valid, false otherwise.
     */
    public static boolean isValidInput(String input) {
        if (input == null || input.length() != 2) {
            return false;
        }

        char column = input.charAt(0);
        char row = input.charAt(1);

        // Check if the column is within A-H and the row is within 1-8
        return (column >= 'A' && column <= 'H') && (row >= '1' && row <= '8');
    }

    /**
     * Generates a random coordinate for the board.
     *
     * @return An array where the first element is the row index and the second is the column index.
     */
    public static int[] generateRandomCoordinate() {
        Random random = new Random();
        int row = random.nextInt(BOARD_SIZE);
        int col = random.nextInt(BOARD_SIZE);
        return new int[]{row, col};
    }

    /**
     * Converts a coordinate from string format (e.g., "A1") to array indices.
     *
     * @param input The input string coordinate.
     * @return An array where the first element is the row index and the second is the column index.
     */
    public static int[] convertInputToIndices(String input) {
        int row = input.charAt(1) - '1';
        int col = input.charAt(0) - 'A';
        return new int[]{row, col};
    }
}
