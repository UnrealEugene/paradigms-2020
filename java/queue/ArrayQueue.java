package queue;

import java.util.Arrays;

// Последовательность a = {a[0], a[1], ..., a[n - 1]}
// Для любого i из [0; n) a[i] != null
// n >= 0
public class ArrayQueue extends AbstractQueue {
    private Object[] elements = new Object[2];
    private int head, tail;

    // Pre: arg != null
    // Post: a' = {a[0], ..., a[n - 1], arg} && n' = n + 1
    @Override
    public void enqueue(Object arg) {
        assert arg != null;
        if (size == elements.length) {
            expand();
        }
        elements[tail] = arg;
        tail = (tail + 1) % elements.length;
        // size++;
        super.enqueue(arg);
    }

    // Pre: n > 0
    // Post: a' = {a[1], ..., a[n - 1]} && n' = n - 1
    //       R = a[0]
    @Override
    public Object dequeue() {
        assert !isEmpty();
        Object ret = elements[head];
        elements[head] = null;
        head = (head + 1) % elements.length;
        // size--;
        super.dequeue();
        return ret;
    }

    // Pre: n > 0
    // Post: a' = a && n' = n
    //       R = a[0]
    @Override
    public Object element() {
        assert !isEmpty();
        return elements[head];
    }

//    // Pre: true
//    // Post: a' = a && n' = n
//    //       R = n
//    @Override
//    public int size() {
//        return size;
//    }

//    // Pre: true
//    // Post: a' = a && n' = n
//    //       R = (n == 0)
//    @Override
//    public boolean isEmpty() {
//        return size() == 0;
//    }

    // Pre: true
    // Post: a' = { } && n' = 0
    @Override
    public void clear() {
        elements = new Object[2];
        head = 0;
        tail = 0;
        super.clear();
    }

    // Pre: true
    // Post: a' = a && n' = n
    private void expand() {
        elements = Arrays.copyOf(elements, 2 * elements.length);
        System.arraycopy(elements, head, elements,
                head + elements.length / 2, elements.length / 2 - head);
        System.arraycopy(new Object[elements.length / 2 - head], 0, elements,
                head, elements.length / 2 - head);
        head += elements.length / 2;
    }

    // Pre: arg != null
    // Post: a' = {arg, a[0], ..., a[n - 1]} && n' = n + 1
    public void push(Object arg) {
        assert arg != null;
        if (size == elements.length) {
            expand();
        }
        size++;
        head = (head + elements.length - 1) % elements.length;
        elements[head] = arg;
    }

    // Pre: n > 0
    // Post: a' = a && n' = n
    //       R = a[n - 1]
    public Object peek() {
        assert !isEmpty();
        return elements[(tail + elements.length - 1) % elements.length];
    }

    // Pre: n > 0
    // Post: a' = {a[0], ..., a[n - 2]} && n' = n - 1
    //       R = a[n - 1]
    public Object remove() {
        assert !isEmpty();
        tail = (tail + elements.length - 1) % elements.length;
        Object ret = elements[tail];
        elements[tail] = null;
        size--;
        return ret;
    }

    // Pre: 0 <= index && index < n && n > 0
    // Post: a' = a && n' = n
    //       R = a[index]
    public Object get(int index) {
        assert 0 <= index && index < size;
        return elements[(head + index) % elements.length];
    }

    // Pre: 0 <= index && index < n && n > 0 && value != null
    // Post: a = {..., a[index - 1], value, a[index + 1], ...} && n' = n
    public void set(int index, Object value) {
        assert 0 <= index && index < size;
        elements[(head + index) % elements.length] = value;
    }
}
