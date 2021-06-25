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

    public int addOne(int index) {
        return (index + 1) % items.length;
    }

    public int minusOne(int index) {
        return (index - 1 + items.length) % items.length;
    }

    public void addFirst(T item) {
        items[nextFirst] = item;
        nextFirst = addOne(nextFirst);
        size += 1;
    }

    public void addLast(T item) {
        items[nextLast] = item;
        nextLast = minusOne(nextLast);
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
        for (int j = 0; j < size(); j++) {

            System.out.println(items[i] + " ");
            i = addOne(nextFirst);
        }
        System.out.println("\n");
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
        int start = addOne(nextFirst);
        return items[(start + index) % items.length];
    }

}
