package edu.brown.cs.bouncingBalls;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.MouseInfo;
import java.awt.Point;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

public class BouncingBall {
    public static void main(String[] args) {
    	new BouncingBall();
    }
    
    public BouncingBall() {
        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                try {
                    UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
                } catch (ClassNotFoundException ex) {
                } catch (InstantiationException ex) {
                } catch (IllegalAccessException ex) {
                } catch (UnsupportedLookAndFeelException ex) {
                }

                JFrame frame = new JFrame("Bouncing Ball");
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.setLayout(new BorderLayout());
                frame.setSize(400, 400);
                Playground playground = new Playground();
                frame.setVisible(true);
                frame.add(playground);


                new Thread(new BouncingBallRunnable(playground)).start();
            }
        });
    }
    
    public class BouncingBallRunnable implements Runnable {
    	private Playground playground;
    	
    	public BouncingBallRunnable(Playground playground) {
    		this.playground = playground;
    	}

    	@Override
    	public void run() {
            while (playground.isVisible()) {

                // repaint
                SwingUtilities.invokeLater(new Runnable() {
                    @Override
                    public void run() {
                        playground.repaint();
                    }
                });

                for (Ball ball : playground.getBalls()) {
                    update(ball);
                }
                Point p = MouseInfo.getPointerInfo().getLocation();
                SwingUtilities.convertPointFromScreen(p, playground);
                playground.setMouseBallLoc(p);

                try {
                    Thread.sleep(50);
                } catch (InterruptedException ex) {
                }
            }
    	}
    	
        public void update(Ball ball) {
            Point location = ball.getLocation();
            Point velocity = ball.getVelocity();
            Dimension size = ball.getSize();
            int mass = ball.getMass();
            int g = 1;

            int dx = velocity.x;
            int dy = velocity.y + g * mass;
            
            // bounce off mouse ball
            Ball mouse = playground.getMouseBall();
            int mouseDistsq = (mouse.getLocation().x - location.x - dx) * 
	            			  (mouse.getLocation().x - location.x - dx) +
	   			     		  (mouse.getLocation().y - location.y - dy) *
	   			     		  (mouse.getLocation().y - location.y - dy);
            if (mouseDistsq < size.width * size.width) {
            	double dist = Math.sqrt(mouseDistsq);
            	double dS = (Math.sqrt(dx*dx + dy*dy) +
   				     Math.sqrt(mouse.getVelocity().x * mouse.getVelocity().x +
   				    		   mouse.getVelocity().y * mouse.getVelocity().y)) / 2;
        		int xcomp = location.x + dx - mouse.getLocation().x;
        		int ycomp = location.y + dy - mouse.getLocation().y;
        		
        		dx = (int) (dS/dist * xcomp);
        		dy = (int) (dS/dist * ycomp);
            }
            
            // bounce off walls
            if (location.x + dx < 0 || location.x + dx + size.width > playground.getWidth()) {
            	dx *= 0.9;
                dx *= -1;
            }
            if (location.y + dy < 0 || location.y + dy + size.height > playground.getHeight()) {
                dy *= 0.9;
            	dy *= -1;
            }
            
            // bouce off other balls
            for (Ball b : playground.getBalls()) {
            	if (b == ball) {
            		continue;
            	}
            	int distsq = (b.getLocation().x - location.x - dx) * (b.getLocation().x - location.x - dx) +
            			     (b.getLocation().y - location.y - dy) * (b.getLocation().y - location.y - dy);
            	if (distsq < size.width * size.width) {
            		double dist = Math.sqrt(distsq);
            		double dS = (Math.sqrt(dx*dx + dy*dy) +
            				     Math.sqrt(b.getVelocity().x * b.getVelocity().x +
            				    		  b.getVelocity().y * b.getVelocity().y)) / 2;
            		int xcomp = location.x + dx - b.getLocation().x;
            		int ycomp = location.y + dy - b.getLocation().y;
            		
            		dx = (int) (dS/dist * xcomp);
            		dy = (int) (dS/dist * ycomp);
            		b.setVelocity(new Point(-dx, -dy));
            	}
            }
            
            int nx = location.x + dx;
            int ny = location.y + dy;
            ball.setVelocity(new Point(dx, dy));
            ball.setLocation(new Point(nx, ny));
        }
    }
}


