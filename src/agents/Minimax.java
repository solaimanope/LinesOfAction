package agents;

import agents.heuristics.Heuristics;
import agents.heuristics.MaximizePositionalScore;
import game.Move;
import game.Node;
import game.State;

import java.util.Collections;
import java.util.Vector;

public class Minimax extends Agent {
    public static final double INF = 1000000000;

    private Vector<Heuristics>heuristicsVector;
    protected double evaluateState(State state, int color) {
        double result = 0;
        for (Heuristics heuristics : heuristicsVector) {
            result += heuristics.calculateScore(state, color) * heuristics.getCoefficient();
        }
        return result;
    }
    public void addHeuristics(Heuristics heuristics, double coefficient) {
        heuristics.setCoefficient(coefficient);
        heuristicsVector.add(heuristics);
    }

    public Minimax(int pieceColor) {
        this.pieceColor = pieceColor;
        heuristicsVector = new Vector<>();
    }

    private double minimax(Node node, int depth, double alpha, double beta) {
        double score = evaluateState(node.state, pieceColor);
        if (score == INF || score == -INF) return score;
        if (depth == 0) return score;

        if (node.state.currentPlayer == pieceColor) {
            ///MAXIMIZE SCORE
            score = -INF - 1;
            for (Node child : node.getChildren()) {
                double newScore = minimax(child, depth - 1, alpha, beta);
                child.maximizeBestScoreSoFar(newScore);
                if (newScore > score) {
                    score = newScore;
                }
                alpha = Math.max(alpha, score);
                if (alpha >= beta) break;
            }
            Collections.sort(node.getChildren(), (o1, o2) -> {
                double d1 = o1.getBestScoreSoFar();
                double d2 = o2.getBestScoreSoFar();
                if (d1 > d2) return -1;
                if (d1 < d2) return 1;
                return 0;
            });
        } else {
            ///MINIMIZE SCORE
            score = INF + 1;
            for (Node child : node.getChildren()) {
                double newScore = minimax(child, depth - 1, alpha, beta);
                child.minimizeBestScoreSoFar(newScore);
                if (newScore < score) {
                    score = newScore;
                }
                beta = Math.min(beta, score);
                if (alpha >= beta) break;
            }
            Collections.sort(node.getChildren(), (o1, o2) -> {
                double d1 = o1.getBestScoreSoFar();
                double d2 = o2.getBestScoreSoFar();
                if (d1 < d2) return -1;
                if (d1 > d2) return 1;
                return 0;
            });
        }
        return score;
    }

    @Override
    public Move makeMove(State state) {
        Node root = new Node(state, null);
        long startTime = System.currentTimeMillis();
        for (int depth = 1; depth <= 5; depth++) {
            root = new Node(state, null);
            minimax(root, depth, -INF, INF);
        }
        System.out.println("time taken " + (System.currentTimeMillis()-startTime));
        return root.getChildren().get(0).getPreviousMove();
    }

    @Override
    public String AgentName() {
        return "Minimax";
    }
}
