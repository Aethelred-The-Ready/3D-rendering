import java.awt.Color;
import java.util.Comparator;

public class poly {
	public vert a,b,c;
	private int xMin, xMax, yMin, yMax;
	private double r,s,t,plane;
	public Color col;
	public byte colCode;
	public poly(vert A, vert B, vert C, byte colCode) {
		a = A;
		b = B;
		c = C;
		this.colCode = colCode;
		xMin = min(a.x, b.x, c.x);
		xMax = max(a.x, b.x, c.x);
		yMin = min(a.y, b.y, c.y);
		yMax = max(a.y, b.y, c.y);
		setPlaneVars();
	}
	public poly(int ax, int ay, int az, int bx, int by, int bz, int cx, int cy, int cz, Color Col) {
		a = new vert(ax, ay, az);
		b = new vert(bx, by, bz);
		c = new vert(cx, cy, cz);
		xMin = min(ax, bx, cx);
		xMax = max(ax, bx, cx);
		yMin = min(ay, by, cy);
		yMax = max(ay, by, cy);
		setPlaneVars();
		col = Col;
	}
	public poly(String nextLine) {
		String[] objs = nextLine.split(" ");
		int p = 0;
		a = new vert(Integer.parseInt(objs[p++]), Integer.parseInt(objs[p++]), Integer.parseInt(objs[p++]));
		b = new vert(Integer.parseInt(objs[p++]), Integer.parseInt(objs[p++]), Integer.parseInt(objs[p++]));
		c = new vert(Integer.parseInt(objs[p++]), Integer.parseInt(objs[p++]), Integer.parseInt(objs[p++]));
		xMin = min(a.x, b.x, c.x);
		xMax = max(a.x, b.x, c.x);
		yMin = min(a.y, b.y, c.y);
		yMax = max(a.y, b.y, c.y);
		setPlaneVars();
		col = new Color(Integer.parseInt(objs[p++]),Integer.parseInt(objs[p++]),Integer.parseInt(objs[p++]));
	}
	public vert[] get() {
		vert[] tr = {a,b,c};
		return tr;
	}

	private int max(int a, int b, int c) {
		if(a > b && a > c) {
			return a;
		}else if(b > c) {
			return b;
		}
		return c;
	}
	
	private int min(int a, int b, int c) {
		if(a < b && a < c) {
			return a;
		}else if(b < c) {
			return b;
		}
		return c;
	}
	
	public void addToZBuffer(zpix[][] zbuff, int ce) {
		for(int i = xMin; i < xMax;i++) {
			for(int k = yMin; k < yMax;k++) {
				if(in(i, k)) {
					int z = getZ(i, k);
					if(z > zbuff[i + ce][k + ce].z) {
						zbuff[i + ce][k + ce].c = colCode;
						zbuff[i + ce][k + ce].z = (short) z;
					}
				}
			}
		}
	}
	
	private boolean in(int x, int y) {
		vert p = new vert(x, y, 0);
		

	    int as_x = p.x-a.x;
	    int as_y = p.y-a.y;

	    boolean s_ab = (b.x-a.x)*as_y-(b.y-a.y)*as_x > 0;

	    if((c.x-a.x)*as_y-(c.y-a.y)*as_x > 0 == s_ab) {
	    	return false;
	    }

	    if((c.x-b.x)*(p.y-b.y)-(c.y-b.y)*(p.x-b.x) > 0 != s_ab) {
	    	return false;
	    }

	    return true;

		
		
		//double d = areaForTri(a, b, p) + areaForTri(a, p, c) + areaForTri(p, b, c) - area;
		//return d < 0.1;
	}

	private int getZ(int x, int y) {
		return (int) (-(plane - r * x - s * y)/t);
	}
	
	private void setPlaneVars() {
		double[] i = {a.x - b.x, a.y - b.y, a.z - b.z};
		double[] k = {a.x - c.x, a.y - c.y, a.z - c.z};
		r = i[1]*k[2] - i[2]*k[1];
		s = i[2]*k[0] - i[0]*k[2];
		t = i[0]*k[1] - i[1]*k[0];
		plane = r * a.x + s * a.y + t * a.z;
	}
	
	public static poly[] rotate(poly[] polys, matrix rot) {
		poly[] temp = new poly[polys.length];
		for(int i = 0;i < polys.length; i++) {
			temp[i] = new poly(rot.transM(polys[i].a), rot.transM(polys[i].b), rot.transM(polys[i].c), polys[i].colCode);
		}
		return temp;
	}
}