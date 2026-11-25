package alphabeta;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;


/**
 * We are going to subdivide the [0, 1] interval.
 * As parameters we need two real numbers, alpha and beta (barycentric weights), elements of [0, 1].
 *
 * Let's start with x0 = 0 and x1 = 1 and mark x1 as being the current x_last.
 * 
 * The rule to create a new x when one has a subdivision of [0, 1],
 * namely 0 = x_0 < x_1 < x_2 < ... < x_n} = 1, is the following:
 * find the index i that maximizes | x_{i+1} - x_i |
 * x will be in [x_i, x_{i+1}]
 * if x will be less than x_last
 * then define x as: (1 - alpha) * x_i + (alpha) * x_{i + 1}
 * else define x as: (1 - beta) * x_i + (beta) * x_{i + 1}
 * Insert x in the list of the x_k (and renumber this list for the next iteration).
 *
 * This program illustrates this process by creating a picture of the [0, 1] X [0, 1] square, where the coordinates are (affine conversions of) alpha and beta.
 * For each pixel, a different ordered pair (alpha, beta) is tried.
 * Take N iterations.
 * Color the pixel depending on:
 * - the last computed x
 * - the number of iterations where the rule chose the branch with alpha
 * - the number of iterations where the rule chose the branch with beta
 * 
 */
public class Main {

	private static final int XSIZE = 192 * 20;
	private static final int YSIZE = 108 * 20;
	
	public static void main(String[] args) {
		
		BufferedImage img = new BufferedImage(XSIZE, YSIZE, BufferedImage.TYPE_INT_RGB);

		for (int x = 0; x < XSIZE; x++) {
			System.out.println("x = " + x);
			for (int y = 0; y < YSIZE; y++) {
				int rgb = computeColor(x, y);
				img.setRGB(x, y, rgb);
			}
		}
		
		File file = new File("C:\\Users\\Luc\\Desktop\\image" + N + ".png");
		try {
			ImageIO.write(img, "PNG", file);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private static final double AX = 1.0 / (XSIZE - 1.0);
	private static final double AY = 1.0 / (YSIZE - 1.0);
	private static final int N = 300;
	
	private static int computeColor(int x, int y) {
		// x = 0 ==> X = 0
		// x = (XSIZE - 1) ==> X = 1
		// X = a x + b
		// 0 = a 0 + b ==> b = 0
		// X = a x
		// 1 = a (XSIZE - 1)
		// a = 1 / (XSIZE - 1)
		double xx = AX * x;
		double yy = AY * y;
		return computeColor(xx, yy);
	}
	
	private static int computeColor(double x, double y) {
		State s = new State(x, y);
		for (int k = 1; k <= N; k++) {
			s.evolve();
		}
		double cc = s.getLast();
		int nbLefts = s.getNbLefts();
		if (nbLefts > 255) nbLefts = 255;
		int nbRights = s.getNbRights();
		if (nbRights > 255) nbRights = 255;
		int z;
		if (cc <= 0.0) { z = 0; }
		else if (cc >= 1.0) { z = 255; }
		else { z = (int) (255.0 * cc); }
		return (z << 16) | (nbRights << 8) | (nbLefts << 0);
	}
}
