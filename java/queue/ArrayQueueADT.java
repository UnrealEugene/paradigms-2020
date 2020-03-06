package queue;

import java.util.Arrays;

// Последовательность a = {a[0], a[1], ..., a[n - 1]}
// Для любого i из [0; n) a[i] != null
// n >= 0
public class ArrayQueueADT {
    private Object[] elements = new Object[2];
    private int size;
    private int head, end;

    // Pre: arg != null && queue != null
    // Post: a' = {a[0], ..., a[n - 1], arg} && n' = n + 1
    public static void enqueue(ArrayQueueADT queue, Object arg) {
        assert arg != null && queue != null;
        if (queue.size == queue.elements.length) {
            expand(queue);
        }
        queue.size++;
        queue.elements[queue.end] = arg;
        queue.end = (queue.end + 1) % queue.elements.length;
    }

    // Pre: n > 0 && queue != null
    // Post: a' = {a[1], ..., a[n - 1]} && n' = n - 1
    //       R = a[0]
    public static Object dequeue(ArrayQueueADT queue) {
        assert !isEmpty(queue);
        Object ret = queue.elements[queue.head];
        queue.elements[queue.head] = null;
        queue.head = (queue.head + 1) % queue.elements.length;
        queue.size--;
        return ret;
    }

    // Pre: n > 0 && queue != null
    // Post: a' = a && n' = n
    //       R = a[0]
    public static Object element(ArrayQueueADT queue) {
        assert !isEmpty(queue);
        return queue.elements[queue.head];
    }

    // Pre: queue != null
    // Post: a' = a && n' = n
    //       R = n
    public static int size(ArrayQueueADT queue) {
        assert queue != null;
        return queue.size;
    }

    // Pre: queue != null
    // Post: a' = a && n' = n
    //       R = (n == 0)
    public static boolean isEmpty(ArrayQueueADT queue) {
        assert queue != null;
        return size(queue) == 0;
    }

    // Pre: queue != null
    // Post: a' = { } && n' = 0
    public static void clear(ArrayQueueADT queue) {
        assert queue != null;
        queue.elements = new Object[2];
        queue.size = 0;
    }

    // Pre: queue != null
    // Post: a' = a && n' = n
    private static void expand(ArrayQueueADT queue) {
        assert queue != null;
        queue.elements = Arrays.copyOf(queue.elements, 2 * queue.elements.length);
        System.arraycopy(queue.elements, queue.head, queue.elements,
                queue.head + queue.elements.length / 2, queue.elements.length / 2 - queue.head);
        System.arraycopy(new Object[queue.elements.length / 2 - queue.head], 0, queue.elements,
                queue.head, queue.elements.length / 2 - queue.head);
        queue.head += queue.elements.length / 2;
    }

    // Pre: arg != null && queue != null
    // Post: a' = {arg, a[0], ..., a[n - 1]} && n' = n + 1
    public static void push(ArrayQueueADT queue, Object arg) {
        assert arg != null && queue != null;
        if (queue.size == queue.elements.length) {
            expand(queue);
        }
        queue.size++;
        queue.head = (queue.head + queue.elements.length - 1) % queue.elements.length;
        queue.elements[queue.head] = arg;
    }

    // Pre: n > 0 && queue != null
    // Post: a' = a && n' = n
    //       R = a[n - 1]
    public static Object peek(ArrayQueueADT queue) {
        assert !isEmpty(queue);
        return queue.elements[(queue.end + queue.elements.length - 1) % queue.elements.length];
    }

    // Pre: n > 0 && queue != null
    // Post: a' = {a[0], ..., a[n - 2]} && n' = n - 1
    //       R = a[n - 1]
    public static Object remove(ArrayQueueADT queue) {
        assert !isEmpty(queue);
        queue.end = (queue.end + queue.elements.length - 1) % queue.elements.length;
        Object ret = queue.elements[queue.end];
        queue.elements[queue.end] = null;
        queue.size--;
        return ret;
    }

    // Pre: 0 <= index && index < n && n > 0 && queue != null
    // Post: a' = a && n' = n
    //       R = a[index]
    public static Object get(ArrayQueueADT queue, int index) {
        assert queue != null && 0 <= index && index < queue.size;
        return queue.elements[(queue.head + index) % queue.elements.length];
    }

    // Pre: 0 <= index && index < n && n > 0 && value != null && queue != null
    // Post: a = {..., a[index - 1], value, a[index + 1], ...} && n' = n
    public static void set(ArrayQueueADT queue, int index, Object value) {
        assert queue != null && 0 <= index && index < queue.size;
        queue.elements[(queue.head + index) % queue.elements.length] = value;
    }
}
