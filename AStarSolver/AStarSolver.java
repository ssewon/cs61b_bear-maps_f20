package bearmaps.proj2c;

import bearmaps.proj2ab.DoubleMapPQ;
import bearmaps.proj2ab.ExtrinsicMinPQ;
import edu.princeton.cs.algs4.Stopwatch;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class AStarSolver<Vertex> implements ShortestPathsSolver<Vertex> {
    private SolverOutcome outcome;
    private double solutionWeight;
    private LinkedList<Vertex> solution;
    private int numStatesExplored;
    private double explorationTime;
    Map<Vertex, Double> dist = new HashMap<>();
    Map<Vertex, Vertex> edge = new HashMap<>();
    ExtrinsicMinPQ<Vertex> minPQ = new DoubleMapPQ<>();

    public AStarSolver(AStarGraph<Vertex> input, Vertex start, Vertex end, double timeout) {
        Stopwatch sw = new Stopwatch();
        solution = new LinkedList<>();
        solutionWeight = 0;
        numStatesExplored = 0;
        dist.put(start, 0.0);
        minPQ.add(start, input.estimatedDistanceToGoal(start, end));

        while (minPQ.size() > 0) {
            if (minPQ.getSmallest().equals(end)) {
                outcome = SolverOutcome.SOLVED;
                Vertex temp = end;
                solutionWeight = dist.get(temp);
                while (!temp.equals(start)) {
                    solution.addFirst(temp);
                    temp = edge.get(temp);
                }
                solution.addFirst(start);
                explorationTime = sw.elapsedTime();
                return;
            }

            if (sw.elapsedTime() > timeout) {
                outcome = SolverOutcome.TIMEOUT;
                explorationTime = sw.elapsedTime();
                return;
            }

            Vertex curr = minPQ.removeSmallest();
            numStatesExplored += 1;

            for (WeightedEdge<Vertex> e : input.neighbors(curr)) {
                if (!dist.containsKey(e.to())
                        || dist.get(curr) + e.weight() < dist.get(e.to())) {
                    dist.put(e.to(), dist.get(curr) + e.weight());
                    edge.put(e.to(), curr);
                    if (minPQ.contains(e.to())) {
                        minPQ.changePriority(e.to(), dist.get(e.to())
                                + input.estimatedDistanceToGoal(e.to(), end));
                    } else {
                        minPQ.add(e.to(), dist.get(e.to())
                                + input.estimatedDistanceToGoal(e.to(), end));
                    }
                }
            }
        }

        outcome = SolverOutcome.UNSOLVABLE;
        explorationTime = sw.elapsedTime();

    }

    @Override
    public SolverOutcome outcome() {
        return outcome;
    }

    @Override
    public List<Vertex> solution() {
        return solution;
    }

    @Override
    public double solutionWeight() {
        return solutionWeight;
    }

    @Override
    public int numStatesExplored() {
        return numStatesExplored;
    }

    @Override
    public double explorationTime() {
        return explorationTime;
    }
}
