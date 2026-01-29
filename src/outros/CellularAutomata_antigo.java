package outros;

import processing.core.PApplet;
import tools.SubPlot;

import java.util.Random;

public class CellularAutomata_antigo {

    private int nrows;
    private int ncols;
    private int StateDead = 0;
    private int StateAlive = 1;
    private int radiusNeigh;
   // private Cell[][] cells;
    private int[] colors = new int[2];
    protected float cellWidth, cellHeight; //pixels
    protected float xmin, ymin;
    private Random rnd;
    private SubPlot plt;

    public CellularAutomata_antigo(PApplet p,SubPlot plt, int nrows, int ncols, int radiusNeigh) {
        this.nrows = nrows;
        this.ncols = ncols;
        this.radiusNeigh = radiusNeigh;
       // cells = new Cell[nrows][ncols];
        
        float[] bb = plt.getBoudingBox();
        xmin = bb[0];
        ymin = bb[1];
        
        cellWidth = bb[2] / ncols;
        cellHeight = bb[3] / nrows;
        this.plt = plt;
       // createCells(p);
        //setStateColors(p);
        rnd = new Random();
    }

    /* public Cell[][] getCells() {
        return this.cells;
    }

    public void setStateColors(PApplet p) {
        int R = (int) (Math.random() * 256);
        int G = (int) (Math.random() * 256);
        int B = (int) (Math.random() * 256);

        colors[0] = p.color(255, 255, 255);
        colors[1] = p.color(R, G, B);
    }

    public int[] getStateColors() {
        return colors;
    }

    private void createCells(PApplet p) {
        for (int i = 0; i < nrows; i++) {
            for (int j = 0; j < ncols; j++) {
//                cells[i][j] = new Cell(p,this, i, j);
            }
        }
        setMooreNeighbors();
    }

    public void initRandom() {

        for (int i = 0; i < nrows; i++) {
            for (int j = 0; j < ncols; j++) {
                if (rnd.nextInt(2) == 0) cells[i][j].setState(StateDead);
                else cells[i][j].setState(StateAlive);
            }
        }
    }

    //Coordenada do mundo e retorna a celula
    public Cell world2Cell(double x, double y) {
    	float[] xy = plt.getPixelCoord(x, y);
    	return pixel2Cell(xy[0], xy[1]);
    }
    
    //Através do pixel retorna a celula
    public Cell pixel2Cell(float x, float y) {
        int row = (int) ((y-ymin) / cellHeight);
        int col = (int) ((x-xmin) / cellWidth);
        if (row >= nrows) row = nrows - 1;
        if (col >= ncols) col = ncols - 1;
        if(row < 0) row = 0;
        if (col < 0) col = 0;
        return cells[row][col];
    }

    private void setMooreNeighbors() {
        int NN = 9;
        for (int i = 0; i < nrows; i++) {
            for (int j = 0; j < ncols; j++) {
                Cell[] neigh = new Cell[NN];
                int n = 0;
                for (int ii = -radiusNeigh; ii <= radiusNeigh; ii++) {
                    int row = (i + ii + nrows) % nrows;
                    for (int jj = -radiusNeigh; jj <= radiusNeigh; jj++) {
                        int col = (j + jj + ncols) % ncols;
                        neigh[n++] = cells[row][col];
                    }
                }
                Cell[] newNeigh = new Cell[8];
                int aux = 0;
                for (int ii = 0; ii < neigh.length; ii++) {
                    if (ii != 4) {
                        newNeigh[aux] = neigh[ii];
                        aux++;
                    }
                }
                cells[i][j].setNeighbors(newNeigh);

            }
        }
    }

    public void display(PApplet p) {
        for (int i = 0; i < nrows; i++) {
            for (int j = 0; j < ncols; j++) {
                cells[i][j].display(p);
            }
        }
    }*/


}
