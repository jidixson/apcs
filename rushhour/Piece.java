package rushhour;

import apcs.Window;

public class Piece {
	
	int x = 0;
	int y = 0;
	int length = 2;
	boolean horizontal = true;
	int color;
	
	public Piece(int x, int y, int length, boolean horizontal) {
		this.x = x;
		this.y = y;
		this.length = length;
		this.horizontal = horizontal;
		
		this.color = Window.rollDice(6);
	}
	
	public void setColor() {
		switch(color) {
		case 1: Window.out.color("red"); break;
		case 2: Window.out.color("green"); break;
		case 3: Window.out.color("blue"); break;
		case 4: Window.out.color("yellow"); break;
		case 5: Window.out.color("orange"); break;
		case 6: Window.out.color("purple"); break;
		}
	}
	
	public void draw() {
		setColor();
		if (horizontal) {
			Window.out.rectangle(50 + x * 80 + (length - 1) * 40, 50 + y * 80, length * 80 - 20, 70);
		}
		else {
			Window.out.rectangle(50 + x * 80, 50 + y * 80 + (length - 1) * 40, 70, length * 80 - 20);
		}
	}

	public void drawAtMouse() {
		setColor();
		if (horizontal) {
			Window.out.rectangle(Window.mouse.getX(), 50 + y * 80, length * 80 - 20, 70);
		}
		else {
			Window.out.rectangle(50 + x * 80, Window.mouse.getY(), 70, length * 80 - 20);
		}
	}

}
