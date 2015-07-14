package gravity;

import apcs.Window;

public class Mass {
	
	// The gravitational constant that masses are affected by.
	public static final int G = 1;
	
	double mass;		// The size of the mass
	int radius;
	String color;	// The color to draw the mass.
	
	// Whether the mass is not merged with a bigger mass.
	boolean independent = true;
	
	Vector position;
	Vector motion;
	
	public Mass(double x, double y) {
		this(x, y, 10, "white");
	}
	
	public Mass(double x, double y, double mass) {
		this(x, y, mass, "white");
	}
	
	public Mass(double x, double y, double mass, String color) {
		position = new Vector(x, y);
		motion = new Vector(0, 0);
		this.mass = mass;
		this.color = color;
		radius = (int) Math.log(mass);
	}
	
	public void setRadius(int radius) {
		this.radius = radius;
	}
	
	public void draw() {
		if (independent) {
			Window.out.color(color);
			Window.out.circle(position.getX(), position.getY(), radius);
		}
	}
	
	public void accelerate(Vector force) {
		motion.x += force.x / mass;
		motion.y += force.y / mass;
	}
	
	public void move() {
		position.add(motion);
	}
	
	/**
	 * Merges the other mass into this one.
	 * @param other
	 */
	public void merge(Mass other) {
		if (mass >= other.mass) {
			mass += other.mass;
			radius += (int) Math.log(other.radius);
			other.independent = false;
		}
		else {
			other.merge(this);
		}
	}
	
	public boolean isTouching(Mass other) {
		return Math.sqrt(
				Math.pow(other.position.y - position.y, 2) + 
				Math.pow(other.position.x - position.x, 2) ) < radius + other.radius;
	}
	
	/**
	 * Returns the gravitational force between this mass and the other mass.
	 * @param other
	 * @return
	 */
	public Vector gravitationalForce(Mass other) {
		double angle = Math.atan2(
				other.position.y - position.y, 
				other.position.x - position.x );
		double distance = Math.sqrt(
				Math.pow(other.position.y - position.y, 2) + 
				Math.pow(other.position.x - position.x, 2) );
		double magnitude = Mass.G * mass * other.mass / (distance * distance);
		
		return new Vector(magnitude * Math.cos(angle), magnitude * Math.sin(angle));
	}
	
	/**
	 * Returns the normal force for this mass and the other mass (the force applied
	 * by the other mass on this one when touching)
	 */
	public Vector normalForce(Mass other) {
		double angle = Math.atan2(
				other.position.y - position.y, 
				other.position.x - position.x );
		double gravity = Mass.G * mass * other.mass / Math.pow(radius + other.radius, 2);
		
		return new Vector(-1 * gravity * Math.cos(angle), -1 * gravity * Math.sin(angle));
	}
}
