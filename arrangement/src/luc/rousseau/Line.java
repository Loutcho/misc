package luc.rousseau;

public class Line implements Comparable<Line> {

	/*
	 * a * x + b * y + c = 0
	 */
	private int a;
	private int b;
	private int c;
	
	public Line(int a, int b, int c) {
		this.a = a;
		this.b = b;
		this.c = c;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		boolean needsOp = false;
		switch (a) {
		case  0: break;
		case  1: sb.append("x"); needsOp = true; break;
		case -1: sb.append("-x"); needsOp = true; break;
		default: sb.append(String.format("%dx", a)); needsOp = true; break;
		}
		if (b > 0 && needsOp) { sb.append("+"); }
		switch (b) {
		case  0: break;
		case  1: sb.append("y"); needsOp = true; break;
		case -1: sb.append("-y"); needsOp = true; break;
		default: sb.append(String.format("%dy", b)); needsOp = true; break;
		}
		if (c > 0 && needsOp) { sb.append("+"); }
		switch (c) {
		case  0: break;
		default: sb.append(String.format("%d", c)); break;
		}
		sb.append("=0");
		return sb.toString();
	}
	
	public static Point intersection(Line d1, Line d2) {
		int det = d1.a * d2.b - d1.b * d2.a;
		if (det == 0) {
			return null; // either no point of intersection, or d1 == d2.
		}
		// ax + by + c = 0
		// a'x + b'y + c' = 0

		// ab'x + bb'y + cb' = 0
		// a'b x + bb'y + bc' = 0
		// (ab'-a'b)x + (cb'-bc') = 0
		// (bc'-cb') / det
		Rational x = new Rational(d1.b * d2.c - d1.c * d2.b, det);

		// aa'x + ba'y + ca' = 0
		// aa'x + ab'y + c'a = 0
		// (ba'-ab')y + (ca'-c'a) = 0
		// y = (ca'-c'a)/det
		Rational y = new Rational(d1.c * d2.a - d2.c * d1.a, det); 
		return new Point(x, y);
	}

	@Override
	public int compareTo(Line that) {
		int d;
		d = this.a - that.a;
		if (d != 0) {
			return d;
		}
		d = this.b - that.b;
		if (d != 0) {
			return d;
		}
		d = this.c - that.c;
		if (d != 0) {
			return d;
		}
		return 0;
	}
}
