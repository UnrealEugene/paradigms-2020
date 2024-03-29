package queue;

import java.util.function.Predicate;

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

    // Pre: true
    // Post: a' = { } && n' = 0
    public void clear() {
        size = 0;
    }

    // Pre: n > 0
    // Post: n' = n - 1
    protected void delete() {
        size--;
    }

    // Pre: true
    // Post: n' = n + 1
    protected void add() {
        size++;
    }

    // Pre: true
    // Post: ∀ j ∈ [0; k - 2]: i_j < i_(j+1) && ∀ j ∈ [0; k - 1]: i_j ∈ [0; n - 1],
    //       pred(a[i_j]) == true && ∀ t ∈ [0; n - 1]: ∀ j ∈ [0; k - 1] t != i_j =>
    //       pred(a[t]) == false && a' = {a[i_0], a[i_1], ..., a[i_(k-1)]} && n' = k
    public void removeIf(Predicate<Object> pred) {
        int tempSize = size;
        for (int i = 0; i < tempSize; i++) {
            Object tmp = dequeue();
            if (!pred.test(tmp)) {
                enqueue(tmp);
            }
        }
    }

    // Pre: true
    // Post: ∀ j ∈ [0; k - 2]: i_j < i_(j+1) && ∀ j ∈ [0; k - 1]: i_j ∈ [0; n - 1],
    //       pred(a[i_j]) == false && ∀ t ∈ [0; n - 1]: ∀ j ∈ [0; k - 1] t != i_j =>
    //       pred(a[t]) == true && a' = {a[i_0], a[i_1], ..., a[i_(k-1)]} && n' = k
    public void retainIf(Predicate<Object> pred) {
        removeIf(Predicate.not(pred));
    }

    private void processOnPrefix(Predicate<Object> pred, boolean keep) {
        int tempSize = size;
        boolean validPref = true;
        for (int i = 0; i < tempSize; i++) {
            Object tmp = dequeue();
            if (validPref && ((pred.test(tmp) && keep) || (!pred.test(tmp) && !keep))) {
                if (keep) {
                    enqueue(tmp);
                }
            } else {
                validPref = false;
                if (!keep) {
                    enqueue(tmp);
                }
            }
        }
    }

    // Pre: pred != null
    // Post: k = max i ∈ [0; n - 1]: ∀ j ∈ [0; k - 1] pred(a[j]) == true &&
    // a' = {a[0], a[1], ..., a[k - 1]} && n' = k
    public void takeWhile(Predicate<Object> pred) {
        processOnPrefix(pred, true);
    }

    // Pre: pred != null
    // Post: k = max i ∈ [0; n - 1]: ∀ j ∈ [0; k - 1] pred(a[j]) == false &&
    // a' = {a[k], a[k+1], ..., a[n - 1]} && n' = n - k
    public void dropWhile(Predicate<Object> pred) {
        processOnPrefix(Predicate.not(pred), false);
    }
}
