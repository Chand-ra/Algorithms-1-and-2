/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import edu.princeton.cs.algs4.Digraph;
import edu.princeton.cs.algs4.DirectedCycle;
import edu.princeton.cs.algs4.In;

import java.util.HashMap;
import java.util.LinkedList;

public class WordNet {
    private final HashMap<String, LinkedList<Integer>> hashMap;
    private final HashMap<Integer, String> synsetMap;
    private final SAP sap;

    public WordNet(String synsets, String hypernyms) {
        if (synsets == null || hypernyms == null) throw new IllegalArgumentException();
        int size = 0;
        hashMap = new HashMap<>();
        synsetMap = new HashMap<>();
        In synIn = new In(synsets);
        while (synIn.hasNextLine()) {
            String line = synIn.readLine();
            String[] lineArray = line.split(",", 3);
            int id = Integer.parseInt(lineArray[0]);
            String[] words = lineArray[1].split(" ", 50);
            for (int i = 0; i < words.length; i++) {
                synsetMap.put(id, lineArray[1]);
                if (!hashMap.containsKey(words[i])) {
                    LinkedList<Integer> list = new LinkedList<>();
                    list.add(id);
                    hashMap.put(words[i], list);
                }
                else {
                    LinkedList<Integer> list = hashMap.get(words[i]);
                    list.add(id);
                }
            }
            size++;
        }
        In hypIn = new In(hypernyms);
        Digraph digraph = new Digraph(size);
        while (hypIn.hasNextLine()) {
            String line = hypIn.readLine();
            String[] lineArray = line.split(",", 50);
            int id = Integer.parseInt(lineArray[0]);
            for (int i = 1; i < lineArray.length; i++) {
                digraph.addEdge(id, Integer.parseInt(lineArray[i]));
            }
        }
        if (!validateRoots(digraph)) throw new IllegalArgumentException();
        DirectedCycle directedCycle = new DirectedCycle(digraph);
        if (directedCycle.hasCycle()) throw new IllegalArgumentException();
        sap = new SAP(digraph);
    }

    private boolean validateRoots(Digraph G) {
        int rootsCount = 0;
        for (int i = 0; i < G.V(); i++) {
            if (G.outdegree(i) == 0) rootsCount++;
        }
        if (rootsCount > 1) return false;
        return true;
    }

    public Iterable<String> nouns() {
        return hashMap.keySet();
    }


    public boolean isNoun(String word) {
        if (word == null) throw new IllegalArgumentException();
        return hashMap.containsKey(word);
    }

    public int distance(String nounA, String nounB) {
        if (nounA == null || nounB == null) throw new IllegalArgumentException();
        if (!isNoun(nounA) || !isNoun(nounB)) throw new IllegalArgumentException();
        LinkedList<Integer> synsetA = hashMap.get(nounA);
        LinkedList<Integer> synsetB = hashMap.get(nounB);
        return sap.length(synsetA, synsetB);
    }

    public String sap(String nounA, String nounB) {
        if (nounA == null || nounB == null) throw new IllegalArgumentException();
        if (!isNoun(nounA) || !isNoun(nounB)) throw new IllegalArgumentException();
        LinkedList<Integer> synsetA = hashMap.get(nounA);
        LinkedList<Integer> synsetB = hashMap.get(nounB);
        int ancestor = sap.ancestor(synsetA, synsetB);
        return synsetMap.get(ancestor);
    }

    public static void main(String[] args) {
        WordNet wordnet = new WordNet("synsets.txt", "hypernyms.txt");
        System.out.println(wordnet.sap("individual", "edible_fruit"));
    }
}
