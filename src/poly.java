import java.awt.Color;

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
}