package luc.rousseau;

import java.awt.image.BufferedImage;
import java.math.BigInteger;
import java.util.List;

/**
 * Class that implements a (discrete) 3D line, represented as a pair of 2D lines.
 */
public class Ray {
	
	private BigInteger a;
	private BigInteger b;
	private BigInteger c;
	private BigInteger alpha;
	private BigInteger beta;
	private BigInteger gamma;
	
	public Ray(BigInteger a, BigInteger b, BigInteger c, BigInteger epsilon) {
		this.a = a;
		this.b = b;
		this.c = c;
		List<BigInteger> grecs = SomeMath.computeAlphaBetaGamma(a, b, c, epsilon);
		this.alpha = grecs.get(0);
		this.beta = grecs.get(1);
		this.gamma = grecs.get(2);
	}
	
	public void work(BufferedImage buf) {
		BigInteger i;
		BigInteger j;
		BigInteger n;
		BigInteger one = BigInteger.ONE;
		BigInteger nmax = new BigInteger(Integer.toString(QuasiPythagoreanTriples.NMAX));
		BigInteger imax = new BigInteger(Integer.toString(QuasiPythagoreanTriples.IMAX));

		i = alpha;
		n = gamma;
		while ((n.compareTo(nmax) < 0) && (i.compareTo(imax) < 0)) {
			if ((n.compareTo(one) >= 0) && (i.compareTo(one) >= 0)) {
				redify(buf, i, n);
			}
			i = i.add(a);
			n = n.add(c);
		}

		j = beta;
		n = gamma;
		while ((n.compareTo(nmax) < 0) && (j.compareTo(imax) < 0)) {
			if ((n.compareTo(one) >= 0) && (j.compareTo(one) >= 0)) {
				redify(buf, j, n);
			}
			j = j.add(b);
			n = n.add(c);
		}
	}
	
	private void redify(BufferedImage buf, BigInteger i, BigInteger n) {
		int ii = i.intValue();
		int nn = n.intValue();
		int c = buf.getRGB(ii, nn);
		int d = c | 0xFF0080;
		if ((c & 0x00FF7F) != 0x00FF7F || (d & 0xFFFFFF) != 0xFFFFFF) {
			System.err.println(String.format("PB n=%d, i=%d, c=%x, d=%x", n, i, c, d));
		}
		buf.setRGB(ii, nn,  d);
	}
}
