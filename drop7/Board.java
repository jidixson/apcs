package drop7;
import apcs.Window;

/**
 * @author keshav
 * @version 1.0
 */

public class Board {
	
	// Constants to represent various discs.
	private static final int GRAY_DISC = 8;
	private static final int GRAY_CRACKED_DISC = 9;
	private static final int EXPLODED_GRAY = 10;
	private static final int EXPLODED = 11;
	private static final int EMPTY = 0;
	
	// The underlying grid to store the game board.
	private int[][] board;
	
	// The value of chained explosions.
	private int chainValue[] = { 
		7, 39, 109, 224, 391, 617, 907, 1267, 
		1701, 2213, 2809, 3391, 3851, 4265, 4681, 5113
	};
	
	// Constants for interactive 
	public int column = -1;
	public int number = Window.rollDice(7);
	public int dropped = 0;
	public int score = 0;
	public int levelUpRate = 10;
	
	// AI options
	public int depth = 5;
	public int onlyExplosionPenalty = 1;
	
	// UI flags and constants.
	public boolean isDrawing = false;
	private boolean isDropping = false;
	public static final int SIZE = 100;
	public static final int EXPLOSION_DELAY = 300;
	public static final int DROP_DELAY = 0;
	public static final int FALLING_DELAY = 50;
	public static final int PADDING = 10;
	
	/**
	 *  Constructs a Board object. A board is a 7 x 7 matrix, with 1-7 representing numbers, 8 a gray circle, 
	 *  9 a cracked gray, 10 a revealed gray.
	 *  @param int[][] other is a 7 x 7 matrix
	 */
	public Board() {
		board = new int[7][7];
		for (int x = 0 ; x < 7 ; x++) {
			for (int y = 0 ; y < 7 ; y++) {
				board[x][y] = 0;
			}
		}
	}
	
	/**
	 * Copies another board object or 
	 * @param other
	 */
	public Board(Board other) {
		board = new int[7][7];
		for (int x = 0 ; x < 7 ; x++) {
			for (int y = 0 ; y < 7 ; y++) {
				board[x][y] = other.board[x][y];
			}
		}
		
		this.column = other.column;
		this.number = other.number;
		this.dropped = other.dropped;
		this.score = other.score;
		this.depth = other.depth;
	}
	
	public void reset() {
		board = new int[7][7];
		this.column = -1;
		this.number = Window.rollDice(7);
		this.dropped = 0;
		this.score = 0;
	}
	
	/**
	 *  Drops a number (num) into the column col (1 through 7), and returns the score resulting from the drop.
	 */
	
	public int drop(int num, int col) {
		if (board[0][col] != 0 || num < 1 || num > 7 || col < 0 || col >= 7) {
			return -1;
		}
		
		board[0][col] = num;
		applyGravity();
		
		if (isDrawing) {
			int score = explode(0);
			draw();
			Window.frame();
			Window.sleep(DROP_DELAY);
			draw();
			number = Window.rollDice(7);
			dropped++;
			if (dropped % levelUpRate == 0) {
				levelUp();
			}
			
			return score;
		}
		else return explode(0);
	}
	
	public boolean canDrop(int col) {
		return board[0][col] == 0 && col >= 0 && col < 7;
	}
	
	/**
	 * Recursive function for exploding.
	 * @param index - the current chain value index.
	 * @return the score from the explosion.
	 */
	public int explode(int index) {
		int chainScore = 0;
		
		// First, explode all the discs that can be exploded.
		for (int x = 0 ; x < 7 ; x++) {
			for (int y = 0 ; y < 7 ; y++) {
				if (canExplode(x, y)) {
					chainScore += chainValue[index];
					board[x][y] = EXPLODED;
					revealAround(x, y);
				}
			}
		}
		
		// If there were any explosions in this step.
		if (chainScore > 0) {
			for (int x = 0 ; x < 7 ; x++) {
				for (int y = 0 ; y < 7 ; y++) {
					if (board[x][y] == EXPLODED_GRAY) {
						board[x][y] = Window.rollDice(7);
					}
				}
			}
			
			if (isDrawing) {
				draw();
				Window.frame();
				Window.sleep(EXPLOSION_DELAY);
			}
			
			for (int x = 0 ; x < 7 ; x++) {
				for (int y = 0 ; y < 7 ; y++) {
					if (board[x][y] == EXPLODED) {
						board[x][y] = EMPTY;
					}
				}
			}
			applyGravity();
			
			if (isDrawing) {
				score += chainScore;
				draw();
				Window.frame();
				Window.sleep(100);
			}
			
			return chainScore + explode(index + 1);
		}
		else return 0;
	}
	
