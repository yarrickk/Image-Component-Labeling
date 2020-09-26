package DataStructures;

import java.util.Iterator;

public class Queue<Item> implements Iterable<Item> {

    private int head = 0, tail = 0;
    public Item[] elements = (Item[]) new Object[100];

    public boolean isEmpty() {
        return size() == 0;
    }

    public int size() {
        return head - tail;
    }

    private void resize(int newLength) {
        Item[] aux = (Item[]) new Object[newLength];
        for (int i = 0; i < size(); i++)
            aux[i] = elements[tail + i];
        elements = aux;
        head = size();
        tail = 0;
    }

    public void enqueue(Item element) {
        if (head >= elements.length - 1)
            resize(elements.length * 2);
        elements[head++] = element;
    }

    public Item dequeue() throws Underflow {
        if (isEmpty()) throw new Underflow();
        Item result = elements[tail++];
        elements[tail - 1] = null;

        if (size() < elements.length / 4) {
            resize(elements.length / 2);
        }
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
            return pos < head - 1;
        }

        @Override
        public Item next() {
            return elements[pos++];
        }
    }

    @Override
    public String toString() {
        StringBuilder returnValue = new StringBuilder("Queue(");
        for (int i = tail; i < head; i++)
            returnValue.append(elements[i]).append(", ");

        returnValue.append(")");
        return returnValue.toString();
    }

    public static class Underflow extends RuntimeException {
    }


}
