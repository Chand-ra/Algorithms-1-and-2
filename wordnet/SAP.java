/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import edu.princeton.cs.algs4.BreadthFirstDirectedPaths;
import edu.princeton.cs.algs4.Digraph;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;

public class SAP {
    private final Digraph diG;

    public SAP(Digraph G) {
        if (G == null) throw new IllegalArgumentException();
        diG = new Digraph(G);
    }

    private void validateVertex(int w, int v) {
        if (v < 0 || v >= diG.V()) throw new IllegalArgumentException();
        if (w < 0 || w >= diG.V()) throw new IllegalArgumentException();
    }

    public int length(int v, int w) {
        validateVertex(v, w);
        boolean hasSAP = false;
        int shortestLength = Integer.MAX_VALUE;
        BreadthFirstDirectedPaths bfs1 = new BreadthFirstDirectedPaths(diG, v);
        BreadthFirstDirectedPaths bfs2 = new BreadthFirstDirectedPaths(diG, w);
        for (int i = 0; i < diG.V(); i++) {
            if (bfs1.hasPathTo(i) && bfs2.hasPathTo(i)) {
                hasSAP = true;
                int length = bfs1.distTo(i) + bfs2.distTo(i);
                if (length < shortestLength) {
                    shortestLength = length;
                }
            }
        }
        if (hasSAP) return shortestLength;
        return -1;
    }

    public int ancestor(int v, int w) {
        validateVertex(v, w);
        int ancestor = -1;
        int shortestLength = Integer.MAX_VALUE;
        BreadthFirstDirectedPaths bfs1 = new BreadthFirstDirectedPaths(diG, v);
        BreadthFirstDirectedPaths bfs2 = new BreadthFirstDirectedPaths(diG, w);
        for (int i = 0; i < diG.V(); i++) {
            if (bfs1.hasPathTo(i) && bfs2.hasPathTo(i)) {
                int length = bfs1.distTo(i) + bfs2.distTo(i);
                if (length < shortestLength) {
                    shortestLength = length;
                    ancestor = i;
                }
            }
        }
        return ancestor;
    }

    public int length(Iterable<Integer> v, Iterable<Integer> w) {
        validateIterable(v, w);
        boolean hasSAP = false;
        int shortestLength = Integer.MAX_VALUE;
        BreadthFirstDirectedPaths bfs1 = new BreadthFirstDirectedPaths(diG, v);
        BreadthFirstDirectedPaths bfs2 = new BreadthFirstDirectedPaths(diG, w);
        for (int i = 0; i < diG.V(); i++) {
            if (bfs1.hasPathTo(i) && bfs2.hasPathTo(i)) {
                hasSAP = true;
                int length = bfs1.distTo(i) + bfs2.distTo(i);
                if (length < shortestLength) {
                    shortestLength = length;
                }
            }
        }
        if (hasSAP) return shortestLength;
        return -1;
    }

    private void validateIterable(Iterable<Integer> w, Iterable<Integer> v) {
        if (v == null || w == null) throw new IllegalArgumentException();
        int sizev = 0, sizew = 0;
        for (Integer i : w) {
            sizew++;
            if (i == null) throw new IllegalArgumentException();
        }
        for (Integer i : v) {
            sizev++;
            if (i == null) throw new IllegalArgumentException();
        }
        if (sizev == 0 || sizew == 0) throw new IllegalArgumentException();
    }

    public int ancestor(Iterable<Integer> v, Iterable<Integer> w) {
        validateIterable(v, w);
        int ancestor = -1;
        int shortestLength = Integer.MAX_VALUE;
        BreadthFirstDirectedPaths bfs1 = new BreadthFirstDirectedPaths(diG, v);
        BreadthFirstDirectedPaths bfs2 = new BreadthFirstDirectedPaths(diG, w);
        for (int i = 0; i < diG.V(); i++) {
            if (bfs1.hasPathTo(i) && bfs2.hasPathTo(i)) {
                int length = bfs1.distTo(i) + bfs2.distTo(i);
                if (length < shortestLength) {
                    shortestLength = length;
                    ancestor = i;
                }
            }
        }
        return ancestor;
    }

    public static void main(String[] args) {
        In in = new In(args[0]);
        Digraph G = new Digraph(in);
        SAP sap = new SAP(G);
        int v = Integer.parseInt(args[1]);
        int w = Integer.parseInt(args[2]);
        int length = sap.length(v, w);
        int ancestor = sap.ancestor(v, w);
        StdOut.printf("length = %d, ancestor = %d\n", length, ancestor);
    }
}
