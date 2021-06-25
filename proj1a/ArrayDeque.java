public class ArrayDeque<T> {

    private int size;
    private T[] items;
    private int nextFirst;
    private int nextLast;



    public ArrayDeque() {
        size = 0;
        items = (T[]) new Object[8];
        nextFirst = 0;
        nextLast = 1;

    }

    private void resize(int length) {
        T[] newitems = (T[]) new Object[length];
        int oldindex = addOne(nextFirst);
        for (int i = 0; i < size; i++) {
            newitems[i] = items[oldindex];
            oldindex = addOne(oldindex);
        }
        this.items = newitems;
        nextFirst = items.length - 1;
        nextLast = size;
    }

    private int addOne(int index) {
        return (index + 1) % items.length;
    }

    private int minusOne(int index) {
        return (index - 1 + items.length) % items.length;
    }

    public void addFirst(T item) {
        if (size == items.length) {
            resize(size * 2);
        }
        items[nextFirst] = item;
        nextFirst = minusOne(nextFirst);
        size += 1;
    }

    public void addLast(T item) {
        if (size == items.length) {
            resize(size * 2);
        }
        items[nextLast] = item;
        nextLast = addOne(nextLast);
        size += 1;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public int size() {
        return size;
    }

    public void printDeque() {
        int i = addOne(nextFirst);
        for (int j = 0; j < size; j++) {
            System.out.print(items[i] + " ");
            i = addOne(i);
        }
    }

    public T removeFirst() {
        if (size() == 0) {
            return null;
        }
        T value = items[addOne(nextFirst)];
        items[addOne(nextFirst)] = null;
        nextFirst = addOne(nextFirst);
        size -= 1;
        return value;


    }

    public T removeLast() {
        if (size() == 0) {
            return null;
        }
        T value = items[minusOne(nextLast)];
        items[addOne(nextFirst)] = null;
        nextLast = minusOne(nextLast);
        size -= 1;
        return value;
    }

    public T get(int index) {
        if (index >= size) {
            return null;
        }
        int start = addOne(nextFirst);
        return items[(start + index) % items.length];
    }

}
