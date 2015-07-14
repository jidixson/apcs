package gravity;

public class Vector {
	double x;
	double y;
	
	public Vector(int x, int y) {
		this.x = x;
		this.y = y;
	}
	
	public Vector(double x, double y) {
		this.x = x;
		this.y = y;
	}
	
	public Vector(Vector other) {
		this.x = other.x;
		this.y = other.y;
	}
	
	public void add(Vector other) {
		this.x += other.x;
		this.y += other.y;
	}
	
	public int getX() {
		return (int) x;
	}
	
	public int getY() {
		return (int) y;
	}
}
