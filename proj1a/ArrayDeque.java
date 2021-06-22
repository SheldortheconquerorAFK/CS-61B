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
    }

    /**
     * Check if the array-based list is empty(i.e. including 0 element).
     * @return true if size = 0, otherwise return false.
     */
    public boolean isEmpty() {
        return size == 0;
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
     * The specific process depends on the specific list. Even if the user uses the addFirst method multiple times so that the nextFirst pointer move back to the front of the array, we still use the pos next to nextFirst as the beginning pos.
     * @return the value of the front, or zeroth item, then nullify it from the array.
     */
    public T removeFirst() {
        if (size == 0) {
            return null;
        } else {
            if (nextFirst == items.length - 1) {
                T value = items[0];
                items[0] = null;
                nextFirst = 0;
                size -= 1;
                return value;
            } else {
                T value = items[nextFirst + 1];
                items[nextFirst + 1] = null;
                nextFirst += 1;
                size -= 1;
                return value;
            }
        }
    }

    public T removeLast() {
        if (size == 0) {
            return null;
        } else {
            if (nextLast == 0) {
                T value = items[items.length - 1];
                items[items.length - 1] = null;
                nextLast = items.length - 1;
                size -= 1;
                return value;
            } else {
                T value = items[nextLast - 1];
                items[nextLast - 1] = null;
                nextLast = nextLast - 1;
                size -= 1;
                return value;
            }
        }
    }

    public T get(int index) {
       if (size == 0) {
           return null;
       } else {
           if (nextFirst + index >= items.length - 1) {
               return items[index - (items.length - 1 - nextFirst) - 1];    // e.g. if the items spans from pos 5 to pos 1 (i.e 5,6,7,0,1), and you pick index 3, then the pos should be 0.
           } else {
               return items[nextFirst + index + 1];
           }
       }
    }

}
