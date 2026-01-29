package fractals;

import processing.core.PApplet;
import tools.SubPlot;

public class MandelbrotApp implements IProcessingApp {

	private double[] window = {-2, 2, -2, 2};
	private float[] viewport = {0, 0, 1, 1};
	private SubPlot plt;
	private MandelbrotSet ms;
	private int mx0, my0, mx1, my1;
	
	@Override
	public void setup(PApplet p) {
		
		plt = new SubPlot(window, viewport, p.width,p.height);
		ms = new MandelbrotSet(300, plt);
		
	}

	@Override
	public void draw(PApplet p, float dt) {
		ms.display(p, plt);
		
	}

	@Override
	public void keyPressed(PApplet p) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(PApplet p) {
		mx0 = p.mouseX;
		my0 = p.mouseY;
	}

	@Override
	public void mouseReleased(PApplet p) {
		double[] xy0 = plt.getWorldCoord(mx0, my0);
		double[] xy1 = plt.getWorldCoord(p.mouseX, p.mouseY);
		double xmin = Math.min(xy0[0], xy1[0]);
		double xmax = Math.max(xy0[0], xy1[0]);
		double ymin = Math.min(xy0[1], xy1[1]);
		double ymax = Math.max(xy0[1], xy1[1]);
		
		double[] window = {xmin, xmax, ymin, ymax};
		plt = new SubPlot(window, viewport, p.width, p.height);
	}

	@Override
	public void mouseDragged(PApplet p) {
		// TODO Auto-generated method stub
		
	}

}
