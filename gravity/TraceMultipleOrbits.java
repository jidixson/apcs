package gravity;

import java.util.ArrayList;

import apcs.Window;

public class TraceMultipleOrbits {
	
	public static void main(String[] args) {
		Window.size(1000, 800);
		Window.setFrameRate(20);
		
		Mass sun = new Mass(500, 400, 400000, "yellow");
		sun.setRadius(50);
		
		Mass mercury = new Mass(600, 400, 100, "red");
		mercury.setRadius(2);
		mercury.motion = new Vector(0, 70);
		
		Mass venus = new Mass(650, 400, 200, "orange");
		venus.setRadius(3);
		venus.motion = new Vector(0, -50);
		
		Mass earth = new Mass(700, 400, 250, "blue");
		earth.setRadius(4);
		earth.motion = new Vector(0, -40);
		
		Mass mars = new Mass(730, 400, 250, "yellow");
		mars.setRadius(3);
		mars.motion = new Vector(0, -30);
		
		Mass jupiter = new Mass(800, 400, 5000, "tan");
		jupiter.setRadius(10);
		jupiter.motion = new Vector(0, 40);
		
		Mass[] massList = new Mass[] { sun, mercury, venus, earth, mars, jupiter };
		
		ArrayList <Vector> trace = new ArrayList <Vector> ();
		ArrayList <String> color = new ArrayList <String> ();
		
		while (true) {
			Window.out.background("black");
			
			if (! Window.key.pressed("space")) {
				trace.clear();
				color.clear();
			}
			
			for (int i = 0 ; i < trace.size() ; i++) {
				Window.out.color(color.get(i));
				Window.out.circle(trace.get(i).getX(), trace.get(i).getY(), 2);
			}
			
			for (Mass m : massList) {
				m.draw();
				if (Window.key.pressed("space")) {
					trace.add(new Vector(m.position));
					color.add(m.color);
				}
			}
			
			for (Mass m : massList) {
				Vector netForce = new Vector(0, 0);
				for (Mass other : massList) {
					if (m != other) {
						if (! m.isTouching(other)) {
							netForce.add(m.gravitationalForce(other));
						}
					}
				}
				m.accelerate(netForce);
			}
			
			for (Mass m : massList) {
				m.move();
			}
			
			Window.frame();
		}
	}
}
