public class ArrayDeque<T> {
    private T[] items;
    private int size;
    private int nextfirst;
    private int nextlast;

    public ArrayDeque() {
        items = (T[]) new Object[8];
        nextfirst = 0;
        nextlast = 1;
        size = 0;
    }


    private int addOne(int a) {
        return (a + 1) % items.length;
    }

    private int subOne(int a) {
        return (a - 1 + items.length) % items.length;
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

    public void addFirst(T item) {
        if (size == items.length) {
            resize(size * 2);
        }
        items[nextfirst] = item;
        nextfirst = subOne(nextfirst);
        size += 1;
    }

    public void addLast(T item) {
        if (size == items.length) {
            resize(size * 2);
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
        int i = addOne(nextfirst);
        for (int j = 0; j < size; j++) {
            System.out.print(items[i] + " ");
            i = addOne(i);
        }
    }

    public T removeFirst() {
        if (size == 0) {
            return null;
        }
        T a = items[addOne(nextfirst)];
        items[addOne(nextfirst)] = null;
        nextfirst = addOne(nextfirst);
        size -= 1;
        if (items.length >= 16 && size < (items.length / 4)) {
            resize(items.length / 2);
        }
        return a;
    }

    public T removeLast() {
        if (size == 0) {
            return null;
        }
        T a = items[subOne(nextlast)];
        items[subOne(nextlast)] = null;
        nextlast = subOne(nextlast);
        size -= 1;
        if (items.length >= 16 && size < (items.length / 4)) {
            resize(items.length / 2);
        }
        return a;
    }


    public T get(int index) {
        if (index >= size) {
            return null;
        }
        int start = addOne(nextfirst);
        return items[(start + index) % items.length];
    }
}
