package reactiveAgent;

import java.awt.Color;
import java.util.Collections;

public class ReactiveAgent implements Agent {

    private Cell cell;

    public ReactiveAgent(Cell cell) {
        this.cell = cell;
        this.cell.setAgent(this);
    }

    public void act(Environment environment) {
        Perception perception = buildPerception(environment);
        Action action = decide(perception);
        execute(action, environment);
    }

    public Cell getCell() {
        return cell;
    }

    public void setCell(Cell cell) {
        this.cell.setAgent(null);
        this.cell = cell;
        this.cell.setAgent(this);
    }

    public Color getColor() {
        return Color.BLACK;
    }

    private Perception buildPerception(Environment environment) {
        return new Perception(
                environment.getNorthCell(cell),
                environment.getSouthCell(cell),
                environment.getEastCell(cell),
                environment.getWestCell(cell));
    }

    private Action decide(Perception perception) {

     if(perception.getN()!=null&&perception.getN().hasGarbage()){
         return Action.NORTH;
     }
     if(perception.getS()!=null&&perception.getS().hasGarbage()){
         return Action.SOUTH;
     }
     if(perception.getE()!=null&&perception.getE().hasGarbage()){
         return Action.EAST;
     }
     if(perception.getW()!=null&&perception.getW().hasGarbage()){
         return Action.WEST;
     }
    int totalN,totalE,totalS,totalW;
    totalN=totalE=totalS=totalW=Integer.MAX_VALUE;
    if(perception.getN()!=null && !perception.getN().hasWall() && !perception.getN().hasAgent())
        totalN= Collections.frequency(visitedCells,perception.getN());
    if(perception.getE()!=null && !perception.getE().hasWall() && !perception.getE().hasAgent())
        totalE=Collections.frequency(visitedCells,perception.getE());
    if(perception.getW()!=null && !perception.getW().hasWall() && !perception.getW().hasAgent())
        totalW=Collections.frequency(visitedCells,perception.getW());
    if(perception.getS()!=null && !perception.getS().hasWall() && !perception.getS().hasAgent())
        totalS=Collections.frequency(visitedCells,perception.getS());
    if(totalN<=totalE && totalN<= totalS && totalN<= totalW)
        return Action.NORTH;
    if(totalS<= totalS && totalE<= totalW)
           return Action.EAST;
     if(totalS<= totalW)
        return Action.SOUTH;


     return Action.WEST;


    }

    private void execute(Action action, Environment environment) {

        // todo modify to improve the ReactiveAgent's decision process
        
        Cell nextCell = null;

        if (action == Action.NORTH && environment.hasNorthCell(cell)) {
            nextCell = environment.getNorthCell(cell);
        } else if (action == Action.SOUTH && environment.hasSouthCell(cell)) {
            nextCell = environment.getSouthCell(cell);
        } else if (action == Action.WEST && environment.hasWestCell(cell)) {
            nextCell = environment.getWestCell(cell);
        } else if (action == Action.EAST && environment.hasEastCell(cell)) {
            nextCell = environment.getEastCell(cell);
        }


        int total=Collections.frequency(visitedCells,cell);
        switch(total){
            case 1:
                cell.setColor(Color.LIGHT_GRAY);
                break;
            case 2:
                cell.setColor(Color.GRAY);
                break;
            default:
                cell.setColor(Color.darkGray);
        }
    }
}
