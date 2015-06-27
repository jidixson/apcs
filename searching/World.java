package searching;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;

import javax.swing.JFrame;

public class World extends JFrame {

	// Run the world
	public static void main(String[] args) {
		World w = new World();
		
	}


	public static final int WIDTH = 100;
	public static final int HEIGHT = 80;
	public static final int SCALE = 10;

	Cell[][] grid;

	Cell start;
	Cell end;

	public static final int WALL = 1;
	public static final int START = 2;
	public static final int END = 3;
	public static final int MARK = 4;

	// Make the world
	public World() {
		super("World");

		// Create the grid and size the window accordingly
		grid = new Cell[WIDTH][HEIGHT];
		for (int x = 0 ; x < WIDTH ; x++) {
			for (int y = 0 ; y < HEIGHT ; y++) {
				grid[x][y] = new Cell(x, y, this);
			}
		}
		setSize(WIDTH * SCALE, HEIGHT * SCALE);

		// Make the world visible
		setVisible(true);
	}

	public void setDesiredPath(Cell start, Cell end) {
		this.start = start;
		this.end = end;

		start.type = START;
		end.type = END;
	}

	public void setWall(int x, int y) {
		grid[x][y].type = WALL;
	}

	public Cell getCell(int x, int y) {
		return grid[x][y];
	}

	public Cell getStart() {
		return start;
	}

	public Cell getEnd() {
		return end;
	}

	public void paint(Graphics g) {
		g.setColor(Color.GRAY);
		for (int x = 0 ; x < WIDTH * SCALE ; x = x + SCALE) {
			g.drawLine(x, 0, x, HEIGHT * SCALE);
		}
		for (int y = 0 ; y < HEIGHT * SCALE ; y = y + SCALE) {
			g.drawLine(0, y, WIDTH * SCALE, y);
		}

		// Go to every square in the grid
		for (int x = 0 ; x < WIDTH ; x++) {
			for (int y = 0 ; y < HEIGHT ; y++) {
				if (grid[x][y].type == WALL) {
					g.setColor(Color.BLACK);
					g.fillRect(x * SCALE, y * SCALE, SCALE, SCALE);
				}
				if (grid[x][y].type == START) {
					g.setColor(Color.GREEN);
					g.fillRect(x * SCALE, y * SCALE, SCALE, SCALE);
				}
				if (grid[x][y].type == END) {
					g.setColor(Color.RED);
					g.fillRect(x * SCALE, y * SCALE, SCALE, SCALE);
				}
				if (grid[x][y].marked) {
					g.setColor(Color.LIGHT_GRAY);
					g.fillRect(x * SCALE, y * SCALE, SCALE, SCALE);
				}
			}
		}
	}
}

class Cell {
	int x;
	int y;
	World world;
	boolean marked = false;

	int type = 0;

	public Cell(int x, int y, World world) {
		this.x = x;
		this.y = y;
		this.world = world;
	}

	public void mark() {
		marked = true;
	}

	public Cell[] neighbors() {
		ArrayList <Cell> cells = new ArrayList <Cell> ();

		if (x > 0) {
			cells.add(world.grid[x - 1][y]);
		}
		if (x + 1 < World.WIDTH) {
			cells.add(world.grid[x + 1][y]);
		}
		if (y > 0) {
			cells.add(world.grid[x][y - 1]);
		}
		if (y + 1 < World.HEIGHT) {
			cells.add(world.grid[x][y + 1]);
		}
		
		return cells.toArray(new Cell[cells.size()]);
	}

	// Returns true if the cells are equal
	public boolean equals(Cell other) {
		if (x == other.x && y == other.y) {
			return true;
		}
		return false;
	}
}
