package reactiveAgent;

import java.awt.Color;

public class Cell {
    private final int line, column;
    private ReactiveAgent agent;
    private Wall wall;
    private Color color = Color.white;
    private Garbage garbage;


    public Cell(int line, int column) {
        this.line = line;
        this.column = column;
    }

    public Color getColor() {
        if (agent != null) {
            return agent.getColor();
        }
        if (wall != null) {
            return wall.getColor();
        }
        if (garbage!=null){
            return garbage.getColor();
        }
        return Color.WHITE;
    }
    public  void setColor(Color color){
        this.color=color;
    }

    public ReactiveAgent getAgent() {
        return agent;
    }

    public void setAgent(ReactiveAgent agent) {
        this.agent = agent;
    }

    public boolean hasAgent() {
        return agent != null;
    }

    public Wall getWall() {
        return wall;
    }

    public void setWall(Wall wall) {
        this.wall = wall;
    }

    public boolean hasWall() {
        return wall != null;
    }
    public void setGarbage(Garbage garbage) {
        this.garbage = garbage;
    }

    public Garbage getGarbage() {
        return garbage;
    }

    public boolean hasGarbage() {
        return garbage != null;
    }


     public int getLine() {
        return line;
    }

    public int getColumn() {
        return column;
    }
}