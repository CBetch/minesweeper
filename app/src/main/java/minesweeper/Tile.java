package minesweeper;

import javax.naming.TimeLimitExceededException;
import java.util.ArrayList;
import java.util.List;

public class Tile {
    private boolean hidden;
    private List<Tile> neighbors;
    private int neighborWtihMineCount;
    private boolean isMine;

    public Tile() {
        this.hidden = true;
        this.neighbors = new ArrayList<>();
        this.neighborWtihMineCount = 0;
        this.isMine = false;
    }

    private void addNeighbor(Tile neighbor) {
        this.neighbors.add(neighbor);
    }

    public void connect(Tile neighbor) {
        this.addNeighbor(neighbor);
        neighbor.addNeighbor(this);
    }

    void placeMine() {
        this.isMine = true;
        // set neighborWtihMineCount to avoid tile being marked as "empty"
        this.neighborWtihMineCount = -1;
    }

    public void updateStatus() {
        if (!this.isMine) {
            // Count number of neighbors with a mine
            int neighborsWithMine = 0;
            for (Tile neighbor : neighbors)
                if (neighbor.isMine())
                    neighborsWithMine++;

            if (neighborsWithMine > 0) {
                this.neighborWtihMineCount = neighborsWithMine;
            }
        }
    }

    private void revealNeighbors() {
        for (Tile neighbor : neighbors) {
            if (!neighbor.isMine() && neighbor.isHidden()) neighbor.click();
        }
    }

    public boolean click() {
        this.hidden = false;
        // return "true", end game if tile is a mine
        if (this.isMine()) return true;

        // Cascading reveal of empty tiles
        if (this.isEmpty()) revealNeighbors();

        // TODO - Update display tile
        return false;
    }

    public boolean isHidden() {
        return this.hidden;
    }

    public boolean isMine() {
        return this.isMine;
    }

    public boolean isEmpty() {
        return (this.neighborWtihMineCount == 0);
    }

    public int getCount() {
        return this.neighborWtihMineCount;
    }

    public int numNeighbors() {
        return neighbors.size();
    }
}
