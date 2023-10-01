/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

public class BruteCollinearPoints {
    private int count = 0;
    private LineSegment[] lineSegments;
    private Point max, min;

    public BruteCollinearPoints(Point[] points) {
        if (points == null) throw new IllegalArgumentException();
        int n = points.length;
        lineSegments = new LineSegment[n * n];

        for (int i = 0; i < n; i++) {
            if (points[i] == null) throw new IllegalArgumentException();
        }
        
        for (int i = 0; i < n; i++) {
            Point point1 = points[i];
            for (int j = i + 1; j < n; j++) {
                Point point2 = points[j];
                if (point1.compareTo(point2) == 0) throw new IllegalArgumentException();
                setMinMax(point1, point1, point2);
                for (int k = j + 1; k < n; k++) {
                    Point point3 = points[k];
                    if (point1.slopeTo(point2) == point1.slopeTo(point3)) {
                        setMinMax(max, min, point3);
                        for (int m = k + 1; m < n; m++) {
                            Point point4 = points[m];
                            if (point1.slopeTo(point2) == point1.slopeTo(point4)) {
                                setMinMax(max, min, point4);
                                addSegment(new LineSegment(min, max));
                            }
                        }
                    }
                }
            }
        }
    }

    private Point min(Point a, Point b) {
        Point minimum;
        if (a.compareTo(b) < 0) minimum = a;
        else minimum = b;
        return minimum;
    }

    private Point max(Point a, Point b) {
        Point maximum;
        if (a.compareTo(b) > 0) maximum = a;
        else maximum = b;
        return maximum;
    }

    private void setMinMax(Point maximum, Point minimum, Point toCompare) {
        min = min(minimum, toCompare);
        max = max(maximum, toCompare);
    }

    private void addSegment(LineSegment segment) {
        for (int a = 0; a < lineSegments.length; a++) {
            if (lineSegments[a] == null) break;
            if (segment.toString().equals(lineSegments[a].toString())) return;
        }
        lineSegments[count++] = segment;
    }

    public int numberOfSegments() {
        return count;
    }

    public LineSegment[] segments() {
        LineSegment[] segments = new LineSegment[count];
        int i = 0;
        while (lineSegments[i] != null) {
            segments[i] = lineSegments[i];
            i++;
        }
        lineSegments = null;
        return segments;
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

        System.out.println(collinear.numberOfSegments());
    }
}
