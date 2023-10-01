/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class RandomizedQueue<Item> implements Iterable<Item> {

    private Item[] array;
    private int itemCount = 0;

    public RandomizedQueue() {
        array = (Item[]) new Object[1];
    }

    public int size() {
        return itemCount;
    }

    public boolean isEmpty() {
        return itemCount == 0;
    }

    public void enqueue(Item item) {
        if (item == null) throw new IllegalArgumentException("Argument can't be null");
        if (array.length == itemCount) resize(2 * array.length);
        array[itemCount] = item;
        itemCount++;
    }

    private void resize(int n) {
        int j = 0;
        Item[] newArray = (Item[]) new Object[n];
        for (int i = 0; i < array.length; i++) {
            if (array[i] != null) {
                newArray[j++] = array[i];
            }
        }
        array = newArray;
    }

    public Item sample() {
        if (itemCount == 0) throw new NoSuchElementException("No element in queue");
        resize(itemCount);
        int randomNumber = StdRandom.uniformInt(array.length);
        return array[randomNumber];
    }

    public Item dequeue() {
        if (itemCount == 0) throw new NoSuchElementException("No element in queue");
        if (array.length < itemCount / 4) resize(array.length / 2);
        Item item = null;
        int randomNumber = 0;
        while (item == null) {
            randomNumber = StdRandom.uniformInt(array.length);
            item = array[randomNumber];
        }
        array[randomNumber] = null;
        itemCount--;
        return item;
    }

    public Iterator<Item> iterator() {
        return new ListIterator();
    }

    private class ListIterator implements Iterator<Item> {
        int i = 0;
        Item[] newArray;

        ListIterator() {
            resize(itemCount);
            newArray = array;
            StdRandom.shuffle(newArray);
        }

        public boolean hasNext() {
            return i < newArray.length;
        }

        public void remove() {
            throw new UnsupportedOperationException("Operation not supported");
        }

        public Item next() {
            if (!hasNext()) throw new NoSuchElementException("No more elements");
            return newArray[i++];
        }
    }

    public static void main(String[] args) {
        int n = 5;
        RandomizedQueue<Integer> queue = new RandomizedQueue<Integer>();
        for (int i = 0; i < n; i++)
            queue.enqueue(i);
        for (int a : queue) {
            for (int b : queue)
                StdOut.print(a + "-" + b + " ");
            StdOut.println();
        }
        StdOut.println(queue.isEmpty());
        StdOut.println(queue.dequeue());
        StdOut.println(queue.sample());
        StdOut.println(queue.size());
    }

}
