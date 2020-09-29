// I just wanted to practice TDD
package project.datastructures;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;


class QueueTest {
    Queue<Integer> intQueue;

    @BeforeEach
    void setUp() {
        intQueue = new Queue<Integer>();
    }

    @Test
    void createEmptyQueue() {
        assertTrue(intQueue.isEmpty());
    }

    @Test
    void notEmptyAfterEnqueue() {
        intQueue.enqueue(0);
        assertFalse(intQueue.isEmpty());
    }

    @Test
    void throwUnderflow_IfDequeued_WhileEmpty() {
        assertThrows(Queue.Underflow.class, () -> intQueue.dequeue());
    }

    @Test
    void isEmptyAfterEnqueueAndDequeue() {
        intQueue.enqueue(0);
        intQueue.dequeue();
        assertTrue(intQueue.isEmpty());
    }

    @Test
    void Enqueue_X_Dequeue_X() {
        intQueue.enqueue(11);
        assertEquals(intQueue.dequeue(), 11);
    }


    @Test
    void Dequeue_X_Y_Enqueue_X_Y() {
        intQueue.enqueue(11);
        intQueue.enqueue(22);
        assertEquals(intQueue.dequeue(), 11);
        assertEquals(intQueue.dequeue(), 22);
    }

    @Test
    void Dequeue_Ten_Enqueue_Ten() {
        int total = 10;
        for (int i = 0; i < total; i++)
            intQueue.enqueue(i);
        for (int i = 0; i < total; i++)
            assertEquals(i, intQueue.dequeue());
    }

}

