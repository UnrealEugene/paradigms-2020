package queue;

public class ArrayQueueMyTest {
    public static void fillEnd(ArrayQueue queue) {
        for (int i = 0; i < 10; i++) {
            queue.enqueue(i);
        }
    }

    public static void fillBegin(ArrayQueue queue) {
        for (int i = 0; i < 10; i++) {
            queue.push(i);
        }
    }

    public static void dumpBegin(ArrayQueue queue) {
        while (!queue.isEmpty()) {
            System.out.println(queue.size() + " " + queue.element() + " " + queue.dequeue());
        }
    }

    public static void dumpEnd(ArrayQueue queue) {
        while (!queue.isEmpty()) {
            System.out.println(queue.size() + " " + queue.peek() + " " + queue.remove());
        }
    }

    public static void main(String[] args) {
        ArrayQueue queue = new ArrayQueue();
        fillEnd(queue);
        dumpEnd(queue);
        fillBegin(queue);
        dumpBegin(queue);
        fillEnd(queue);
        System.out.println(queue.size() + " " + queue.isEmpty());
        queue.clear();
        System.out.println(queue.size() + " " + queue.isEmpty());
    }
}
