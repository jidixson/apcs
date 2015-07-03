package rushhour;

import apcs.Window;

public class RushHour {
	static Piece[][] grid = new Piece[6][6];
	static Piece selected;
	
	public static void main(String[] args) {
		addPiece(new Piece(2, 2, 3, false));
		addPiece(new Piece(0, 4, 2, false));
		addPiece(new Piece(1, 5, 2, true));
		
		while (true) {
			draw();
			move();
			Window.frame();
		}
	}
	
	private static void move() {
		if (Window.mouse.clicked()) {
			if (selected == null) {
				int x = (Window.mouse.getX() - 10) / 80;
				int y = (Window.mouse.getY() - 10) / 80;
				
				if (x < 6 && y < 6 && x >= 0 && y >= 0 && grid[x][y] != null) {
					selected = grid[x][y]; 
				}
			}
		}
		else {
			// If there is a selected piece
			if (selected != null) {
				if (selected.horizontal) {
					int startX = (Window.mouse.getX() - (selected.length * 40)) / 80;
					//System.out.println(startX);
					if (startX >= 0 && startX < 6) {
						while(startX < 6 && grid[startX][selected.y] != null && grid[startX][selected.y] != selected) {
							startX++;
						}
						if (startX + selected.length <= 6) {
							for (int x = selected.x ; x < selected.x + selected.length ; x++) {
								grid[x][selected.y] = null;
							}
							for (int x = startX ; x < startX + selected.length ; x++) {
								grid[x][selected.y] = selected;
							}
							selected.x = startX;
						}
					}
				}
				else {
					int startY = (Window.mouse.getY() - (selected.length * 40)) / 80;
					//System.out.println(startX);
					if (startY >= 0 && startY < 6) {
						while(startY < 6 && grid[selected.x][startY] != null && grid[selected.x][startY] != selected) {
							startY++;
						}
						if (startY + selected.length <= 6) {
							for (int y = selected.y ; y < selected.y + selected.length ; y++) {
								grid[selected.x][y] = null;
							}
							for (int y = startY ; y < startY + selected.length ; y++) {
								grid[selected.x][y] = selected;
							}
							selected.y = startY;
						}
					}
				}
			}
			selected = null;
		}
	}

	public static void addPiece(Piece p) {
		if (p.horizontal) {
			for (int x = p.x ; x < p.x + p.length ; x++) {
				grid[x][p.y] = p;
			}
		}
		else {
			for (int y = p.y ; y < p.y + p.length ; y++) {
				grid[p.x][y] = p;
			}
		}
	}
	
	public static void draw() {
		drawBackground();
		for (int x = 0 ; x < 6 ; x++) {
			for (int y = 0 ; y < 6 ; y++) {
				if (grid[x][y] != null) {
					if (grid[x][y] == selected) {
						grid[x][y].drawAtMouse();
					}
					else {
						grid[x][y].draw();
					}
				}
			}
		}
	}

	public static void drawBackground() {
		Window.out.background(200, 200, 200);

		Window.out.color(180, 180, 180);
		for (int x = 90 ; x < 480 ; x += 80) {
			Window.out.rectangle(x, 250, 6, 500);
		}
		for (int y = 90 ; y < 480 ; y += 80) {
			Window.out.rectangle(250, y, 500, 6);
		}

		Window.out.color(100, 100, 100);
		Window.out.rectangle(250, 5, 500, 10);
		Window.out.rectangle(250, 495, 500, 10);
		Window.out.rectangle(5, 250, 10, 500);
		Window.out.rectangle(495, 250, 10, 500);

		Window.out.color(200, 200, 200);
		Window.out.rectangle(5, 210, 10, 74);
		Window.out.rectangle(495, 210, 10, 74);
	}

}
