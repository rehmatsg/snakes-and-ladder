# Snakes and Ladders

A simple implementation of the classic board game, written in Java.

## Installation

To install and run this game, you will need to have Java installed on your computer.

1. Clone this repository to your local machine:
  `git clone <https://github.com/rehmatsg/snakes-and-ladder.git>`
  
2. Navigate to the directory where you cloned the repository:
  `cd snakes-and-ladders`

3. Compile the Java source files:
  `javac *.java`

4. Run the game:
  `java SnakesAndLadders`

## How to Play

The game is played on a 10x10 board, with 100 squares numbered from 1 to 100. The aim of the game is to reach square 100 before any of your opponents.

At the beginning of each turn, you roll a six-sided die to determine how many squares you can move. You must then move your piece forward by the number of squares indicated by the die roll.

If you land on a square that has a ladder on it, you can climb the ladder to reach a higher square. If you land on a square that has a snake on it, you must slide down the snake to a lower square.

The first player to reach square 100 wins the game.

## Game Play

This program is a replica of the board game snakes and ladders. the program doesn't require user input and rolls the dice for itself. The user starts the program, and the program then prints the board and puts all the items on the board (ex: players/snakes/ladders). After this, A die is rolled for the first player, and the player is moved based on the amount that was rolled. Then this repeats until the game is finished when a player makes it to the bottom right of the board, and a player is named the winner.

## Code

The program uses 2D-Arrays to display the board and hold the info on where each piece of the board is. It adds each aspect of the different aspects to the game using methods. The program rolls the dice using a random until and a bound of six, based on the dice that are used in the actual game. There are different classes utilized for holding some information, like where each character is and which character is rolling the dice. The program also uses different for loops to continue the game until someone wins.
