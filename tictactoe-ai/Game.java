package tictactoeai;

import apcs.Window;

public class Game {

	public static void main(String[] args) {
		Window.size(600, 600);
		
		Board b = new Board();
		
		while (true) {
			b.draw();
			
			
			// Get the board winner
			int winner = b.getWinner();
			if (winner > 0) {
				Window.out.color("red");
				Window.out.font("Courier", 100);
				
				// Print out who the winner is
				if (winner == 1) {
					Window.out.print("X wins!", 100, 320);
				}
				else {
					Window.out.print("O wins!", 100, 320);
				}
				
				// To reset, put a new board object into b when the space key is pressed
				if (Window.key.pressed("space")) {
					b = new Board();
				}
			}
			
			// Otherwise, if there is no winner, then allow a human to move.
			else {
				if (b.player == 1) {
					b.humanMove();
				}
				else {
					b.computerMove();
				}
			}
			
			
			Window.frame();
		}
	}

}
