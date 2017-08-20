import edu.princeton.cs.algs4.StdDraw;

import java.util.Comparator;

/**
 * Created by lu on 18/08/17
 */
public class Point implements Comparable<Point> {

    private final int x;
    private final int y;

    public Point(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public void draw() {
        StdDraw.point(this.x, this.y);
    }

    public void drawTo(Point that) {
        StdDraw.line(this.x, this.y, that.x, that.y);
    }

    public String toString() {
        return "(" + this.x + ", " + this.y + ")";
    }

    public int compareTo(Point that) {
        if (this.y < that.y) return -1;
        if (this.y > that.y) return 1;
        return this.x - that.x;
    }

    public double slopeTo(Point that) {
        if (this.x == that.x && this.y == that.y) return Double.NEGATIVE_INFINITY;
        if (this.x == that.x) return Double.POSITIVE_INFINITY;
        if (this.y == that.y) return 0.0;
        return ((double) (that.y - this.y)) / (that.x - this.x);
    }

    public Comparator<Point> slopeOrder() {
        return new Comparator<Point>() {

            @Override
            public int compare(Point o1, Point o2) {
                if (o1.slopeTo(Point.this) < o2.slopeTo(Point.this)) return -1;
                if (o1.slopeTo(Point.this) > o2.slopeTo(Point.this)) return 1;
                return 0;
            }
        };
    }
}