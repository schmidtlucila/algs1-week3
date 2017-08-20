import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

import java.util.Arrays;
import java.util.Comparator;

/**
 * Created by lu on 18/08/17
 */
public class BruteCollinearPoints {

    private final LineSegment[] lineSegments;

    public BruteCollinearPoints(Point[] points) {
        if (!validate(points)) throw new IllegalArgumentException();
        Point[][] groups = new Point[points.length][4];
        int collinears = 0;
        Point[] sortedPoints = Arrays.copyOf(points, points.length);
        Arrays.sort(sortedPoints);
        for (int i = 0; i < sortedPoints.length; i++) {
            Point p = sortedPoints[i];
            for (int j = i + 1; j < sortedPoints.length; j++) {
                Point q = sortedPoints[j];
                for (int h = j + 1; h < sortedPoints.length; h++) {
                    Point r = sortedPoints[h];
                    for (int k = h + 1; k < sortedPoints.length; k++) {
                        Point s = sortedPoints[k];
                        if (areCollinears(p, q, r, s)) {
                            Point[] collinearPoints = new Point[]{p, q, r, s};
                            groups[collinears] = collinearPoints;
                            collinears++;
                        }
                    }
                }
            }
        }
        this.lineSegments = new LineSegment[collinears];
        for (int i = 0; i < collinears; i++) {
            this.lineSegments[i] = new LineSegment(groups[i][0], groups[i][3]);
        }
    }

    private boolean areCollinears(Point p, Point q, Point r, Point s) {
        return p.slopeTo(q) == p.slopeTo(r) && p.slopeTo(r) == p.slopeTo(s);
    }

    private boolean validate(Point[] points) {
        if (points == null) return false;
        int n = points.length;
        for (int i = 0; i < n; i++) {
            if (points[i] == null) return false;
            for (int j = i + 1; j < n; j++) {
                if (points[j] == null || points[i].slopeTo(points[j]) == Double.NEGATIVE_INFINITY) {
                    return false;
                }
            }
        }
        return true;
    }
    public int numberOfSegments() {
        return lineSegments == null ? 0 : lineSegments.length;
    }

    public LineSegment[] segments() {
        return lineSegments == null ? new LineSegment[]{} : Arrays.copyOf(lineSegments, lineSegments.length);
    }

    public static void main(String[] args) {

        // read the n points from a file
        In in = new In(args[0]);
        int n = in.readInt();
        Point[] points = new Point[n];
        for (int i = 0; i < n; i++) {
            String s = in.readString();
            if (s.equalsIgnoreCase("null")) {
                points[i] = null;
            } else {
                int x = Integer.parseInt(s);
                int y = in.readInt();
                points[i] = new Point(x, y);
            }
        }

        // draw the points
        StdDraw.enableDoubleBuffering();
        StdDraw.setXscale(0, 32768);
        StdDraw.setYscale(0, 32768);
        for (Point p : points) {
            if (p != null) {
                p.draw();
            }
        }
        StdDraw.show();

        // print and draw the line segments
        BruteCollinearPoints collinear = new BruteCollinearPoints(points);
        for (LineSegment segment : collinear.segments()) {
            StdOut.println(segment);
            segment.draw();
        }
        StdDraw.show();
    }

}

