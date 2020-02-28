package queue;

public class ArrayQueueModuleMyTest {
    public static void fillEnd() {
        for (int i = 0; i < 10; i++) {
            ArrayQueueModule.enqueue(i);
        }
    }

    public static void fillBegin() {
        for (int i = 0; i < 10; i++) {
            ArrayQueueModule.push(i);
        }
    }

    public static void dumpBegin() {
        while (!ArrayQueueModule.isEmpty()) {
            System.out.println(ArrayQueueModule.size() + " " + ArrayQueueModule.element() + " " + ArrayQueueModule.dequeue());
        }
    }

    public static void dumpEnd() {
        while (!ArrayQueueModule.isEmpty()) {
            System.out.println(ArrayQueueModule.size() + " " + ArrayQueueModule.peek() + " " + ArrayQueueModule.remove());
        }
    }

    public static void main(String[] args) {
        fillEnd();
        dumpEnd();
        fillBegin();
        dumpBegin();
        fillEnd();
        System.out.println(ArrayQueueModule.size() + " " + ArrayQueueModule.isEmpty());
        ArrayQueueModule.clear();
        System.out.println(ArrayQueueModule.size() + " " + ArrayQueueModule.isEmpty());
    }
}
