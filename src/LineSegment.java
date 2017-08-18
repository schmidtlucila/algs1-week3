/**
 * Created by lu on 18/08/17
 */
public class LineSegment {

    private final Point p;
    private final Point q;

    public LineSegment(Point p, Point q) {
        this.p = p;
        this.q = q;
    }

    public void draw() {
        p.drawTo(q);
    }

    public String toString() {
        return "[" + p.toString() + ", " + q.toString() + "]";
    }

}