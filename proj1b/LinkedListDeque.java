public class LinkedListDeque<T> implements Deque<T> {
    private class Node {
        private T item;
        private Node prev;
        private Node next;

        public Node(T item, Node prev, Node next) {
            this.item = item;
            this.prev = prev;
            this.next = next;
        }
    }

    private Node sentinal;
    private int size;

    public LinkedListDeque() {
        sentinal = new Node(null, null, null);
        sentinal.prev = sentinal;
        sentinal.next = sentinal;
        size = 0;
    }

    @Override
    public void addFirst(T item) {
        Node n = new Node(item, sentinal, sentinal.next);
        sentinal.next.prev = n;
        sentinal.next = n;
        size += 1;
    }

    @Override
    public void addLast(T item) {
        Node n = new Node(item, sentinal.prev, sentinal);
        sentinal.prev.next = n;
        sentinal.prev = n;
        size += 1;
    }

    @Override
    public T removeFirst() {
        if (isEmpty()) {
            return null;
        }
        T item = sentinal.next.item;
        sentinal.next = sentinal.next.next;
        sentinal.next.prev = sentinal;
        size--;
        return item;
    }

    @Override
    public T removeLast() {
        if (isEmpty()) {
            return null;
        }
        T item = sentinal.prev.item;
        sentinal.prev = sentinal.prev.prev;
        sentinal.prev.next = sentinal;
        size--;
        return item;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public void printDeque() {
        if (isEmpty()) {
            System.out.println();
            return;
        }
        Node cur = sentinal.next;
        while (cur != sentinal) {
            System.out.print(cur.item + " ");
            cur = cur.next;
        }
        System.out.println();
    }

    @Override
    public T get(int index) {
        if (index >= size) {
            return null;
        }
        Node cur = sentinal.next;
        for (int i = 0; i < index; ++i) {
            cur = cur.next;
        }
        return cur.item;
    }

    public T getRecursive(int index) {
        if (size == 0 || index >= size) {
            return null;
        }
        return getR(sentinal.next, index);
    }

    private T getR(Node n, int index) {
        if (index == 0) {
            return n.item;
        }
        return getR(n.next, index - 1);
    }
}
