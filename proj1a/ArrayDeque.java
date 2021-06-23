public class ArrayDeque<T> {

    private int size;
    private T[] items;
    private int nextFirst;
    private int nextLast;
    private int frontAdd;
    private int lastAdd;


    public ArrayDeque() {
        size = 0;
        items = (T[]) new Object[8];
        nextFirst = 0;
        nextLast = 1;
        frontAdd = 0;
        lastAdd = 0;
    }

    /**
     * Add one item to the front, or the zeroth position of the array-based list(the list, not the array).
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
        frontAdd += 1;
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
        lastAdd += 1;
        size += 1;
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
        for (int i = calcFrontInd() + 1; i < calcLastInd(); i++) {
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
        if (size == 0) {
            return null;
        } else {
            T value = items[calcFrontInd()];
            frontAdd -= 1;
            size -= 1;
            items[calcFrontInd()] = null;
            return value;
        }
    }

    public T removeLast() {
        if (size == 0) {
            return null;
        } else {
            T value = items[calcLastInd()];
            lastAdd -= 1;
            size -= 1;
            items[calcLastInd()] = null;
            return value;
        }
    }

    public T get(int index) {
        if (calcFrontInd() + 1 + index > items.length - 1) {            // if the list spans over end of the array and turns around to front of the array.
            return items[index - (items.length - calcFrontInd())];
        } else {
            return items[calcFrontInd() + index];
        }

    }

    /**
     * The function is used to calculate the index of zeroth element of the list.
     * The case is, if the user only uses addLast to add elements, they still get a list which has a front.
     * So it is important to distinguish what exactly is the zeroth item in the list, in various cases.
     * @return the index of the zeroth element of the list
     */
    public int calcFrontInd() {
        if (frontAdd > 0) {
            if (nextFirst == items.length) {
                return 0;
            } else {
                return nextFirst + 1;
            }
        } else {
            if (nextLast - lastAdd > 0) {
                return nextLast -lastAdd;
            } else {
                return nextLast + items.length - lastAdd;
            }
        }
    }

    public int calcLastInd() {
        if (lastAdd > 0) {
            if (nextLast == 0) {
                return items.length;
            } else {
                return nextLast - 1;
            }
        } else {
            if (nextFirst + frontAdd >= items.length) {
                return nextFirst +frontAdd -items.length;
            } else {
                return nextFirst + frontAdd;
            }
        }
    }
}
