package drop7;

import apcs.Window;

public class Drop7 {

	public static void main(String[] args) {
		Board b = new Board();
		b.startDrawing();
		
		while (true) {
			b.draw();
			b.move();
			
			Window.frame();
		}
	}

}
