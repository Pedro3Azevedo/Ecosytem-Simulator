package outros;

import ca.Cell;
import ca.CellularAutomata;
import ca.IProcessingApp;
import processing.core.PApplet;
import tools.SubPlot;

public class JogoDaVida implements IProcessingApp {

    private int nrows = 15;
    private int ncols = 20;
    private int nStates = 2;
    private int radiusNeigh = 1;
    private CellularAutomata ca;
    private boolean on = false;
    private char rule = 'z';
    private SubPlot plt;
    private double[] window = {0, 10, 0, 10};
    private float[] viewport = {0.3f, 0.3f, 0.5f, 0.6f};

    public void setup(PApplet p) {
    	plt = new SubPlot(window, viewport, p.width, p.height);
        ca = new CellularAutomata(p, plt, nrows, ncols,nStates, radiusNeigh);
    }

    public void draw(PApplet p, float dt) {
    	/*  ca.display(p);

        if (on) {
            for (int i = 0; i < nrows; i++) {
                for (int j = 0; j < ncols; j++) {
                    Cell cell = ca.getCells()[i][j];
                    Cell[] neigh = cell.getNeighbors();

                    int nAlive = 0;

                    for (int ii = 0; ii < neigh.length; ii++) {
                        if (neigh[ii].getState() == 1) nAlive++;
                    }
                    if(rule == 'z'){
                        rule_23_3(p, cell, nAlive);
                    }
                    else if(rule == 'x'){
                         rule_23_36(p, cell, nAlive);
                    }
                    else if(rule == 'c'){
                        rule_majority(p, cell, nAlive);
                    }
                }

            }
            for (int i = 0; i < nrows; i++) {
                for (int j = 0; j < ncols; j++) {
                    Cell cell = ca.getCells()[i][j];
                    cell.setState(cell.getNextState());
                }
            }
            //on = false;
        }*/

    }

    /*public void rule_23_3(PApplet p, Cell cell, int nAlive) {
        if (cell.getState() == 1) {
            if (nAlive < 2 || nAlive > 3) cell.setNextState(0);
            else cell.setNextState(1);

        } else if (cell.getState() == 0) {
            if (nAlive == 3) {
                cell.setCellColor(p, false);
                cell.setNextState(1);
            } else cell.setNextState(0);
        }
    }

    public void rule_23_36(PApplet p, Cell cell, int nAlive) {
        if (cell.getState() == 1) {
            if (nAlive < 2 || nAlive > 3) cell.setNextState(0);
            else cell.setNextState(1);

        } else if (cell.getState() == 0) {
            if (nAlive == 3 || nAlive == 6) {
                cell.setCellColor(p, false);
                cell.setNextState(1);
            } else cell.setNextState(0);
        }
    }

    public void rule_majority(PApplet p, Cell cell, int nAlive) {
        if (nAlive > cell.getNeighbors().length / 2) {
            cell.setCellColor(p, false);
            cell.setNextState(1);
        } else if (nAlive < cell.getNeighbors().length / 2) {
            cell.setNextState(0);
        }

    }*/


    public void keyPressed(PApplet p) {
        if (p.key == ' ' && !on) {
            on = true;
        } else if (p.key == ' ' && on) {
            on = false;
        } else if (p.key == 'r') {
            ca.initRandom();
        } else if (p.key == 'd') {
            for (int i = 0; i < nrows; i++) {
                for (int j = 0; j < ncols; j++) {
                    Cell cell = ca.getCells()[i][j];
                    cell.setState(0);
                }
            }
        } else if (p.key == 'l') {
            for (int i = 0; i < nrows; i++) {
                for (int j = 0; j < ncols; j++) {
                    Cell cell = ca.getCells()[i][j];
                    cell.setState(1);
                }
            }
        }
        else if(p.key == 'z'){
            rule = 'z';
        }
        else if(p.key == 'x'){
            rule = 'x';
        }
        else if(p.key == 'c'){
            rule = 'c';
        }


    }

    public void mousePressed(PApplet p) {
        Cell cell = ca.pixel2Cell(p.mouseX, p.mouseY);
        if (cell.getState() == 0) cell.setState(1);
        else cell.setState(0);
        ca.display(p);

    }

}
