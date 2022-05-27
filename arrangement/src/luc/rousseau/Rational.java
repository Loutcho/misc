package luc.rousseau;

public class Rational implements Comparable<Rational> {

	private int numerator;
	private int denominator;
	
	public Rational(int numerator, int denominator) {
		if (numerator == 0) {
			this.numerator = 0;
			this.denominator = 1;
		} else {
			boolean negative = (numerator > 0) ^ (denominator > 0);
			int a = Math.abs(numerator);
			int b = Math.abs(denominator);
			int d = gcd(a, b);
			this.numerator = a / d;
			this.denominator = b / d;
			if (negative) {
				this.numerator = - this.numerator;
			}
		}
	}
	
	public Rational plus(Rational r1, Rational r2) {
		int a = r1.numerator;
		int b = r1.denominator;
		int c = r2.numerator;
		int d = r2.denominator;
		// a/b + c/d = (ad)/(bd) + (bc)/(bd) = (ad+bc)/(bd)
		return new Rational(a * d + b * c, b * d);
	}
	
	public Rational times(Rational r1, Rational r2) {
		int a = r1.numerator;
		int b = r1.denominator;
		int c = r2.numerator;
		int d = r2.denominator;
		return new Rational(a * c, b * d);
	}

	@Override
	public String toString() {
		if (denominator == 1) {
			return Integer.toString(numerator);
		}
		return "" + numerator + "/" + denominator;
	}

	@Override
	public int compareTo(Rational that) {
		int a = this.numerator;
		int b = this.denominator;
		int c = that.numerator;
		int d = that.denominator;
		// a/b < c/d <==> ad < bc <==> ad - bc < 0
		return a * d - b * c;
	}
	
	private static int gcd(int a, int b) { //valid for positive integers 
		while (b > 0) {
			int c = a % b;
			a = b;
			b = c;
		}
		return a;
	}
}
