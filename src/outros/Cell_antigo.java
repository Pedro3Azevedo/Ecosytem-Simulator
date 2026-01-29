package outros;

import ca.CellularAutomata;
import processing.core.PApplet;

public class Cell_antigo {
    private int row, col;
    private int state;
    private int nextState;
    private Cell_antigo[] neighbors;
    private CellularAutomata ca;
    private int[] cellColors = new int[2];

    public Cell_antigo(PApplet p, CellularAutomata ca, int row, int col) {
        this.ca = ca;
        this.row = row;
        this.col = col;
        this.state = 0;
        this.nextState = 0;
        this.neighbors = null;
        //setCellColor(p, true);
    }

    public void setCellColor(PApplet p, boolean rand){

        int colorAlive;
        if(rand){
            int R = 0, G = 0, B = 0;
            R = (int) (Math.random() * 256);
            G = (int) (Math.random() * 256);
            B = (int) (Math.random() * 256);
            colorAlive = p.color(R, G, B);
        }
        else{
            int dominantC = this.neighbors[0].getCellColors()[1];
            int rec = 0;
            for(int i = 0; i < this.neighbors.length; i++){
                if(this.neighbors[i].getState() == 1) {
                    int nIguais = -1;
                    for (int j = 0; j < this.neighbors.length; j++) {
                        if (this.neighbors[i].getCellColors()[1] == this.neighbors[j].getCellColors()[1]) {
                            nIguais++;
                        }
                    }

                    if (nIguais >= rec) {
                        rec = nIguais;
                        dominantC = this.neighbors[i].getCellColors()[1];
                    }
                }
            }
            colorAlive = dominantC;

        }

        cellColors[0] = p.color(255, 255, 255);
        cellColors[1] = colorAlive;
    }

    public int[] getCellColors() {
        return cellColors;
    }

    public void setNeighbors(Cell_antigo[] neigh) {
        this.neighbors = neigh;
    }

    public Cell_antigo[] getNeighbors() {
        return this.neighbors;
    }

    public void setState(int state) {
        this.state = state;
    }

    public int getState() {
        return this.state;
    }

    public void setNextState(int state){this.nextState = state;}

    public int getNextState(){return this.nextState;}

    public void display(PApplet p){
    	/*p.pushStyle();
        p.fill(getCellColors()[state]);
        p.rect(ca.xmin+col*ca.cellWidth, ca.ymin+row* ca.cellHeight,ca.cellWidth, ca.cellHeight);
        p.popStyle();*/
    }


}
