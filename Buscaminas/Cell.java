package Game.Buscaminas;

public class Cell {
    private boolean mine;
    private int adjacentMines;
    private boolean revealed;

    public Cell() {
        this.mine = false;
        this.adjacentMines = 0;
        this.revealed = false;
    }

    public boolean hasMine() {
        return mine;
    }

    public void setMine(boolean mine) {
        this.mine = mine;
    }

    public int getAdjacentMines() {
        return adjacentMines;
    }

    public void setAdjacentMines(int adjacentMines) {
        this.adjacentMines = adjacentMines;
    }

    public boolean isRevealed() {
        return revealed;
    }

    public void reveal() {
        this.revealed = true;
    }
}
