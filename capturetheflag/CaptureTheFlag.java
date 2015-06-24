package capturetheflag;

import apcs.Window;

public class CaptureTheFlag {

	public static void main(String[] args) {
		Window.size(1000, 500);
		
		Thing[] things = new Thing[6];
		
		things[0] = new Player(1);
		things[1] = new Player(2);
		things[2] = new Flag(1);
		things[3] = new Flag(2);
		
		things[4] = new ComputerPlayer(1);
		things[5] = new ComputerPlayer(2);
		
		while (true) {
			Window.out.background("green");
			Window.out.color("white");
			Window.out.line(500, 0, 500, 500);
			
			for (Thing thing : things) {
				thing.draw();
				thing.move();
				
				for (Thing otherThing : things ) {
					if (thing != otherThing && 
							thing.shouldFollow(otherThing)) {
						thing.follow(otherThing);
					}
				}
			}
			
			Window.frame();
		}
		
	}

}
