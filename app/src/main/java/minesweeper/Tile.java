package minesweeper;

import java.util.ArrayList;
import java.util.List;

public class Tile implements TileObserver {
    private boolean hidden;
    private List<Tile> neighbors;
    private List<TileObserver> observers;
    private int neighborWithMineCount;
    private boolean isMine;
    private boolean isFlagged;

    public Tile() {
        this.hidden = true;
        this.neighbors = new ArrayList<>();
        this.observers = new ArrayList<>();
        this.neighborWithMineCount = 0;
        this.isMine = false;
        this.isFlagged = false;
    }

    // ---- Observer logic ----

    public void addObserver(TileObserver observer) {
        observers.add(observer);
    }

    private void notifyObservers() {
        for (TileObserver observer : observers) {
            observer.onTileChanged();
        }
    }

    @Override
    public void onTileChanged() {
        updateStatus();
    }

    // ----- Neighbor Management -----

    private void addNeighbor(Tile neighbor) {
        this.neighbors.add(neighbor);
    }

    public void connect(Tile neighbor) {
        this.addNeighbor(neighbor);
        neighbor.addNeighbor(this);

        this.addObserver(neighbor);
        neighbor.addObserver(this);
    }

    // ----- Mine Logic -----

    void placeMine() {
        this.isMine = true;
        // set neighborWithMineCount to avoid tile being marked as "empty"
        this.neighborWithMineCount = -1;
        // Tell neighbors to update their status number
        notifyObservers();
    }

    public void updateStatus() {
        if (!this.isMine) {
            // Count number of neighbors with a mine
            int neighborsWithMine = 0;
            for (Tile neighbor : neighbors)
                if (neighbor.isMine())
                    neighborsWithMine++;
            this.neighborWithMineCount = neighborsWithMine;
        }
    }

    // ----- "Hidden State" logic -----

    private void revealNeighbors() {
        for (Tile neighbor : neighbors) {
            if (!neighbor.isMine() && neighbor.isHidden()) neighbor.click();
        }
    }

    public boolean click() {
        System.out.println("CLICK -> Tile@" + this.hashCode()
                + " | mine=" + isMine
                + " | hidden=" + hidden
                + " | count=" + getCount());

        this.hidden = false;
        // return "true", end game if tile is a mine
        if (this.isMine()) return true;

        // Cascading reveal of empty tiles
        if (this.isEmpty()) revealNeighbors();

        return false;
    }

    // ----- Right Click Flag logic -----
    public void toggleFlag(){
        if(hidden){
            isFlagged = !isFlagged;
        }
    }

    // ----- Getters -----

    public boolean isHidden() { return this.hidden;}

    public boolean isMine() {
        return this.isMine;
    }

    public boolean isFlagged() { return this.isFlagged; }

    public boolean isEmpty() {
        return (this.neighborWithMineCount == 0);
    }

    public int getCount() {
        return this.neighborWithMineCount;
    }

    public int numNeighbors() {
        return neighbors.size();
    }

}
