package craftmine;

import apcs.Window;

public class Player {

	int x = 40;
	int y = 40;
	int dy = 0;
	
	int digging = 10;
	
	Block closest = null;
	
	boolean canJump = true;
	
	public void draw() {
		Window.out.color("blue");
		Window.out.circle(x, y, 10);
	}
	
	public void move() {
		if (Window.key.pressed("right")) {
			x = x + 5;
		}
		if (Window.key.pressed("left")) {
			x = x - 5;
		}
		if (Window.key.pressed("up") && canJump) {
			dy = -10;
			canJump = false;
		}
		//upReleased = Window.key.pressed("up") 
		if (! Window.key.pressed("up") && dy == 0) {
			
		}
		
		if (Window.key.pressed("down")) {
			dig();
		}
		y = y + dy;
		dy = dy + 1;
		
		if (closest != null) {
			if (Math.abs(y + 10 - closest.y) <= 10 && Math.abs(x - closest.x) <= 10) {
				y = closest.y - 20;
				dy = 0;
				canJump = true;
			}
		}
	}
	
	public void dig() {
		if (digging > 0) {
			digging = digging - 1;
		}
		else {
			digging = 10;
			if (closest != null) {
				closest.hide();
			}
		}
	}
	
	public void setClosestBlock(Block b) {
		closest = b;
	}

	public Block closestBlock() {
		return closest;
	}
}
