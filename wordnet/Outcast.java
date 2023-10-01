/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;

public class Outcast {
    private final WordNet wordnet;

    public Outcast(WordNet wordNet) {
        wordnet = wordNet;
    }

    public String outcast(String[] nouns) {
        int length;
        int maxLength = 0;
        String outcast = null;
        for (String noun : nouns) {
            length = 0;
            for (String otherNoun : nouns) {
                length = length + wordnet.distance(noun, otherNoun);
            }
            if (length > maxLength) {
                maxLength = length;
                outcast = noun;
            }
        }
        return outcast;
    }

    public static void main(String[] args) {
        WordNet wordnet = new WordNet(args[0], args[1]);
        Outcast outcast = new Outcast(wordnet);
        for (int t = 2; t < args.length; t++) {
            In in = new In(args[t]);
            String[] nouns = in.readAllStrings();
            StdOut.println(args[t] + ": " + outcast.outcast(nouns));
        }
    }
}
