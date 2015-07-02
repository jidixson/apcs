package tictactoeai;

import apcs.Window;

public class Board {
	int[][] board;
	int player = 1;

	public Board() {
		board = new int[3][3];
	}

	public Board(Board start, Move move) {
		// Make a new 2D array and deep copy over the values
		// of the other array.
		board = new int[3][3];
		for (int x = 0 ; x < 3 ; x++) {
			for (int y = 0 ; y < 3 ; y++) {
				board[x][y] = start.board[x][y];
			}
		}
		
		player = start.player;

		// Perform the move on the board.
		board[move.x][move.y] = player;
		player = 1 + player % 2;
	}

	public Move[] getMoves() {
		int count = 0;
		// 1. Go to every square on the tic tac toe board
		for (int x = 0 ; x < 3 ; x++) {
			for (int y = 0 ; y < 3 ; y++) {

				// If the square is empty
				if (board[x][y] == 0) {
					count = count + 1;
				}

			}
		}

		// 2. Once you have the count, make an array of moves of that length
		Move[] moves = new Move[count];
		int index = 0;

		// 3. Go again to every square on the tic tac toe board
		for (int x = 0 ; x < 3 ; x++) {
			for (int y = 0 ; y < 3 ; y++) {

				// If the square is empty
				if (board[x][y] == 0) {
					moves[index] = new Move(x, y);
					index = index + 1;
				}

			}
		}

		// 4. Return the array of moves
		return moves;
	}
	
	// Draw the board
	public void draw() {
		Window.out.background("white");
		
		// Draw the grid line
		Window.out.color("black");
		Window.out.rectangle(300, 200, 570, 10);
		Window.out.rectangle(300, 400, 570, 10);
		Window.out.rectangle(200, 300, 10, 570);
		Window.out.rectangle(400, 300, 10, 570);
		
		// Draw the x's and the o's
		Window.out.font("Courier", 150);
		for (int x = 0 ; x < 3 ; x++) {
			for (int y = 0 ; y < 3 ; y++) {
				if (board[x][y] == 1) {
					Window.out.print("X", 50 + x * 200, 150 + y * 200);
				}
				if (board[x][y] == 2) {
					Window.out.print("O", 50 + x * 200, 150 + y * 200);
				}
			}
		}
	}
	
	// Just puts a human move on the board.
	public void humanMove() {
		if (Window.mouse.clicked()) {
			int x = Window.mouse.getX() / 200;
			int y = Window.mouse.getY() / 200;
			
			if (board[x][y] == 0) {
				board[x][y] = player;
				player = 1 + player % 2;
			}
		}
	}
	
	public int getWinner() {
		// Horizontal ways to win
		for (int x = 0 ; x < 3 ; x++) {
			if (board[x][0] > 0 && board[x][0] == board[x][1] && board[x][1] == board[x][2]) {
				return board[x][0];
			}
		}
		// Vertical ways to win
		for (int y = 0 ; y < 3 ; y++) {
			if (board[0][y] > 0 && board[0][y] == board[1][y] && board[1][y] == board[2][y]) {
				return board[0][y];
			}
		}
		// Diagonal ways to win
		if (board[0][0] > 0 && board[0][0] == board[1][1] && board[1][1] == board[2][2]) {
			return board[0][0];
		}
		if (board[2][0] > 0 && board[2][0] == board[1][1] && board[1][1] == board[0][2]) {
			return board[2][0];
		}
		return 0;
	}
	
	public void computerMove() {
		Window.out.color("red");
		if (getMoves().length > 0) {
			int worst = 2;
			Move worstMove = null;
			for (Move m : getMoves()) {
				Board next = new Board(this, m);
				int val = next.minimax(1 + player % 2, true);
				Window.out.print(val, 50 + m.x * 200, 150 + m.y * 200);
				if (val < worst) {
					worst = val;
					worstMove = m;
				}
			}
			Window.frame(1000);
			
			board[worstMove.x][worstMove.y] = player;
			player = 1 + player % 2;
			
//			Move best = bestMove(player);
//			board[best.x][best.y] = player;
//			player = 1 + player % 2;
		}
	}
	
	private int minimax(int player, boolean findBest) {
		// If there is no move on the board
		if (getMoves().length == 0 || getWinner() > 0) {
			int winner = getWinner();
			if (winner == player) {
				return 1;
			}
			else if (winner == 1 + player % 2) {
				return -1;
			}
			else {
				return 0;
			}
		}
		
		int best = 0;
		if (findBest) {
			best = -2;
			for (Move m : getMoves()) {
				Board next = new Board(this, m);
				//next.draw();
				//Window.frame();
				int result = next.minimax(player, ! findBest);
				if (result > best) {
					best = result;
				}
			}
		}
		else {
			best = 2;
			for (Move m : getMoves()) {
				Board next = new Board(this, m);
				int result = next.minimax(player, ! findBest);
				if (result < best) {
					best = result;
				}
			}
		}
		return best;
	}

	// max
	private Move bestMove(int forPlayer) {
		Move bestMove = null;
		Move[] possibleMoves = getMoves();
		
		for (Move move : possibleMoves) {
			Board nextState = new Board(this, move);
			
			// Case 1: there's a winner on the nextState and it's 
			// the player I'm picking the best move for
			if (nextState.getWinner() == forPlayer) {
				move.value = 1;
				return move;
			}
			
			// Case 2: the winner is the other player
			else if (nextState.getWinner() == 1 + forPlayer % 2) {
				move.value = -1;
				if (bestMove == null || bestMove.value < move.value) {
					bestMove = move;
				}
			}
			
			// Case 3: if the next state is a draw
			else if (nextState.getMoves().length == 0) {
				move.value = 0;
				if (bestMove == null || bestMove.value < move.value) {
					bestMove = move;
				}
			}
			
			// Case 4: more to play
			else {
				Move worst = nextState.worstMove(1 + forPlayer % 2);
				if (bestMove == null || bestMove.value < worst.value) {
					bestMove = move;
				}
			}
		}
		
		return bestMove;
	}
	
	// min
	private Move worstMove(int forPlayer) {
		Move worstMove = null;
		Move[] possibleMoves = getMoves();
		
		for (Move move : possibleMoves) {
			Board nextState = new Board(this, move);
			
			// Case 1: there's a winner on the nextState and it's 
			// the player I'm picking the best move for
			if (nextState.getWinner() == forPlayer) {
				move.value = 1;
				if (worstMove == null || worstMove.value > move.value) {
					worstMove = move;
				}
			}
			
			// Case 2: the winner is the other player
			else if (nextState.getWinner() == 1 + forPlayer % 2) {
				move.value = -1;
				return move;
			}
			
			// Case 3: if the next state is a draw
			else if (nextState.getMoves().length == 0) {
				move.value = 0;
				if (worstMove == null || worstMove.value > move.value) {
					worstMove = move;
				}
			}
			
			// Case 4: more to play
			else {
				Move best = nextState.bestMove(1 + forPlayer % 2);
				if (worstMove == null || worstMove.value > best.value) {
					worstMove = move;
				}
			}
		}
		
		return worstMove;
	}
}
