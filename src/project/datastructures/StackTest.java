package project.datastructures;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class StackTest {
    Stack<Integer> integerStack;
    Stack<String> stringStack;


    @BeforeEach
    void setUp() {
        integerStack = new Stack<Integer>();
        stringStack = new Stack<String>();
    }

    @Test
    void createStack_thatIsEmpty() {
        assertTrue(integerStack.isEmpty());
    }

    @Test
    void afterOnePush_isNotEmpty() {
        integerStack.push(0);
        assertFalse(integerStack.isEmpty());
    }

    @Test()
    void popWhenEmpty_throwsUnderflow() {
        assertThrows(Stack.Underflow.class, () -> integerStack.pop());
    }

    @Test
    void afterPush_X_pop_X() {
        integerStack.push(99);
        assertEquals(99, integerStack.pop());
    }

    @Test
    void afterOnePushOnePop_WillBeEmpty() {
        integerStack.push(22);
        integerStack.pop();
        assertTrue(integerStack.isEmpty());
    }

    @Test
    void afterTwoPushesAndOnePop_WillNotBeEmpty() {
        integerStack.push(22);
        integerStack.push(33);
        integerStack.pop();
        assertFalse(integerStack.isEmpty());
    }

    @Test
    void afterPush_X_Y_willPop_Y_X() {
        integerStack.push(1);
        integerStack.push(2);
        assertEquals(2, integerStack.pop());
        assertEquals(1, integerStack.pop());
    }

    @Test
    void pushPopString() {
        stringStack.push("hello");
        assertEquals("hello", stringStack.pop());
    }

    @Test
    void testIteration() {
        stringStack.push("1");
        stringStack.push("2");
        StringBuilder result = new StringBuilder();

        for (Object elem : stringStack)
            result.append(elem.toString());
        assertEquals("21", result.toString());
    }

}












