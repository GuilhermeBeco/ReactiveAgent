package reactiveAgent;

import java.awt.Color;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class Environment {

    private final Cell[][] grid;
    private final List<ReactiveAgent> agents;
    private final int numIterations;

    public Environment(int numLines, int numColumns, int numIterations) {
        this.numIterations = numIterations;
        grid = new Cell[numLines][numColumns];
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[0].length; j++) {
                grid[i][j] = new Cell(i, j);
            }
        }

        // todo modify to place walls
        grid[2][2].setWall(new Wall());
        grid[2][3].setWall(new Wall());
        grid[2][4].setWall(new Wall());
        grid[5][2].setWall(new Wall());

        // todo modify to place more agents
        agents = new LinkedList<>();
        agents.add(new ReactiveAgent(getCell(6, 3)));
//        agents.add(new ReactiveAgent(getCell(8, 3)));
//        agents.add(new ReactiveAgent(getCell(8, 7)));
    }

    public void run() {
        for (int i = 0; i < numIterations; i++) {
            for (Agent agent : agents) {
                agent.act(this);
                fireUpdatedEnvironment();
            }
        }
    }

    public Cell getNorthCell(Cell cell) {
        return (cell.getLine() > 0)? grid[cell.getLine() - 1][cell.getColumn()] : null;
    }

    public boolean hasNorthCell(Cell cell){
        return getNorthCell(cell) != null;
    }

    public Cell getSouthCell(Cell cell) {
        return (cell.getLine() < grid.length - 1)? grid[cell.getLine() + 1][cell.getColumn()] : null;
    }

    public boolean hasSouthCell(Cell cell){
        return getSouthCell(cell) != null;
    }

    public Cell getEastCell(Cell cell) {
        return (cell.getColumn() < grid[0].length - 1)? grid[cell.getLine()][cell.getColumn() + 1] : null;
    }

    public boolean hasEastCell(Cell cell){
        return getEastCell(cell) != null;
    }

    public Cell getWestCell(Cell cell) {
        return (cell.getColumn() > 0)? grid[cell.getLine()][cell.getColumn() - 1] : null;
    }

    public boolean hasWestCell(Cell cell){
        return getWestCell(cell) != null;
    }

    public int getNumLines() {
        return grid.length;
    }

    public int getNumColumns() {
        return grid[0].length;
    }

    public final Cell getCell(int linha, int coluna) {
        return grid[linha][coluna];
    }

    public Color getCellColor(int linha, int coluna) {
        return grid[linha][coluna].getColor();
    }

    @Override
    public String toString() {
        StringBuilder s = new StringBuilder("Environment:\n");
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[0].length; j++) {
                if (grid[i][j].hasWall()) {
                    s.append('X');
                } else if (grid[i][j].hasAgent()) {
                    s.append('A');
                } else {
                    s.append('.');
                }
            }
            s.append('\n');
        }
        return s.toString();
    }

    //listeners
    private final ArrayList<EnvironmentListener> listeners = new ArrayList<EnvironmentListener>();

    public synchronized void addEnvironmentListener(EnvironmentListener l) {
        if (!listeners.contains(l)) {
            listeners.add(l);
        }
    }

    public void fireUpdatedEnvironment() {
        for (EnvironmentListener listener : listeners) {
            listener.environmentUpdated();
        }
    }
}
