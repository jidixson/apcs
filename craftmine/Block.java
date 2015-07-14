package craftmine;

import apcs.Window;

public class Block {

	int x;
	int y;
	int type = Window.rollDice(10);
	
	boolean hide = false;
	
	public Block(int xpos, int ypos) {
		x = xpos;
		y = ypos;
	}

	public void draw() {
		if (! hide) {
			if (type < 8) {
				Window.out.color("chocolate");
			}
			else if (type == 8) {
				Window.out.color("silver");
			}
			else if (type == 9) {
				Window.out.color("gold");
			}
			else if (type == 10) {
				Window.out.color("purple");
			}
			
			Window.out.square(x, y, 20);
		}
	}

	public int distanceTo(Player player) {
		return (int) Math.sqrt(Math.pow(player.x - x, 2) + Math.pow(player.y - y, 2));
	}

	public void hide() {
		hide = true;
	}

}
