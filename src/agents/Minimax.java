package agents;

import agents.heuristics.Heuristics;
import game.Move;
import game.Node;
import game.State;
import referee.Timer;

import java.util.Collections;
import java.util.Vector;

public class Minimax extends Agent {
    public static final double INF = 1000000000;

    private Vector<Heuristics>heuristicsVector;
    protected double evaluateState(State state, int color) {
        int status = state.gameEndStatus();
        if (status != State.NONE) {
            return status == color ? Minimax.INF : -Minimax.INF;
        }
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

    private int leafCount;
    private int cutOffCounter;
    private Timer timer;
    private double minimax(Node node, int depth, double alpha, double beta) {
        double score = evaluateState(node.state, pieceColor);
        if (score == INF || score == -INF) return score;
        if (timer.timesUp()) return score;
        if (depth == 0) {
            leafCount++;
            return score;
        }

        if (node.state.currentPlayer == pieceColor) {
            ///MAXIMIZE SCORE
            score = -INF - 1;
            for (Node child : node.getChildren()) {
                double newScore = minimax(child, depth - 1, alpha, beta);
                if (timer.timesUp()) break;
                child.maximizeBestScoreSoFar(newScore);
                if (newScore > score) {
                    score = newScore;
                }
                alpha = Math.max(alpha, score);
                if (alpha >= beta) {
                    cutOffCounter++;
                    break;
                }
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
                if (timer.timesUp()) break;
                child.minimizeBestScoreSoFar(newScore);
                if (newScore < score) {
                    score = newScore;
                }
                beta = Math.min(beta, score);
                if (alpha >= beta) {
                    cutOffCounter++;
                    break;
                }
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
        timer = new Timer(state.dimension);
        Node root = new Node(state, null);

        Move bestMoveSoFar = null;
        int depthsFullyDiscovered = 0;
        for (int depth = 1; depth <= 100; depth++) {
            cutOffCounter = 0;
            leafCount = 0;
            //root = new Node(state, null);
            minimax(root, depth, -INF, INF);
//            System.out.println("Depth " + depth + " nodes: " + leafCount + " total cutoff: " + cutOffCounter);
            if (timer.timesUp()) {
                break;
            } else {
                depthsFullyDiscovered++;
                bestMoveSoFar = root.getChildren().get(0).getPreviousMove();
                if (Math.abs(root.getChildren().get(0).getBestScoreSoFar()) == INF) break;
            }
        }
//        System.out.println("Depths full discovered: " + depthsFullyDiscovered);
//        System.out.println("time taken " + timer.timePassed());
        return bestMoveSoFar;
    }

    @Override
    public String AgentName() {
        return "Minimax";
    }
}
