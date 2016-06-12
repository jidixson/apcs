package platformer;

import java.util.ArrayList;

import apcs.Window;

public class PlatformGame {

	public static void main(String[] args) {
		Window.size(400, 600);
		
		// Set up all the variables/data
		// One jumper
		Jumper jumper = new Jumper();
		// Many platforms
		ArrayList<Platform> platformList = new ArrayList<Platform>();
		
		// Timer variable
		int timer = 1;
		
		// Infinitely draw frames
		while (true) {
			Window.frame();
			Window.out.background("light blue");
			
			Window.out.color("green");
			Window.out.rectangle(Window.width() / 2, Window.height(), Window.width(), 60);
			
			// Tell the one jumper to draw and move
			jumper.draw();
			jumper.move();
			
			// Tell every platform to draw and move
			for (Platform platform : platformList) {
				platform.draw();
				platform.move();
				
				// Check if the player should be on this platform
				if (jumper.isOn(platform)) {
					jumper.jumpTo(platform);
				}
			}
			
			// Timer to add platforms
			timer = timer - 1;
			if (timer == 0) {
				timer = 30;
				platformList.add(new Platform());
			}
		}
	}

}