	public double dropExpectedValue(int num, int col) {
		return dropExpectedValue(num, col, depth);
	}
	
	/**
	 * Same as the drop function above, but returns the score plus the expected value from any revealed grays.
	 * This is used in the simulator.
	 */
	public double dropExpectedValue(int num, int col, int depth) {
		if (depth <= 0) {
			return 0;
		}
		if (board[0][col] != 0) {
			return -1;
		}
		column = col;
		
		Board search = new Board(this);
		search.board[0][col] = num;
		search.applyGravity();
		
		// If there is a more complex explosion, do a search.
		return search.expectedValue(0, depth);
	}
	
	/**
	 * Returns the expected value of the current board by weighing future board positions by their probabilities of occurring.  
	 * @return the score to be expected from the board in the next `depth` moves. 
	 */
	public double expectedValue(int index, int depth) {
		if (depth <= 0) return 0;
		double chainScore = 0;
		
		// First explode all discs.
		for (int x = 0 ; x < 7 ; x++) {
			for (int y = 0 ; y < 7 ; y++) {
				if (canExplode(x, y)) {
					chainScore += chainValue[index];
					board[x][y] = EXPLODED;
					revealAround(x, y);
				}
			}
		}
		// If there were any explosions.
		if (chainScore > 0) {
			int grayCount = 0;
			
			for (int x = 0 ; x < 7 ; x++) {
				for (int y = 0 ; y < 7 ; y++) {
					if (board[x][y] == EXPLODED) {
						board[x][y] = EMPTY;
					}
					if (board[x][y] == EXPLODED_GRAY) {
						grayCount++;
					}
				}
			}
			applyGravity();
			
			// If there should be a level up.
			if (dropped % levelUpRate == 0) {
				levelUp();
			}
			
			// If there were any exploded gray blocks, calculate the
			// expected value from the cases where the gray discs explode.
			if (grayCount > 0) {
				// Go through every permutation of filled in gray squares.
				Permutation gray = new Permutation(7, grayCount);
				double permutationCount = gray.length();
				for (int i = 0 ; i < permutationCount ; i++) {
					// Create a temporary board and enter the permutation to fill the
					// exploded gray squares.
					Board temp = new Board(this);
					int[] permutation = gray.get(i);
					int grayIndex = 0;
					
					for (int x = 0 ; x < 7 ; x++) {
						for (int y = 0 ; y < 7 ; y++) {
							if (grayIndex < grayCount && temp.board[x][y] == EXPLODED_GRAY) {
								temp.board[x][y] = permutation[grayIndex];
								grayIndex++;
							}
						}
					}
					
					if (temp.canExplode()) {
						chainScore += temp.expectedValue(index + 1, depth) / permutationCount;
					}
					else {
						chainScore += temp.expectedValue(0, depth - grayCount) / permutationCount;
					}
				}
				return chainScore;
			}
			else {
				return chainScore + expectedValue(index + 1, depth);
			}
		}
		else {
			double expected = 0;
			for (int num = 1 ; num <= 7 ; num++) {
				
				// Check for branch pruning opportunity.
//				boolean pruneBranch = false;
//				for (int col = 0 ; col < 7 && pruneBranch ; col++) {
//					if (board[0][col] == 0) {
//						int row = 0;
//						while (row < 6 && board[row + 1][col] == 0) {
//							row++;
//						}
//						board[row][col] = num;
//						if (! onlyExplosionAt(row, col)) {
//							pruneBranch = false;
//						}
//						board[row][col] = 0;
//					}
//				}
//				
//				if (pruneBranch) {
//					Board next = new Board(this);
//					expected += 7 * (7 + next.expectedValue(0, depth - 1));
//				}
//				else {
					for (int col = 0 ; col < 7 ; col++) {
						Board next = new Board(this);
						expected += next.dropExpectedValue(num, col, depth - 1);
					}
				//}
			}
			return expected / 49;
		}
	}
	
	private boolean canExplode() {
		for (int x = 0 ; x < 7 ; x++) {
			for (int y = 0 ; y < 7 ; y++) {
				if (canExplode(x, y)) return true;
			}
		}
		return false;
	}
	
