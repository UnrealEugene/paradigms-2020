package queue;

import java.util.function.Predicate;

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

    // Pre: true
    // Post: ∀ j ∈ [0; k - 2]: i_j < i_(j+1) && ∀ j ∈ [0; k - 1]: i_j ∈ [0; n - 1],
    //       pred(a[i_j]) == true && ∀ t ∈ [0; n - 1]: ∀ j ∈ [0; k - 1] t != i_j =>
    //       pred(a[t]) == false && a' = {a[i_0], a[i_1], ..., a[i_(k-1)]} && n' = k
    void removeIf(Predicate<Object> pred);

    // Pre: true
    // Post: ∀ j ∈ [0; k - 2]: i_j < i_(j+1) && ∀ j ∈ [0; k - 1]: i_j ∈ [0; n - 1],
    //       pred(a[i_j]) == false && ∀ t ∈ [0; n - 1]: ∀ j ∈ [0; k - 1] t != i_j =>
    //       pred(a[t]) == true && a' = {a[i_0], a[i_1], ..., a[i_(k-1)]} && n' = k
    void retainIf(Predicate<Object> pred);

    // Pre: true
    // Post: k = max i ∈ [0; n - 1]: ∀ j ∈ [0; k - 1] pred(a[j]) == true &&
    // a' = {a[0], a[1], ..., a[k - 1]} && n' = k
    void takeWhile(Predicate<Object> pred);

    // Pre: true
    // Post: k = max i ∈ [0; n - 1]: ∀ j ∈ [0; k - 1] pred(a[j]) == false &&
    // a' = {a[k], a[k+1], ..., a[n - 1]} && n' = n - k
    void dropWhile(Predicate<Object> pred);
}
