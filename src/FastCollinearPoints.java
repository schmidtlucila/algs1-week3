import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

import java.util.Arrays;

/**
 * Created by lu on 18/08/17
 */
public class FastCollinearPoints {

    private final LineSegment[] lineSegments;

    public FastCollinearPoints(Point[] points) {
        if (!validate(points)) throw new IllegalArgumentException();
        if (points.length < 4) {
            lineSegments = new LineSegment[]{};
            return;
        }

        Point[][] lineSegments = new Point[points.length][2];
        int lineSegmentAmount = 0;
        Point[] sortedPoints = Arrays.copyOf(points, points.length);
        for (Point p : points) {
            Arrays.sort(sortedPoints, p.slopeOrder());
            //first will be -infinity
            double referenceSlope = p.slopeTo(sortedPoints[0]);
            Point[] sameLinePoints = new Point[points.length];
            sameLinePoints[0] = p;
            int amount = 1;
            for (int i = 0; i < sortedPoints.length; i++) {
                Point q = sortedPoints[i];
                if (p.slopeTo(q) != Double.NEGATIVE_INFINITY) {
                    sameLinePoints[amount] = q;
                    amount++;
                }

                if (i == sortedPoints.length - 1 || nextHasDifferentSlope(p, i, sortedPoints)) {
                    if (amount >= 4 && p.slopeTo(q) != Double.NEGATIVE_INFINITY) {
                        sameLinePoints = Arrays.copyOf(sameLinePoints, amount);
                        Arrays.sort(sameLinePoints);
                        Point[] segment = new Point[]{sameLinePoints[0], sameLinePoints[amount - 1]};
                        if (!isPresentIn(lineSegments, segment)) {
                            if (lineSegmentAmount == lineSegments.length) {
                                lineSegments = Arrays.copyOf(lineSegments, 2 * lineSegmentAmount);
                            }
                            lineSegments[lineSegmentAmount] = segment;
                            lineSegmentAmount++;
                        }
                    }
                    sameLinePoints = new Point[points.length];
                    sameLinePoints[0] = p;
                    amount = 1;
                }

                /*double currentSlope = p.slopeTo(q);
                if (referenceSlope == currentSlope) {
                    sameLinePoints[amount] = q;
                    amount++;
                } else {
                    if (amount >= 4 && referenceSlope != Double.NEGATIVE_INFINITY) {
                        Arrays.sort(sameLinePoints);
                        Point[] segment = new Point[]{sameLinePoints[0], sameLinePoints[sameLinePoints.length - 1]};
                        if (!isPresentIn(lineSegments, segment)) {
                            lineSegments[lineSegmentAmount] = segment;
                            lineSegmentAmount++;
                        }
                    }
                    referenceSlope = currentSlope;
                    sameLinePoints = new Point[4];
                    sameLinePoints[0] = p;
                    sameLinePoints[1] = q;
                    amount = 2;
                }
                if (i == sortedPoints.length - 1) {
                    if (amount >= 4 && referenceSlope != Double.NEGATIVE_INFINITY) {
                        Arrays.sort(sameLinePoints);
                        Point[] segment = new Point[]{sameLinePoints[0], sameLinePoints[sameLinePoints.length - 1]};
                        if (!isPresentIn(lineSegments, segment)) {
                            lineSegments[lineSegmentAmount] = segment;
                            lineSegmentAmount++;
                        }
                    }
                }*/
            }
        }

        this.lineSegments = new LineSegment[lineSegmentAmount];
        for (int i = 0; i < lineSegmentAmount; i++) {
            Point[] rawLine = lineSegments[i];
            this.lineSegments[i] = new LineSegment(rawLine[0], rawLine[1]);
        }
    }

    private boolean nextHasDifferentSlope(Point p, int i, Point[] sortedPoints) {
        return p.slopeTo(sortedPoints[i]) != p.slopeTo(sortedPoints[i + 1]);
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

    private boolean isPresentIn(Point[][] lineSegments, Point[] segment) {
        for (Point[] pair : lineSegments) {
            if (pair == null) break;
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
        return lineSegments == null ? new LineSegment[]{} : Arrays.copyOf(lineSegments, lineSegments.length);
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
