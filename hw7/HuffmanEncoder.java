import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HuffmanEncoder {
    public static Map<Character, Integer> buildFrequencyTable(char[] inputSymbols) {
        Map<Character, Integer> map = new HashMap<>();
        for (char ch : inputSymbols) {
            if (!map.containsKey(ch)) {
                map.put(ch, 1);
            } else {
                int i = map.get(ch);
                map.put(ch, i + 1);
            }
        }
        return map;
    }

    public static void main(String[] args) {
        if (args.length < 1) {
            throw new RuntimeException("File name isn't provided.");
        }
        char[] inputSymbols = FileUtils.readFile(args[0]);
        Map<Character, Integer> ft = HuffmanEncoder.buildFrequencyTable(inputSymbols);
        BinaryTrie bt = new BinaryTrie(ft);
        ObjectWriter ow = new ObjectWriter(args[0] + ".huf");
        ow.writeObject(bt);
        int numSymbols = inputSymbols.length;
        ow.writeObject(numSymbols);
        Map<Character, BitSequence> lt = bt.buildLookupTable();
        List<BitSequence> ls = new ArrayList<>();
        for (char ch : inputSymbols) {
            ls.add(lt.get(ch));
        }
        BitSequence wholeBS = BitSequence.assemble(ls);
        ow.writeObject(wholeBS);
    }
}
