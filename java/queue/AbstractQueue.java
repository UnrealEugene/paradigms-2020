package queue;

// Последовательность a = {a[0], a[1], ..., a[n - 1]}
// Для любого i из [0; n) a[i] != null
// n >= 0
public abstract class AbstractQueue implements Queue {
    protected int size = 0;

    // Pre: true
    // Post: a' = a && n' = n
    //       R = n
    public int size() {
        return size;
    }

    // Pre: true
    // Post: a' = a && n' = n
    //       R = (n == 0)
    public boolean isEmpty() {
        return size == 0;
    }

    public void enqueue(Object arg) {
        size++;
    }

    public Object dequeue() {
        size--;
        return null;
    }

    public void clear() {
        size = 0;
    }
}
