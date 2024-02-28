import java.util.Random;
import java.util.Scanner;

/**
 * The GameBoard class represents the game board for a Battleship game. It includes methods for initializing the board,
 * placing ships and grenades, checking for occupied positions, making moves, and determining the game's state.
 *
 * @author Sujith Kumaravel and Jothi Basu
 * @version 1.0
 * @since 2024-02-15
 */
public class GameBoard {
    private static final int BOARD_SIZE = 8;
    private static final int NUM_SHIPS = 6;
    private static final int NUM_GRENADES = 4;
    public String[][] realBoard; // Board displayed to the player
    public String[][] sampleBoard; // Board with actual ship and grenade locations
    private final Random rand = new Random();
    private final Scanner sc = new Scanner(System.in);

    /**
     * Constructs a new GameBoard instance with the specified board size and initializes the board.
     */
    public GameBoard() {
        this.realBoard = new String[BOARD_SIZE][BOARD_SIZE];
        this.sampleBoard = new String[BOARD_SIZE][BOARD_SIZE];
        initializeBoard();
    }

    public GameBoard clone() {
        GameBoard clonedBoard = new GameBoard();

        // No need to initialize boards in the cloned instance as they are already initialized in its constructor

        // Deep copy of realBoard and sampleBoard to the cloned instance
        for (int i = 0; i < BOARD_SIZE; i++) {
            for (int j = 0; j < BOARD_SIZE; j++) {
                clonedBoard.realBoard[i][j] = this.realBoard[i][j];
                clonedBoard.sampleBoard[i][j] = this.sampleBoard[i][j];
            }
        }

        // Note: This simple cloning does not copy the state of the Random or Scanner objects,
        // as they are not directly related to the game board's state. If needed, their states
        // could be managed separately or reinitialized in the clone.

        return clonedBoard;
    }


    /**
     * Initializes the game board with empty spaces.
     */
    public void initializeBoard() {
        for (int i = 0; i < BOARD_SIZE; i++) {
            for (int j = 0; j < BOARD_SIZE; j++) {
                realBoard[i][j] = "_"; // Empty space
                sampleBoard[i][j] = "_"; // Empty space
            }
        }
    }

//    /**
//     * Places ships and grenades on the board for the specified player.
//     *
//     * @param playerSymbol The symbol representing the player (e.g., "H" for Human, "C" for Computer).
//     * @param numShips The number of ships to be placed on the board.
//     * @param numGrenades The number of grenades to be placed on the board.
//     */
public void placeShipsAndGrenades(String playerSymbol, int numShips, int numGrenades) {
    Scanner sc = new Scanner(System.in);
    int shipsPlaced = 0;
    int grenadesPlaced = 0;

    // Determine symbols for ships and grenades based on the player
    String shipSymbol = playerSymbol.equals("H") ? "s" : "S";
    String grenadeSymbol = playerSymbol.equals("H") ? "g" : "G";

    // Place ships with human input for human player
    if (playerSymbol.equals("H")) {
        while (shipsPlaced < numShips) {
            System.out.print("Enter the coordinates for ship #" + (shipsPlaced + 1) + ": ");
            String input = sc.next().toUpperCase();
            if (InputValidator.isValidInput(input)) {
                int row = input.charAt(1) - '1'; // Convert '1'-'8' to 0-7
                int col = input.charAt(0) - 'A'; // Convert 'A'-'H' to 0-7

                // Use sampleBoard for checking and placing ships
                if (sampleBoard[row][col].equals("_")) {
                    sampleBoard[row][col] = shipSymbol; // Place ship with the appropriate symbol
                    shipsPlaced++;
                } else {
                    System.out.println("sorry, coordinates already used. try again. ");
                }
            } else {
                System.out.println("sorry, coordinates outside the grid. try again. ");
            }
        }

        // Place grenades with human input for human player
        while (grenadesPlaced < numGrenades) {
            System.out.print("Enter the coordinates for grenade #" + (grenadesPlaced + 1) + ": ");
            String input = sc.next().toUpperCase();
            if (InputValidator.isValidInput(input)) {
                int row = input.charAt(1) - '1'; // Convert '1'-'8' to 0-7
                int col = input.charAt(0) - 'A'; // Convert 'A'-'H' to 0-7

                // Use sampleBoard for checking and placing grenades
                if (sampleBoard[row][col].equals("_")) {
                    sampleBoard[row][col] = grenadeSymbol; // Place grenade with the appropriate symbol
                    grenadesPlaced++;
                } else {
                    System.out.println("sorry, coordinates already used. try again. ");
                }
            } else {
                System.out.println("sorry, coordinates outside the grid. try again. ");
            }
        }
    } else {
        // Automatically place ships and grenades for the computer player
        placeShipsAndGrenadesAutomatically(shipSymbol, numShips, grenadeSymbol, numGrenades);
    }
}


