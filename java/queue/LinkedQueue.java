package queue;

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
}
