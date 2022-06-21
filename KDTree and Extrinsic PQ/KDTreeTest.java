package bearmaps;

import java.util.ArrayList;
import java.util.List;
import org.junit.Test;
import static org.junit.Assert.assertEquals;
import java.util.Random;
import edu.princeton.cs.algs4.Stopwatch;

public class KDTreeTest {
    @Test
    public void naiveTest() {
        List<Point> points = new ArrayList<>();
        points.add(new Point(2.0, 3.0));
        points.add(new Point(4.0, 2.0));
        points.add(new Point(4.0, 5.0));
        points.add(new Point(3.0, 3.0));
        points.add(new Point(1.0, 5.0));
        points.add(new Point(4.0, 4.0));
        NaivePointSet naivePointSet = new NaivePointSet(points);
        Point bestNaive = naivePointSet.nearest(0, 7);
        System.out.println(bestNaive.toString()); /**should get (1,5)*/
    }

    @Test
    public void kdTreeTest() {
        List<Point> points = new ArrayList<>();
        points.add(new Point(2.0, 3.0));
        points.add(new Point(4.0, 2.0));
        points.add(new Point(4.0, 5.0));
        points.add(new Point(3.0, 3.0));
        points.add(new Point(1.0, 5.0));
        points.add(new Point(4.0, 4.0));
        KDTree kdTree = new KDTree(points);
        Point bestKd = kdTree.nearest(0, 7);
        System.out.println(bestKd.toString()); /**should get (1,5)*/
    }

    @Test
    public void naiveEqualsKd() {
        List<Point> points = new ArrayList<>();
        points.add(new Point(2.0, 3.0));
        points.add(new Point(4.0, 2.0));
        points.add(new Point(4.0, 5.0));
        points.add(new Point(3.0, 3.0));
        points.add(new Point(1.0, 5.0));
        points.add(new Point(4.0, 4.0));
        NaivePointSet naivePointSet = new NaivePointSet(points);
        Point bestNaive = naivePointSet.nearest(0, 7);
        KDTree kdTree = new KDTree(points);
        Point bestKd = kdTree.nearest(0, 7);
        assertEquals(bestNaive, bestKd); /**should both be (1,5)*/
    }

    private static Random random = new Random(1000);

    private Point randomPoint() {
        double x = random.nextDouble();
        double y = random.nextDouble();
        return new Point(x, y);
    }

    private List<Point> randomPoints(int n) {
        List<Point> points = new ArrayList<>();
        for (int i = 0; i < n; i += 1) {
            points.add(randomPoint());
        }
        return points;
    }

    @Test
    public void timing() {
        List<Point> testNaive = randomPoints(1000);
        NaivePointSet nps = new NaivePointSet(testNaive);
        List<Point> testKd = randomPoints(1000000);
        KDTree kd = new KDTree(testKd);
        List<Point> queryPoints = randomPoints(1000000);

        Stopwatch sw = new Stopwatch();
        for (Point p : queryPoints) {
            nps.nearest(p.getX(), p.getY());
        }
        System.out.println("Total time elapsed NAIVE: " + sw.elapsedTime() + " seconds.");

        sw = new Stopwatch();
        for (Point p : queryPoints) {
            kd.nearest(p.getX(), p.getY());
        }
        System.out.println("Total time elapsed KD: " + sw.elapsedTime() + " seconds.");
    }
}
