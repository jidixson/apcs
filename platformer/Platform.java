package platformer;

import apcs.Window;

public class Platform {
	// Random x position
	int x = 50 + Window.rollDice( Window.width() - 100 );
	// Starts off the screen above
	int y = -10;
	// X speed
	int dx = 5;
	
	public Platform() {
		if (Window.flipCoin()) {
			dx = 5;
		}
		else {
			dx = -5;
		}
	}
	
	public void draw() {
		Window.out.color("black");
		Window.out.rectangle(x, y, 100, 10);
	}

	public void move() {
		// Physics
		y = y + 2;
		x = x + dx;
		
		// Constraints
		if (x < 50) {
			x = 50;
			dx = -dx;
		}
		if (x > Window.width() - 50) {
			x = Window.width() - 50;
			dx = -dx;
		}
	}

}
