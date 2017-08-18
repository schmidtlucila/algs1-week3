/**
 * Created by lu on 18/08/17
 */

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

import java.util.Arrays;

/**
 * Created by lu on 18/08/17
 */
public class BruteCollinearPoints {

    private LineSegment[] lineSegments;

    public BruteCollinearPoints(Point[] points) {
        if (!validate(points)) throw new IllegalArgumentException();
        if (points.length == 0) return;

        int[] segmentEnds = new int[points.length];
        Arrays.sort(points);

        for (int i = 0; i < points.length; i++) {
            segmentEnds[i] = i;
        }
        int collinears = 0;
        for (int i = 0; i < points.length; i++) {
            Point p = points[i];
            for (int j = i + 1; j < points.length; j++) {
                Point q = points[j];
                for (int h = j + 1; h < points.length; h++) {
                    Point r = points[h];
                    for (int k = h + 1; k < points.length; k++) {
                        Point s = points[k];
                        if (areCollinears(p, q, r, s)) {
                            segmentEnds[j] = k;
                            collinears++;
                        }
                    }
                }
            }
        }
        this.lineSegments = new LineSegment[collinears];
        for (int i = 0; i < collinears; i++) {
            lineSegments[i] = new LineSegment(points[i], points[segmentEnds[i]]);
        }
    }

    private boolean areCollinears(Point p, Point q, Point r, Point s) {
        return p.slopeTo(q) == p.slopeTo(q) && p.slopeTo(q) == q.slopeTo(r) && q.slopeTo(r) == r.slopeTo(s);
    }

    private boolean validate(Point[] points) {
        if (points == null) return false;
        int n = points.length;
        for (int i = 0; i < n; i++) {
            Point point = points[i];
            if (point == null) return false;
            int index = Arrays.binarySearch(points, i + 1, n, point);
            if (index > 0) return false;
        }

        return true;
    }

    public int numberOfSegments() {
        return lineSegments.length;
    }

    public LineSegment[] segments() {
        return lineSegments;
    }

    public static void main(String[] args) {

        // read the n points from a file
        In in = new In(args[0]);
        int n = in.readInt();
        Point[] points = new Point[n];
        for (int i = 0; i < n; i++) {
            int x = in.readInt();
            int y = in.readInt();
            points[i] = new Point(x, y);
        }

        // draw the points
        StdDraw.enableDoubleBuffering();
        StdDraw.setXscale(0, 32768);
        StdDraw.setYscale(0, 32768);
        for (Point p : points) {
            p.draw();
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

