package queue;

// Последовательность a = {a[0], a[1], ..., a[n - 1]}
// Для любого i из [0; n) a[i] != null
// n >= 0
public interface Queue {
    // Pre: arg != null
    // Post: a' = {a[0], ..., a[n - 1], arg} && n' = n + 1
    void enqueue(Object obj);

    // Pre: n > 0
    // Post: a' = {a[1], ..., a[n - 1]} && n' = n - 1
    //       R = a[0]
    Object dequeue();

    // Pre: n > 0
    // Post: a' = a && n' = n
    //       R = a[0]
    Object element();

    // Pre: true
    // Post: a' = a && n' = n
    //       R = n
    int size();

    // Pre: true
    // Post: a' = a && n' = n
    //       R = (n == 0)
    boolean isEmpty();

    // Pre: true
    // Post: a' = { } && n' = 0
    void clear();
}
