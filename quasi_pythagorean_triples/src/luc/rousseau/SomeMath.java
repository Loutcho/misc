package luc.rousseau;

import java.math.BigInteger;
import java.util.Arrays;
import java.util.List;

/**
 * Some of the math behind the scene
 */
public class SomeMath {

	private static final BigInteger ZERO = BigInteger.ZERO;
	private static final BigInteger ONE = BigInteger.ONE;
	private static final BigInteger TWO = BigInteger.TWO;
	private static final BigInteger THREE = BigInteger.valueOf(3);
	
	/**
	 * Extended Euclid Algorithm
	 * @param a
	 * @param b
	 * @return (d = GCD(a, b), u, v) such that au + bv = d
	 */
	public static List<BigInteger> extendedEuclid(BigInteger a, BigInteger b) {
		if (b.equals(ZERO)) {
			return Arrays.asList(a, ONE, ZERO);
		}
		List<BigInteger> l = extendedEuclid(b, a.mod(b));
		BigInteger d = l.get(0);
		BigInteger u = l.get(1);
		BigInteger v = l.get(2);
		return Arrays.asList(d, v, u.subtract((a.divide(b)).multiply(v)));
	}
	
	/**
	 * Main formulae, equations of the rays 
	 * @param a
	 * @param b
	 * @param c
	 * @param epsilon
	 * @return
	 */
	public static List<BigInteger> computeAlphaBetaGamma(BigInteger a, BigInteger b, BigInteger c, BigInteger epsilon) {
		List<BigInteger> l = extendedEuclid(a, b);
		BigInteger u = l.get(1);
		BigInteger v = l.get(2);
		BigInteger alpha = v.multiply(epsilon).multiply(c).mod(a);
		BigInteger beta = u.negate().multiply(epsilon).multiply(c).mod(b);
		
		BigInteger aa = alpha.multiply(alpha);
		BigInteger bb = beta.multiply(beta);
		BigInteger cc = aa.add(bb).subtract(ONE);
		
		if (cc.compareTo(ZERO) < 0) {
			System.err.println("?? cc<0: a=" + a + ", b=" + b + ", c=" + c + ", epsilon=" + epsilon);
		}
		
		BigInteger gamma = cc.sqrt();
		
		return Arrays.asList(alpha, beta, gamma);
	}

	/**
	 * Multiplication by the Berggren matrix "R1"
	 */
	public static List<BigInteger> berggrenR1(List<BigInteger> x) {
		BigInteger a = x.get(0);
		BigInteger b = x.get(1);
		BigInteger c = x.get(2);
		/*
		 *  / aa \   /  1 -2  2 \  / a \
		 *  | bb | = |  2 -1  2 |  | b |
		 *  \ cc /   \  2 -2  3 /  \ c /
		 */
		BigInteger aa =               a  .subtract(TWO.multiply(b)).add(TWO.multiply(c));
		BigInteger bb = (TWO.multiply(a)).subtract(             b ).add(TWO.multiply(c));
		BigInteger cc = (TWO.multiply(a)).subtract(TWO.multiply(b)).add(THREE.multiply(c));
		return Arrays.asList(aa, bb, cc);
	}

	/**
	 * Multiplication by the Berggren matrix "R2"
	 */
	public static List<BigInteger> berggrenR2(List<BigInteger> x) {
		BigInteger a = x.get(0);
		BigInteger b = x.get(1);
		BigInteger c = x.get(2);
		/*
		 *  / aa \   /  1  2  2 \  / a \
		 *  | bb | = |  2  1  2 |  | b |
		 *  \ cc /   \  2  2  3 /  \ c /
		 */
		BigInteger aa =               a  .add(TWO.multiply(b)).add(TWO.multiply(c));
		BigInteger bb = (TWO.multiply(a)).add(             b ).add(TWO.multiply(c));
		BigInteger cc = (TWO.multiply(a)).add(TWO.multiply(b)).add(THREE.multiply(c));
		return Arrays.asList(aa, bb, cc);
	}
	
	/**
	 * Multiplication by the Berggren matrix "R3"
	 */
	public static List<BigInteger> berggrenR3(List<BigInteger> x) {
		BigInteger a = x.get(0);
		BigInteger b = x.get(1);
		BigInteger c = x.get(2);
		/*
		 *  / aa \   / -1  2  2 \  / a \
		 *  | bb | = | -2  1  2 |  | b |
		 *  \ cc /   \ -2  2  3 /  \ c /
		 */
		BigInteger aa =               a.negate().add(TWO.multiply(b)).add(TWO.multiply(c));
		BigInteger bb = (TWO.multiply(a)).negate().add(             b ).add(TWO.multiply(c));
		BigInteger cc = (TWO.multiply(a)).negate().add(TWO.multiply(b)).add(THREE.multiply(c));
		return Arrays.asList(aa, bb, cc);
	}
	
	/**
	 * Is the argument the square of an integer?
	 * @param x
	 * @return
	 */
	public static boolean isSquare(BigInteger x) {
		if (x.compareTo(ZERO) < 0) {
			return false;
		}
		BigInteger[] s = x.sqrtAndRemainder();
		return s[1].compareTo(ZERO) == 0;
	}
}
