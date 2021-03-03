package com.lt.board;

public class BoardPrinter {

    private char bombSign = 'b';
    private char blankSign = ' ';
    private char numberSign;
    private char hiddenSign = '.';
    private char markedSign = 'x';

    public void printBoard(Board board) {
        int ROWS = board.getNumberRows();
        int COLUMNS = board.getNumberColumns();
        char SPACE = ' ';

        for (int r = 0; r < ROWS; r++) {
            System.out.printf("%-3d| ", r);
            for (int c = 0; c < COLUMNS; c++) {
                Board.Cell cell = board.getCell(r, c);

                char toPrint;
                if (!cell.isVisible()) {
                    if (cell.isMarked()) {
                        toPrint = markedSign;
                    } else {
                        toPrint = hiddenSign;
                    }
                } else {
                    switch (cell.getType()) {
                        case BOMB:
                            toPrint = bombSign;
                            break;
                        case NUMBER:
                            toPrint = (char) ('0' + cell.getValue());
                            break;
                        case BLANK:
                            toPrint = blankSign;
                            break;
                        default:
                            throw new IllegalArgumentException("No such Cell type");
                    }
                }

                System.out.print(toPrint);
                System.out.print(SPACE);
            }
            System.out.println();
        }

        // print bottom line
        for (int i = 0; i < COLUMNS * 2 + 5; i++) {
            System.out.print('-');
        }
        System.out.println();

        // print columns number
        System.out.print("     ");
        for (int i = 0; i < COLUMNS; i++) {
            if (i < 10)
                System.out.print(i);
            else
                System.out.print(i / 10);
            System.out.print(SPACE);
        }
        System.out.println();

        System.out.print("     ");
        for (int i = 0; i < COLUMNS; i++) {
            if (i < 10)
                System.out.print(" ");
            else
                System.out.print(i % 10);
            System.out.print(SPACE);
        }

        System.out.println();
    }
}
