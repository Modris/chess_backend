package com.modris.model;

import org.springframework.stereotype.Component;

@Component
public class FenValidator {

	public FenValidator() {
	}

	public boolean isFenValid(String fen) {
		fen = fen.trim();
		String[] fenArray = fen.split("/"); // for Piece placement
		String[] fenArrayExtra = fen.split(" "); // for active color, castling, half move, fullmove
		String[] fenArrayPieces = fenArrayExtra[0].split("/");

		eightPartsCheck(fenArray);
		
		sixPartsCheck(fenArrayExtra);
		activeColor(fenArrayExtra);
		halfMoveCheck(fenArrayExtra);
		fullMoveCheck(fenArrayExtra);
		castlingCheck(fenArrayExtra);
		enPassantCheck(fenArrayExtra);
		
		twoKingsCheck(fenArrayPieces);
		return false;
	}

	private boolean eightPartsCheck(String fen[]) {
		return fen.length == 8;

	}

	private boolean sixPartsCheck(String fen[]) {
		return fen.length == 6;

	}

	private boolean activeColor(String fen[]) {
		return fen[1].length() == 1 && (fen[1].equals("w") || fen[1].equals("b"));
	}

	private boolean twoKingsCheck(String fen[]) {
		int count = 0;
		for(int i =0; i<fen.length; i++) {
			if(fen[i].contains("k") || fen[i].contains("K")) {
				count++;
			}
		}
		return count == 2;
	}

	private boolean enPassantCheck(String fen[]) { 
		// Either '-' or 'e6' is possible. Max length 1 or 2.
		if (fen[3].length() == 1 && fen[3].charAt(0) == '-') {
			return true;
		} else if (fen[3].length() == 2) {
			char file = fen[3].charAt(0);
			char rank = fen[3].charAt(1);
			if( (rank >= '1' && rank <='8') && fileCheck(file)) {
				return true;
			}
		}
		return false;
	}
	
	private boolean fileCheck(char file) {
		return file == 'a' || file == 'b' || file == 'c' || file == 'd' || file == 'e'
				|| file == 'f' || file == 'g' || file == 'h';
	}

	private boolean numbersOneToEighCheck(String fen) {

		return false;
	}

	private boolean validInputCheck(String fen) { // 1-8, P,N,B,Q,R,-,K check

		return false;
	}

	private boolean sumOfEightCheck(String fen) { // also no consecutive numbers.

		return false;
	}

	private boolean castlingCheck(String fen[]) {
		fen[2] = fen[2].toLowerCase();
		if (fen[2].length() <= 4) { // max we can have kQkQ. No more.
			for (int i = 0; i < fen[2].length(); i++) {
				if (fen[2].charAt(i) == 'k' || fen[2].charAt(i) == 'q' || fen[2].charAt(i) == '-') {
					return true;
				} else {
					return false;
				}
			}
		} else {
			return false;
		}

		
		// kK,kQ etc
		// if rooks are not in their starting positions no castling rights
		return false;
	}

	private boolean halfMoveCheck(String fen[]) {
		return Integer.valueOf(fen[4]) >= 0;

	}

	private boolean fullMoveCheck(String fen[]) {
		return Integer.valueOf(fen[5]) >= 1;
	}
}
