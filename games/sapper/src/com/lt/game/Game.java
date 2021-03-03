package com.lt.game;

import com.lt.board.Board;
import com.lt.board.BoardPrinter;
import java.util.ArrayDeque;
import java.util.Scanner;

public class Game {

    enum Status {
        WON, LOST, PLAYING
    }

    private Board board;
    private BoardPrinter boardPrinter;
    private Action action;
    private Status gameStatus;

    public void start() {
        System.out.println("\n\t *** SAPPER *** \n");

        Scanner scanner = new Scanner(System.in);
        System.out.print("Number of rows: ");
        int ROWS = Integer.parseInt(scanner.nextLine());
        System.out.print("Number of columns: ");
        int COLUMNS = Integer.parseInt(scanner.nextLine());
        System.out.println();

        init(ROWS, COLUMNS);
        printBoard();

        while (gameStatus == Status.PLAYING) {
            System.out.println("Give coordinations : (+ optional m/f - for mark/flip)");
            String strAction = scanner.nextLine();

            Board.Cell.Type cellType = null;

            if (convertToAction(strAction) == null)
                System.out.println("Invalid data!");
            else {
                if (!this.board.isInBounds(action.row, action.column))
                    System.out.println("invalid coordinates");
                else {
                    if (action.isFlip)
                        try {
                            cellType = board.flip(action.row, action.column);
                        } catch (IllegalArgumentException e) {
                            System.out.println("Can not flip marked cell!");
                        }
                    else
                        board.mark(action.row, action.column);
                }
            }

            if (cellType != null) {
                // update status after flipping
                switch (cellType) {
                    case BOMB:
                        gameStatus = Status.LOST;
                        break;
                    case BLANK:
                        flipNeighbourCellsIfBlank(action.row, action.column);
                        // no break
                    case NUMBER:
                        if (!board.areAnyNumberCellsHidden()) {
                            gameStatus = Status.WON;
                        }
                }
            }

            System.out.println();
            printBoard();
            System.out.println();
        }

        if (gameStatus == Status.WON) {
            System.out.println("Congratulations. You won");
        } else {
            System.out.println("You lost");
        }
    }

    private void init(int ROWS, int COLUMNS) {
        int BOMBS = ROWS * COLUMNS / 10;

        board = new Board(ROWS, COLUMNS, BOMBS);
        boardPrinter = new BoardPrinter();
        action = new Action();
        gameStatus = Status.PLAYING;
    }

    public void printBoard() {
        boardPrinter.printBoard(board);
    }

    /**
     * returns null if string is not correct
     */
    public Action convertToAction(String action) {
        String[] arr = action.split(" ");

        try {
            int row = Integer.parseInt(arr[0]);
            int column = Integer.parseInt(arr[1]);

            boolean isFlip = arr.length == 2 || arr[2].charAt(0) == 'f' ? true : false;

            this.action.set(row, column, isFlip);
            return this.action;

        } catch (NumberFormatException | ArrayIndexOutOfBoundsException e) {
            return null;
        }
    }

    /**
     * flipping all neighbour cells when current cell is blank and was not visible before
     *
     * @param row    - row coordinate of curren cell
     * @param column - column coorinate of current cell
     */
    private void flipNeighbourCellsIfBlank(int row, int column) {
        ArrayDeque<int[]> queue = new ArrayDeque<>();
        queue.offer(new int[]{row, column});

        int[][] deltas = {
                {-1, -1}, {-1, 0}, {-1, 1},
                {0, -1}, {0, 1},
                {1, -1}, {1, 0}, {1, 1}
        };

        // add elements to queue that are not visible
        while (!queue.isEmpty()) {
            int[] coor = queue.poll();
            Board.Cell cell = board.getCell(coor[0], coor[1]);
            Board.Cell.Type cellType = board.flip(coor[0], coor[1]);

            if (cellType != Board.Cell.Type.BLANK)
                continue;

            // add hidden neighbour
            for (int[] delta : deltas) {
                int r = coor[0] + delta[0];
                int c = coor[1] + delta[1];

                if (!board.isInBounds(r, c))
                    continue;

                cell = board.getCell(r, c);
                if (!cell.isVisible()) {
                    queue.add(new int[]{r, c});
                }
            }
        }
    }


    /**
     * Action class
     */
    private static class Action {
        private int row;
        private int column;
        private boolean isFlip;

        public void set(int row, int column, boolean flip) {
            this.row = row;
            this.column = column;
            this.isFlip = flip;
        }
    }
}
