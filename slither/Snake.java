package snake;

import apcs.Window;

public class Snake {
	int x;
	int y;
	int dx;
	int dy;
	
	int r, g, b;
	
	Snake next;

	public Snake(int xpos, int ypos, int xspeed, int yspeed) {
		x = xpos;
		y = ypos;
		dx = xspeed;
		dy = yspeed;
		
		r = Window.rollDice(255);
		g = Window.rollDice(255);
		b = Window.rollDice(255);
	}

	public void draw() {
		Window.out.color(r, g, b);
		Window.out.circle(x, y, 10);
		
		if (next != null) {
			next.draw();
		}
	}

	public void move() {
		x = x + dx;
		y = y + dy;
		
		if (next != null) {
			next.move();
			next.dx = dx;
			next.dy = dy;
		}
	}
	
	public void grow() {
		if (next == null) {
			next = new Snake(x - dx, y - dy, dx, dy);
		}
		else {
			next.grow();
		}
	}

}
