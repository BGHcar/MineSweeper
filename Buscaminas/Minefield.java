package Game.Buscaminas;

import java.util.Random;

public class Minefield {
    private final Cell[][] field;
    private final int rows;
    private final int cols;
    private final int totalMines;

    public Minefield(int rows, int cols, int totalMines) {
        this.rows = rows;
        this.cols = cols;
        this.totalMines = totalMines;
        field = new Cell[rows][cols];
        initializeField();
        placeMines();
        calculateNumbers();
    }

    private void initializeField() {
        for (int r = 0; r < rows; r++) {
            for (int c = 0; c < cols; c++) {
                field[r][c] = new Cell();
            }
        }
    }

    private void placeMines() {
        Random rand = new Random();
        int minesPlaced = 0;

        while (minesPlaced < totalMines) {
            int r = rand.nextInt(rows);
            int c = rand.nextInt(cols);
            if (!field[r][c].hasMine()) {
                field[r][c].setMine(true);
                minesPlaced++;
            }
        }
    }

    private void calculateNumbers() {
        for (int r = 0; r < rows; r++) {
            for (int c = 0; c < cols; c++) {
                if (!field[r][c].hasMine()) {
                    int count = countAdjacentMines(r, c);
                    field[r][c].setAdjacentMines(count);
                }
            }
        }
    }

    private int countAdjacentMines(int r, int c) {
        int count = 0;
        for (int i = r - 1; i <= r + 1; i++) {
            for (int j = c - 1; j <= c + 1; j++) {
                if (i >= 0 && j >= 0 && i < rows && j < cols && field[i][j].hasMine()) {
                    count++;
                }
            }
        }
        return count;
    }

    public Cell getCell(int row, int col) {
        return field[row][col];
    }

    public boolean isMine(int row, int col) {
        return field[row][col].hasMine();
    }

    public int getRows() {
        return rows;
    }

    public int getCols() {
        return cols;
    }
}
