package com.agreem.connect4;

public class Board {
    private String[][] cells;
    private final int consecutiveToWin;
    private final int row;
    private final int column;

    public Board(int row, int column, int consecutiveToWin) throws BusinessLogicException {
        this.row = row;
        this.column = column;
        if (row <= 0 || column <= 0 || consecutiveToWin <= 0) {
            throw new BusinessLogicException("row, column and win count can't be less or equals to zero");
        }
        this.consecutiveToWin = consecutiveToWin;
        initiateBoard();
    }

    public void initiateBoard() {
        this.cells = new String[row][column];
    }

    public boolean play(Player player, int column) throws BusinessLogicException {
        column -= 1;
        int base = 0;
        if (!(column < cells[0].length)) {
            throw new BusinessLogicException("Column size must be within 1 to " + cells.length);
        }
        while (cells[base][column] != null) {
            base++;
            if (!(base < cells.length)) {
                throw new BusinessLogicException("No empty cell available on this column");
            }
        }
        cells[base][column] = player.getColor();
        return isWin(base, column);
    }

    @Override
    public String toString() {
        String dashLine = printDashLine(cells[0].length * 2 + 1);
        String output = "";
        for (int i = 0; i < cells.length; i++) {
            output = printEachRow(cells[i]) + dashLine + output;
        }
        return output;
    }

    // checking connected pieces horizontally, vertically and diagonally
    private boolean isWin(int row, int column) {
        String element = cells[row][column];
        int horizontal = 1 + countSameElement(element, row, column, -1, 0)
                + countSameElement(element, row, column, 1, 0);
        if (horizontal >= consecutiveToWin) {
            return true;
        }
        int vertical = 1 + countSameElement(element, row, column, 0, -1)
                + countSameElement(element, row, column, 0, 1);
        if (vertical >= consecutiveToWin) {
            return true;
        }
        int diagonal1 = 1 + countSameElement(element, row, column, -1, -1)
                + countSameElement(element, row, column, 1, 1);
        if (diagonal1 >= consecutiveToWin) {
            return true;
        }
        int diagonal2 = 1 + countSameElement(element, row, column, -1, 1)
                + countSameElement(element, row, column, 1, -1);
        if (diagonal2 >= consecutiveToWin) {
            return true;
        }
        return false;
    }

    private int countSameElement(String element, int row, int column, int deltaRow, int deltaColumn) {
        int count = 0;
        int newRow = row + deltaRow;
        int newColumn = column + deltaColumn;
        while ((notInRange(newRow, cells.length) && notInRange(newColumn, cells[0].length))
                && element.equals(cells[newRow][newColumn])) {
            newRow += deltaRow;
            newColumn += deltaColumn;
            count++;
        }
        return count;
    }

    private boolean notInRange(int number, int right) {
        return (number >= 0 && number < right);
    }

    private String printDashLine(int size) {
        String output = "";
        for (int i = 0; i < size; i++) {
            output += "-";
        }
        return output + "\n";
    }

    private String printEachRow(String[] row) {
        String output = "|";
        for (int i = 0; i < row.length; i++) {
            String rowValue = row[i];
            if (rowValue == null) {
                rowValue = " ";
            }
            output += rowValue + "|";
        }
        return output + "\n";
    }
}