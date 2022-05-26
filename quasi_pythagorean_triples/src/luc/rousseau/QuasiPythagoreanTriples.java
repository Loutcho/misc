package luc.rousseau;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.math.BigInteger;
import java.util.Arrays;
import java.util.List;

import javax.imageio.ImageIO;

/**
 * This program generates a PNG file which lists, by lighting or not each pixel,
 * the points (i, n) and (j, n) such that i^2 + j^2 = 1 + n^2.
 * I call such a (i, j, n) triple a "quasi-pythagorean triple".
 * The program can also (experimentally) show that the quasi-pythagorean triples
 * can be obtained by rays: lines with equations
 * i = a * k + alpha; j = b * k + beta; n = c * k + gamma;
 * where (a, b, c) are pythagorean triples (not quasi-).  
 */
public class QuasiPythagoreanTriples {

	public static final int SIDEX = 640;
	public static final int SIDEY = 640;
	public static final int NMAX = SIDEY - 1;
	public static final int IMAX = SIDEX - 1;
	public static final int DEPTH = 12;

	public static void main(String[] args) {
		try {
			BufferedImage bi = new BufferedImage(SIDEX, SIDEY, BufferedImage.TYPE_INT_RGB);
			mainChoice(0, bi);
		    File outputfile = new File("quasi-pythagorean-triples.png");
		    ImageIO.write(bi, "png", outputfile);
		} catch (IOException e) {
			e.printStackTrace();
		}		
	}
	
	private static void mainChoice(int n, BufferedImage bi) {
		switch(n) {
		case 0: main_showAllTriples(bi); break;
		case 1: main_showAllTriplesAndOneRay(bi); break;
		case 2: main_showAllTriplesAndFirstFewRays(bi); break;
		}
	}
	private static void main_showAllTriples(BufferedImage bi) {
		greenify(bi);
	}
	
	private static void main_showAllTriplesAndOneRay(BufferedImage bi) {
		greenify(bi);
		BigInteger a = BigInteger.valueOf(3);
		BigInteger b = BigInteger.valueOf(4);
		BigInteger c = BigInteger.valueOf(5);
		new Ray(a, b, c, BigInteger.ONE).work(bi);
	}
	
	private static void main_showAllTriplesAndFirstFewRays(BufferedImage bi) {
		greenify(bi);
		scanBerggrenTree(DEPTH, bi);
	}

	private static void greenify(BufferedImage bi) {
		for (int n = 1; n <= NMAX; n ++) {
			for (int i = 1; i <= Math.min(n, IMAX); i ++) {
				bi.setRGB(i, n, maybeGreen(i, n));
			}
		}
	}
	
	private static void scanBerggrenTree(int depth, BufferedImage bi) {
		BigInteger a = BigInteger.valueOf(3);
		BigInteger b = BigInteger.valueOf(4);
		BigInteger c = BigInteger.valueOf(5);
		scanBerggrenTree(depth, Arrays.asList(a, b, c), bi);
	}
	
	private static void scanBerggrenTree(int depth, List<BigInteger> node, BufferedImage bi) {
		if (depth < 0) {
			return;
		}
		// System.out.println(String.format("%d %s", depth, node.toString()));
		BigInteger a = node.get(0);
		BigInteger b = node.get(1);
		BigInteger c = node.get(2);
		new Ray(a, b, c, BigInteger.ONE).work(bi);
		new Ray(a, b, c, BigInteger.ONE.negate()).work(bi);
		if (depth >= 1) {
			int newDepth = depth - 1;
			scanBerggrenTree(newDepth, SomeMath.berggrenR1(node), bi);
			scanBerggrenTree(newDepth, SomeMath.berggrenR2(node), bi);
			scanBerggrenTree(newDepth, SomeMath.berggrenR3(node), bi);
		}
	}
	
	private static int maybeGreen(int i, int n) {
		return ok(n, i) ? 0x00FF7F : 0x000000;
	}
	
	private static boolean ok(int n, int i) {
		BigInteger nn = new BigInteger(Integer.toString(n));
		BigInteger ii = new BigInteger(Integer.toString(i));
		BigInteger nn2 = nn.multiply(nn);
		BigInteger ii2 = ii.multiply(ii);
		BigInteger ss = BigInteger.ONE.add(nn2).subtract(ii2);
		return SomeMath.isSquare(ss);
	}
}
