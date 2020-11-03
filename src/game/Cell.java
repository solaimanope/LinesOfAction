package game;

import java.util.Objects;

public class Cell {
    public final int row, column;
    public Cell(int row, int column) {
        this.row = row;
        this.column = column;
    }

    @Override
    public String toString() {
        char x = (char)(row+'1');
        char y = (char)(column+'A');
        return ""+y+x;
    }

    public static Cell fromString(String string) {
        if (string.length() != 2) return null;
        int y = string.charAt(0)-'A';
        int x = string.charAt(1)-'1';
        return new Cell(x, y);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Cell cell = (Cell) o;
        return row == cell.row && column == cell.column;
    }
}
