package drop7;

import apcs.Window;

public class Drop7AI {

	public static void main(String[] args) {
		Board b = new Board();
		b.startDrawing();
		
		while (true) {
			b.draw();
			
			System.out.println("Checking permutations for " + b.number);
			
			int maxCol = 0;
			double maxValue = 0, expected;
			for (int col = 0 ; col < 7 ; col++) {
				Board testBoard = new Board(b);
				expected = testBoard.dropExpectedValue(b.number, col, 3);
				System.out.println((col + 1) + ": " + expected);
				if (expected > maxValue) {
					maxValue = expected;
					maxCol = col;
				}
			}
			b.drop(b.number, maxCol);
			
			Window.frame();
		}
	}

}
