package bearmaps;

import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.Stopwatch;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static org.junit.Assert.*;

public class ArrayHeapMinPQTest {

    @Test
    public void test1() {
        ArrayHeapMinPQ<String> testPQ = new ArrayHeapMinPQ<>();
        testPQ.add("C", 3);
        testPQ.add("A", 1);
        testPQ.add("B", 2);
        testPQ.add("D", 4);
        assertEquals(4, testPQ.size());
        assertEquals("A", testPQ.getSmallest());
        assertEquals("A", testPQ.removeSmallest());
        assertEquals(3, testPQ.size());
        assertEquals("B", testPQ.getSmallest());
        assertNotEquals("C", testPQ.getSmallest());
        assertTrue(testPQ.contains("C"));
        assertFalse(testPQ.contains("E)"));
        testPQ.changePriority("B", 4);
        testPQ.changePriority("C", 1);
        assertEquals("C", testPQ.getSmallest());
    }

    @Test
    public void test2()  {
        ArrayHeapMinPQ<String> myPQ = new ArrayHeapMinPQ<>();
        myPQ.add("first", 1);
        myPQ.add("fifth", 5);
        myPQ.add("fourth", 4);
        myPQ.add("third", 3);
        myPQ.add("last", 11);
        myPQ.add("sixth", 6);
        myPQ.add("second", 2);
        myPQ.add("seventh", 7);
        myPQ.add("ninth", 9);
        assertEquals("first", myPQ.getSmallest());
        assertEquals("first", myPQ.removeSmallest());
        assertEquals("second", myPQ.getSmallest());
        assertNotEquals("third", myPQ.getSmallest());
    }

    @Test
    public void speedTest() {
        Stopwatch sw = new Stopwatch();
        ArrayHeapMinPQ<Integer> testHeap = new ArrayHeapMinPQ<>();
        for (int i = 100000; i > 0; i--) {
            testHeap.add(i, i);
        }
        for (int i = 1; i <= 100000; i++) {
            testHeap.removeSmallest();
        }
        System.out.println("Total time elapsed HEAP (add/remove): " + sw.elapsedTime() + " seconds.");

        sw = new Stopwatch();
        NaiveMinPQ<Integer> testNaive = new NaiveMinPQ<>();
        for (int i = 100000; i > 0; i--) {
            testNaive.add(i, i);
        }
        for (int i = 1; i <= 100000; i++) {
            testNaive.removeSmallest();
        }
        System.out.println("Total time elapsed NAIVE (add/remove): " + sw.elapsedTime() + " seconds.");

        for (int i = 100000; i > 0; i--) {
            testHeap.add(i, i);
            testNaive.add(i, i);
        }

        sw = new Stopwatch();
        for (int i = 100000; i > 0; i--) {
            testHeap.changePriority(i, 100000 - i);
        }
        System.out.println("Total time elapsed HEAP (changePriority): " + sw.elapsedTime() + " seconds.");

        sw = new Stopwatch();
        for (int i = 100000; i > 0; i--) {
            testNaive.changePriority(i, 100000 - i);
        }
        System.out.println("Total time elapsed NAIVE (changePriority): " + sw.elapsedTime() + " seconds.");
    }
}
