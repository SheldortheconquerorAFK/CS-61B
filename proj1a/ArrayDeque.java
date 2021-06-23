public class ArrayDeque<T> {

    private int size;
    private T[] items;
    private int nextFirst;
    private int nextLast;
    private int frontAdded = 0;
    private int lastAdded = 0;

    public ArrayDeque() {
        size = 0;
        items = (T[]) new Object[8];
        nextFirst = 0;
        nextLast = 1;
    }

    /**
     * Add one item to the front, or the zeroth position of the underlying array.
     * Using circular sentinel node abstraction.
     * @param item The single item which is going to be added to the underlying array.
     */
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

    /**
     * Check if the array-based list is empty(i.e. including 0 element).
     * @return true if size = 0, otherwise return false.
     */
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

    /**
     * Remove the front, or the zeroth item in the underlying array, then return it.
     * @return the value of the front, or zeroth item, then nullify it from the array.
     */
    public T removeFirst() {
        T value;
        if (size == 0) {
            return null;
        } else if (frontAdded == 0) {
            if (nextLast - lastAdded > 0) {
                value = items[nextLast -lastAdded];
                items[nextLast -lastAdded] = null;
                return value;
            } else {
                value = items[nextLast + items.length - lastAdded];
                items[nextLast + items.length - lastAdded] = null;
                return value;
            }
        } else {
            if (nextFirst == items.length - 1) {
                value = items[0];
                items[0] = null;
                nextFirst = 0;
            } else {
                value = items[nextFirst + 1];
                items[nextFirst + 1] = null;
                nextFirst += 1;
            }
            frontAdded -= 1;
            size -= 1;
            return value;
        }
    }

    public T removeLast() {
        T value;
        if (size == 0) {
            return null;
        } else if (frontAdded == 0) {
            if (nextFirst + frontAdded >= items.length) {
                value = items[nextFirst +frontAdded -items.length];
                items[nextFirst +frontAdded -items.length] = null;
                return value;
            } else {
                value = items[nextFirst + frontAdded];
                items[nextFirst + frontAdded] = null;
                return value;
            }
        } else {
            if (nextLast == 0) {
                value = items[items.length - 1];
                items[items.length - 1] = null;
                nextLast = items.length - 1;
            } else {
                value = items[nextLast - 1];
                items[nextLast - 1] = null;
                nextLast = nextLast - 1;
            }
            lastAdded -= 1;
            size -= 1;
            return value;
        }
    }

    public T get(int index) {
        if (size == 0) {
            return null;
        } else {
            if (nextFirst + 1 + index > items.length - 1) {
                return items[index - (items.length - 1 - nextFirst)];
            } else {
                return items[nextFirst + index + 1];
            }
        }
    }
}
