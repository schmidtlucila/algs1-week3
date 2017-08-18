import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

import java.util.Arrays;

/**
 * Created by lu on 18/08/17
 */
public class FastCollinearPoints {

    private LineSegment[] lineSegments;
    private final Point[] points;

    public FastCollinearPoints(Point[] points) {
        if (!validate(points)) throw new IllegalArgumentException();
        this.points = Arrays.copyOf(points, points.length);
        if (points.length == 0) return;
        Point[][] lineSegments = new Point[points.length][2];
        int lineSegmentAmount = 0;
        for (Point p : this.points) {
            Arrays.sort(points, p.slopeOrder());
            double referenceSlope = points[0].slopeTo(points[0]);
            Point[] sameLinePoints = new Point[4];
            int amount = 2;
            for (Point q : points) {
                double currentSlope = p.slopeTo(q);
                if (referenceSlope == currentSlope && (currentSlope != Double.POSITIVE_INFINITY || p.getX() == q.getX())) {
                    sameLinePoints[amount] = q;
                    amount++;
                } else {
                    if (amount >= 4) {
                        sameLinePoints[0] = p;
                        sameLinePoints[1] = q;
                        Arrays.sort(sameLinePoints);
                        Point[] segment = new Point[]{sameLinePoints[0], sameLinePoints[sameLinePoints.length - 1]};
                        if (!isPresentIn(lineSegments, segment)) {
                            lineSegments[lineSegmentAmount] = segment;
                            lineSegmentAmount++;
                        }
                    }
                    referenceSlope = currentSlope;
                    sameLinePoints = new Point[4];
                    amount = 0;
                }
            }
        }

        this.lineSegments = new LineSegment[lineSegmentAmount];
        for (int i = 0; i < lineSegmentAmount; i++) {
            Point[] rawLine = lineSegments[i];
            this.lineSegments[i] = new LineSegment(rawLine[0], rawLine[1]);
        }
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

    private boolean isPresentIn(Point[][] lineSegments, Point[] segment) {
        for (Point[] pair : lineSegments) {
            if (pair.length == 2 && segment.length == 2 && pair[0] == segment[0] && pair[1] == segment[1]) {
                return true;
            }
        }

        return false;
    }

    public int numberOfSegments() {
        return lineSegments.length;
    }

    public LineSegment[] segments() {
        return lineSegments == null ? new LineSegment[]{} : lineSegments;
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
        FastCollinearPoints collinear = new FastCollinearPoints(points);
        for (LineSegment segment : collinear.segments()) {
            StdOut.println(segment);
            segment.draw();
        }
        StdDraw.show();
    }
}
