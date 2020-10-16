package edu.brown.cs.bouncingBalls;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Point;

public class Ball {
	private Point location;
	private Point velocity;
	private Color color;
	private Dimension size;
	private int mass;
	
	public Ball(Point location, Point velocity, Color color, Dimension size, int mass) {
		this.location = location;
		this.velocity = velocity;
		this.color = color;
		this.size = size;
		this.mass = mass;
	}
	
    public Ball(Color color) {
        setColor(color);
        velocity = new Point(0, 0);
        size = new Dimension(35, 35);

    }
	
	public Point getLocation() {
		return location;
	}
	
	public Point getVelocity() {
		return velocity;
	}
	
	public Color getColor() {
		return this.color;
	}
	
	public Dimension getSize() {
		return this.size;
	}
	
	public int getMass() {
		return this.mass;
	}
	
	public void setSize(Dimension size) {
		this.size = size;
	}
	
	public void setColor(Color color) {
		this.color = color;
	}
	
	public void setVelocity(Point velocity) {
		this.velocity = velocity;
	}
	
	public void setLocation(Point location) {
		this.location = location;
	}
	
	public void setMass(int mass) {
		this.mass = mass;
	}
	
    public void paint(Graphics2D g) throws NoSuchFieldException {
    	Point location = getLocation();
    	Dimension size = getSize();
    	Color color = getColor();
    	if (location == null) {
    		throw new NoSuchFieldException("Ball location not set");
    	}
    	g.setColor(color);
        g.fillOval(location.x, location.y, size.width, size.height);

    }
}
