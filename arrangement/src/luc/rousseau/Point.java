package luc.rousseau;

public class Point implements Comparable<Point> {
	Rational x;
	Rational y;
	
	public Point(Rational x, Rational y) {
		this.x = x;
		this.y = y;
	}

	@Override
	public String toString() {
		return "(" + x + "," + y + ")";
	}

	@Override
	public int compareTo(Point that) {
		int d;
		d = this.x.compareTo(that.x);
		if (d != 0) {
			return d;
		}
		d = this.y.compareTo(that.y);
		if (d != 0) {
			return d;
		}
		return 0;
	}
}
