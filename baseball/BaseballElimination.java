/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import edu.princeton.cs.algs4.Bag;
import edu.princeton.cs.algs4.FlowEdge;
import edu.princeton.cs.algs4.FlowNetwork;
import edu.princeton.cs.algs4.FordFulkerson;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;

import java.util.HashMap;

public class BaseballElimination {
    private final HashMap<String, Integer> teamsToIndex;
    private final String[] indexToTeams;
    private final int[] wins;
    private final int[] loss;
    private final int[] left;
    private final int[][] matchups;
    private Bag<String> certificateOfElimination;

    public BaseballElimination(String filename) {
        In in = new In(filename);
        int size = in.readInt();
        teamsToIndex = new HashMap<>();
        wins = new int[size];
        loss = new int[size];
        left = new int[size];
        matchups = new int[size][size];
        indexToTeams = new String[size];
        int i = 0;
        while (!in.isEmpty()) {
            String team = in.readString();
            teamsToIndex.put(team, i);
            indexToTeams[i] = team;
            wins[i] = in.readInt();
            loss[i] = in.readInt();
            left[i] = in.readInt();
            for (int j = 0; j < size; j++) {
                matchups[i][j] = in.readInt();
            }
            i++;
        }
    }

    public int numberOfTeams() {
        return teamsToIndex.size();
    }

    public Iterable<String> teams() {
        return teamsToIndex.keySet();
    }

    private void validateTeam(String team) {
        if (team == null) throw new IllegalArgumentException();
        if (!teamsToIndex.containsKey(team)) throw new IllegalArgumentException();
    }

    public int wins(String team) {
        validateTeam(team);
        return wins[teamsToIndex.get(team)];
    }

    public int losses(String team) {
        validateTeam(team);
        return loss[teamsToIndex.get(team)];
    }

    public int remaining(String team) {
        validateTeam(team);
        return left[teamsToIndex.get(team)];
    }

    public int against(String team1, String team2) {
        validateTeam(team1);
        validateTeam(team2);
        return matchups[teamsToIndex.get(team1)][teamsToIndex.get(team2)];
    }

    private FlowNetwork createNetwork(String team) {
        int size = numberOfTeams() - 1;
        int teamIndex = teamsToIndex.get(team);
        int verticesCount = 1 + size * (size - 1) / 2 + size + 1;
        FlowNetwork network = new FlowNetwork(verticesCount);
        for (int i = 0; i < numberOfTeams(); i++) {
            if (i == teamIndex) continue;
            network.addEdge(
                    new FlowEdge(i, verticesCount - 1, wins(team) + remaining(team) - wins[i]));
        }
        int gameVertex = size + 1;
        for (int i = 0; i < numberOfTeams(); i++) {
            for (int j = i + 1; j < numberOfTeams(); j++) {
                if (i == teamIndex || j == teamIndex) continue;
                network.addEdge(new FlowEdge(gameVertex, i, Double.POSITIVE_INFINITY));
                network.addEdge(new FlowEdge(gameVertex, j, Double.POSITIVE_INFINITY));
                network.addEdge(new FlowEdge(teamIndex, gameVertex, matchups[i][j]));
                gameVertex++;
            }
        }
        return network;
    }

    private boolean isTrivialElimination(String team) {
        int maxWins = wins(team) + remaining(team);
        for (int i = 0; i < numberOfTeams(); i++) {
            if (wins[i] > maxWins) {
                certificateOfElimination.add(indexToTeams[i]);
                return true;
            }
        }
        return false;
    }

    private boolean isNonTrivialElimination(String team) {
        int size = numberOfTeams() - 1;
        int source = teamsToIndex.get(team);
        int sink = size * (size - 1) / 2 + size + 1;
        int teamIndex = teamsToIndex.get(team);
        int maxFlow = 0;
        FordFulkerson fulkerson = new FordFulkerson(createNetwork(team), source, sink);
        for (int i = 0; i < numberOfTeams(); i++) {
            for (int j = i + 1; j < numberOfTeams(); j++) {
                if (i == teamIndex || j == teamIndex) continue;
                maxFlow += matchups[i][j];
            }
        }
        if (maxFlow == fulkerson.value()) return false;
        for (int i = 0; i < numberOfTeams(); i++) {
            if (i == teamIndex) continue;
            if (fulkerson.inCut(i)) certificateOfElimination.add(indexToTeams[i]);
        }
        return true;
    }

    public boolean isEliminated(String team) {
        validateTeam(team);
        certificateOfElimination = new Bag<>();
        if (isTrivialElimination(team)) return true;
        if (isNonTrivialElimination(team)) return true;
        return false;
    }

    public Iterable<String> certificateOfElimination(String team) {
        validateTeam(team);
        if (!isEliminated(team)) return null;
        else return certificateOfElimination;
    }

    public static void main(String[] args) {
        BaseballElimination division = new BaseballElimination(args[0]);
        for (String team : division.teams()) {
            if (division.isEliminated(team)) {
                StdOut.print(team + " is eliminated by the subset R = { ");
                for (String t : division.certificateOfElimination(team)) {
                    StdOut.print(t + " ");
                }
                StdOut.println("}");
            }
            else {
                StdOut.println(team + " is not eliminated");
            }
        }
    }
}
