package game;

import java.util.*;

public class State {
    public static final int NONE = -1;
    public static final int BLACK = 0;
    public static final int WHITE = 1;

    public int[][] board;
    public final int dimension;
    public int currentPlayer;

    @Override
    public int hashCode() {
        int result = Objects.hash(dimension, currentPlayer);
        result = 31 * result + Arrays.hashCode(board);
        return result;
    }

    private List<Cell>[] pieces;

    public State(int dimension) {
        this.dimension = dimension;
        board = new int[dimension][dimension];

        for (int i = 0; i < dimension; i++) {
            for (int j = 0; j < dimension; j++) {
                board[i][j] = NONE;
            }
        }
        for (int x = 1; x+1 < dimension; x++) {
            board[0][x] = BLACK;
            board[dimension-1][x] = BLACK;
            board[x][0] = WHITE;
            board[x][dimension-1] = WHITE;
        }
        currentPlayer = BLACK;
        pieces = new List[2];
        movesAt = new Vector[dimension][dimension];
        allMoves = null;
    }
    public State(State other) {
        ///create a deep copy of other object
        this.dimension = other.dimension;
        board = new int[dimension][dimension];

        for (int i = 0; i < dimension; i++) {
            for (int j = 0; j < dimension; j++) {
                board[i][j] = other.board[i][j];
            }
        }
        currentPlayer = other.currentPlayer;
        pieces = new List[2];
        movesAt = new Vector[dimension][dimension];
        allMoves = null;
    }
    public State makeMove(Move move) {
        State newState = new State(this);
        newState.board[move.source.row][move.source.column] = NONE;
        newState.board[move.destination.row][move.destination.column] = currentPlayer;
        newState.currentPlayer = otherPlayer();
        return newState;
    }

    public int gameEndStatus() {
        if (isConnected(otherPlayer())) return otherPlayer();
        if (allAvailableMoves().isEmpty()) return otherPlayer();
        return NONE;
    }

    private int[][] directions = {{1, 1}, {-1, -1}, {1, 0}, {-1, 0}, {1, -1}, {-1, 1}, {0, 1}, {0, -1}};

    private Vector<Move>[][] movesAt;
    public Vector<Move> availableMovesAt(Cell source) {
        if (movesAt[source.row][source.column] != null) return movesAt[source.row][source.column];
        Vector<Move> moves = movesAt[source.row][source.column] = new Vector<>();
        if (board[source.row][source.column] != currentPlayer) return moves;

        for (int i = 0; i < directions.length; i += 2) {
            int jump =  countPieces(source, directions[i][0],   directions[i][1]) +
                        countPieces(source, directions[i+1][0], directions[i+1][1]) + 1;
            ///move to directions[i]
            Cell destination = new Cell(source.row+directions[i][0]*jump, source.column+directions[i][1]*jump);
            if (isValidMove(source, destination, directions[i])) {
                moves.add(new Move(source, destination));
            }
            ///move to directions[i+1]
            destination = new Cell(source.row+directions[i+1][0]*jump, source.column+directions[i+1][1]*jump);
            if (isValidMove(source, destination, directions[i+1])) {
                moves.add(new Move(source, destination));
            }
        }
        return moves;
    }

    private Vector<Move>allMoves;
    public Vector<Move>allAvailableMoves() {
        if (allMoves != null) return allMoves;
        allMoves = new Vector<>();
        for (int x = 0; x < dimension; x++) {
            for (int y = 0; y < dimension; y++) {
                if (board[x][y] == currentPlayer) {
                    Vector<Move>tmp = availableMovesAt(new Cell(x, y));
                    allMoves.addAll(tmp);
                }
            }
        }
        return allMoves;
    }

    public List<Cell> getPieces(int color) {
        if (pieces[color] == null) {
            pieces[color] = new LinkedList<>();
            for (int i = 0; i < dimension; i++) {
                for (int j = 0; j < dimension; j++) {
                    if (board[i][j] == color) {
                        pieces[color].add(new Cell(i, j));
                    }
                }
            }
        }
        return pieces[color];
    }

    private int countPieces(Cell source, int dx, int dy) {
        int x = source.row;
        int y = source.column;
        int ans = 0;
        while (true) {
            x += dx;
            y += dy;
            if (!isValid(x, y)) break;
            ans += board[x][y] != NONE ? 1 : 0;
        }
        return ans;
    }

    private boolean enemyBetween(Cell source, Cell destination, int[] dir) {
        int x = source.row;
        int y = source.column;
        for (int j = 1; ; j++) {
            x += dir[0];
            y += dir[1];
            if (x == destination.row && y == destination.column) break;
            if (board[x][y] == otherPlayer()) return true;
        }
        return false;
    }
    public boolean isValid(int x, int y) {
        if (x < 0 || x >= dimension) return false;
        if (y < 0 || y >= dimension) return false;
        return true;
    }
    public boolean isValidMove(Cell source, Cell destination, int[] dir) {
        if (!isValid(destination)) return false;
        if (board[destination.row][destination.column] == currentPlayer) return false;
        return !enemyBetween(source, destination, dir);
    }
    public boolean isValidMove(Cell source, Cell destination) {
        int dx = destination.row - source.row;
        int dy = destination.column - source.column;
        if (dx == 0 && dy == 0) return false;
        if (dx != 0 && dy != 0 && Math.abs(dx) != Math.abs(dy)) {
            return false;
        }
        int jump = Math.max(Math.abs(dx), Math.abs(dy));
        int vx = dx/jump;
        int vy = dy/jump;
        int idx = -1;
        for (int i = 0; i < directions.length; i++) {
            if (directions[i][0] == vx && directions[i][1] == vy) {
                idx = i;
                break;
            }
        }
        return isValidMove(source, destination, directions[idx]);
    }

    private int dfs(Cell at, int[][] vis) {
        int size = 1;
        vis[at.row][at.column] = 1;
        for (int[] dir : directions) {
            Cell to = new Cell(at.row+dir[0], at.column+dir[1]);
            if (isValid(to) && board[at.row][at.column] == board[to.row][to.column] && vis[to.row][to.column] == 0) {
                size += dfs(to, vis);
            }
        }
        return size;
    }

    public boolean isConnected(int player) {
        if (getPieces(player).size() <= 1) return true;
        return dfs(getPieces(player).get(0), new int[dimension][dimension]) == getPieces(player).size();
    }

    public boolean isValid(Cell cell) {
        return isValid(cell.row, cell.column);
    }
    public int otherPlayer() {
        return currentPlayer^1;
    }

    public Cell[] boundingBoxOf(int player) {
        int mnx = dimension, mny = dimension;
        int mxx = -1, mxy = -1;
        for (Cell cell : getPieces(player)) {
            mnx = Math.min(mnx, cell.row);
            mxx = Math.max(mxx, cell.row);
            mny = Math.min(mny, cell.column);
            mxy = Math.max(mxy, cell.column);
        }
        Cell cells[] = {new Cell(mnx, mny), new Cell(mxx, mxy)};
        return cells;
    }
}
