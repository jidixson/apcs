package snake;

import apcs.Window;

public class SnakeGame {

	public static void main(String[] args) {
		
		Snake head = new Snake(250, 250, 5, 0);
		
		while (true) {
			Window.frame();
			Window.out.background("white");
			
			head.draw();
			head.move();
			
			head.dx = (Window.mouse.getX() - head.x) / 10;
			head.dy = (Window.mouse.getY() - head.y) / 10;
			
			if (Window.key.pressed("space")) {
				head.grow();
			}
		}
	}

}
