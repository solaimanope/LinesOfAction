package agents.heuristics;

import game.State;

public abstract class Heuristics {
    public abstract double calculateScore(State state, int color);

    //default
    private double coefficient = 1;

    public double getCoefficient() {
        return coefficient;
    }

    public void setCoefficient(double coefficient) {
        this.coefficient = coefficient;
    }
}
