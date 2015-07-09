package blobexample;

import java.util.ArrayList;

import apcs.Window;

public class Game {

	public static void main(String[] args) {
		Window.size(800, 800);
		Window.setFrameRate(30);
		
		int myRadius = 15;
		
		int x = 9000;
		int y = 5000;
		
		ArrayList <Blob> blobs = new ArrayList <Blob> ();
		
		for (int i = 0 ; i < 1000 ; i++) {
			blobs.add(new Blob());
		}
		
		while (true) {
			Window.out.background("white");
			
			Window.out.color("black");
			Window.out.circle(400, 400, myRadius);
			
			for (int i = 0 ; i < blobs.size() ; i++) {
				blobs.get(i).draw(x, y);
			}
			
			if (x > 9600) {
				Window.out.color("black");
				Window.out.square(10800 - x, 400, 800);
			}
			
			// Get the raw difference i
			int dx = Window.mouse.getX() - 400;
			int dy = Window.mouse.getY() - 400;
			
			double magnitude = Math.sqrt(dx * dx + dy * dy);
			
			if (magnitude > 10) {
				dx = (int) (dx * 10 / magnitude);
				dy = (int) (dy * 10 / magnitude);
			}
			
			x = x + dx;
			y = y + dy;
			
			Window.frame();
		}
	}

}
