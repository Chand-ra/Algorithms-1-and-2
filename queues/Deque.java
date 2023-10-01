/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

import java.util.Iterator;
import java.util.NoSuchElementException;


public class Deque<Item> implements Iterable<Item> {
    private Integer size = 0;
    private Node first, last = null;

    private class Node {
        Item item;
        Node next;
        Node previous;
    }

    public int size() {
        return size;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public void addFirst(Item item) {
        if (item == null) throw new IllegalArgumentException("Argument cannot be null.");
        Node oldFirst = first;
        first = new Node();
        first.item = item;
        first.previous = null;
        first.next = oldFirst;
        if (size == 0) last = first;
        else oldFirst.previous = first;
        size++;
    }

    public void addLast(Item item) {
        if (item == null) throw new IllegalArgumentException("Argument cannot be null.");
        Node oldLast = last;
        last = new Node();
        last.item = item;
        last.next = null;
        if (size == 0) first = last;
        else {
            last.previous = oldLast;
            oldLast.next = last;
        }
        size++;
    }

    public Item removeFirst() {
        if (size == 0) throw new NoSuchElementException("No elements in Deque");
        Node oldFirst = first;
        first = oldFirst.next;
        oldFirst.next = null;
        if (size == 1) last = first;
        if (size > 1) first.previous = null;
        size--;
        return oldFirst.item;
    }

    public Item removeLast() {
        if (size == 0) throw new NoSuchElementException("No elements in Deque");
        Node oldLast = last;
        last = oldLast.previous;
        oldLast.previous = null;
        if (size == 1) first = last;
        if (size > 1) last.next = null;
        size--;
        return oldLast.item;
    }

    public Iterator<Item> iterator() {
        return new ListIterator();
    }

    private class ListIterator implements Iterator<Item> {
        private Node current = first;

        public boolean hasNext() {
            return current != null;
        }

        public Item next() {
            if (!hasNext()) throw new NoSuchElementException("No more elements");
            Item item = current.item;
            current = current.next;
            return item;
        }

        public void remove() {
            throw new UnsupportedOperationException("Operation not supported");
        }
    }

    public static void main(String[] args) {
        Deque<String> deque = new Deque<>();
        while (!StdIn.isEmpty()) {
            String s = StdIn.readString();
            deque.addLast(s);
            deque.addFirst(s);
        }
        deque.addFirst(deque.removeFirst());
        deque.addLast(deque.removeLast());
        int size = deque.size();
        deque.addFirst(Integer.toString(size));

        for (String word : deque) {
            StdOut.print(word + " ");
        }
    }
}
