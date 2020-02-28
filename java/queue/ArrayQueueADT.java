package queue;

import java.util.Arrays;

// Последвательность a: a[1], a[2], ..., a[n]
// Операции с первым и последним элементом
// n >= 0
public class ArrayQueueADT {
    private Object[] elements = new Object[2];
    private int size;
    private int start, end;

    // Pre: true
    // Post: n' = n + 1 && для любого i из [1; n]: a'[i] = a[i] && a'[n'] = arg
    public static void enqueue(ArrayQueueADT queue, Object arg) {
        if (queue.size == queue.elements.length) {
            expand(queue);
        }
        queue.size++;
        queue.elements[queue.end] = arg;
        queue.end = (queue.end + 1) % queue.elements.length;
    }

    // Pre: n > 0
    // Post: n' = n - 1 && для любого i из [1; n']: a'[i] = a[i + 1]
    //       R = a[0]
    public static Object dequeue(ArrayQueueADT queue) {
        assert !isEmpty(queue);
        Object ret = queue.elements[queue.start];
        queue.elements[queue.start] = null;
        queue.start = (queue.start + 1) % queue.elements.length;
        queue.size--;
        return ret;
    }

    // Pre: n > 0
    // Post: R = a[0] && n' = n && для любого i из [1; n']: a'[i] = a[i]
    public static Object element(ArrayQueueADT queue) {
        assert !isEmpty(queue);
        return queue.elements[queue.start];
    }

    // Pre: true
    // Post: R = n' && n' = n && для любого i из [1; n']: a'[i] = a[i]
    public static int size(ArrayQueueADT queue) {
        return queue.size;
    }

    // Pre: true
    // Post: R = (n' == 0) && n' = n && для любого i из [1; n']: a'[i] = a[i]
    public static boolean isEmpty(ArrayQueueADT queue) {
        return size(queue) == 0;
    }

    // Pre: true
    // Post: n' = 0
    public static void clear(ArrayQueueADT queue) {
        while (!isEmpty(queue)) {
            dequeue(queue);
        }
    }

    // Pre: true
    // Post: true
    private static void expand(ArrayQueueADT queue) {
        queue.elements = Arrays.copyOf(queue.elements, 2 * queue.elements.length);
        System.arraycopy(queue.elements, queue.start, queue.elements,
                queue.start + queue.elements.length / 2, queue.elements.length / 2 - queue.start);
        System.arraycopy(new Object[queue.elements.length / 2 - queue.start], 0, queue.elements,
                queue.start, queue.elements.length / 2 - queue.start);
        queue.start += queue.elements.length / 2;
    }

    // Pre: true
    // Post: n' = n + 1 && для любого i из [1; n]: a'[i + 1] = a[i] && a'[0] = arg
    public static void push(ArrayQueueADT queue, Object arg) {
        if (queue.size == queue.elements.length) {
            expand(queue);
        }
        queue.size++;
        queue.start = (queue.start + queue.elements.length - 1) % queue.elements.length;
        queue.elements[queue.start] = arg;
    }


    // Pre: n > 0
    // Post: R = a[0] && n' = n && для любого i из [1; n']: a'[i] = a[i]
    public static Object peek(ArrayQueueADT queue) {
        assert !isEmpty(queue);
        return queue.elements[(queue.end + queue.elements.length - 1) % queue.elements.length];
    }

    // Pre: n > 0
    // Post: n' = n - 1 && для любого i из [1; n']: a'[i + 1] = a[i]
    //       R = a[n]
    public static Object remove(ArrayQueueADT queue) {
        assert !isEmpty(queue);
        queue.end = (queue.end + queue.elements.length - 1) % queue.elements.length;
        Object ret = queue.elements[queue.end];
        queue.elements[queue.end] = null;
        queue.size--;
        return ret;
    }
}
