package queue;

import java.util.Arrays;

// Последвательность a: a[1], a[2], ..., a[n]
// Операции с первым и последним элементом
// n >= 0
public class ArrayQueueModule {
    private static Object[] elements = new Object[2];
    private static int size;
    private static int start, end;

    // Pre: true
    // Post: n' = n + 1 && для любого i из [1; n]: a'[i] = a[i] && a'[n'] = arg
    public static void enqueue(Object arg) {
        if (size == elements.length) {
            expand();
        }
        size++;
        elements[end] = arg;
        end = (end + 1) % elements.length;
    }

    // Pre: n > 0
    // Post: n' = n - 1 && для любого i из [1; n']: a'[i] = a[i + 1]
    //       R = a[0]
    public static Object dequeue() {
        assert !isEmpty();
        Object ret = elements[start];
        elements[start] = null;
        start = (start + 1) % elements.length;
        size--;
        return ret;
    }

    // Pre: n > 0
    // Post: R = a[0] && n' = n && для любого i из [1; n']: a'[i] = a[i]
    public static Object element() {
        assert !isEmpty();
        return elements[start];
    }

    // Pre: true
    // Post: R = n' && n' = n && для любого i из [1; n']: a'[i] = a[i]
    public static int size() {
        return size;
    }

    // Pre: true
    // Post: R = (n' == 0) && n' = n && для любого i из [1; n']: a'[i] = a[i]
    public static boolean isEmpty() {
        return size() == 0;
    }

    // Pre: true
    // Post: n' = 0
    public static void clear() {
        while (!isEmpty()) {
            dequeue();
        }
    }


    // Pre: true
    // Post: true
    private static void expand() {
        elements = Arrays.copyOf(elements, 2 * elements.length);
        System.arraycopy(elements, start, elements,
                start + elements.length / 2, elements.length / 2 - start);
        System.arraycopy(new Object[elements.length / 2 - start], 0, elements,
                start, elements.length / 2 - start);
        start += elements.length / 2;
    }

    // Pre: true
    // Post: n' = n + 1 && для любого i из [1; n]: a'[i + 1] = a[i] && a'[0] = arg
    public static void push(Object arg) {
        if (size == elements.length) {
            expand();
        }
        size++;
        start = (start + elements.length - 1) % elements.length;
        elements[start] = arg;
    }


    // Pre: n > 0
    // Post: R = a[0] && n' = n && для любого i из [1; n']: a'[i] = a[i]
    public static Object peek() {
        assert !isEmpty();
        return elements[(end + elements.length - 1) % elements.length];
    }

    // Pre: n > 0
    // Post: n' = n - 1 && для любого i из [1; n']: a'[i + 1] = a[i]
    //       R = a[n]
    public static Object remove() {
        assert !isEmpty();
        end = (end + elements.length - 1) % elements.length;
        Object ret = elements[end];
        elements[end] = null;
        size--;
        return ret;
    }
}
