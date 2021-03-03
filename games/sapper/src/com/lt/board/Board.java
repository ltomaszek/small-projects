package com.lt.board;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Board {

    private final int ROWS;
    private final int COLUMNS;
    private final Cell[][] cells;
    private int BOMBS;
    public int hiddenNotBombCells;

    public Board (int rows, int columns, int bombs) {
        if (rows <= 0 || columns <= 0 || bombs < 0)
            throw new IllegalArgumentException();
        this.ROWS = rows;
        this.COLUMNS = columns;
        this.BOMBS = bombs;
        this.hiddenNotBombCells = ROWS * COLUMNS - bombs;
        this.cells = new Cell[ROWS][COLUMNS];
        this.init();
    }

    /** initialize board by generating all cells and bombs */
    public void init() {
        new Generator().generate();
    }

    public int getNumberRows() {
        return ROWS;
    }

    public int getNumberColumns() {
        return COLUMNS;
    }

    public Cell getCell(int row, int column) {
        return this.cells[row][column];
    }

    public boolean areAnyNumberCellsHidden() {
        return hiddenNotBombCells > 0;
    }

    public boolean isInBounds(int row, int column) {
        return row >= 0 && column >= 0 && row < ROWS && column < COLUMNS;
    }

    public Cell.Type flip(int row, int column) {
        Cell cell = cells[row][column];

        if (cell.isVisible)
            return cell.getType();

        if (cell.isMarked)
            throw new IllegalArgumentException("Can not flip marked cell!");

        if (cell.getType() != Cell.Type.BOMB) {
            hiddenNotBombCells--;
        }
        return cell.flip();
    }

    public void mark(int row, int column) {
        cells[row][column].mark();
    }

    /** inner class Cell */
    public final static class Cell {

        public enum Type {
            BOMB, NUMBER, BLANK
        }

        private boolean isVisible;
        private boolean isMarked;
        private int value;

        public boolean isVisible() {
            return isVisible;
        }

        public boolean isMarked() {
            return isMarked;
        }

        /** type will be only returnet if the cell is not hidden, otherwise null will be returned */
        public Type getType() {
            if (!isVisible)
                return null;

            switch (value) {
                case -1: return Type.BOMB;
                case 0: return Type.BLANK;
                default: return Type.NUMBER;
            }
        }

        private Type getRealType() {
            switch (value) {
                case -1: return Type.BOMB;
                case 0: return Type.BLANK;
                default: return Type.NUMBER;
            }
        }

        /** value will be only returnet if the cell is not hidden, otherwise Integer.Min_Value will be returned */
        public int getValue() {
            return isVisible ? value : Integer.MIN_VALUE;
        }

        private Type flip() {
            isVisible = true;
            return getType();
        }

        private void mark() {
            isMarked = !isMarked;
        }

        private void setToBomb() {
            this.value = -1;
        }

    }

    /** inner class Generator */
    private class Generator {
        void generate() {
            createDefaultCells();
            List<int[]> bombsCoor = generateBombs();
            updateBombsNeighbours(bombsCoor);
        }

        void createDefaultCells() {
            for (int r = 0; r < ROWS; r++) {
                for (int c = 0; c < COLUMNS; c++) {
                    cells[r][c] = new Cell();
                }
            }
        }

        List<int[]> generateBombs() {
            int numOfAllCells = ROWS * COLUMNS;
            List<Integer> allIndexes = IntStream
                    .range(0, numOfAllCells)
                    .mapToObj(x -> x)
                    .collect(Collectors.toCollection(ArrayList::new));

            // shuffle indexes and the first that are in num of bomb range set to bombs
            Collections.shuffle(allIndexes);

            List<int[]> bombCoor = new ArrayList<>(BOMBS);
            for (int i = 0; i < BOMBS; i++) {
                int cellIdx = allIndexes.get(i);
                int row = cellIdx / COLUMNS;
                int column = cellIdx % COLUMNS;

                // set cell to bomb and add to list
                Cell cell = cells[row][column];
                cell.setToBomb();
                bombCoor.add(new int[]{row, column});
            }

            return bombCoor;
        }

        void updateBombsNeighbours(List<int[]> bombsCoor) {
            int[][] deltas = {
                    {-1, -1}, {-1, 0}, {-1, 1},
                    {0, -1},            {0, 1},
                    {1, -1}, {1, 0}, {1, 1}
            };

            for (int[] coor : bombsCoor) {
                for (int[] delta : deltas) {
                    int r = coor[0] + delta[0];
                    int c = coor[1] + delta[1];

                    if (!isInBounds(r, c))
                        continue;

                    Cell neighbour = cells[r][c];
                    if (neighbour.getRealType() == Cell.Type.BOMB)
                        continue;

                    neighbour.value++;
                }
            }
        }
    }
}
