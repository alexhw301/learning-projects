package core;

import java.util.ArrayList;
import java.util.Random;

public class CheckersComputerPlayer extends CheckersLogic {
	
	public CheckersComputerPlayer() {
		
	}

	public String pickMove(CheckersLogic game) { //will randomly pick out of arraylist
		Random random = new Random();
		int randomInt;
		int chosenMoveIndex = 0;
		String move = "";
		ArrayList<String> allMoves = new ArrayList<>(); //new empty String arraylist
		
		allValidMoves(game, allMoves); //fill arrayList with every valid move
		
		randomInt = random.nextInt(50); //generate random int from 0 to 50
		
		while(randomInt > 0) {
			for (int i = 0; i < allMoves.size(); i++) {
				chosenMoveIndex = i;
				randomInt--;
			}
		}
		
		move = allMoves.get(chosenMoveIndex);
		return move;
	}
	
	private ArrayList<String> allValidMoves(CheckersLogic game, ArrayList<String> moveList) {
		
		for (int i = 0; i < game.getRows(); i++) {
			for (int j = 0; j < game.getRows(); j++) {
				if(game.getElement(i, j) == 2) { //if a space contains white piece
					if(game.validateMove(i, j, i-1, j-1) < 1) { //check left diagonal 1 space away
						moveList.add(convertMoveTurn(i, j, i-1, j-1));
						
					}
					if(game.validateMove(i, j, i-2, j-2) < 1) { //check left diagonal 2 spaces away
						moveList.add(convertMoveTurn(i, j, i-2, j-2));
						
					}
					if(game.validateMove(i, j, i-1, j+1) < 1) { //check right diagonal 1 space away
						moveList.add(convertMoveTurn(i, j, i-1, j+1));
						
					}
					if(game.validateMove(i, j, i-2, j+2) < 1) { //check right diagonal 2 spaces away
						moveList.add(convertMoveTurn(i, j, i-2, j+1));
						
					}
				}
			}
		}
		
		
		return moveList;
	}
	
	private String convertMoveTurn(int row1, int column1, int row2, int column2) {
		char newRow1, newRow2, newColumn1, newColumn2;
		String fullMove;
		
		newRow1 = (char)(row1+49);
		newColumn1 = (char)(column1+97);
		newRow2 = (char)(row2+49);
		newColumn2 = (char)(column2+97);
		
		fullMove = newRow1 + "" + newColumn1 + "-" + newRow2 + newColumn2;
		return fullMove;
	}

}
