package project.main;

public class Point {
    public int row;
    public int col;

    public Point(int row, int col) {
        this.row = row;
        this.col = col;
    }

    public Point add(Point other) {
        return new Point(row + other.row, col + other.col);
    }

    @Override
    public String toString() {
        return "(" + row + ", " + col + ")";
    }

}
