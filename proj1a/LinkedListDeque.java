public class LinkedListDeque<T> {

    private class Node {
        private Node prev;
        private T item;
        private Node next;

        public Node(Node a, T i, Node b) {
            prev = a;
            item = i;
            next = b;
        }

    }

    private Node sentinel;
    private int size;

    public LinkedListDeque() {
        sentinel = new Node(null, null, null);
        sentinel.prev = sentinel;
        sentinel.next = sentinel;
        size = 0;
    }

    public void addFirst(T item) {
        Node first = new Node(sentinel, item, sentinel.next);
        first.next.prev = first;
        sentinel.next = first;
        size += 1;
    }

    public void addLast(T item) {
        Node last = new Node(sentinel.prev, item, sentinel);
        sentinel.prev = last;
        last.prev.next = last;
        size += 1;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public int size() {
        return size;
    }

    public void printDeque() {
        Node p = sentinel.next;
        while (p.next != sentinel) {
            System.out.println(p.item + " ");
            p = p.next;
        }
        System.out.println("\n");
    }

    public T removeFirst() {
        if (size == 0) {
            return null;
        } else {
            Node first = sentinel.next;
            T value = first.item;
            sentinel.next = sentinel.next.next;
            if (size == 1) {
                sentinel.prev = sentinel;
            }
            sentinel.next.prev = sentinel;        /* modify the prev of second node */
            first.prev = null;                    /* erase pointers of then-first node */
            first.next = null;
            first = null;
            size -= 1;
            return value;
        }
    }

    public T removeLast() {
        if (size == 0) {
            return null;
        } else {
            Node last = sentinel.prev;
            T value = last.item;
            sentinel.prev = sentinel.prev.prev;
            if (size == 1) {
                sentinel.next = sentinel;
            }
            sentinel.prev.next = sentinel;
            last.prev = null;
            last.next = null;
            last = null;
            size -= 1;
            return value;
        }
    }

    public T get(int index) {
        if (size - 1 < index) {
            return null;
        } else {
            Node p = sentinel;
            int i = 0;
            while (i <= index) {
                p = p.next;
                i += 1;
            }
            return p.item;
        }
    }

    public T getRecursive(int index) {
        if (size - 1 < index) {
            return null;
        }
        if (index == 0) {
            return sentinel.next.item;
        } else {
            LinkedListDeque<T> helper = new LinkedListDeque();
            helper.sentinel = sentinel.next;
            helper.size = size - 1;
            return helper.getRecursive(index - 1);
        }
    }
}
