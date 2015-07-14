package gravity;

import apcs.Window;

public class SimulateOrbitingPlanets {
	
	public static void main(String[] args) {
		Window.size(1000, 800);
		
		Mass m1 = new Mass(400, 400, 40000, "yellow");
		m1.setRadius(50);
		Mass m2 = new Mass(100, 400, 100, "blue");
		m2.setRadius(5);
		m2.accelerate(new Vector(0, 800));
		m2.move();
		
		Mass m3 = new Mass(600, 400, 200, "red");
		m3.setRadius(10);
		m3.accelerate(new Vector(0, -3000));
		m3.move();
		
		Mass[] massList = new Mass[] { m1, m2, m3 };
		
		while (true) {
			Window.out.background("black");
			
			for (Mass m : massList) {
				m.draw();
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
