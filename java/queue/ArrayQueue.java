package queue;

import java.util.Arrays;
import java.util.function.Predicate;

// Последовательность a = {a[0], a[1], ..., a[n - 1]}
// Для любого i из [0; n) a[i] != null
// n >= 0
public class ArrayQueue extends AbstractQueue {
    private int size;
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
//        size++;
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
//        size--;
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
//    public int size() {
//        return size;
//    }
//
//    // Pre: true
//    // Post: a' = a && n' = n
//    //       R = (n == 0)
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
//        size = 0;
        super.clear();
    }

    // Pre: true
    // Post: ∀ j ∈ [0; k - 2]: i_j < i_(j+1) && ∀ j ∈ [0; k - 1]: i_j ∈ [0; n - 1],
    //       pred(a[i_j]) == true && ∀ t ∈ [0; n - 1]: ∀ j ∈ [0; k - 1] t != i_j =>
    //       pred(a[t]) == false && a' = {a[i_0], a[i_1], ..., a[i_(k-1)]} && n' = k
    @Override
    public void removeIf(Predicate<Object> pred) {
        Object[] newElements = new Object[elements.length];
        size = 0;
        for (int i = head; i != tail; i = (i + 1) % elements.length) {
            if (!pred.test(elements[i])) { // keep
                newElements[size++] = elements[i];
            }
        }
        head = 0;
        tail = size;
        elements = newElements;
    }

    // Pre: true
    // Post: ∀ j ∈ [0; k - 2]: i_j < i_(j+1) && ∀ j ∈ [0; k - 1]: i_j ∈ [0; n - 1],
    //       pred(a[i_j]) == false && ∀ t ∈ [0; n - 1]: ∀ j ∈ [0; k - 1] t != i_j =>
    //       pred(a[t]) == true && a' = {a[i_0], a[i_1], ..., a[i_(k-1)]} && n' = k
    @Override
    public void retainIf(Predicate<Object> pred) {
        Object[] newElements = new Object[elements.length];
        size = 0;
        for (int i = head; i != tail; i = (i + 1) % elements.length) {
            if (pred.test(elements[i])) { // keep
                newElements[size++] = elements[i];
            }
        }
        head = 0;
        tail = size;
        elements = newElements;
    }

    // Pre: true
    // Post: k = max i ∈ [0; n - 1]: ∀ j ∈ [0; k - 1] pred(a[j]) == true &&
    // a' = {a[0], a[1], ..., a[k - 1]} && n' = k
    @Override
    public void takeWhile(Predicate<Object> pred) {
        boolean del = false;
        Object[] newElements = new Object[elements.length];
        size = 0;
        for (int i = head; i != tail; i = (i + 1) % elements.length) {
            if (!del && pred.test(elements[i])) { // keep
                newElements[size++] = elements[i];
            } else {
                del = true;
            }
        }
        head = 0;
        tail = size;
        elements = newElements;
    }

    // Pre: true
    // Post: k = max i ∈ [0; n - 1]: ∀ j ∈ [0; k - 1] pred(a[j]) == false &&
    // a' = {a[k], a[k+1], ..., a[n - 1]} && n' = n - k
    @Override
    public void dropWhile(Predicate<Object> pred) {
        boolean del = true;
        Object[] newElements = new Object[elements.length];
        size = 0;
        for (int i = head; i != tail; i = (i + 1) % elements.length) {
            if (!del || pred.test(elements[i])) { // keep
                del = false;
                newElements[size++] = elements[i];
            }
        }
        head = 0;
        tail = size;
        elements = newElements;
    }

    // Pre: true
    // Post: a' = a && n' = n && elements'.length == elements.length * 2
    private void expand() {
        elements = Arrays.copyOf(elements, 2 * elements.length);
        System.arraycopy(elements, head, elements,
                head + elements.length / 2, elements.length / 2 - head);
        Arrays.fill(elements, head, elements.length / 2, null);
        head += elements.length / 2;
    }



//    // Pre: arg != null
//    // Post: a' = {arg, a[0], ..., a[n - 1]} && n' = n + 1
//    public void push(Object arg) {
//        assert arg != null;
//        if (size == elements.length) {
//            expand();
//        }
//        size++;
//        head = (head + elements.length - 1) % elements.length;
//        elements[head] = arg;
//    }
//
//    // Pre: n > 0
//    // Post: a' = a && n' = n
//    //       R = a[n - 1]
//    public Object peek() {
//        assert !isEmpty();
//        return elements[(tail + elements.length - 1) % elements.length];
//    }
//
//    // Pre: n > 0
//    // Post: a' = {a[0], ..., a[n - 2]} && n' = n - 1
//    //       R = a[n - 1]
//    public Object remove() {
//        assert !isEmpty();
//        tail = (tail + elements.length - 1) % elements.length;
//        Object ret = elements[tail];
//        elements[tail] = null;
//        size--;
//        return ret;
//    }
//
//    // Pre: 0 <= index && index < n && n > 0
//    // Post: a' = a && n' = n
//    //       R = a[index]
//    public Object get(int index) {
//        assert 0 <= index && index < size;
//        return elements[(head + index) % elements.length];
//    }
//
//    // Pre: 0 <= index && index < n && n > 0 && value != null
//    // Post: a = {..., a[index - 1], value, a[index + 1], ...} && n' = n
//    public void set(int index, Object value) {
//        assert 0 <= index && index < size && value != null;
//        elements[(head + index) % elements.length] = value;
//    }
}
