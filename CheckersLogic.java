package core;

public class CheckersLogic {
	final int row = 8, column = 8;
	int numberOfRed, numberOfWhite;
	int[][] board = new int [row][column];
	int playerTurn = 1; //auto set first player to red
	int gapRow;
	int gapColumn;
	
	public CheckersLogic() {
		numberOfRed = 12; numberOfWhite = 12;
		playerTurn = 1;
		
		for (int i = 0; i < row; i++) {
			for (int j = 0; j < column; j++) {
				if(j%2 == 0) { //if j is even
					if(i == 0 || i == 2) { board[i][j] = 1; } //placing down red pieces
					if (i == 6) { board[i][j] = 2; } //placing down white pieces
				}
				
				else { //if j is odd
					if(i == 1) { board[i][j] = 1; } //placing down red pieces
					if(i == 5 || i == 7) { board[i][j] = 2; } //placing down white pieces
				}
			}
		}
	}
	
	public int getRows() {
		return row;
	}
	
	public int getColumns() {
		return column;
	}
	
	public int getElement(int pRow, int pColumn) {
		return board[pRow][pColumn];
	}
	
	public int getPlayerTurn() {
		return playerTurn;
	}
	
	private int getNumberOfRed() {
		return numberOfRed;
	}
	
	private int getNumberOfWhite() {
		return numberOfWhite;
	}
	
	private void setNumberOfRed(int newRedCount) {
		numberOfRed = newRedCount;
	}
	
	private void setNumberOfWhite(int newWhiteCount) {
		numberOfWhite = newWhiteCount;
	}
	
	private boolean anyValidMovesRed() {
		for (int i = 0; i < row; i++) {
			for (int j = 0; j < column; j++) {
				if(board[i][j] == 1) { //if a space contains red piece
					if(validateMove(i, j, i+1, j-1) < 1) { //check left diagonal 1 space away
						return true;
					}
					else if(validateMove(i, j, i+2, j-2) < 1) { //check left diagonal 2 spaces away
						return true;
					}
					else if(validateMove(i, j, i+1, j+1) < 1) { //check right diagonal
						return true;
					}
					else if(validateMove(i, j, i+2, j+2) < 1) { //check right diagonal 2 spaces away
						return true;
					}
				}
			}
		}
		
		return false;
	}
	
	public boolean anyValidMovesWhite() {
		for (int i = 0; i < row; i++) {
			for (int j = 0; j < column; j++) {
				if(board[i][j] == 2) { //if a space contains white piece
					if(validateMove(i, j, i-1, j-1) < 1) { //check left diagonal 1 space away
						return true;
					}
					else if(validateMove(i, j, i-2, j-2) < 1) { //check left diagonal 2 spaces away
						return true;
					}
					else if(validateMove(i, j, i-1, j+1) < 1) { //check right diagonal 1 space away
						return true;
					}
					else if(validateMove(i, j, i-2, j+2) < 1) { //check right diagonal 2 spaces away
						return true;
					}
				}
			}
		}
		
		return false;
	}
	
	public void movePiece(int rowStart, int columnStart, int rowEnd, int columnEnd) {
		int outcome = validateMove(rowStart, columnStart, rowEnd, columnEnd);
		if(outcome < 1) { //if the piece can be moved where the user wants, move it
			board[rowEnd][columnEnd] = board[rowStart][columnStart]; //set end position to appropriate piece color
			board[rowStart][columnStart] = 0; //set old position to empty space
			nextPlayerTurn();
			if (outcome == -1) { //-1 is the case for capturing an enemy piece
				removePiece(gapRow, gapColumn);
			}
		}
	}
	
	private void removePiece(int pRow, int pColumn) {
		board[pRow][pColumn] = 0;
		
		if(board[pRow][pColumn] == 1) { //if removed red piece
			setNumberOfRed(getNumberOfRed() - 1); //decrement red piece counter
		}
		else { //if removed white piece
			setNumberOfWhite(getNumberOfWhite() - 1); //decrement white piece counter
		}
	}
	
	public int hasEnded() {
		if(getNumberOfRed() == 0 || (playerTurn == 1 && !anyValidMovesRed())) { return 2; } //white won
		
		else if(getNumberOfWhite() == 0 || (playerTurn == 2 && !anyValidMovesWhite())) { return 1; } //red won
		
		else { return 0; } //game still going
	}
	
	private void nextPlayerTurn() {
		playerTurn = (-1*playerTurn) + 3; //if player 1s turn, return 2. if player 2s turn, return 1.
	}
	
	public int validateMove(int rowStart, int columnStart, int rowEnd, int columnEnd) {
		int rowTranslation = rowEnd - rowStart;
		int columnTranslation = columnEnd - columnStart;
		int pathLine; //basically the linear growth of the path they take on the board (if it was a graph)
		int userPiece = board[rowStart][columnStart];
		int enemyPiece = (-1*userPiece)+3; //if user is moving red(1), returns 2. if user is moving white(2), returns 1
		int gapPiece; //if user moves two spaces, returns the value of the gap between
		
		if(board[rowStart][columnStart] != 1 && board[rowStart][columnStart] != 2) { //if start is neither a red nor a white piece
			return 1; //display user hasn't selected a piece
		}
		
		if(rowEnd > 7 || rowEnd < 0 || columnEnd > 7 || columnEnd < 0) { //if user moving out of bounds
			return 1; //display user moving out of bounds
		}
		
		if(userPiece == 1) {                //if piece is red
			if(playerTurn != 1) {           //if it's player 1's turn
				return 1; //display user cannot move enemy piece
			}
			else if(rowTranslation < 1) { //if piece moving down or not at all
				return 1; //display user moving wrong way
			}
		} 
		else {                              //if piece is white
			if(playerTurn != 2) {           //if it's player 2's turn
				return 1; //display user cannot move enemy piece
			}
			if(rowTranslation > -1) { //if piece moving up or not at all
				return 1; //display user moving wrong way
			}
		}
		
		pathLine = rowTranslation / columnTranslation; //basically the linear growth of the path they take on the board (if it was a graph)
		if(pathLine != 1 && pathLine != -1) { //if not moving correctly diagonally left nor right or didn't move at all
			return 1; //display piece can't be moved like that
		}
		
		if(rowTranslation > 2 || rowTranslation < -2) { //if moving more than 2 spaces away
			return 1; //display piece can't move that far
		}
		
		if(board[rowEnd][columnEnd] != 0) { //if there's isn't an empty space where user is trying to move
			return 1; //display can't move on top of another piece
		}
		
		if(Math.abs(columnTranslation) == 2) { //if moving 2 spaces diagonally left or right
			gapPiece = board[rowEnd-(rowTranslation/2)][columnEnd-(columnTranslation/2)]; //if user moves two spaces, returns the value of the gap between
			if(gapPiece != enemyPiece) { //getting value of the gap the piece skipped over, checking if it's an enemy piece
				return 1; //display moving too many spaces
			}
			else { //if you did jump over an enemy piece: 
				gapRow = rowEnd-(rowTranslation/2);
				gapColumn = columnEnd-(columnTranslation/2);
				return -1; //allowed to move two spaces because captured an enemy piece
			}
		}
		
		if(rowTranslation == 1 && columnTranslation == 1) { //if theyre moving one space diagonally
			return 0;
		}
		
		return 0;
	}
	
}
