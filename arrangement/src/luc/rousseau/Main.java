package luc.rousseau;

public class Main {
	/**
	 * This program illustrates the usage of the Arrangement class
	 */
	public static void main(String[] args) {
		Arrangement a = new Arrangement();
		// populate with a set of distinct lines, preferably not in general position in order to illustrate something:
		a.addLine(new Line(1,   1,  1)); //  x +   y + 1 = 0
		a.addLine(new Line(2,   1,  1)); // 2x +   y + 1 = 0
		a.addLine(new Line(1,   3,  1)); //  x +  3y + 1 = 0
		a.addLine(new Line(5, -15, -1)); // 5x - 15y - 1 = 0
		a.addLine(new Line(6,  -1, -1)); // 6x -   y - 1 = 0

		// call compute():
		a.compute();

		// use the results
		System.out.print(a); // might be useful for debugging
		System.out.print("Number of regions: " + a.numberOfRegions()); // the answer should be: 14
	}
}