    public void placeShipsAndGrenadesAutomatically(String shipSymbol, int numShips, String grenadeSymbol, int numGrenades) {
        // Place ships automatically for the computer on the sampleBoard
        int shipsToPlace = numShips;
        while (shipsToPlace > 0) {
            int[] location = findRandomEmptyLocation();
            sampleBoard[location[0]][location[1]] = shipSymbol; // Use specific symbol for ship
            shipsToPlace--;
        }

        // Place grenades automatically for the computer on the sampleBoard
        int grenadesToPlace = numGrenades;
        while (grenadesToPlace > 0) {
            int[] location = findRandomEmptyLocation();
            sampleBoard[location[0]][location[1]] = grenadeSymbol; // Use specific symbol for grenade
            grenadesToPlace--;
        }
    }
    /**
     * Finds an empty location on the board based on user input.
     */
    private int[] findEmptyLocation(Scanner sc) {
        int row = 0; // Default value, can be any valid initial value within the array bounds
        int col = 0; // Default value, can be any valid initial value within the array bounds
        boolean validInput;

        do {
            System.out.print("Enter the coordinates for your rocket (e.g., A1): ");
            String input = sc.next().toUpperCase();

            validInput = InputValidator.isValidInput(input);

            if (validInput) {
                row = input.charAt(1) - '1';
                col = input.charAt(0) - 'A';

                // Check if the location is empty
                if (!isOccupied(row, col)) {
                    validInput = true;
                } else {
                    System.out.println("Position already occupied. Try again.");
                    validInput = false;
                }
            } else {
                System.out.println("Invalid coordinates. Try again.");
            }

        } while (!validInput);

        return new int[]{row, col};
    }


    public boolean isOccupied(int row, int col) {
        // Use sampleBoard for checking occupation status in game logic
        return !sampleBoard[row][col].equals("_");
    }

    public void makeMove(int row, int col, String playerSymbol) {
        String target = sampleBoard[row][col];

        if (!realBoard[row][col].equals("X")) {
            if (!sampleBoard[row][col].equals("X")) {
                if (!target.equals("_")) {
                    if (target.equalsIgnoreCase("S") || target.equalsIgnoreCase("G")) {
                        realBoard[row][col] = target;
                    }
                } else {
                    realBoard[row][col] = "*";
                }

                sampleBoard[row][col] = "X";
            } else {
                System.out.println("Position already called at " + (char) ('A' + col) + (row + 1));
            }
        }
    }


    public String getCell(int row, int col) {
        // Use sampleBoard for game logic checks
        return realBoard[row][col];
    }


    public void printBoard(boolean reveal) {
        String[][] boardToPrint = reveal ? sampleBoard : realBoard;
        System.out.println("  A B C D E F G H");
        for (int i = 0; i < BOARD_SIZE; i++) {
            System.out.print((i + 1) + " ");
            for (int j = 0; j < BOARD_SIZE; j++) {
                System.out.print(boardToPrint[i][j] + " ");
            }
            System.out.println();
        }
    }


    private int[] findRandomEmptyLocation() {
        int row, col;
        do {
            row = rand.nextInt(BOARD_SIZE);
            col = rand.nextInt(BOARD_SIZE);
        } while (!sampleBoard[row][col].equals("_")); // Check the sampleBoard for empty locations
        return new int[]{row, col};
    }

    public boolean hasShipsRemaining(String playerSymbol) {
        // Check sampleBoard for remaining ships
        String shipSymbol = playerSymbol.equals("H") ? "s" : "S";
        for (int i = 0; i < BOARD_SIZE; i++) {
            for (int j = 0; j < BOARD_SIZE; j++) {
                if (sampleBoard[i][j].equals(shipSymbol)) {
                    return true;
                }
            }
        }
        return false;
    }

    public boolean checkGameOver() {
        // Use sampleBoard to determine if the game is over
        return !hasShipsRemaining("H") || !hasShipsRemaining("C");
    }
}
