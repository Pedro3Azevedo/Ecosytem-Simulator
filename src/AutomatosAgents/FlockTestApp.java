package AutomatosAgents;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import physics.Body;
import processing.core.PApplet;
import processing.core.PVector;
import tools.SubPlot;

public class FlockTestApp implements IProcessingApp {

	private List<Body> allTrackingBodies;
	
	private Boid boid, boidPredador;
	private Flock flock;
	private float[] sacWeights = { 1f, 1f, 1f };

	private double[] window = { -10, 10, -10, 10 };
	private float[] viewport = { 0, 0, 1, 1 };
	private SubPlot plt;

	private int idx;

	@Override
	public void setup(PApplet p) {
		plt = new SubPlot(window, viewport, p.width, p.height);
		flock = new Flock(10, .1f, .3f, p.color(0, 100, 200), this.sacWeights, p, plt);

		//Boid que é controlado
		idx = new Random().nextInt(flock.getBoids().size());
		boid = flock.getBoid(idx);
		boid.setColor(p.color(0,255,0));
		boid.setShape(p, plt);
		
		//Boid predador
		allTrackingBodies = new ArrayList<Body>();
		
		boidPredador = new Boid(new PVector(), 1f, .2f, p.color(255,0,0), plt, p);
		boidPredador.addBehavior(new Pursuit(1f));
		boidPredador.getDna().setMaxForce(20);

		allTrackingBodies.add(boid);
		Eye eye = new Eye(allTrackingBodies, boidPredador);
		boidPredador.setEye(eye);
	}

	@Override
	public void draw(PApplet p, float dt) {
		p.background(255);
		float[] bb = plt.getBoudingBox();
		p.fill(255, 100);
		p.rect(bb[0], bb[1], bb[2], bb[3]);

		flock.applyBehavior(dt);
		flock.display(p, plt);
		
		boidPredador.applyBehavior(0, dt);
		boidPredador.display(p, plt);
	}

	@Override
	public void keyPressed(PApplet p) {
		switch (p.key) {
		case 'w':
			boid.getPos().add(new PVector(0, 0.1f));
			break;

		case 'a':
			boid.getPos().add(new PVector(-0.1f, 0));
			break;

		case 's':
			boid.getPos().add(new PVector(0, -0.1f));
			break;

		case 'd':
			boid.getPos().add(new PVector(0.1f, 0));
			break;
		}
	}

	@Override
	public void mousePressed(PApplet p) {

	}
}
