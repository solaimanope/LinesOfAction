package agents;

import game.Cell;
import game.Move;
import game.State;
import ui.Main;

import java.util.Vector;

public class HumanConsole extends Agent {
    public HumanConsole(int pieceColor) {
        super.pieceColor = pieceColor;
    }

    @Override
    public Move makeMove(State state) {
        int dimension = state.dimension;

        System.out.println("Your move (" + designatedColor() + ")");

        System.out.print("#");
        for (int i = 0; i < dimension; i++) System.out.print((char)('A'+i));
        System.out.println();
        for (int i = 0; i < dimension; i++) {
            System.out.print(i+1);
            for (int j = 0; j < dimension; j++) {
                if (state.board[i][j] == State.NONE)        System.out.print('O');
                else if (state.board[i][j] == State.BLACK)  System.out.print('B');
                else if (state.board[i][j] == State.WHITE)  System.out.print('W');
            }
            System.out.println();
        }
        System.out.println("Print source cell");

        Cell source;
        while (true) {
            String string = Main.scanner.nextLine();
            source = Cell.fromString(string);
            System.out.println("got " + source.row + "," + source.column);
            if (source != null && state.isValid(source) && state.board[source.row][source.column] == pieceColor) {
                ///valid source
                break;
            } else {
                System.out.println("INVALID SOURCE. TRY AGAIN!");
            }
        }

        Vector<Move>moves = state.availableMovesAt(source);
        System.out.print("Available moves at " + source + " :");
        for (Move move : moves) {
            System.out.print(" " + move.destination);
        }
        System.out.println();

        System.out.println("Print destination cell");

        Cell destination;
        while (true) {
            String string = Main.scanner.nextLine();
            destination = Cell.fromString(string);
            if (destination != null && state.isValidMove(source, destination)) {
                ///valid source
                break;
            } else {
                System.out.println("INVALID DESTINATION. TRY AGAIN!");
            }
        }

        System.out.println("[HumanConsole] Move from " + source + " to " + destination);
        return new Move(source, destination);
    }
    @Override
    public String AgentName() {
        return "HumanConsole";
    }
}
