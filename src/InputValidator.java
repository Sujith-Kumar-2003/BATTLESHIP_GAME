/**
 * The InputValidator class provides a static method for validating user input in the context of a Battleship game.
 * It checks whether the input is a valid coordinate on the game board, ensuring it consists of a letter between 'A' and 'H'
 * representing the column and a digit between '1' and '8' representing the row.
 *
 * @author Sujith Kumaravel and Jothi Basu
 * @version 1.0
 * @since 2024-02-15
 */
public class InputValidator {

    /**
     * Validates user input for a Battleship game coordinate.
     *
     * @param input The user input to be validated.
     * @return true if the input is a valid coordinate, false otherwise.
     */
    public static boolean isValidInput(String input) {
        return input.length() == 2 && input.charAt(0) >= 'A' && input.charAt(0) <= 'H' &&
                input.charAt(1) >= '1' && input.charAt(1) <= '8';
    }
}