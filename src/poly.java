import java.awt.Color;
import java.util.Comparator;

public class poly {
	public vert a,b,c;
	public Color col;
	public poly(vert A, vert B, vert C, Color Col) {
		a = A;
		b = B;
		c = C;
		col = Col;
	}
	public poly(int ax, int ay, int az, int bx, int by, int bz, int cx, int cy, int cz, Color Col) {
		a = new vert(ax, ay, az);
		b = new vert(bx, by, bz);
		c = new vert(cx, cy, cz);
		col = Col;
	}
	public vert[] get() {
		vert[] tr = {a,b,c};
		return tr;
	}
	
	public int avgZ() {
		//System.out.println((a.z + b.z + c.z) / 3);
		return (a.z + b.z + c.z) / 3;
	}
	
	public static poly[] rotate(poly[] polys, matrix rot) {
		poly[] temp = new poly[polys.length];
		for(int i = 0;i < polys.length; i++) {
			temp[i] = new poly(rot.transM(polys[i].a), rot.transM(polys[i].b), rot.transM(polys[i].c), polys[i].col);
		}
		return temp;
	}
}

class SortByZ implements Comparator<poly> 
{ 
    // Used for sorting in ascending order of 
    // roll number 
    public int compare(poly a, poly b){ 
        return b.avgZ() - a.avgZ(); 
    } 
} 