package craftmine;

import java.util.ArrayList;

import apcs.Window;

public class CraftMine {

	public static void main(String[] args) {
		Window.size(1000, 600);
		Window.setFrameRate(30);
		
		// The player object.
		Player player = new Player();
		
		// Initialize all the blocks in the game
		ArrayList <Block> blocks = new ArrayList <Block> ();
		for (int x = 10 ; x < Window.width() ; x = x + 20) {
			for (int y = 90 ; y < Window.height() ; y = y + 20) {
				blocks.add(new Block(x, y));
			}
		}
		
		// Infinitely draw frames
		while (true) {
			Window.out.background("light blue");
			player.draw();
			player.move();
			
			for (Block block : blocks) {
				block.draw();
				if (block.y > player.y && (player.closestBlock() == null ||
					block.distanceTo(player) < player.closestBlock().distanceTo(player))) {
					player.setClosestBlock(block);
				}
			}
			
			for (int i = 0 ; i < blocks.size() ; i++) {
				if (blocks.get(i).hide) {
					if (blocks.get(i) == player.closestBlock()) {
						player.setClosestBlock(null);
					}
					blocks.remove(i);
					i = i - 1;
				}
			}
			
			Window.frame();
		}
		
	}

}