	private boolean onlyExplosionAt(int xpos, int ypos) {
		for (int x = 0 ; x < 7 ; x++) {
			for (int y = 0 ; y < 7 ; y++) {
				if (canExplode(x, y)) {
					if (x == xpos && y == ypos) {
						if (x < 6 && board[x + 1][y] > 7) return false;
						if (x > 0 && board[x - 1][y] > 7) return false;
						if (y < 6 && board[x][y + 1] > 7) return false;
					}
					else return false;
				}
			}
		}
		return true;
	}
	
	/**
	 * Returns true if the given x, y coordinate has a disc that can explode.
	 */
	private boolean canExplode(int x,int y) {
		if (board[x][y] == 0 || board[x][y] > 7) {
			return false;
		}
		
		if (horizontal(x,y) == board[x][y] || vertical(x,y) == board[x][y]) {
			return true;
		}
		return false;
	}
	
	private int horizontal(int xpos, int ypos) {
		int horizontal = 1;
		if (xpos != 0) {
			for (int x = xpos - 1; x >= 0 ; x--) {
				if (board[x][ypos] != 0) horizontal++;
				else break;
			}
		}
		if (xpos != 6) {
			for (int x = xpos + 1 ; x < 7 ; x++) {
				if (board[x][ypos] != 0) horizontal++;
				else break;
			}
		}
		return horizontal;
	}
	
	private int vertical(int xpos, int ypos) {
		int vertical = 1;
		if (ypos != 0) {
			for (int y = ypos - 1; y >= 0 ; y--) {
				if (board[xpos][y] != 0) vertical++;
				else break;
			}
		}
		if (ypos != 6) {
			for (int y = ypos + 1; y < 7 ; y++) {
				if (board[xpos][y] != 0) vertical++;
				else break;
			}
		}
		return vertical;
	}
	
	private void applyGravity() {
		for (int i = 0 ; i < 6 ; i++) {
			boolean gravitated = false;
			for (int x = 5 ; x >= 0 ; x--) {
				for (int y = 0 ; y < 7 ; y++) {
					if (board[x][y] != 0 && board[x + 1][y] == 0) {
						board[x + 1][y] = board[x][y];
						board[x][y] = 0;
						gravitated = true;
					}
				}
			}
			if (! gravitated) break;
			if (isDrawing) {
				draw();
				Window.frame();
				Window.sleep(FALLING_DELAY);
			}
		}
	}
	
	/**
	 * For an explosion, crack or reveal any gray squares in the neighborhood
	 * of the x, y coordinate.
	 */
	private void revealAround(int x, int y) {
		if (x - 1 >= 0 && board[x - 1][y] > 7 && board[x - 1][y] < 10) {
			board[x - 1][y]++;
		}
		if (y - 1 >= 0 && board[x][y - 1] > 7 && board[x][y - 1] < 10) {
			board[x][y - 1]++;
		}
		if (y + 1 < 7 && board[x][y + 1] > 7 && board[x][y + 1] < 10) {
			board[x][y + 1]++;
		}
		if (x + 1 < 7 && board[x + 1][y] > 7 && board[x + 1][y] < 10) {
			board[x + 1][y]++;
		}
	}
	
	/**
	 * Pushes a new row of gray circles to the bottom row.
	 */
	private int levelUp() {
		// Check if the game is over.
		for (int i = 0 ; i < 7 ; i++) {
			if (board[0][i] != 0) {
				
				// If the board object is being drawn, show a game over screen.
				if (isDrawing) {
					draw();
					gameOver();
					Window.frame();
					draw();
					while (! Window.mouse.clicked());
					reset();
				}
				
				return -1;
			}
		}
		
		// Move all discs up one row.
		for (int y = 0 ; y < 7 ; y++) {
			for (int x = 1 ; x < 7 ; x++) {
				board[x - 1][y] = board[x][y];
			}
		}
		// Add a solid gray disc underneath.
		for (int k = 0 ; k < 7 ; k++) {
			board[6][k] = GRAY_DISC;
		}
		int score = explode(0);
		applyGravity();
		
		if (isDrawing) {
			draw();
			Window.frame();
			Window.sleep(DROP_DELAY);
			draw();
		}
		
		return score;
	}
	
	private void gameOver() {
		Window.out.color(240, 240, 240);
		Window.out.rectangle(PADDING + SIZE * 7 / 2, PADDING + SIZE * 5 / 4, SIZE * 7 / 2, SIZE);
		Window.out.color(40, 40, 40);
		Window.out.font("Courier", 45);
		Window.out.print("GAME OVER", PADDING + SIZE * 2 + 25, PADDING + SIZE + 15);
		Window.out.font("Courier", 30);
		Window.out.print("click to restart", PADDING + SIZE * 2 + 5, PADDING + SIZE + 50);
	}

