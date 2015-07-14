package gravity;

import apcs.Data;
import apcs.Window;

public class SimulateCoagulation {
	public static void main(String[] args) {
		Window.size(1000, 800);
		Window.setFrameRate(30);
		Window.frame();
		Mass[] massList = new Mass[300];
		
		for (int i = 0 ; i < massList.length ; i++) {
			massList[i] = new Mass(Window.rollDice(900) + 50, Window.rollDice(700) + 50, 20);
			massList[i].setRadius(5);
		}
		
		while (true) {
			Window.out.background("black");
			
			for (Mass m : massList) {
				m.draw();
			}
			
			for (Mass m : massList) {
				if (m.independent) {
					Vector netForce = new Vector(0, 0);
					for (Mass other : massList) {
						if (m != other && other.independent) {
							if (m.isTouching(other)) {
								m.merge(other);
							}
							else {
								netForce.add(m.gravitationalForce(other));
							}
							
						}
					}
					m.accelerate(netForce);
				}
			}
			
			for (Mass m : massList) {
				m.move();
			}
			
			Window.frame();
		}
	}
}
