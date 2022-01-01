/**
 * Class for doing Radix sort
 *
 * @author Akhil Batra, Alexander Hwang
 *
 */
public class RadixSort {
    public static int R = 256;

    /**
     * Does LSD radix sort on the passed in array with the following restrictions:
     * The array can only have ASCII Strings (sequence of 1 byte characters)
     * The sorting is stable and non-destructive
     * The Strings can be variable length (all Strings are not constrained to 1 length)
     *
     * @param asciis String[] that needs to be sorted
     *
     * @return String[] the sorted array
     */
    public static String[] sort(String[] asciis) {
        String[] sorted = new String[asciis.length];
        String[] copy = new String[asciis.length];
        System.arraycopy(asciis, 0, copy, 0, asciis.length);

        int maxLen = Integer.MIN_VALUE;
        for (String s : copy) {
            if (s.length() > maxLen) {
                maxLen = s.length();
            }
        }

        for (int i = maxLen - 1; i >= 0; i--) {
            int[] count = new int[R + 2]; // from 0 to R - 1 (index range) we need R positions, plus ONE for MIN_VALUE, plus ONE again for moving indexes one position forward
            for (String s : copy) {
                if (s.length() - (maxLen - 1 - i) <= i) {
                    count[R + 1]++; // put NUMBER 0 to R - 1 to INDEXES from 1 to R, INDEX R + 1 we put MIN_VALUE
                } else {
                    count[s.charAt(i) + 1]++;
                }
            }
            count[0] += count[R + 1];
            count[R + 1] = 0;
            for (int j = 1; j < count.length - 1; j++) {
                count[j] += count[j - 1];
            }

            for (int k = 0; k < copy.length; k++) {
                if (copy[k].length() - (maxLen - 1 - i) <= i) {
                    sorted[count[R + 1]++] = copy[k];
                } else {
                    sorted[count[copy[k].charAt(i)]++] = copy[k];
                }
            }
            copy = sorted;
        }
        return copy;
    }

    /**
     * LSD helper method that performs a destructive counting sort the array of
     * Strings based off characters at a specific index.
     * @param asciis Input array of Strings
     * @param index The position to sort the Strings on.
     */
    private static void sortHelperLSD(String[] asciis, int index) {
        // Optional LSD helper method for required LSD radix sort
    }

    /**
     * MSD radix sort helper function that recursively calls itself to achieve the sorted array.
     * Destructive method that changes the passed in array, asciis.
     *
     * @param asciis String[] to be sorted
     * @param start int for where to start sorting in this method (includes String at start)
     * @param end int for where to end sorting in this method (does not include String at end)
     * @param index the index of the character the method is currently sorting on
     *
     **/
    private static void sortHelperMSD(String[] asciis, int start, int end, int index) {
        // Optional MSD helper method for optional MSD radix sort
        return;
    }

    public static void main(String[] args) {
        String[] test = new String[5];
        test[0] = "a";
        test[1] = "b";
        test[2] = "ab";
        test[3] = "9";
        test[4] = "012";
        String[] result = sort(test);
        System.out.println(result[0]);
        System.out.println(result[1]);
        System.out.println(result[2]);
        System.out.println(result[3]);
        System.out.println(result[4]);
    }
}
