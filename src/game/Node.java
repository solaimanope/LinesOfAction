package game;

import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;

public class Node {
    private Double bestScoreSoFar;
    public final State state;
    private final Move previousMove;
    private List<Node> children;
    public Node(State state, Move previousMove) {
        this.state = state;
        this.previousMove = previousMove;
        children = null;
    }
    public List<Node> getChildren() {
        if (children == null) {
            children = new LinkedList<>();
            for (Move move : state.allAvailableMoves()) {
                children.add(new Node(state.makeMove(move), move));
            }
        }
        return children;
    }

    public Double getBestScoreSoFar() {
        return bestScoreSoFar;
    }

    public void maximizeBestScoreSoFar(Double bestScoreSoFar) {
        if (this.bestScoreSoFar != null) {
            this.bestScoreSoFar = Math.max(this.bestScoreSoFar, bestScoreSoFar);
        } else {
            this.bestScoreSoFar = bestScoreSoFar;
        }
    }
    public void minimizeBestScoreSoFar(Double bestScoreSoFar) {
        if (this.bestScoreSoFar != null) {
            this.bestScoreSoFar = Math.max(this.bestScoreSoFar, bestScoreSoFar);
        } else {
            this.bestScoreSoFar = bestScoreSoFar;
        }
    }
    public Move getPreviousMove() {
        return previousMove;
    }
}
