package DataStructures;

import java.util.Iterator;

public class Queue<Item> implements Iterable<Item> {

    private int head = 0;
    private int tail = 0;
    public Item[] elements = (Item[]) new Object[1];

    public boolean isEmpty() {
        return head == 0;
    }

    private void resize(int newLength) {
        Item[] aux = (Item[]) new Object[newLength];
        for (int i = tail; i < head; i++)
            aux[i] = elements[tail + i];
        elements = aux;
        tail = 0;
    }

    public void enqueue(Item element) {
        if (head >= elements.length) resize(elements.length * 2);
        elements[head++] = element;
    }


    public Item dequeue() throws Underflow {
        if (isEmpty()) throw new Underflow();
        head--;

        Item result = elements[tail++];
        elements[tail - 1] = null;
        if (head <= elements.length / 4)
            resize(elements.length / 2);

        return result;
    }

    @Override
    public Iterator<Item> iterator() {
        return new QueueIterator();
    }

    private class QueueIterator implements Iterator<Item> {
        int pos = tail;

        @Override
        public boolean hasNext() {
            return pos < head;
        }

        @Override
        public Item next() {
            return elements[pos++];
        }
    }

    public static class Underflow extends RuntimeException {
    }
}
