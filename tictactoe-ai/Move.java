package tictactoeai;

public class Move {
	int x;
	int y;
	int value;
	
	public Move(int xpos, int ypos) {
		x = xpos;
		y = ypos;
	}
	
	// Optional: make a getter
	public int getX() {
		return x;
	}
	
	// Optional: make a getter
	public int getY() {
		return y;
	}
}
