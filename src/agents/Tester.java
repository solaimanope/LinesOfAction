package agents;

import agents.heuristics.*;
import game.State;
import referee.Referee;
import ui.Main;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Tester {
    public static void main(String[] args) {
        Scanner scanner = Main.scanner;
        int dimension = 8;
//        while (dimension != 6 && dimension != 8) {
//            System.out.println("input dimension (6 or 8)");
//            dimension = scanner.nextInt();
//            scanner.nextLine();
//        }

        HashMap<String, Integer>fr = new HashMap<>();
        double _m = 0.05;
        double _c = 0.05;
        for (int scenario = 0; scenario <= 10; scenario++) {
            long startTime = System.currentTimeMillis();
            Minimax blackAgent = new Minimax(State.BLACK);
            blackAgent.addHeuristics(new MaximizePositionalScore(), 1);
//            blackAgent.addHeuristics(new MaximizeDensity(), 1);
//            blackAgent.addHeuristics(new MaximizeQuadCount(), scenario*_m+_c);

            Minimax whiteAgent = new Minimax(State.WHITE);
//            whiteAgent.addHeuristics(new MaximizeDensity(), 1);
            whiteAgent.addHeuristics(new MaximizeUnweightedMobility(), 1);

            Referee referee = new Referee(dimension, blackAgent, whiteAgent, null);
            String winner = referee.conductGame();
            System.out.println((scenario*_m+_c) + " Winner: " + winner + " " + (System.currentTimeMillis()-startTime) +"ms");
            if (fr.containsKey(winner)) {
                fr.put(winner, fr.get(winner)+1);
            } else {
                fr.put(winner, 1);
            }
        }

        System.out.println();
        for (Map.Entry<String, Integer> pair : fr.entrySet()) {
            System.out.println(pair.getKey() + " wins " + pair.getValue());
        }
    }
}


/**
 MaximizePositionalScore:MinimumBoundingBox=1:[0.5,0.8]
 MaximizePositionalScore:MaximizeUnweightedMobility=1:[0.13,0.36]
 MaximizePositionalScore:MaximizeDensity=[0.1,1]:1
 MaximizePositionalScore:MaximizeQuadCount=1:[0.01,0.1]
 */