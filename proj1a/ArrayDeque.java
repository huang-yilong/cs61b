public class ArrayDeque<T> {
    private T[] items;
    private int size;
    private int nextfirst;
    private int nextlast;

    public ArrayDeque() {
        items = (T[]) new Object[8];
        size = 0;
        nextfirst = 0;
        nextlast = 1;
    }

    /**
     * 将双端队列中的数的下一个数的位置映射到实际数组中
     * @param a
     * @return
     */

    private int addOne(int a) {
        return (a + 1) % items.length;
    }

    private int subOne(int a) {
        return (a - 1 + items.length) % items.length;
    }

    public void addFirst(T item) {
        if(size == items.length) {
            resize(size * 2);
        }
        items[nextfirst] = item;
        nextfirst = subOne(nextfirst);
        size += 1;
    }

    private void resize(int length) {
        T[] newitems = (T[]) new Object[length];
        int oldindex = addOne(nextfirst);
        for (int i = 0; i < size; i++) {
            newitems[i] = items[oldindex];
            oldindex = addOne(oldindex);
        }
        this.items = newitems;
        nextfirst = items.length - 1;
        nextlast = size;
    }

    public void addLast(T item) {
        if(size == items.length) {
            resize(2 * size);
        }
        items[nextlast] = item;
        nextlast = addOne(nextlast);
        size += 1;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public int size() {
        return size;
    }

    public void printDeque() {
        int cur = addOne(nextfirst);
        while (cur != nextlast) {
            System.out.print(items[cur] + " ");
            cur = addOne(cur);
        }
        System.out.println();
    }

    public T removeFirst() {
        if (isEmpty()) {
            return null;
        }
        nextfirst = addOne(nextfirst);
        T item = items[nextfirst];
        items[nextfirst] = null;
        size -= 1;
        return item;
    }

    public T removeLast() {
        if(isEmpty()) {
            return null;
        }
        nextlast = subOne(nextlast);
        T item = items[nextlast];
        items[nextlast] = null;
        size -= 1;
        return item;
    }

    public T get(int index) {
        if(index >= size) {
            return null;
        }
        int cur = addOne(nextfirst);
        return items[(cur + index) % items.length];
    }
}
