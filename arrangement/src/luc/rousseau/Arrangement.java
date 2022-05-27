package luc.rousseau;

import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;

/**
 * This class implements the formula that computes the number of regions delimited by an arrangement of lines:
 *
 * Restrictions:
 * - dimension is 2 (lines in a plane);
 * - the lines have integer coefficients (a, b, c) in their equations (ax + by + c = 0);
 * - no protection against equivalent equations (e.g. ax + by + c = 0 AND 2ax + 2by + 2c = 0 both provided) 
 *
 * The formula is:
 * f2 = 1 - p0 + p1 + p01
 * (see: https://www.unilim.fr/pages_perso/stephane.vinatier/Articles/arrangements.pdf)
 */
public class Arrangement {
	
	private Set<Line> lines;
	private Set<Point> points;
	private Map<Line, Set<Point>> pointsByLine;
	private Map<Point, Set<Line>> linesByPoint;
	
	public Arrangement() {
		lines = new TreeSet<>();
	}
	
	public void addLine(Line d) {
		lines.add(d);
	}
	
	public void compute() {
		points = new TreeSet<>();
		pointsByLine = new TreeMap<>();
		linesByPoint = new TreeMap<>();
		for (Line line1 : lines) {
			for (Line line2 : lines) {
				if (line1.compareTo(line2) < 0) {
					Point p = Line.intersection(line1, line2);
					if (p != null) {
						if (! points.contains(p)) {
							points.add(p);
						}
						if (! pointsByLine.containsKey(line1)) {
							pointsByLine.put(line1, new TreeSet<>());
						}
						if (! pointsByLine.containsKey(line2)) {
							pointsByLine.put(line2, new TreeSet<>());
						}
						if (! linesByPoint.containsKey(p)) {
							linesByPoint.put(p, new TreeSet<>());
						}
						Set<Point> p1 = pointsByLine.get(line1);
						Set<Point> p2 = pointsByLine.get(line2);
						Set<Line> l = linesByPoint.get(p);
						p1.add(p);
						p2.add(p);
						l.add(line1);
						l.add(line2);
					}
				}
			}
		}
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("Lines:\n");
		for (Line line : lines) {
			sb.append("    " + line + "\n");
		}
		sb.append("Points of intersection:\n");
		for (Point point : points) {
			sb.append("    " + point + "\n");
		}
		for (Line line : lines) {
			sb.append("Points of the " + line + " line: ");
			Set<Point> points = pointsByLine.get(line);
			if (points != null) {
				for (Point point : points) {
					sb.append(" " + point);
				}
				sb.append(" -- " + points.size() + " points\n");
			}
		}
		for (Point point : points) {
			sb.append("Lines of the " + point + " point: ");
			Set<Line> lines = linesByPoint.get(point);
			if (lines != null) {
				for (Line line : lines) {
					sb.append(" " + line);
				}
			}
			sb.append(" -- " + lines.size() + " lines\n");
		}
		return sb.toString();
	}
	
	public int numberOfRegions() {
		int p0;
		int p1;
		int p01;
		p0 = points.size();
		p1 = lines.size();
		p01 = 0;
		for (Map.Entry<Line, Set<Point>> entry : pointsByLine.entrySet()) {
			Set<Point> points = entry.getValue();
			p01 += points.size();
		}
		return 1 - p0 + p1 + p01;
	}
}
