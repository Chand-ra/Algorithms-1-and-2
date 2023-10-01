/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

import java.util.Arrays;


public class FastCollinearPoints {

    private class Segment {
        double slope;
        LineSegment segment;
        Point point;

        Segment(double slope, LineSegment segment, Point point) {
            this.slope = slope;
            this.segment = segment;
            this.point = point;
        }
    }

    private int count = 0;
    private Segment[] lineSegments;
    private Point max, min;

    public FastCollinearPoints(Point[] point) {
        if (point == null) throw new IllegalArgumentException();
        int pointsCounter;
        int n = point.length;
        Point[] points = new Point[n];
        Point[] copy = new Point[n];
        lineSegments = new Segment[n * n];

        for (int i = 0; i < n; i++) {
            if (point[i] == null) throw new IllegalArgumentException();
            points[i] = point[i];
        }

        Arrays.sort(points, Point::compareTo);

        for (int p = 0; p < n - 1; p++) {
            if (points[p].compareTo(points[p + 1]) == 0) throw new IllegalArgumentException();
        }

        for (int i = 0; i < n; i++) {
            for (int p = 0; p < n; p++) {
                copy[p] = points[p];
            }
            Arrays.sort(copy, points[i].slopeOrder());
            for (int j = 1; j < n - 1; j++) {
                if (points[i].slopeOrder().compare(copy[j], copy[j + 1]) == 0) {
                    pointsCounter = 3;
                    int k = j + 2;
                    double slope = points[i].slopeTo(copy[j]);
                    while (k != n && slope == points[i].slopeTo(copy[k])) {
                        pointsCounter++;
                        k++;
                    }
                    if (pointsCounter >= 4) {
                        setMinMax(points[i], points[i], copy[j]);
                        setMinMax(max, min, copy[k - 1]);
                        LineSegment segment = new LineSegment(min, max);
                        addSegment(new Segment(slope, segment, min));
                    }
                    j = k - 1;
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

    private void addSegment(Segment segment) {
        for (int a = 0; a < count; a++) {
            if (segment.slope == lineSegments[a].slope && segment.point.compareTo(lineSegments[a].point) == 0) return;
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
        FastCollinearPoints collinear = new FastCollinearPoints(points);
        for (LineSegment segment : collinear.segments()) {
            StdOut.println(segment);
            segment.draw();
        }
        StdDraw.show();
        System.out.println(collinear.numberOfSegments());
    }
}
