import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.Timer;

public class D3 {
	
	
	static boolean rotatin = false;
	static int XY = 0;
	static int XZ = 0;
	static int YZ = 0;
	static double[][] rotd = {{1,0,0},{0,1,0},{0,0,1}};
	static int ce = 500;
	static zpix[][] zbuff = new zpix[1000][1000];
	static byte curCols = 1;
	static Color[] colors = new Color[127];
	static vert point = new vert(0,0,0);
	static matrix rot = new matrix(rotd);
	static JPanel j;
	static poly[] cur;

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
				XY++;
			}else if(e.getKeyCode() == KeyEvent.VK_E) {
				XY--;
			}else if(e.getKeyCode() == KeyEvent.VK_W) {
				XZ--;
			}else if(e.getKeyCode() == KeyEvent.VK_S) {
				XZ++;
			}else if(e.getKeyCode() == KeyEvent.VK_A) {
				YZ++;
			}else if(e.getKeyCode() == KeyEvent.VK_D) {
				YZ--;
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
		}
	};
	
	static MouseListener m = new MouseListener() {

		@Override
		public void mouseClicked(MouseEvent e) {
			j.repaint();
		}

		@Override
		public void mouseEntered(MouseEvent arg0) {
			
		}

		@Override
		public void mouseExited(MouseEvent arg0) {
			
		}

		@Override
		public void mousePressed(MouseEvent arg0) {
		}

		@Override
		public void mouseReleased(MouseEvent arg0) {
			
		}

			
	};
	
	public static void main(String[] args) {
		load("cube.txt");
		colors[0] = Color.BLACK;
		for(int i = 0; i < cur.length;i++) {
			int a = contains(colors, cur[i].col);
			if(a == -1) {
				colors[curCols] = cur[i].col;
				a = curCols;
				curCols++;
			}
			cur[i].colCode = (byte) a;
		}
		render();
		t.start();
	}
	
	private static int contains(Color[] arr, Color c) {
		for(int i = 0; i < arr.length;i++) {
			if(arr[i] == c) {
				return i;
			}
		}
		return -1;
	}
	
	private static void load(String filename) {
		System.out.println("LOADING FROM " + filename);
		try {
			File f = new File(filename);
			Scanner fr = new Scanner(f);
			int numF = fr.nextInt();
			fr.nextLine();
			poly[] p = new poly[numF];
			for(int i = 0;i < numF;i++) {
				p[i] = new poly(fr.nextLine());
			}
			cur = p;
			fr.close();
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	public static void applyRot() {
		double[][] xyd = {{Math.cos(Math.toRadians(XY)), -Math.sin(Math.toRadians(XY)), 0},
						  {Math.sin(Math.toRadians(XY)), Math.cos(Math.toRadians(XY)) , 0},
						  {0						   , 0						      , 1}};
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
				poly[] temp = poly.rotate(cur, rot);
				
				for(int i = 0; i < zbuff.length;i++) {
					for(int k = 0; k < zbuff[i].length;k++) {
						zbuff[i][k] = new zpix();
					}
				}
				for(poly p : temp) {
					p.addToZBuffer(zbuff, ce);
				}
				for(int i = 0; i < zbuff.length;i++) {
					for(int k = 0; k < zbuff[i].length;k++) {
						g.setColor(colors[zbuff[i][k].c]);
						g.fillRect(i, k, 1, 1);
					}
				}
				g.setColor(Color.WHITE);
				g.drawString("{" + XY + ", " + XZ + ", " + YZ + "}", 10, 30);
			}
		};
		
		frame.addKeyListener(k);
		frame.addMouseListener(m);
		frame.add(j);
		frame.setLocation(100,0);
		frame.setSize(1000, 1000);
		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
}
