import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;

public class SnakeAndLadders {
  public static void main(String[] args) {
    // Create an instance of the SnakeAndLaddersGame class
    SnakeAndLaddersGame game = new SnakeAndLaddersGame();

    // Run the game
    game.runGame();
  }
}

class SnakeAndLaddersGame {
  // The game board is represented as a two-dimensional array of squares
  private Square[][] board;

  // The players are represented as a list of Player objects
  private List<Player> players;

  // Initialize the game board with squares of different types
  public void initializeBoard() {
    // The size of the game board (10x10)
    int size = 10;

    // Create the two-dimensional array of squares
    board = new Square[size][size];

    // Loop through each row in the game board
    for (int row = 0; row < size; row++) {
      // Loop through each column in the current row
      for (int col = 0; col < size; col++) {
        // Create a new square at the current position
        Square square = new Square(row, col, java.util.Optional.empty());
        // Add the square to the game board
        board[row][col] = square;
      }
    }

    addLadders();
    addSnakes();
  }

  public void initializePlayers() {
    // Create the players and add them to the list
    players = new ArrayList<Player>();
    players.add(new Player("Player 1", "1"));
    players.add(new Player("Player 2", "2"));
    // Set the initial position of the players to the starting square (0, 0)
    for (Player player : players) {
      player.setPosition(board[0][0]);
    }
  }

  // Add ladders to the game board
  public void addLadders() {
    // Add a ladder from square (1, 3) to square (4, 5)
    board[1][3].addLadder(board[4][5]);
    // Add another ladder from square (2, 6) to square (8, 5)
    board[2][6].addLadder(board[8][5]);
    // Add more ladders as needed...
  }

  // Add snakes to the game board
  public void addSnakes() {
    // Add a snake from square (8, 1) to square (2, 3)
    board[8][1].addSnake(board[2][3]);
    // Add another snake from square (9, 5) to square (1, 1)
    board[9][5].addSnake(board[1][1]);
    // Add more snakes as needed...
  }

  // Roll the dice and return a random number between 1 and 6
  public int rollDice() {
    Random rand = new Random();
    return rand.nextInt(6) + 1;
  }

  // Move a player on the game board by a certain number of squares
  public void movePlayer(Player player, int moveBy) {
    // Get the current position of the player on the game board
    Square currentPosition = player.getPosition();
    int row = currentPosition.getRow();
    int col = currentPosition.getCol();

    // Update the player's position on the game board
    row += moveBy;
    if (row >= board.length) {
      // The player has reached the last row, so set the row to the last row and
      // the column to the last column
      row = row - 10;
      if (col < 9) {
        col += 1;
      } else {
        col = 9;
      }
    } else if (col >= board[row].length) {
      // The player has moved past the last column in the current row, so set the
      // column to the last column in the current row
      col = board[row].length - 1;
    }
    player.setPosition(board[row][col]);
  }

  public void checkForEvents(Player player) {
    // Get the current position of the player on the game board
    Square currentPosition = player.getPosition();

    // Check if the player has landed on a ladder
    Square destination = currentPosition.getLadderDestination();
    if (destination != null) {
      // Move the player to the destination square
      player.setPosition(destination);
    }

    // Check if the player has landed on a snake
    destination = currentPosition.getSnakeDestination();
    if (destination != null) {
      // Move the player to the destination square
      player.setPosition(destination);
    }
  }

  // The main function for the SnakeAndLaddersGame class, containing the game loop
  public void runGame() {
    // Initialize the game board and players
    initializeBoard();
    initializePlayers();

    displayBoard();

    // The game loop
    while (true) {
      // Loop through each player in turn
      for (Player player : players) {

        try {
          Thread.sleep(2000);
        } catch (InterruptedException e) {
          e.printStackTrace();
        }
        // Roll the dice and move the player
        int moveBy = rollDice();

        // Display the player's name and the number of squares they have moved
        System.out.println(player.getName() + " rolled " + moveBy);

        movePlayer(player, moveBy);

        displayBoard();

        // Check if the player has landed on a ladder or snake
        checkForEvents(player);

        // Check if the game is over
        Player winner = gameOver();
        if (winner != null) {
          // The game is over, so display the winner and exit the game loop
          System.out.println("The winner is: " + winner.getName());
          return;
        }
      }
    }
  }

