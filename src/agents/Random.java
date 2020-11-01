package agents;

import game.Move;
import game.State;
import ui.Main;

import java.util.Vector;

public class Random extends Agent {
    public Random(int pieceColor) {
        super.pieceColor = pieceColor;
    }

    @Override
    public Move makeMove(State state) {
        Vector<Move> moves = state.allAvailableMoves();
        int idx = Main.random.nextInt(moves.size());
        return moves.get(idx);
    }
}
