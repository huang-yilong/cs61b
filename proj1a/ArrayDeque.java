public class ArrayDeque<T> {
    private T[] items;
    private int size;

    public ArrayDeque(){
        items = (T[]) new Object[8];
        size = 0;
    }

    public ArrayDeque(ArrayDeque<T> other){
        items = (T[])new Object[other.size()];
        System.arraycopy(other.getItems(), 0, items, 0, size);
        size = other.size();
    }

    public void addFirst(T item){
        if(size == items.length)
            resize(size+1, 0, 1, size);
        else
            resize(items.length, 0, 1, size);
        items[0] = item;
        size++;
    }

    private void resize(int capacity, int srcPos, int desPos, int len){
        T[] a = (T[]) new Object[capacity];
        System.arraycopy(items, srcPos, a, desPos, len);
        items = a;
    }

    public void addLast(T item){
        if(size == items.length){
            resize(size+1, 0, 0, size);
        }
        items[size] = item;
        size++;
    }

    public boolean isEmpty(){
        return size == 0;
    }

    public int size(){
        return size;
    }

    public void printDeque(){
        for(int i=0; i<size; ++i){
            System.out.print(items[i] + " ");
        }
        System.out.println();
    }

    public T removeFirst(){
        if(isEmpty()) return null;
        T item = items[0];
        resize(size-1, 1, 0, size-1);
        size--;
        return item;
    }

    public T removeLast(){
        if(isEmpty())return null;
        T item = items[--size];
        return item;
    }

    public T get(int index){
        if(index >= size) return null;
        return items[index];
    }

    public T[] getItems(){
        return items;
    }

}
