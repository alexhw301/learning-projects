package ui;

import core.CheckersComputerPlayer;
import core.CheckersLogic;
import java.util.Scanner;

public class CheckersTextConsole {

	public static void main(String[] args) {
		Scanner input = new Scanner(System.in);
		String move = "";
		String gameMode;
		String p = "P";
		String c = "C";
		boolean computerPlayer = false;
		char firstRow, firstColumn, secondRow, secondColumn;
		int row1, column1, row2, column2;
		int result;
		
		CheckersLogic game = new CheckersLogic();
		CheckersComputerPlayer computer = new CheckersComputerPlayer();
		drawBoard(game);
		
		
		System.out.print("Begin Game. ");
		System.out.print("Enter 'P' if you want to play against another player; ");
		System.out.print("enter 'C' to play against computer\n");
		
		gameMode = input.next();
		
		if(gameMode.equals(p)) {
			computerPlayer = false;
		}
		else if(gameMode.equals(c)) {
			computerPlayer = true;
		}
		
		do {
			if(game.getPlayerTurn() == 1) { //red player turn
				System.out.println("Player X - your turn.");
				System.out.println("Choose a cell position of piece to be moved and the new position. e.g., 3a-4b");
				move = input.next();
			}
			else { //white player turn
				if(computerPlayer == false) { //not in computer player mode
					System.out.println("Player O - your turn.");
					System.out.println("Choose a cell position of piece to be moved and the new position. e.g., 3a-4b");
					move = input.next();
				}
				else if(computerPlayer == true) { //if it's computers turn
					move = computer.pickMove(game);
				}
			}
			
			System.out.println(move);
			
			if(move.length() == 5) {
		
				firstRow = move.charAt(0);
				firstColumn = move.charAt(1);
				secondRow = move.charAt(3);
				secondColumn = move.charAt(4);
		
				row1 = firstRow - 49;
				column1 = firstColumn - 97;
				row2 = secondRow - 49;
				column2 = secondColumn - 97;
			
				if(row1 <= 7 && row1 >= 0 && column1 <= 7 && column2 >= 0) {
					if(game.validateMove(row1, column1, row2, column2) < 1) {
						game.movePiece(row1, column1, row2, column2);
						drawBoard(game);
					}
					else {
						System.out.println("Not a valid move");
					}
				}
				else {
					System.out.println("Move not allowed");
				}
			}
			else {
				System.out.println("Move not allowed");
			}
		} while(game.hasEnded() == 0);
		
		result = game.hasEnded();
		if(result == 2) {
			System.out.println("Player O Won the Game");
		}
		else if(result == 1) {
			System.out.println("Player X Won the Game");
		}
		
	}
	
	public static void drawBoard(CheckersLogic game) {
		for (int i = game.getRows()-1; i >= 0; i--) {
			System.out.print((i+1) + " |");
			for (int j = 0; j < game.getColumns(); j++) {
				if(game.getElement(i, j) == 1) {
					System.out.print(" x |");
				}
				else if(game.getElement(i, j) == 2) {
					System.out.print(" o |");
				}
				else {
					System.out.print(" _ |");
				}
			}
			
			System.out.print("\n\n");
		}
		
		System.out.println("    a   b   c   d   e   f   g   h\n");
	}
}
