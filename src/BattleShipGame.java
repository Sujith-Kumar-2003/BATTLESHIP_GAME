import java.util.Scanner;
import java.util.Random;

/**
 * The BattleShipGame class represents a simple Battleship game between a human player and a computer player.
 * The game is played on a game board where players take turns to launch rockets and try to hit each other's ships.
 * The class includes methods for setting up the game, handling player turns, and declaring the winner.
 *
 * @author Sujith Kumaravel and Sujith Kumaravel
 * @version 1.0
 * @since 2024-02-15
 */

public class BattleShipGame {
    private GameBoard gameBoard;
    private Player humanPlayer;
    private Player computerPlayer;
    private final Scanner sc = new Scanner(System.in);
    public static final int BOARD_SIZE = 8; // Ensuring BOARD_SIZE is accessible
    private static final int NUM_SHIPS = 6;
    private static final int NUM_GRENADES = 4;
    private boolean humanMissNextTurn = false;
    private boolean computerMissNextTurn = false;
    private GameBoard clonedGameBoard; // Add a field to store the cloned game board


    public BattleShipGame() {
        this.gameBoard = new GameBoard();
        this.humanPlayer = new Player("Human", true);
        this.computerPlayer = new Player("Computer", false);
    }
//This is the Start of the game, it is like init for a class.
    private void setupGame() {
        System.out.println("Welcome to Battleship!");
        gameBoard.initializeBoard();

        // Ask the user for ship and grenade placements
        gameBoard.placeShipsAndGrenades("H", NUM_SHIPS, NUM_GRENADES); // For the human player

        // For the computer, determine symbols and use the correct method
        String computerShipSymbol = "S"; // Assuming "S" for computer ships
        String computerGrenadeSymbol = "G"; // Assuming "G" for computer grenades
        gameBoard.placeShipsAndGrenadesAutomatically(computerShipSymbol, NUM_SHIPS, computerGrenadeSymbol, NUM_GRENADES);
//        new n = gameBoard.clone()
        clonedGameBoard = gameBoard.clone();

        System.out.println("Ships and grenades have been placed.");
        gameBoard.printBoard(false);

        System.out.println("The game begins!");
    }


    public void playGame() {
        setupGame();
        while (!gameBoard.checkGameOver()) {
            if (!humanMissNextTurn) {
                humanTurn();
            } else {
                System.out.println("You missed this turn because you hit a grenade!");
                humanMissNextTurn = false;
            }

            if (gameBoard.checkGameOver()) {
                break; // Exiting the loop if the game is over
            }

            if (!computerMissNextTurn) {
                computerTurn();
            } else {
                System.out.println("Computer missed this turn because it hit a grenade!");
                computerMissNextTurn = false;
            }
        }
        declareWinner();
    }

    private void humanTurn() {
        System.out.print("Position of your rocket: ");
        String input = sc.next().toUpperCase();
        while (!Utils.isValidInput(input)) {
            System.out.println("Invalid coordinates. Try again.");
            input = sc.next().toUpperCase();
        }
        int[] indices = Utils.convertInputToIndices(input);
        gameBoard.makeMove(indices[0], indices[1], humanPlayer.getSymbol());
        processMove(indices[0], indices[1], humanPlayer); // Move processMove before makeMove
        gameBoard.printBoard(false);
    }


    private void computerTurn() {
        int[] move = computerPlayer.generateMove();
        gameBoard.makeMove(move[0], move[1], computerPlayer.getSymbol());
        System.out.println("Computer placed its rocket at: " + (char) ('A' + move[1]) + (move[0] + 1));
        processMove(move[0], move[1], computerPlayer); // Move processMove before makeMove
        gameBoard.printBoard(false);
    }


    private void processMove(int row, int col, Player player) {
        String cell = gameBoard.getCell(row, col);

        if (cell.equalsIgnoreCase("S")) {
            System.out.println("Ship hit!");
        } else if (cell.equalsIgnoreCase("G")) {
            System.out.println("Boom! Grenade.");
            if (player.isHuman()) {
                humanMissNextTurn = true;
            } else {
                computerMissNextTurn = true;
            }
        } else {
            System.out.println("Miss.");
        }

        if (gameBoard.checkGameOver()) {
            if (gameBoard.hasShipsRemaining(humanPlayer.getSymbol())) {
                System.out.println("You win!");
                clonedGameBoard.printBoard(true);

            } else {
                System.out.println("Computer wins!");
                clonedGameBoard.printBoard(true);
            }
            System.exit(0);  // Exit the program after printing the result
        }
    }



    private void declareWinner() {
        if (gameBoard.hasShipsRemaining(humanPlayer.getSymbol())) {
            System.out.println("You win!");
        } else {
            System.out.println("Computer wins!");
        }
    }

    public static void main(String[] args) {
        new BattleShipGame().playGame();
    }
}
