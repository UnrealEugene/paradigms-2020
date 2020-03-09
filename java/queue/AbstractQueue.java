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
    //       pred(a[i_j]) == false && ∀ t ∈ [0; n - 1]: ∀ j ∈ [0; k - 1] t != i_j =>
    //       pred(a[t]) == true && a' = {a[i_0], a[i_1], ..., a[i_(k-1)]} && n' = k
    public void retainIf(Predicate<Object> pred) {
        removeIf(Predicate.not(pred));
    }

    // Pre: true
    // Post: k = max i ∈ [0; n - 1]: ∀ j ∈ [0; k - 1] pred(a[j]) == true &&
    // a' = {a[0], a[1], ..., a[k - 1]} && n' = k
    public void takeWhile(Predicate<Object> pred) {
        boolean[] delete = {false};
        removeIf(x -> delete[0] |= !pred.test(x));
    }

    // Pre: true
    // Post: k = max i ∈ [0; n - 1]: ∀ j ∈ [0; k - 1] pred(a[j]) == false &&
    // a' = {a[k], a[k+1], ..., a[n - 1]} && n' = n - k
    public void dropWhile(Predicate<Object> pred) {
        boolean[] delete = {true};
        removeIf(x -> delete[0] &= pred.test(x));
    }
}
