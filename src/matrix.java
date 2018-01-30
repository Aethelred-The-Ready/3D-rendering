public class matrix {

	double[][] m;

	public matrix(double[][] in) {
		m = in;
	}
	
	public double[][] multiplyM(double[][] o) {
		double[][] tr = new double[m.length][m[0].length];
		for(int r = 0; r < m.length;r++) {
			for(int c = 0; c < m[0].length;c++) {
				for(int i = 0; i < m.length;i++) {
					tr[r][c] += m[r][i] * o[i][c];
				}
			}
		}
		return tr;
	}
	
	public vert transM(vert v) {
		vert tr = new vert((int) (v.x * m[0][0] + v.y * m[1][0] + v.z * m[2][0]),
						   (int) (v.x * m[0][1] + v.y * m[1][1] + v.z * m[2][1]),
						   (int) (v.x * m[0][2] + v.y * m[1][2] + v.z * m[2][2]));
        return tr;
	}
	
	public double[][] get(){
		return m;
	}
	
}