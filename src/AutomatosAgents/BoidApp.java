package AutomatosAgents;

import physics.Body;
import processing.core.PApplet;
import processing.core.PVector;
import tools.SubPlot;

import java.util.ArrayList;
import java.util.List;

public class BoidApp implements IProcessingApp {

	private Boid boid;
	private Body target;
	private List<Body> allTrackingBodies;

	private double[] window = { -10, 10, -10, 10 };
	private float[] viewport = { 0, 0, 1, 1 };
	private SubPlot plt;

	private boolean brake;

	@Override
	public void setup(PApplet p) {

		plt = new SubPlot(window, viewport, p.width, p.height);

		boid = new Boid(new PVector(), 1f, .5f, p.color(0), plt, p);
		boid.addBehavior(new Seek(1f));
		boid.addBehavior(new Brake(1f));

		target = new Body(new PVector(), new PVector(), 1f, 0.2f, p.color(255, 0, 0));
		allTrackingBodies = new ArrayList<Body>();
		allTrackingBodies.add(target);
		Eye eye = new Eye(allTrackingBodies, boid);
		boid.setEye(eye);

		brake = false;
	}

	@Override
	public void draw(PApplet p, float dt) {
		p.background(255);

		double[] ww = plt.getWorldCoord(p.mouseX, p.mouseY);
		target.setPos(new PVector((float) ww[0], (float) ww[1]));
		target.display(p, plt);

		if (this.brake) {
			boid.applyBehavior(1, dt);
		} else {
			// Aplica o behavior seek
			boid.applyBehavior(0, dt);
		}

		boid.display(p, plt);

	}

	@Override
	public void mousePressed(PApplet p) {

	}

	@Override
	public void keyPressed(PApplet p) {

		if (p.key == 't') {
			if (brake) {
				brake = false;
			} else {
				brake = true;
			}
		}

		float maxSpeed = boid.getDna().getMaxSpeed();
		float maxForce = boid.getDna().getMaxForce();

		if (p.key == 'w') {

			boid.getDna().setMaxSpeed(maxSpeed * 2);
		}

		if (p.key == 's') {

			boid.getDna().setMaxSpeed(maxSpeed / 2);
		}

		if (p.key == 'a') {

			boid.getDna().setMaxForce(maxForce * 2);
		}
		
		if (p.key == 'd') {

			boid.getDna().setMaxForce(maxForce / 2);
		}
		
	}

}
