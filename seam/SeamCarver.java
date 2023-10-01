/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import edu.princeton.cs.algs4.Picture;

public class SeamCarver {
    private Picture pic;
    private double[][] energy;

    public SeamCarver(Picture picture) {
        if (picture == null) throw new IllegalArgumentException();
        pic = new Picture(picture);
        energy = new double[height()][width()];
        calculateEnergy();
    }

    public Picture picture() {
        Picture picture = new Picture(pic);
        return picture;
    }

    public int width() {        // returns no. of columns
        return pic.width();
    }

    public int height() {       // returns no. of rows
        return pic.height();
    }

    public double energy(int x, int y) {
        if (x < 0 || x > width() - 1 || y < 0 || y > height() - 1)
            throw new IllegalArgumentException();
        return energy[y][x];
    }

    private void validateSeam(int[] seam, boolean isVertical) {
        if (seam == null) throw new IllegalArgumentException();
        if (isVertical) {
            if (width() <= 1) throw new IllegalArgumentException();
            if (seam.length != height()) throw new IllegalArgumentException();
        }
        else {
            if (height() <= 1) throw new IllegalArgumentException();
            if (seam.length != width()) throw new IllegalArgumentException();
        }
        for (int i = 0; i < seam.length - 1; i++) {
            if (Math.abs(seam[i] - seam[i + 1]) > 1) throw new IllegalArgumentException();
        }
    }

