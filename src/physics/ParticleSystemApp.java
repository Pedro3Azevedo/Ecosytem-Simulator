package physics;

import java.util.ArrayList;
import java.util.List;

import processing.core.PApplet;
import processing.core.PVector;
import tools.SubPlot;

public class ParticleSystemApp implements IProcessingApp{

	private List<ParticleSystem> pss;
	private double[] window = {-10,10,-10,10};
	private float dist = 2;
	private float distEarthSun = 1.496e11f;
	//private double[] window = { -dist * distEarthSun, dist * distEarthSun, -dist * distEarthSun, dist * distEarthSun };
	private float earthSpeed = 3e4f;
	private float[] viewport = {0,0,1,1};
	private SubPlot plt;
	private float[] velParams = {PApplet.radians(180), PApplet.radians(360), 2, 2};
	private float[] lifetimeParams = {3,5};
	private float[] radiusParams = {.1f,.1f};
	private float flow = 50;
	ParticleSystem ps;
	
	@Override
	public void setup(PApplet p) {

		plt = new SubPlot(window, viewport, p.width, p.height);
		pss = new ArrayList<ParticleSystem>();
		
		PSControl psc = new PSControl(velParams, lifetimeParams, radiusParams, flow, 0, false);
		ps = new ParticleSystem(new PVector(), new PVector(0, 0), 1f, .2f, psc);
		pss.add(ps);
		}

	@Override
	public void draw(PApplet p, float dt) {

		p.background(255);
		
//		for (ParticleSystem ps : pss) {
//			ps.applyForce(new PVector(0,0));
//		}
		
		for (int i = pss.size()-1; i>=0 ;i--) {
			ParticleSystem ps = pss.get(i);
			ps.move(dt);
			ps.display(p, plt);
		}
		
//		velParams[0] = PApplet.map(p.mouseX, 0, p.width, PApplet.radians(0), PApplet.radians(360));
//		for(ParticleSystem ps : pss) {
//			PSControl psc = ps.getPSControl();
//			psc.setVelParams(velParams);
//		}
		
	}

	@Override
	public void mousePressed(PApplet p) {

		double[] ww = plt.getWorldCoord(p.mouseX, p.mouseY);
		
		int color = p.color(p.random(255), p.random(255), p.random(255));
		
		PSControl psc = new PSControl(velParams, lifetimeParams, radiusParams, flow, color, false);
		ParticleSystem ps = new ParticleSystem(new PVector((float)ww[0],(float)ww[1]), 
				new PVector(), 1f, .2f, psc);
		pss.add(ps);
	}

	@Override
	public void keyPressed(PApplet p) {
		// TODO Auto-generated method stub
		
	}

}
