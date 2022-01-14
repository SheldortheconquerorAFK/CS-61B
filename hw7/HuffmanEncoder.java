import java.util.*;

public class HuffmanEncoder {
    public static Map<Character, Integer> buildFrequencyTable(char[] inputSymbols) {
        Map<Character, Integer> map = new HashMap<>();
        for (char ch : inputSymbols) {
            if (map.get(ch) == null) {
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
        Map<Character, Integer> map = HuffmanEncoder.buildFrequencyTable(inputSymbols);
        BinaryTrie bt = new BinaryTrie(map);
        ObjectWriter ow = new ObjectWriter("Huffman.huf");
        ow.writeObject(bt);
        ow.writeObject(map.keySet().size());
        Map<Character, BitSequence> table = bt.buildLookupTable();
        List<BitSequence> ls = new ArrayList<>();
        for (char ch : inputSymbols) {
            ls.add(table.get(ch));
        }
        BitSequence wholeBS = BitSequence.assemble(ls);
        ow.writeObject(wholeBS);
    }
}
