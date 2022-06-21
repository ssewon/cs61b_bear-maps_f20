package bearmaps;
import java.util.List;

public class KDTree implements PointSet {

    private final Node root;

    public KDTree(List<Point> points) {
        root = new Node(points.get(0));
        for (int i = 1; i < points.size(); i++) {
            add(root, points.get(i), 0);
        }
    }

    private Node add(Node root, Point temp, int depth) {
        if (root == null) {
            return new Node(temp);
        }
        double cmp = comparePoints(root.p, temp, depth);
        if (cmp >= 0) {
            root.right = add(root.right, temp, depth + 1);
        } else {
            root.left = add(root.left, temp, depth + 1);
        }
        return root;
    }

    private double comparePoints(Point a, Point b, int depth) {
        if (depth % 2 == 0) {
            return (b.getX() - a.getX());
        } else {
            return (b.getY() - a.getY());
        }
    }

    @Override
    public Point nearest(double x, double y) {
        return nearestHelper(root, new Point(x, y), root.p, 0);
    }

    private Point nearestHelper(Node root, Point temp, Point best, int depth) {
        Node goodSide;
        Node badSide;
        if (root == null) {
            return best;
        }
        if (Point.distance(root.p, temp) < Point.distance(best, temp)) {
            best = root.p;
        }
        double cmp = comparePoints(root.p, temp, depth);
        if (cmp >= 0) {
            goodSide = root.right;
            badSide = root.left;
        } else {
            goodSide = root.left;
            badSide = root.right;
        }
        best = nearestHelper(goodSide, temp, best, depth + 1);
        double cmp2 = compareDistance(root, temp, best, depth);
        if (cmp2 < 0) {
            best = nearestHelper(badSide, temp, best, depth + 1);
        }
        return best;
    }

    private double compareDistance(Node root, Point temp, Point best, int depth) {
        double goodDist = Point.distance(best, temp);
        double badDist;
        if (depth % 2 == 0) {
            badDist = Point.distance(new Point(temp.getX(), root.y), temp);
        } else {
            badDist = Point.distance(new Point(root.x, temp.getY()), temp);
        }
        return badDist - goodDist;
    }

    private class Node {
        private final Point p;
        private double x;
        private double y;
        private Node left;
        private Node right;

        public Node(Point p) {
            this.p = p;
            this.x = x;
            this.y = y;
            this.left = null;
            this.right = null;
        }
    }
}
