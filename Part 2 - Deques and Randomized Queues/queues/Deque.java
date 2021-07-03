import java.util.Iterator;
import java.util.NoSuchElementException;

public class Deque<Item> implements Iterable<Item> {

    private class Node {
        public Item val;
        public Node prev;
        public Node next;

        public Node(Item val, Node prev, Node next) {
            this.val = val;
            this.prev = prev;
            this.next = next;
        }
    }

    private Node head;
    private Node tail;
    private int size = 0;

    // construct an empty deque
    public Deque() {
    }

    // is the deque empty?
    public boolean isEmpty() {
        return this.head == null || this.size == 0;
    }

    // return the number of items on the deque
    public int size() {
        return this.size;
    }

    // add the item to the front
    public void addFirst(Item item) {
        if (item == null) throw new IllegalArgumentException();

        Node oldHead = this.head;
        Node newHead = new Node(item, null, oldHead);

        if (this.head != null)
            this.head.prev = newHead;

        this.head = newHead;

        if (this.tail == null)
            this.tail = this.head;

        size++;
    }

    // add the item to the back
    public void addLast(Item item) {
        if (item == null) throw new IllegalArgumentException();

        Node oldTail = this.tail;
        Node newNode = new Node(item, this.tail, null);

        if (oldTail != null)
            oldTail.next = newNode;

        this.tail = newNode;

        if (this.head == null)
            this.head = this.tail;

        size++;
    }

    // remove and return the item from the front
    public Item removeFirst() {
        if (isEmpty()) throw new NoSuchElementException();

        Node oldHead = this.head;
        Node newHead = this.head.next;

        if (newHead != null) {
            newHead.prev = null;
        }

        this.head =  newHead;

        size--;
        return oldHead.val;
    }

    // remove and return the item from the back
    public Item removeLast() {
        if (isEmpty()) throw new NoSuchElementException();

        size--;
        Node newTail = this.tail.prev;

        if (newTail == null) {
            this.head = null;
            this.tail = null;
            return null;
        }

        newTail.next = null;
        this.tail = newTail;

        return this.tail.val;
    }

    // return an iterator over items in order from front to back
    public Iterator<Item> iterator() {
        return new NodeIterator(this.head);
    }

    private class NodeIterator implements Iterator<Item> {
        Node head;

        public NodeIterator(Node head) {
            this.head = head;
        }

        public boolean hasNext() {
            return this.head != null;
        }

        public Item next() {
            if (!hasNext()) throw new NoSuchElementException();

            Item item = this.head.val;
            this.head = this.head.next;

            return item;
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