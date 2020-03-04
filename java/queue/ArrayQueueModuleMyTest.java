package queue;

import static queue.ArrayQueueModule.*;

public class ArrayQueueModuleMyTest {
    public static void fillEnd() {
        for (int i = 0; i < 10; i++) {
            enqueue(i);
        }
    }

    public static void fillBegin() {
        for (int i = 0; i < 10; i++) {
            push(i);
        }
    }

    public static void dumpBegin() {
        while (!isEmpty()) {
            System.out.println(size() + " " + element() + " " + dequeue());
        }
    }

    public static void dumpEnd() {
        while (!isEmpty()) {
            System.out.println(size() + " " + peek() + " " + remove());
        }
    }

    public static void main(String[] args) {
        fillEnd();
        dumpEnd();
        fillBegin();
        dumpBegin();
        fillEnd();
        System.out.println(size() + " " + isEmpty());
        clear();
        System.out.println(size() + " " + isEmpty());
        fillBegin();
        System.out.println(get(5));
        set(5, 123);
        System.out.println(get(5));
    }
}
