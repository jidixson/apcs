package platformer;

import apcs.Window;

public class Jumper {
	// Starts at the center of the screen
	int x = Window.width() / 2;
	// Start 50 pixels from the bottom.
	int y = Window.height() - 50;
	// Speed
	int dy = 0;
	// Can I jump?
	boolean canJump = true;

	public void draw() {
		//Window.out.color("red");
		//Window.out.circle(x, y, 20);
		Window.out.image("sensei.png", x - 30, y - 30);
	}

	public void move() {
		// 1. Check input
		if (Window.key.pressed("left")) {
			x = x - 5;
		}
		if (Window.key.pressed("right")) {
			x = x + 5;
		}
		// Up key makes the speed negative
		if (Window.key.pressed("up") && canJump) {
			dy = -15;
			canJump = false;
		}
		
		// 2. Do the physics
		
		// Y changes by the speed
		y = y + dy;
		// The downward speed increases
		dy = dy + 1;

		// 3. Check constraints
		if (y > Window.height() - 50) {
			y = Window.height() - 50;
			dy = 0;
			canJump = true;
		}
		// Constrain x motion
		if (x < 20) {
			x = 20;
		}
		if (x > Window.width() - 20) {
			x = Window.width() - 20;
		}
	}

	public void jumpTo(Platform platform) {
		y = platform.y - 20;
		dy = 0;
		canJump = true;
		x = x + platform.dx;
	}
	
	public boolean isOn(Platform platform) {
		if (dy >= 0 && Math.abs(x - platform.x) <= 50 && Math.abs(y - platform.y) <= 10) {
			return true;
		}
		return false;
	}

}
