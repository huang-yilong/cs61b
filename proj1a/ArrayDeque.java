public class ArrayDeque<T> {
    private T[] items;
    private int size;
    private int start;

    public ArrayDeque() {
        items = (T[]) new Object[8];
        size = 0;
        start = 4;
    }

//    public ArrayDeque(ArrayDeque<T> other) {
//        items = (T[]) new Object[other.size()];
//        System.arraycopy(other.items, 0, items, 0, size);
//        size = other.size();
//    }

    public void addFirst(T item) {
        if(start == 0){
            resize(2 * size, start, size/2, size);
        }
        start = size / 2;
        items[--start] = item;
        size++;
    }

    private void resize(int capacity, int srcPos, int desPos, int len) {
        T[] a = (T[]) new Object[capacity];
        System.arraycopy(items, srcPos, a, desPos, len);
        items = a;
    }

    public void addLast(T item) {
        if(start + size == items.length){
            resize(size * 2, start, size / 2, size);
        }
        start = size / 2;
        items[start + size] = item;
        size++;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public int size(){
        return size;
    }

    public void printDeque() {
        for(int i = 0; i < size; ++i){
            System.out.print(items[start + i] + " ");
        }
        System.out.println();
    }

    public T removeFirst() {
        if(isEmpty()) {
            return null;
        }
        T item = items[start];
        start++;
        size--;
        return item;
    }

    public T removeLast() {
        if(isEmpty()) {
            return null;
        }
        T item = items[start + size - 1];
        size--;
        return item;
    }

    public T get(int index) {
        if(index >= size) {
            return null;
        }
        return items[start + index];
    }
}
