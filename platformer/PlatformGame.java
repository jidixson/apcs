import java.util.ArrayList;

import apcs.Window;

public class PlatformGame {

	public static void main(String[] args) {
		
		Player me = new Player();
		
		ArrayList <Platform> platforms = new ArrayList <Platform> ();
		
		platforms.add(new Platform(200, 300, 50));
		platforms.add(new Platform(250, 450, 500));
		
		while (true) {
			Window.out.background("light blue");
			Window.out.rectangle(250, 475, 500, 50);
			
			me.draw();
			me.move();
			
			// For nearest neighbor calculation
			double minDistance = 5000;
			Platform closest = null;
			
			for (Platform p : platforms) {
				p.draw();
				
				// Only if this platform is below me.
				if (p.y > me.y) {
					// Calculate the distance and store if closest.
					int dx = p.x - me.x;
					int dy = p.y - me.y;
					double distance = Math.sqrt(dx * dx + dy * dy );
					if (distance < minDistance) {
						minDistance = distance;
						closest = p;
					}
				}
			}
			me.setClosestPlatform(closest);
			
			Window.frame();
		}
	}

}
