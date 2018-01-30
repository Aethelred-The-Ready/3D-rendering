import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.Timer;

public class D3 {
	
	static boolean rotatin = false;
	static int XY = 0;
	static int XZ = 0;
	static int YZ = 0;
	static double[][] rotd = {{1,0,0},{0,1,0},{0,0,1}};
	static matrix rot = new matrix(rotd);
	static JPanel j;
	static poly[] tetra = {	new poly(100, 100, 100, -100, -100, 100, -100, 100, -100, Color.RED),
							new poly(100, 100, 100, -100, -100, 100, 100, -100, -100, Color.GREEN),
							new poly(-100, 100, -100, 100, -100, -100, 100, 100, 100, Color.BLUE),
							new poly(-100, 100, -100, 100, -100, -100, -100, -100, 100, Color.MAGENTA)};
	
	static poly[] cube = {  new poly( 100, 100, 100, 100,-100, 100,-100, 100, 100, Color.RED),
							new poly(-100,-100, 100, 100,-100, 100,-100, 100, 100, Color.RED),
							
							new poly( 100, 100,-100, 100,-100,-100,-100, 100,-100, Color.RED),
							new poly(-100,-100,-100, 100,-100,-100,-100, 100,-100, Color.RED),
							
							new poly( 100, 100, 100, 100,-100, 100, 100, 100,-100, Color.RED),
							new poly( 100,-100,-100, 100,-100, 100, 100, 100,-100, Color.RED),
							
							new poly(-100, 100, 100,-100,-100, 100,-100, 100,-100, Color.RED),
							new poly(-100,-100,-100,-100,-100, 100,-100, 100,-100, Color.RED),
							
							new poly( 100, 100, 100,-100, 100, 100, 100, 100,-100, Color.RED),
							new poly(-100, 100,-100,-100, 100, 100, 100, 100,-100, Color.RED),
							
							new poly( 100,-100, 100,-100,-100, 100, 100,-100,-100, Color.RED),
							new poly(-100,-100,-100,-100,-100, 100, 100,-100,-100, Color.RED)};
	static ActionListener action = new ActionListener(){

		@Override
		public void actionPerformed(ActionEvent a) {
			j.repaint();
			t.start();
			applyRot();
		}
		
		
	};
	static Timer t = new Timer(17,action);
	static KeyListener k = new KeyListener() {

		public void keyPressed(KeyEvent e) {
			if(e.getKeyCode() == KeyEvent.VK_Q) {
				XY--;
			}else if(e.getKeyCode() == KeyEvent.VK_E) {
				XY++;
			}else if(e.getKeyCode() == KeyEvent.VK_W) {
				XZ--;
			}else if(e.getKeyCode() == KeyEvent.VK_S) {
				XZ++;
			}else if(e.getKeyCode() == KeyEvent.VK_A) {
				YZ--;
			}else if(e.getKeyCode() == KeyEvent.VK_D) {
				YZ++;
			}else if(e.getKeyCode() == KeyEvent.VK_R) {
				rotatin =! rotatin;
			}else if(e.getKeyCode() == KeyEvent.VK_0) {
				XY = 0;XZ = 0;YZ = 0;
			}
			applyRot();
			if(!rotatin) {
				XY = 0;XZ = 0;YZ = 0;
			}
			j.repaint();
		}


		public void keyReleased(KeyEvent e) {
			
		}

		public void keyTyped(KeyEvent e) {
			
		}};
	
	public static void main(String[] args) {
		render();
		t.start();
	}
	
	public static void applyRot() {
		double[][] xyd = {{Math.cos(Math.toRadians(XY)), -Math.sin(Math.toRadians(XY)), 0},
						  {Math.sin(Math.toRadians(XY)), Math.cos(Math.toRadians(XY)) , 0},
						  {0						   , 0						  , 1}};
		rot.m = rot.multiplyM(xyd);
		double[][] xzd = {{1, 0							   , 0},
				          {0, Math.cos(Math.toRadians(XZ)) , Math.sin(Math.toRadians(XZ))},
				          {0, -Math.sin(Math.toRadians(XZ)), Math.cos(Math.toRadians(XZ))}};
		rot.m = rot.multiplyM(xzd);
		double[][] yzd = {{Math.cos(Math.toRadians(YZ)), 0, -Math.sin(Math.toRadians(YZ))},
				          {0						   , 1, 0},
				          {Math.sin(Math.toRadians(YZ)), 0, Math.cos(Math.toRadians(YZ))}};
		rot.m = rot.multiplyM(yzd);
	}
	
	private static void render(){
		JFrame frame = new JFrame("3D projector");
		
		j = new JPanel(){
			public void paint(Graphics g) {	
				g.setColor(Color.BLACK);
				g.fillRect(0, 0, 2000, 2000);
				g.setColor(Color.WHITE);
				int ce = 500;
				for(poly p : cube) {
					//g.setColor(p.col);
					vert a = rot.transM(p.a);
					vert b = rot.transM(p.b);
					vert c = rot.transM(p.c);
					g.setColor(Color.BLUE);
					int[] x = {a.x + ce, b.x + ce, c.x + ce};
					int[] y = {a.y + ce, b.y + ce, c.y + ce};
					//g.fillPolygon(x, y, 3);
					g.setColor(Color.WHITE);
					g.drawLine(a.x + ce, a.y + ce, b.x + ce, b.y + ce);
					g.drawLine(b.x + ce, b.y + ce, c.x + ce, c.y + ce);
					g.drawLine(c.x + ce, c.y + ce, a.x + ce, a.y + ce);
				}
				g.setColor(Color.WHITE);
				g.drawString("{" + XY + ", " + XZ + ", " + YZ + "}", 10, 30);
			}
		};
		
		frame.addKeyListener(k);
		frame.add(j);
		frame.setLocation(100,0);
		frame.setSize(1000, 1000);
		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

}
