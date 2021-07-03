import edu.princeton.cs.algs4.StdRandom;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class RandomizedQueue<Item> implements Iterable<Item> {

    private Item[] randomAccessArray;
    private int elementsCount;

    // construct an empty randomized queue
    public RandomizedQueue() {
        this.randomAccessArray = (Item[]) new Object[4];
    }

    // is the randomized queue empty?
    public boolean isEmpty() {
        return this.elementsCount == 0;
    }

    // return the number of items on the randomized queue
    public int size() { return this.elementsCount; }

    // add the item
    public void enqueue(Item item) {

        if (this.elementsCount == this.randomAccessArray.length)
            resizeArray(randomAccessArray.length * 2);

        this.randomAccessArray[this.elementsCount] = item;

        this.elementsCount++;
    }

    private void resizeArray(int newSize) {
        if (newSize <= 4) return;

        Item[] newArray = (Item[]) new Object[newSize];
        for (int i = 0; i < this.randomAccessArray.length; i++) {
            newArray[i] = this.randomAccessArray[i];
        }

        this.randomAccessArray = newArray;
    }

    // remove and return a random item
    public Item dequeue() {
        int randomIndex = StdRandom.uniform(0, elementsCount);

        Item randomItem = this.randomAccessArray[randomIndex];
        this.randomAccessArray[randomIndex] = this.randomAccessArray[elementsCount - 1];

        elementsCount--;

        return randomItem;
    }

    // return a random item (but do not remove it)
    public Item sample() {
        int randomIndex = StdRandom.uniform(0, elementsCount);
        return this.randomAccessArray[randomIndex];
    }

    // return an independent iterator over items in random order
    public Iterator<Item> iterator() { return new NodeIterator(this); }

    private class NodeIterator implements Iterator<Item> {

        private final RandomizedQueue<Item> queue;
        public NodeIterator(RandomizedQueue<Item> queue) {
            this.queue = queue;
        }

        public boolean hasNext() {
            return !this.queue.isEmpty();
        }

        public Item next() {
            if (!hasNext()) throw new NoSuchElementException();

            return this.queue.dequeue();
        }

        public void remove() {
            throw new UnsupportedOperationException();
        }
    }

    // unit testing (required)
    public static void main(String[] args) {
        // UncommentedEmptyMethodBody
    }

}