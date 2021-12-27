public class LinkedListDeque<T> {
    private class Node {
        public T item;
        public Node prev;
        public Node next;
        public Node(T i) {
            item = i;
            prev = next = null;
        }

        T getRecursive(int index) {
            if(index == 0){
                return item;
            }
            return next.getRecursive(index - 1);
        }
    }

    private Node sentinal;
    private int size;

    public LinkedListDeque() {
        sentinal = new Node(null);
        sentinal.prev = sentinal;
        sentinal.next = sentinal;
        size = 0;
    }

//    public LinkedListDeque(LinkedListDeque<T> other) {
//        sentinal = new Node(null);
//        sentinal.prev = sentinal;
//        sentinal.next = sentinal;
//        size=0;
//
//        for(int i = 0;i < other.size(); i++) {
//            addLast(other.get(i));
//        }
//    }

    public void addFirst(T item) {
        Node n = new Node(item);
        n.next = sentinal.next;
        sentinal.next.prev = n;
        n.prev = sentinal;
        sentinal.next = n;
        size++;
    }

    public void addLast(T item) {
        Node n = new Node(item);
        n.prev = sentinal.prev;
        sentinal.prev.next = n;
        n.next = sentinal;
        sentinal.prev = n;
        size++;
    }

    public T removeFirst() {
        if(isEmpty()) {
            return null;
        }
        T item = sentinal.next.item;
        sentinal.next = sentinal.next.next;
        sentinal.next.prev = sentinal;
        size--;
        return item;
    }

    public T removeLast() {
        if(isEmpty()) {
            return null;
        }
        T item = sentinal.prev.item;
        sentinal.prev = sentinal.prev.prev;
        sentinal.prev.next = sentinal;
        size--;
        return item;
    }

    public boolean isEmpty(){
        return sentinal.next==sentinal;
    }

    public int size(){
        return size;
    }

    public void printDeque() {
        if (isEmpty()){
            System.out.println();
            return;
        }
        Node cur = sentinal.next;
        while(cur != sentinal){
            System.out.print(cur.item+" ");
            cur = cur.next;
        }
        System.out.println();
    }

    public T get(int index) {
        if(index >= size) {
            return null;
        }
        Node cur = sentinal.next;
        for(int i = 0;i < index;++i) {
            cur=cur.next;
        }
        return cur.item;
    }

    public T getRecursive(int index) {
        if(size == 0 || index >= size) {
            return null;
        }
        return sentinal.next.getRecursive(index);
    }

    private T get(Node n, int index) {
        if(index == 0) {
            return n.item;
        }
        return get(n.next, index-1);
    }
}