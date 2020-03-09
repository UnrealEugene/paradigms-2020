package queue;

import java.util.function.Predicate;

// Последовательность a = {a[0], a[1], ..., a[n - 1]}
// Для любого i из [0; n) a[i] != null
// n >= 0
public class LinkedQueue extends AbstractQueue {
    Node head = null;
    Node tail = null;

    private static class Node {
        Object val;
        Node next;

        public Node(Object val, Node next) {
            this.val = val;
            this.next = next;
        }
    }

    // Pre: arg != null
    // Post: a' = {a[0], ..., a[n - 1], arg} && n' = n + 1
    @Override
    public void enqueue(Object arg) {
        Node t = new Node(arg, null);
        if (tail == null) {
            head = t;
        } else {
            tail.next = t;
        }
        tail = t;
        super.add();
    }

    // Pre: n > 0
    // Post: a' = {a[1], ..., a[n - 1]} && n' = n - 1
    //       R = a[0]
    @Override
    public Object dequeue() {
        assert !isEmpty();
        Object result = head.val;
        head = head.next;
        if (head == null) {
            tail = null;
        }
        super.delete();
        return result;
    }

    // Pre: n > 0
    // Post: a' = a && n' = n
    //       R = a[0]
    @Override
    public Object element() {
        assert !isEmpty();
        return head.val;
    }

    // Pre: true
    // Post: a' = { } && n' = 0
    @Override
    public void clear() {
        head = null;
        tail = null;
        super.clear();
    }

    private void remove(Node node, Node prev) {
        assert node != null;
        if (prev != null) {
            prev.next = node.next;
        }
        if (node == head) {
            head = node.next;
            if (head == null) {
                tail = null;
            }
        } else if (node == tail) {
            tail = prev;
            if (tail == null) {
                head = null;
            }
        }
        super.delete();
    }

    // Pre: true
    // Post: ∀ j ∈ [0; k - 2]: i_j < i_(j+1) && ∀ j ∈ [0; k - 1]: i_j ∈ [0; n - 1],
    //       pred(a[i_j]) == true && ∀ t ∈ [0; n - 1]: ∀ j ∈ [0; k - 1] t != i_j =>
    //       pred(a[t]) == false && a' = {a[i_0], a[i_1], ..., a[i_(k-1)]} && n' = k
    @Override
    public void removeIf(Predicate<Object> pred) {
        for (Node cur = head, prev = null; cur != null; cur = cur.next) {
            if (pred.test(cur.val)) {
                remove(cur, prev);
            } else {
                prev = cur;
            }
        }
    }
}
