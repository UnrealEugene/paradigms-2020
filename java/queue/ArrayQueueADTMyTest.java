package queue;

import static queue.ArrayQueueADT.*;

public class ArrayQueueADTMyTest {
    public static void fillEnd(ArrayQueueADT queue) {
        for (int i = 0; i < 10; i++) {
            enqueue(queue, i);
        }
    }

    public static void fillBegin(ArrayQueueADT queue) {
        for (int i = 0; i < 10; i++) {
            push(queue, i);
        }
    }

    public static void dumpBegin(ArrayQueueADT queue) {
        while (!isEmpty(queue)) {
            System.out.println(size(queue) + " " + element(queue) + " " + dequeue(queue));
        }
    }

    public static void dumpEnd(ArrayQueueADT queue) {
        while (!isEmpty(queue)) {
            System.out.println(size(queue) + " " + peek(queue) + " " + remove(queue));
        }
    }

    public static void main(String[] args) {
        ArrayQueueADT queue = new ArrayQueueADT();
        fillEnd(queue);
        dumpEnd(queue);
        fillBegin(queue);
        dumpBegin(queue);
        fillEnd(queue);
        System.out.println(size(queue) + " " + isEmpty(queue));
        clear(queue);
        System.out.println(size(queue) + " " + isEmpty(queue));
    }
}
