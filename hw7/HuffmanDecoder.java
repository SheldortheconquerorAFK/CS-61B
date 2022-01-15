public class HuffmanDecoder {
    public  static void main(String[] args) {
        if (args.length < 2) {
            throw new RuntimeException();
        }
        ObjectReader or = new ObjectReader(args[0]);

        BinaryTrie bt = (BinaryTrie) or.readObject();
        int numSymbols = (int) or.readObject();
        BitSequence bs = (BitSequence) or.readObject();

        int count = 0;
        BitSequence currBS = bs;
        char[] charArray = new char[numSymbols];
        while (count < numSymbols) {
            Match m = bt.longestPrefixMatch(currBS);
            char c = m.getSymbol();
            charArray[count] = c;
            count++;
            currBS = currBS.allButFirstNBits(m.getSequence().length());
        }

        FileUtils.writeCharArray(args[1], charArray);
    }
}
