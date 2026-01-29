package ecosystem;

import processing.core.PApplet;
import tools.SubPlot;

public class TesteEcosystemApp implements IProcessingApp {

	private float[] viewport = {0f, 0f, 1f, 1f};
	private SubPlot plt;
	
	private Terrain terrain;
	private Population population;
	
	@Override
	public void setup(PApplet p) {
		plt = new SubPlot(WorldConstants.WINDOW, viewport, p.width, p.height);
		terrain = new Terrain(p,plt);
		terrain.setStateColors(getColors(p));
		terrain.initRandomCostom(WorldConstants.PATCH_TYPE_PROB);
		for(int i=0;i<2;i++) terrain.majorityRule();
		population = new Population(p, plt, terrain);
	}

	@Override
	public void draw(PApplet p, float dt) {
		terrain.regenerate();
		population.update(dt, terrain);
		
		terrain.display(p);
		population.display(p, plt);
		
		System.out.println("numAnimals = " + population.getNumAnimals());
	}

	@Override
	public void keyPressed(PApplet p) {
		// TODO Auto-generated method stub
		
	}
	
	private int[] getColors(PApplet p) {
		int[] colors = new int[WorldConstants.NSTATES];
		for(int i=0;i<WorldConstants.NSTATES;i++) {
			colors[i] = p.color(WorldConstants.TERRAIN_COLORS[i][0],
					WorldConstants.TERRAIN_COLORS[i][1],
					WorldConstants.TERRAIN_COLORS[i][2]);
		}
		return colors;
	}

	@Override
	public void mousePressed(PApplet p) {
		// TODO Auto-generated method stub
		
	}
	

}
