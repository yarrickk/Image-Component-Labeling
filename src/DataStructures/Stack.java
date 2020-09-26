package DataStructures;

import java.util.Iterator;

public class Stack<Item> implements Iterable<Item> {

    public Item[] elements = (Item[]) new Object[2];
    public int size = 0;

    public boolean isEmpty() {
        return size == 0;
    }

    private void resize(int newLength) {
        Item[] aux = (Item[]) new Object[newLength];
        for (int i = 0; i < size; i++)
            aux[i] = elements[i];
        elements = aux;
    }

    public void push(Item element) {
        if (size == elements.length)
            resize(elements.length * 2);
        this.elements[size++] = element;
    }

    public Item pop() throws Underflow {
        if (isEmpty())
            throw new Underflow();
        if (size <= elements.length / 4)
            resize(elements.length / 2);

        Item poppedElem = elements[--size];
        elements[size] = null;
        return poppedElem;
    }

    @Override
    public Iterator<Item> iterator() {
        return new reverseIterator();
    }

    private class reverseIterator implements Iterator<Item> {
        int pos = size;

        @Override
        public boolean hasNext() {
            return pos > 0;
        }

        @Override
        public Item next() {
            return elements[--pos];
        }
    }

    public static class Underflow extends RuntimeException {
    }

}
