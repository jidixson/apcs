package sudoku;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

import apcs.Window;

public class Sudoku {
	static int SIZE = 500;
	
	static int ax = -1, ay = -1;

	public static void main(String[] args) {
		Window.size(SIZE, SIZE);
		int speed = 1;
		int level = 4;
		
		ArrayList <Integer[][]> boards = getBoards(level);
		
		for (int i = 0 ; i < boards.size() ; i++) {
			Integer[][] board = boards.get(i);
			long start = System.currentTimeMillis();
			draw(board);
			solve(board, speed);
			System.out.println("Done in " + (System.currentTimeMillis() - start) + " ms.");
			
			Window.sleep(1000);
		}
		System.out.println("Done");
		
//		ArrayList <Integer[][]> boards = getBoards(4);
//		
//		for (int i = 0 ; i < 3 ; i++) {
//			Integer[][] board = boards.get((int) (Math.random() * boards.size()));
//			System.out.print("verify(\"");
//			print(board);
//			System.out.print("\",\n\"");
//			solve(board, 0);
//			print(board);
//			System.out.println("\");");
//		}
	}
	
	public static void print(Integer[][] board) {
		for (int y = 0 ; y < 9 ; y++) {
			for (int x = 0 ; x < 9 ; x++) {
				System.out.print(board[x][y]);
			}
		}
	}
	
	public static void solve(Integer[][] board) {
		solve(board, 0);
	}
	
	public static boolean solve(int[][] board) {
		
		// Find the first empty square in the board.
		for (int x = 0 ; x < 9 ; x++) {
			for (int y = 0 ; y < 9 ; y++) {
				if (board[x][y] == 0) {
					
					boolean[] has = new boolean[10];
					
					// Reset the array
					for (int i = 0 ; i < 10 ; i++) has[i] = false;
					
					// Eliminate rows and columns
					for (int i = 0 ; i < 9 ; i++) {
						has[board[x][i]] = true;
						has[board[i][y]] = true;
					}
					// Eliminate all numbers in the 3 x 3 cell
					for (int xx = x - x % 3 ; xx < x - x % 3 + 3 ; xx++) {
						for (int yy = y - y % 3 ; yy < y - y % 3 + 3 ; yy++) {
							has[board[xx][yy]] = true;
						}
					}
					
					for (int k = 1 ; k <= 9 ; k++) {
						if (! has[k]) {
							board[x][y] = k;
							
							if (solve(board))
								return true;
							
							board[x][y] = 0;
						}
					}
					
					// If no valid configuration was found, return false.
					return false;
				}
			}
		}
		
		// If no empty square was found, this is a valid configuration, so return true.
		return true;
	}
	
	public static boolean solve(Integer[][] board, int drawSpeed) {
		boolean[] has = new boolean[10];
		
		if (solved(board)) {
			return true;
		}
		
		// Find the first empty square in the board.
		for (int x = 0 ; x < 9 ; x++) {
			for (int y = 0 ; y < 9 ; y++) {
				if (board[x][y] == 0) {
					
					// Reset the array
					for (int i = 0 ; i < 10 ; i++) has[i] = false;
					
					// Eliminate rows and columns
					for (int i = 0 ; i < 9 ; i++) {
						has[board[x][i]] = true;
						has[board[i][y]] = true;
					}
					// Eliminate all numbers in the cell
					for (int xx = x - x % 3 ; xx < x - x % 3 + 3 ; xx++) {
						for (int yy = y - y % 3 ; yy < y - y % 3 + 3 ; yy++) {
							has[board[xx][yy]] = true;
						}
					}
					
					for (int k = 1 ; k <= 9 ; k++) {
						if (! has[k]) {
							board[x][y] = k;
							if (drawSpeed > 0) {
								draw(k, x, y);
								Window.sleep(drawSpeed);
							}
							if (solve(board, drawSpeed)) {
								draw(k, x, y);
								return true;
							}
							else {
								if (drawSpeed > 0) {
									clear(x, y);
								}
								board[x][y] = 0;
							}
						}
					}
					
					// If no valid configuration was found, return false.
					return false;
				}
			}
		}
		return false;
	}
	
	public static void quickSolve(Integer[][] board) {
		quickSolve(board, 0);
	}
	