  // Display the current state of the game board
  public void displayBoard() {
    // Loop through each row in the game board
    // System.out.format("| %-15s | %-4s |%n", Arrays.toString(headers));
    for (int row = 0; row < board.length; row++) {
      // Loop through each column in the current row
      for (int col = 0; col < board[row].length; col++) {
        // Get the square at the current position
        Square square = board[row][col];

        String playerHere = "";

        for (Player player : players) {
          // Check if the current square is occupied by a player
          if (player.getPosition() == square) {
            // Skip to the next iteration of the loop
            playerHere = player.getId();
          }
        }
        String data = "";
        // Print the square's type (L for ladder, S for snake, or blank for an empty square)
        if (square.isLadder()) {
          data = "L";
        } else if (square.isSnake()) {
          data = "S";
        } else {
          if (playerHere != "") {
            data = playerHere;
          } else {
            data = "-";
          }
        }
        System.out.print(" " + data + " ");
      }
      // Print a new line after each row
      System.out.println();
    }
    System.out.println("---------------------");
  }

  // Check if the game is over, and if so, determine the winner
  public Player gameOver() {
    // Check if any player has reached the last square (9, 9)
    for (Player player : players) {
      Square currentPosition = player.getPosition();
      if (currentPosition.getRow() == 9 && currentPosition.getCol() == 9) {
        // The player has reached the last square, so the game is over
        return player;
      }
    }
    // If no player has reached the last square, the game is not over
    return null;
  }

}

class Square {
  // The row and column of this square on the game board
  private int row;
  private int col;

  // The ladder and snake destinations for this square
  private Square ladderDestination;
  private Square snakeDestination;

  private SquareType type;

  // Constructor for the Square class
  public Square(int row, int col, Optional<SquareType> type) {
    this.row = row;
    this.col = col;
    if (type.isEmpty()) {
      this.type = SquareType.EMPTY;
    } else {
      this.type = type.get();
    }
  }

  // Get the row of this square on the game board
  public int getRow() {
    return row;
  }

  // Get the column of this square on the game board
  public int getCol() {
    return col;
  }

  // Add a ladder destination to this square
  public void addLadder(Square destination) {
    ladderDestination = destination;
    setType(SquareType.LADDER);
  }

  // Get the ladder destination of this square
  public Square getLadderDestination() {
    return ladderDestination;
  }

  // Add a snake destination to this square
  public void addSnake(Square destination) {
    snakeDestination = destination;
    setType(SquareType.SNAKES);
  }

  // Get the snake destination of this square
  public Square getSnakeDestination() {
    return snakeDestination;
  }

  // Check if the square is a ladder
  public boolean isLadder() {
    return type == SquareType.LADDER;
  }

  // Check if the square is a snake
  public boolean isSnake() {
    return type == SquareType.SNAKES;
  }

  // Set the square's type
  public void setType(SquareType type) {
    this.type = type;
  }
}

class Player {
  // The name of the player
  private String name;

  private String id;

  // The current position of the player on the game board
  private Square position;

  // Constructor for the Player class
  public Player(String name, String id) {
    this.name = name;
    this.id = id;
  }

  // Get the name of the player
  public String getName() {
    return name;
  }

  // Get the id of the player
  public String getId() {
    return id;
  }

  // Set the current position of the player on the game board
  public void setPosition(Square position) {
    this.position = position;
  }

  // Get the current position of the player on the game board
  public Square getPosition() {
    return position;
  }
}

enum SquareType {
  // The square is empty and does not contain a ladder or snake
  EMPTY,

  // The square contains a ladder
  LADDER,

  // The square contains a snake
  SNAKES
}