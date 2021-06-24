public class ArrayDeque<T> {

    private int size;
    private T[] items;
    private int nextFirst;
    private int nextLast;
    private int frontAdded;
    private int lastAdded;


    public ArrayDeque() {
        size = 0;
        items = (T[]) new Object[8];
        nextFirst = 0;
        nextLast = 1;
        frontAdded = 0;
        lastAdded = 0;
    }

    public void addFirst(T item) {
        if (nextFirst == 0) {
            items[nextFirst] = item;
            nextFirst = items.length - 1;
        } else {
            items[nextFirst] = item;
            nextFirst -= 1;
        }
        size += 1;
        frontAdded += 1;
    }

    public void addLast(T item) {
        if (nextLast == items.length - 1) {
            items[nextLast] = item;
            nextLast = 0;
        } else {
            items[nextLast] = item;
            nextLast += 1;
        }
        size += 1;
        lastAdded += 1;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public int size() {
        return size;
    }

    public void printDeque() {
        for (int i = nextFirst + 1; i < nextLast; i++) {
            if (i == items.length - 1) {
                System.out.println(items[i] + " ");
                i = -1;
                continue;
            }
            System.out.println(items[i] + " ");
        }
        System.out.println("\n");
    }

    public T removeFirst() {
        if (size() == 0) {
            return null;
        }
        T value = items[frontInd()];    // take the item of zeroth index
        items[frontInd()] = null;
        frontAdded -= 1;
        return value;
    }

    private int frontInd() {
        frontAdded -= 1;
        if (frontAdded > 0) {
            if (nextFirst == items.length) {
                return 0;
            } else {
                return nextFirst + 1;
            }
        } else {
            if (nextLast <= 1) {
                return items.length - size();
            } else {
                return nextLast - size();
            }
        }
    }

    public T removeLast() {
        lastAdded -= 1;
        if (size() == 0) {
            return null;
        } else {
            T value = items[lastInd()];     // take the item of the last index
            items[lastInd()] = null;
            lastAdded -= 1;
            return value;
        }
    }

    private int lastInd() {
        if (lastAdded > 0) {
            if (nextLast == 0) {
                return items.length;
            } else {
                return nextLast - 1;
            }
        } else {
            return nextFirst + size();
        }
    }

    /**
     * Take the zeroth item from the list(using frontInd function), then add index onto it.
     * @param index the index of the list(not the underlying array)
     * @return value of the item on slot of the given index
     */
    public T get(int index) {
        if (frontInd() + index >= items.length) {
            return items[frontInd() + index - items.length];
        } else {
            return items[frontInd() + index];
        }
    }

}
