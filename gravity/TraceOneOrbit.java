package gravity;

import java.util.ArrayList;

import apcs.Window;

public class TraceOneOrbit {
	
	public static void main(String[] args) {
		Window.size(1000, 800);
		
		Mass sun = new Mass(500, 400, 400000, "yellow");
		sun.setRadius(50);
		
		Mass mercury = new Mass(600, 400, 100, "blue");
		mercury.setRadius(2);
		mercury.motion = new Vector(0, 70);
		
		Mass[] massList = new Mass[] { sun, mercury };
		
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
