package edu.brown.cs.bouncingBalls;

import java.util.List;
import java.util.Random;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.RenderingHints;
import java.util.ArrayList;

import javax.swing.JPanel;

public class Playground extends JPanel {
	private List<Ball> balls;
	private Ball mouseBall;
	
	public Playground() {
		Random rand = new Random();
		int ball_n = 10;
		int ball_size = 35;
		int ball_mass = 1;
		Dimension size = new Dimension (ball_size, ball_size);
				
		this.balls = new ArrayList<Ball>(ball_n);
		for (int i = 0; i < ball_n; i++) {
			int x = rand.nextInt(400 - ball_size);
			int y = rand.nextInt(400 - ball_size - 50);
			// if overlap, regenerate pos
			while (ifOverlap(x, y, ball_size)) {
				x = rand.nextInt(400 - ball_size);
				y = rand.nextInt(400 - ball_size - 50);
			}
			Point location = new Point(x, y); // random initial location
			Point velocity = new Point(0, 0);
			Color color = new Color(rand.nextInt(255), rand.nextInt(255), rand.nextInt(255), 120);
			
			this.balls.add(new Ball(location, velocity, color, size, ball_mass));
		}
		mouseBall = new Ball(new Point(0,0),
							 new Point(0, 0),
							 new Color(0, 0, 0),
							 size, ball_mass);
	}
	
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g.create();
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        for (Ball ball : balls) {
            try {
				ball.paint(g2d);
			} catch (NoSuchFieldException e) {
				e.printStackTrace();
			}
        }
        // paint mouse ball
        try {
			mouseBall.paint(g2d);
		} catch (NoSuchFieldException e) {
			e.printStackTrace();
		}
        g2d.dispose();
    }
    
    public List<Ball> getBalls() {
    	return this.balls;
    }
    
    public Ball getMouseBall() {
    	return this.mouseBall;
    }
    
    public void setMouseBallLoc(Point p) {
    	int _x = this.mouseBall.getLocation().x;
    	int _y = this.mouseBall.getLocation().y;
    	p.x -= mouseBall.getSize().width / 2;
    	p.y -= mouseBall.getSize().height / 2;
    	this.mouseBall.setLocation(p);
    	this.mouseBall.setVelocity(new Point(p.x-_x, p.y-_y));
    }
    
    private boolean ifOverlap(int x, int y, int ball_size) {
    	for (Ball b : balls) {
        	int distsq = (b.getLocation().x - x) * (b.getLocation().x - x) + 
        				 (b.getLocation().y - y) * (b.getLocation().y - y);
        	if (distsq < ball_size * ball_size) {
        		return true;
        	}
    	}
    	return false;
    }

}