	/**
	 * Returns the disc at the given row and column.
	 * @param row - the row index (0 - 6)
	 * @param column - the column index (0 - 6)
	 */
	public int getDisc(int row, int column) {
		return board[row][column];
	}
	
	public void startDrawing() {
		isDrawing = true;
		Window.size(7 * Board.SIZE + PADDING * 2, 8 * Board.SIZE + PADDING * 2);
		Window.setFrameRate(30);
	}
	
	public void draw() {
		Window.out.font("Courier", 50);
		
		// White background.
		Window.out.color("white");
		Window.out.rectangle(PADDING + SIZE * 7 / 2, PADDING + SIZE * 9 / 2, SIZE * 7 + 4, SIZE * 7 + 4);
		
		// Draw the score.
		Window.out.print(score, 25, 75);
		
		// Draw each cell and disc.
		for (int x = 0 ; x < 7 ; x++) {
			for (int y = 0 ; y < 7 ; y++) {
				drawCell(x, y);
				drawDisc(x, y, board[y][x]);
			}
		}
		
		if (! isDropping) {
			drawDisc(column, -1, number);
		}
	}
	
	private void drawCell(int x, int y) {
		Window.out.color("white");
		Window.out.rectangle(PADDING + x * SIZE + SIZE / 2, PADDING + (y + 1) * SIZE + SIZE / 2, SIZE, SIZE);
		if (column == x)
			Window.out.color(40, 40, 40);
		else Window.out.color("black");
		Window.out.rectangle(PADDING + x * SIZE + SIZE / 2, PADDING + (y + 1) * SIZE + SIZE / 2, SIZE - 2, SIZE - 2);
	}
	
	private void drawDisc(int x, int y, int num) {
		if (num > 0) {
			switch(num) {
			case 1: Window.out.color(104, 188, 68); break;
			case 2: Window.out.color(204, 201, 2); break;
			case 3: Window.out.color(247, 129, 0); break;
			case 4: Window.out.color(218, 40, 38); break;
			case 5: Window.out.color(201, 39, 153); break;
			case 6: Window.out.color(29, 189, 239); break;
			case 7: Window.out.color(66, 83, 152); break;
			case 8: Window.out.color("gray"); break;
			case 9: Window.out.color("light gray"); break;
			case 11: Window.out.color("white"); break;
			}
			Window.out.oval(PADDING + x * SIZE + SIZE / 2, PADDING + (y + 1) * SIZE + SIZE / 2, SIZE * 4 / 5, SIZE * 4 / 5);
			
			// Print the number of the disc for 1 - 7.
			if (num < 8) { 
				Window.out.color("white");
				Window.out.print(Integer.toString(num), PADDING + x * SIZE + SIZE * 2 / 5, PADDING + (y + 1) * SIZE + SIZE * 3 / 5); 
			}
			// Create lines to show a cracked disc.
			else if (num == GRAY_CRACKED_DISC) {
				Window.out.color("black");
				Window.out.print("*", x * SIZE + SIZE / 2 - 5, (y + 1) * SIZE + SIZE * 3 / 4);
			}
			// An exploded gray that hasn't been replaced by a number.
			else if (num == EXPLODED_GRAY) {
				Window.out.color("white");
				Window.out.print("?", x * SIZE + SIZE * 2 / 5, (y + 1) * SIZE + SIZE * 2 / 3);
			}
			// For explosions.
			else if (num == EXPLODED) {
				Window.out.color("black");
				Window.out.print("!", x * SIZE + SIZE / 2 - 5, (y + 1) * SIZE + SIZE * 3 / 4);
			}
		}
	}
	
	public void move() {
		int x = Window.mouse.getX();
		if (x >= PADDING && x < PADDING + SIZE * 7) {
			column = (x - PADDING) / SIZE;
			
			if (Window.mouse.clicked()) {
				if (! isDropping && canDrop(column)) {
					isDropping = true;
					drop(number, column);
				}
			}
			if (Window.key.pressed("space")) {
				for (int col = 0 ; col < 7 ; col++) {
					Board testBoard = new Board(this);
					System.out.println(col + " - " + testBoard.dropExpectedValue(number, col, 3));
				}
			}
			else isDropping = false;
		}
	}
}
