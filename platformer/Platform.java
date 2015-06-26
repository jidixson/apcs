package platformer;

import apcs.Window;

public class Platform {

	int x;
	int y;
	int width = 50;
	
	public Platform(int xpos, int ypos, int w) {
		x = xpos;
		y = ypos;
		width = w;
	}

	public void draw() {
		Window.out.color("black");
		Window.out.rectangle(x, y, width, 5);
	}

}
