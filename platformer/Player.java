package platformer;

import apcs.Window;

public class Player {

	int x = 250;
	int y = 425;
	
	int yspeed = 0;
	
	Platform closest;
	
	public void draw() {
		Window.out.color("red");
		Window.out.circle(x, y, 25);
	}

	public void move() {
		if (Window.key.pressed("up")) {
			yspeed = -15;
		}
		if (Window.key.pressed("left")) {
			x = x - 5;
		}
		if (Window.key.pressed("right")) {
			x = x + 5;
		}
		
		yspeed = yspeed + 1;
		y = y + yspeed;
		
		if (closest != null) {
			if (Math.abs(x - closest.x) <= closest.width / 2 &&
				Math.abs(y + 25 - closest.y) <= 10) {
				y = closest.y - 25;
				yspeed = 0;
			}
		}
	}

	public void setClosestPlatform(Platform p) {
		closest = p;
	}
}
