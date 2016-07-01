package gridsearch;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

import javax.swing.JFileChooser;

import apcs.Window;

/**
 * 
 * @author  Alexander Rosenberg
 *			
 */
public class MakeMaze {

	static Scanner s = new Scanner(System.in);
	public static final int BLANK = 32;
	public static final int WALL = 37;
	public static final int TRAFFIC = 84;
	public static final int START = 80;
	public static final int END = 46;
	static int width = 20;
	static int height = 10;
	static int size;
	static int[][] grid;

	public static void main(String[] args) throws IOException {
		width = Integer.parseInt(Window.ask("Width:"));
		height = Integer.parseInt(Window.ask("Height:"));
		size = 700 / Math.max(width, height);
		grid = new int[width][height];
		for (int x = 0; x < width; x++) {
			for (int y = 0; y < height; y++) {
				grid[x][y] = BLANK;
			}
		}
		Window.size(width * size, height * size);
		while (true) {
			draw();
			process();
		}
	}

	private static void process() {
		if (Window.key.pressed("escape")) {
			save();
			System.exit(1);
		}
		int x = Window.mouse.getX() / size;
		int y = Window.mouse.getY() / size;

		if (Window.key.pressed("w")) {
			grid[x][y] = WALL;
		}
		if (Window.key.pressed("t")) {
			grid[x][y] = TRAFFIC;
		}

		if (Window.key.pressed("s")) {
			grid[x][y] = START;
		}
		if (Window.key.pressed("e")) {
			grid[x][y] = END;
		}
		if (Window.key.pressed("b")) {
			grid[x][y] = BLANK;
		}
		if (Window.key.pressed("c")) {
			clear();
		}
		if (Window.key.pressed("g")) {
			blacken();
		}

	}

	private static void blacken() {
		for (int x = 0; x < width; x++) {
			for (int y = 0; y < height; y++) {
				grid[x][y] = WALL;
			}
		}
	}

	private static void clear() {
		for (int x = 0; x < width; x++) {
			for (int y = 0; y < height; y++) {
				grid[x][y] = BLANK;
			}
		}
	}

	private static void save() {
		JFileChooser choser = new JFileChooser();
		int choice = choser.showSaveDialog(null);
		File path = null;
		try {
			if (choice == JFileChooser.APPROVE_OPTION) {
				path = choser.getSelectedFile();
			} else if (choice == JFileChooser.CANCEL_OPTION) {
				path = choser.getCurrentDirectory();
			} else {
				path = choser.getCurrentDirectory();
			}

			if (!path.exists()) {
				path.createNewFile();
			} else {
				System.out.println("File alredy Exists!");
				save();
			}
			FileWriter fw = new FileWriter(path.getAbsoluteFile());
			BufferedWriter bw = new BufferedWriter(fw);
			for (int y = 0; y < height; y++) {
				for (int x = 0; x < width; x++) {
					bw.write(grid[x][y]);
				}
				bw.write('\n');
			}
			bw.close();
			System.out.println("Done Writeing.");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private static boolean help = false;

	private static void draw() {
		Window.out.background("white");
		Window.out.color("black");
		for (int x = 0; x < width * size; x += size) {
			Window.out.line(x, 0, x, Window.height());
		}
		for (int y = 0; y < height * size; y += size) {
			Window.out.line(0, y, Window.width(), y);
		}
		for (int x = 0; x < width; x++) {
			for (int y = 0; y < height; y++) {
				switch (grid[x][y]) {
				case BLANK:
					Window.out.color("white");
					break;
				case WALL:
					Window.out.color("black");
					break;
				case TRAFFIC:
					Window.out.color("dark orange");
					break;
				case START:
					Window.out.color("green");
					break;
				case END:
					Window.out.color("red");
				}
				Window.out.square(x * size + size / 2 + 1, y * size + size / 2
						+ 1, size - 2);
			}
			Window.out.color("green");
			Window.out.rectangle(20, 20, 40, 30);
			Window.out.color("black");
			Window.out.fontSize(17);
			Window.out.print("Help", 3, 23);
			if (Window.mouse.getX() > 0 && Window.mouse.getX() < 40) {
				if (Window.mouse.getY() > 5 && Window.mouse.getY() < 35) {
					if (Window.mouse.clicked()) {
						help = true;
					}
				}
			}
			while (help) {
				Window.out.color("brown");
				Window.out.fontSize(30);
				Window.out.print("w - wall", Window.width() / 2 - 39,
						Window.height() / 2);
				Window.out.print("t - traffic", Window.width() / 2 - 39,
						Window.height() / 2 + 50);
				Window.out.print("b - blabk", Window.width() / 2 - 39,
						Window.height() / 2 - 25);
				Window.out.print("s - start", Window.width() / 2 - 39,
						Window.height() / 2 - 75);
				Window.out.print("e - end", Window.width() / 2 - 39,
						Window.height() / 2 + 25);
				Window.out.print("c - clear", Window.width() / 2 - 39,
						Window.height() / 2 + 75);
				Window.out.print("g - turn to wall or grass",
						Window.width() / 2 - 50, Window.height() / 2 - 50);
				help = false;
			}
		}
		Window.frame();
	}
}
