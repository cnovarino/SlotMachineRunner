package helpers;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CounterTest {

    @Test
    void get() {
        Counter<Object> counter = new Counter<>();

        Object obj1 = new Object();
        Object obj2 = new Object();

        counter.count(obj1);
        counter.count(obj1);

        assertEquals(2,counter.get(obj1));
        assertEquals(0,counter.get(obj2));

    }

    @Test
    void count() {
        Counter<Object> counter = new Counter<>();

        Object obj1 = new Object();
        Object obj2 = new Object();

        counter.count(obj1);
        counter.count(obj1);

        assertEquals(2,counter.get(obj1));
        assertEquals(0,counter.get(obj2));
    }

    @Test
    void getMostCommon() {
        Counter<Object> counter = new Counter<>();

        Object obj1 = new Object();
        Object obj2 = new Object();

        counter.count(obj1);
        counter.count(obj1);
        counter.count(obj2);

        assertEquals(obj1,counter.getMostCommon());
    }
}