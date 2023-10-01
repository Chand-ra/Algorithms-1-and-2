import edu.princeton.cs.algs4.In;

public class Trie {
    private static final int R = 26;        // extended ASCII

    private Node root;      // root of trie
    private int n;          // number of keys in trie

    // R-way trie node
    private static class Node {
        private Node[] next = new Node[R];
        private boolean isString;
    }

    /**
     * Initializes an empty set of strings.
     */
    public Trie() {
    }

    /**
     * Does the set contain the given key?
     *
     * @param key the key
     * @return {@code true} if the set contains {@code key} and
     * {@code false} otherwise
     * @throws IllegalArgumentException if {@code key} is {@code null}
     */
    public boolean contains(String key) {
        if (key == null) throw new IllegalArgumentException("argument to contains() is null");
        Node x = get(root, key, 0);
        if (x == null) return false;
        return x.isString;
    }

    private Node get(Node x, String key, int d) {
        if (x == null) return null;
        if (d == key.length()) return x;
        char c = key.charAt(d);
        return get(x.next[c - 65], key, d + 1);
    }

    /**
     * Adds the key to the set if it is not already present.
     *
     * @param key the key to add
     * @throws IllegalArgumentException if {@code key} is {@code null}
     */
    public void add(String key) {
        if (key == null) throw new IllegalArgumentException("argument to add() is null");
        root = add(root, key, 0);
    }

    private Node add(Node x, String key, int d) {
        if (x == null) x = new Node();
        if (d == key.length()) {
            if (!x.isString) n++;
            x.isString = true;
        }
        else {
            char c = key.charAt(d);
            x.next[c - 65] = add(x.next[c - 65], key, d + 1);
        }
        return x;
    }

    /**
     * Returns the number of strings in the set.
     *
     * @return the number of strings in the set
     */
    public int size() {
        return n;
    }

    public boolean prefixQuery(String word) {
        Node x = root;
        for (int i = 0; i < word.length(); i++) {
            x = x.next[word.charAt(i) - 65];
            if (x == null) return false;
        }
        for (int i = 0; i < R; i++) {
            if (x.next[i] != null) return true;
        }
        return false;
    }


    /**
     * Unit tests the {@code TrieSET} data type.
     *
     * @param args the command-line arguments
     */
    public static void main(String[] args) {
        Trie set = new Trie();
        In in = new In(args[0]);
        while (!in.isEmpty()) {
            set.add(in.readString());
        }
        System.out.println(set.prefixQuery(args[1]));
    }
}