    private void calculateEnergy() {
        int rows = height();
        int cols = width();
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                if (i == 0 || i == rows - 1 || j == 0 || j == cols - 1) {
                    energy[i][j] = 1000;
                }
                else energy[i][j] = Math.sqrt(
                        deltaXsquared(j, i) + deltaYsquared(j, i));
            }
        }
    }

    private double deltaXsquared(int x, int y) {
        int rgb1 = pic.getRGB(x - 1, y);
        int rgb2 = pic.getRGB(x + 1, y);
        int rx = ((rgb1 >> 16) & 0xFF) - ((rgb2 >> 16) & 0xFF);
        int gx = ((rgb1 >> 8) & 0xFF) - ((rgb2 >> 8) & 0xFF);
        int bx = ((rgb1) & 0xFF) - ((rgb2) & 0xFF);
        return (rx * rx + gx * gx + bx * bx);
    }

    private double deltaYsquared(int x, int y) {
        int rgb1 = pic.getRGB(x, y - 1);
        int rgb2 = pic.getRGB(x, y + 1);
        int rx = ((rgb1 >> 16) & 0xFF) - ((rgb2 >> 16) & 0xFF);
        int gx = ((rgb1 >> 8) & 0xFF) - ((rgb2 >> 8) & 0xFF);
        int bx = ((rgb1) & 0xFF) - ((rgb2) & 0xFF);
        return (rx * rx + gx * gx + bx * bx);
    }

    private void relaxVertical(double[][] distTo, int[][] vertexTo, int jSrc, int iSrc, int jDest,
                               int iDest) {
        if (distTo[iDest][jDest] > distTo[iSrc][jSrc] + energy(jSrc, iSrc)) {
            distTo[iDest][jDest] = distTo[iSrc][jSrc] + energy(jSrc, iSrc);
            vertexTo[iDest][jDest] = jSrc;
        }
    }

    private void relaxVertical(double[][] distTo, int[][] vertexTo, int j, int i) {
        if (width() == 1) {
            relaxVertical(distTo, vertexTo, j, i, j, i + 1);
            return;
        }
        if (j == 0) {
            relaxVertical(distTo, vertexTo, j, i, j + 1, i + 1);
            relaxVertical(distTo, vertexTo, j, i, j, i + 1);
        }
        else if (j == width() - 1) {
            relaxVertical(distTo, vertexTo, j, i, j - 1, i + 1);
            relaxVertical(distTo, vertexTo, j, i, j, i + 1);
        }
        else {
            relaxVertical(distTo, vertexTo, j, i, j + 1, i + 1);
            relaxVertical(distTo, vertexTo, j, i, j, i + 1);
            relaxVertical(distTo, vertexTo, j, i, j - 1, i + 1);
        }
    }

    private void relaxHorizontal(double[][] distTo, int[][] vertexTo, int jSrc, int iSrc, int jDest,
                                 int iDest) {
        if (distTo[iDest][jDest] > distTo[iSrc][jSrc] + energy(jSrc, iSrc)) {
            distTo[iDest][jDest] = distTo[iSrc][jSrc] + energy(jSrc, iSrc);
            vertexTo[iDest][jDest] = iSrc;
        }
    }

    private void relaxHorizontal(double[][] distTo, int[][] vertexTo, int j, int i) {
        if (height() == 1) {
            relaxHorizontal(distTo, vertexTo, j, i, j + 1, i);
            return;
        }
        if (i == 0) {
            relaxHorizontal(distTo, vertexTo, j, i, j + 1, i);
            relaxHorizontal(distTo, vertexTo, j, i, j + 1, i + 1);
        }
        else if (i == height() - 1) {
            relaxHorizontal(distTo, vertexTo, j, i, j + 1, i);
            relaxHorizontal(distTo, vertexTo, j, i, j + 1, i - 1);
        }
        else {
            relaxHorizontal(distTo, vertexTo, j, i, j + 1, i - 1);
            relaxHorizontal(distTo, vertexTo, j, i, j + 1, i);
            relaxHorizontal(distTo, vertexTo, j, i, j + 1, i + 1);
        }
    }

    public int[] findVerticalSeam() {
        int rows = height();
        int cols = width();
        double[][] distTo = new double[height()][width()];
        int[][] vertexTo = new int[height()][width()];
        int[] seam = new int[rows];

        for (int i = 1; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                distTo[i][j] = Double.POSITIVE_INFINITY;
            }
        }
        for (int i = 0; i < rows - 1; i++) {
            for (int j = 0; j < cols; j++) {
                relaxVertical(distTo, vertexTo, j, i);
            }
        }
        int minj = 0;
        double minDist = Double.POSITIVE_INFINITY;
        for (int j = 0; j < cols; j++) {
            if (distTo[rows - 1][j] < minDist) {
                minDist = distTo[rows - 1][j];
                minj = j;
            }
        }
        for (int i = 0; i < rows; i++) {
            seam[rows - i - 1] = minj;
            minj = vertexTo[rows - i - 1][minj];
        }
        return seam;
    }

    public int[] findHorizontalSeam() {
        int rows = height();
        int cols = width();
        double[][] distTo = new double[height()][width()];
        int[][] vertexTo = new int[height()][width()];
        int[] seam = new int[cols];

        for (int i = 0; i < rows; i++) {
            for (int j = 1; j < cols; j++) {
                distTo[i][j] = Double.POSITIVE_INFINITY;
            }
        }

        for (int j = 0; j < cols - 1; j++) {
            for (int i = 0; i < rows; i++) {
                relaxHorizontal(distTo, vertexTo, j, i);
            }
        }

        int mini = 0;
        double minDist = Double.POSITIVE_INFINITY;
        for (int i = 0; i < rows; i++) {
            if (distTo[i][cols - 1] < minDist) {
                minDist = distTo[i][cols - 1];
                mini = i;
            }
        }

        for (int j = 0; j < cols; j++) {
            seam[cols - j - 1] = mini;
            mini = vertexTo[mini][cols - j - 1];
        }
        return seam;
    }

    public void removeVerticalSeam(int[] seam) {
        validateSeam(seam, true);
        Picture newPic = new Picture(width() - 1, height());
        for (int i = 0; i < pic.height(); i++) {
            int colIndex = 0;
            for (int j = 0; j < pic.width(); j++) {
                if (j == seam[i]) continue;
                newPic.setRGB(colIndex, i, pic.getRGB(j, i));
                colIndex++;
            }
        }
        pic = newPic;
        calculateEnergy();
    }

    public void removeHorizontalSeam(int[] seam) {
        validateSeam(seam, false);
        Picture newPic = new Picture(width(), height() - 1);
        for (int j = 0; j < pic.width(); j++) {
            int rowIndex = 0;
            for (int i = 0; i < pic.height(); i++) {
                if (i == seam[j]) continue;
                newPic.setRGB(j, rowIndex, pic.getRGB(j, i));
                rowIndex++;
            }
        }
        pic = newPic;
        calculateEnergy();
    }
}