	public static void quickSolve(Integer[][] board, int drawSpeed) {
		if (drawSpeed > 0) draw(board);
		
		// Used to store 
		boolean[] has = new boolean[10];
		
		// Keep repeating the solving process until no numbers could be found.
		boolean done = false;
		while (! done) {
			done = true;
			
			// For each empty square in the grid
			for (int x = 0 ; x < 9 ; x++) {
				for (int y = 0 ; y < 9 ; y++) {
					if (board[x][y] == 0) {
						
						// Reset the array
						for (int i = 0 ; i < 10 ; i++) has[i] = false;
						
						// Eliminate rows and columns
						for (int i = 0 ; i < 9 ; i++) {
							has[board[x][i]] = true;
							has[board[i][y]] = true;
						}
						// Eliminate all numbers in the cell
						for (int xx = x - x % 3 ; xx < x - x % 3 + 3 ; xx++) {
							for (int yy = y - y % 3 ; yy < y - y % 3 + 3 ; yy++) {
								has[board[xx][yy]] = true;
							}
						}
						
						// Count how many numbers were eliminated
						int count = 9;
						for (int i = 1 ; i <= 9 ; i++)
							if (has[i]) count--;
						
						// If there is only one possibility for this square
						if (count == 1) {
							done = false;
							
							// Find the possibility and mark it
							for (int i = 1 ; i <= 9 ; i++) {
								if (! has[i]) {
									board[x][y] = i;
									
									// Draw the number
									if (drawSpeed > 0) {
										draw(i, x, y);
										Window.sleep(drawSpeed);
									}
									break;
								}
							}
						}
					}
				}
			}
		}
	}
	
	public static boolean solved(Integer[][] board) {
		for (int x = 0 ; x < 9 ; x++)
			for (int y = 0 ; y < 9 ; y++)
				if (board[x][y] == 0)
					return false;
		return true;
	}
	
	public static ArrayList <Integer[][]> getBoards(int level) {
		ArrayList <Integer[][]> boards = new ArrayList <Integer[][]> ();
		
		try {
			Scanner s = new Scanner(new File("level" + level + ".txt"));
			
			while (s.hasNextLine()) {
				Integer[][] board = new Integer[9][9];
				String line = s.nextLine();
				for (int i = 0 ; i < line.length() ; i++)
					board[i % 9][i / 9] = line.charAt(i) - 48;
				boards.add(board);
			}
			s.close();
			
		} catch (FileNotFoundException e) {}
		
		return boards;
	}
	
	public static void clear(int x, int y) {
		draw(0, x, y);
	}
	
	public static void draw(int value, int x, int y) {
		if (value > 0) {
			Window.out.color("red");
			Window.out.print(value, x * SIZE / 9 + SIZE / 40, (y + 1) * SIZE / 9 - SIZE / 50);
		}
		else {
			Window.out.color("white");
			Window.out.square(x * SIZE / 9 + SIZE / 18, y * SIZE / 9 + SIZE / 18, SIZE / 9 - 10);
		}
	}
	
	public static void draw(Integer[][] board) {
		Window.out.background("white");
		Window.out.font("Courier", SIZE / 9 - 5);
		
		if (ax >= 0 && ay >= 0) {
			Window.out.color("light gray");
			Window.out.square(ax * SIZE / 9 + SIZE / 18, ay * SIZE / 9 + SIZE / 18, SIZE / 9);
		}
		
		Window.out.color("black");
		for (int i = 0 ; i < 9 ; i++) {
			Window.out.line(i * SIZE / 9, 0, i * SIZE / 9, SIZE);
			Window.out.line(0, i * SIZE / 9, SIZE, i * SIZE / 9);
		}
		
		Window.out.rectangle(SIZE / 2, 2, SIZE, 4);
		Window.out.rectangle(SIZE / 2, SIZE / 3 - 1, SIZE, 4);
		Window.out.rectangle(SIZE / 2, SIZE * 2 / 3 - 1, SIZE, 4);
		Window.out.rectangle(SIZE / 2, SIZE - 2, SIZE, 4);
		Window.out.rectangle(2, SIZE / 2, 4, SIZE);
		Window.out.rectangle(SIZE / 3 - 1, SIZE / 2, 4, SIZE);
		Window.out.rectangle(SIZE * 2 / 3 - 1, SIZE / 2, 4, SIZE);
		Window.out.rectangle(SIZE - 2, SIZE / 2, 4, SIZE);
		
		for (int x = 0 ; x < 9 ; x++) {
			for (int y = 0 ; y < 9 ; y++) {
				if (board[x][y] > 0) {
					Window.out.print(board[x][y], x * SIZE / 9 + SIZE / 40, (y + 1) * SIZE / 9 - SIZE / 50);
				}
			}
		}
	}
}
