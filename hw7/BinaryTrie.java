import edu.princeton.cs.algs4.MinPQ;

import java.io.Serial;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class BinaryTrie implements Serializable {
    Node root;
    @Serial
    private static final long serialVersionUID = 1L;

    public BinaryTrie(Map<Character, Integer> frequencyTable) {
        MinPQ<Node> pq = new MinPQ<>();
        for (Character ch : frequencyTable.keySet()) {
            if (frequencyTable.get(ch) != 0) {
                pq.insert(new Node(ch, frequencyTable.get(ch), null, null));
            }
        }
        while (pq.size() > 1) {
            Node left = pq.delMin();
            Node right = pq.delMin();
            Node merge = new Node('\0', left.freq + right.freq, left, right);
            pq.insert(merge);
        }
        root = pq.delMin();
    }

    public Match longestPrefixMatch(BitSequence querySequence) {
        if (querySequence == null) {
            return null;
        }
        Node n = root;
        int length = 0;
        while (!n.isLeaf()) {
            if (querySequence.bitAt(length) == 0 && n.ch == '\0') {
                n = n.left;
                length++;
            } else if (querySequence.bitAt(length) == 1 && n.ch == '\0') {
                n = n.right;
                length++;
            }
        }
        return new Match(querySequence.firstNBits(length), n.ch);
    }

    public Map<Character, BitSequence> buildLookupTable() {
        Map<Character, BitSequence> map = new HashMap<>();
        buildLookupTable(map, root, "");
        return map;
    }

    private void buildLookupTable(Map<Character, BitSequence> map, Node n, String s) {
        if (!n.isLeaf()) {
            buildLookupTable(map, n.left, s + '0');
            buildLookupTable(map, n.right, s + '1');
        } else {
            BitSequence bs = new BitSequence(s);
            map.put(n.ch, bs);
        }
    }

    private static class Node implements Comparable<Node>, Serializable {
        private char ch;
        private int freq;
        private Node left;
        private Node right;
        @Serial
        private static final long serialVersionUID = 2L;

        private Node(char c, int f, Node l, Node r) {
            ch = c;
            freq = f;
            left = l;
            right = r;
        }

        @Override
        public int compareTo(Node that) {
            return this.freq - that.freq;
        }

        private boolean isLeaf() {
            return (left == null) && (right == null);
        }
    }
}
