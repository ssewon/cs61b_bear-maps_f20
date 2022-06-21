package bearmaps;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.NoSuchElementException;

public class ArrayHeapMinPQ<T> implements ExtrinsicMinPQ<T> {

    private ArrayList<Node> items;
    private HashMap<T, Integer> index;
    private int size;

    private class Node {
        private T item;
        private double priority;

        public Node(T item, double priority) {
            this.item = item;
            this.priority = priority;
        }
    }

    public ArrayHeapMinPQ() {
        items = new ArrayList<>();
        index = new HashMap<>();
        items.add(null);
    }

    private int parent(int i) {
        return i / 2;
    }

    private int leftChild(int i) {
        return i * 2;
    }

    private int rightChild(int i) {
        return i * 2 + 1;
    }

    @Override
    public void add(T item, double priority) {
        if (contains(item)) {
            throw new IllegalArgumentException();
        }
        items.add(new Node(item, priority));
        size += 1;
        index.put(item, size);
        swim(size, priority);
    }

    private void swim(int i, double priority) {
        while (parent(i) != 0) {
            double parentPriority = items.get(parent(i)).priority;
            if (parentPriority > priority) {
                swap(i, parent(i));
                i = parent(i);
            } else {
                return;
            }
        }
    }

    private void swap(int i, int k) {
        Node temp = items.get(i);
        items.set(i, items.get(k));
        items.set(k, temp);
        index.put(items.get(i).item, i);
        index.put(items.get(k).item, k);
    }

    @Override
    public boolean contains(T item) {
        return index.containsKey(item);
    }

    @Override
    public T getSmallest() {
        if (size == 0) {
            throw new NoSuchElementException();
        }
        return items.get(1).item;
    }

    @Override
    public T removeSmallest() {
        if (size == 0) {
            throw new NoSuchElementException();
        }
        T temp = items.get(1).item;
        swap(1, size);
        items.remove(size);
        size -= 1;
        index.remove(temp);
        sink(1);
        return temp;
    }

    private void sink(int i) {
        while (leftChild(i) < size) {
            int left = leftChild(i);
            int right = rightChild(i);
            if (right <= size && items.get(right).priority < items.get(left).priority) {
                left = right;
            }
            if (items.get(i).priority < items.get(left).priority) {
                return;
            }
            swap(i, left);
            i = left;
        }
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public void changePriority(T item, double priority) {
        if (!contains(item)) {
            throw new NoSuchElementException();
        }
        int temp = index.get(item);
        double oldPriority = items.get(temp).priority;
        items.get(temp).priority = priority;
        if (oldPriority < priority) {
            sink(temp);
        } else {
            swim(temp, priority);
        }
    }
}
