package AutomatosAgents;

import physics.Body;
import processing.core.PApplet;
import processing.core.PVector;
import tools.SubPlot;

import java.util.ArrayList;
import java.util.List;

public class tp2 implements IProcessingApp {

	private Boid boid1, redFlee, bluePatrol;
	private Body target;
	private List<Body> allTrackingBodies, allTrackingBodiesRed, allTrackingBodiesBlue;

	private double[] window = { -10, 10, -10, 10 };

	private float[] view1 = { .02f, .51f, .96f, .47f };
	private float[] view2 = { .02f, .02f, .96f, .47f };

	private SubPlot plt1;
	private SubPlot plt2;

	private boolean brake;
	private boolean start;

	private List<Body> targets;

	private int idx = 0;

	@Override
	public void setup(PApplet p) {

		allTrackingBodies = new ArrayList<Body>();
		allTrackingBodiesRed = new ArrayList<Body>();
		allTrackingBodiesBlue = new ArrayList<Body>();

		targets = new ArrayList<Body>();

		// Ex1
		plt1 = new SubPlot(window, view1, p.width, p.height);

		brake = false;

		boid1 = new Boid(new PVector(), 1f, .2f, p.color(0), plt1, p);
		boid1.addBehavior(new Seek(1f));
		boid1.addBehavior(new Brake(1f));

		target = new Body(new PVector(), new PVector(), 1f, 0.1f, p.color(255, 0, 0));
		allTrackingBodies.add(target);
		Eye eye = new Eye(allTrackingBodies, boid1);
		boid1.setEye(eye);

		// Ex4
		plt2 = new SubPlot(window, view2, p.width, p.height);

		start = false;

		redFlee = new Boid(new PVector(p.random((float) window[0], (float) window[1]),
				p.random((float) window[0], (float) window[1])), .5f, .2f, p.color(255, 0, 0), plt2, p);
		redFlee.addBehavior(new Wander(1f));
		redFlee.addBehavior(new Evade(1f));
		redFlee.getDna().setVisionDistance(5);
		// redFlee.getDna().setVisionAngle((float) (2 * Math.PI));

		bluePatrol = new Boid(new PVector(p.random((float) window[0], (float) window[1]),
				p.random((float) window[0], (float) window[1])), .5f, .2f, p.color(0, 0, 255), plt2, p);
		bluePatrol.addBehavior(new Seek(1f));

		// Tracking do wander1 no wander2
		allTrackingBodiesRed.add(bluePatrol);
		redFlee.setEye(new Eye(allTrackingBodiesRed, redFlee));
	}

	public void changeEye() {
		this.idx = (this.idx + 1) % this.targets.size();
		this.allTrackingBodiesBlue.set(0, this.targets.get(this.idx));
		this.bluePatrol.setEye(new Eye(this.allTrackingBodiesBlue, this.bluePatrol));
	}

	@Override
	public void draw(PApplet p, float dt) {
		p.background(255);

		// Ex1
		float[] bb = plt1.getBoudingBox();
		p.fill(255, 100);
		p.rect(bb[0], bb[1], bb[2], bb[3]);

		if (plt1.isInside(p.mouseX, p.mouseY)) {
			double[] w = plt1.getWorldCoord(p.mouseX, p.mouseY);
			target.setPos(new PVector((float) w[0], (float) w[1]));
		}

		target.display(p, plt1);

		if (this.brake) {
			boid1.applyBehavior(1, dt);
		} else {
			// Aplica o behavior seek
			boid1.applyBehavior(0, dt);
		}

		boid1.display(p, plt1);

		p.fill(0);
		p.text("T - Break", bb[0] + 10, bb[1] + 15);
		p.text("W and S - Speed", bb[0] + 10, bb[1] + 30);
		p.text("A and D - Force", bb[0] + 10, bb[1] + 45);

		// Ex 4
		bb = plt2.getBoudingBox();
		p.fill(120, 170, 145, 100);
		p.rect(bb[0], bb[1], bb[2], bb[3]);

		// Display dos targets
		for (Body target : this.targets) {
			target.display(p, plt2);
		}

		if (start) {
			redFlee.getEye().look();
			if (redFlee.getEye().getNearSight().size() != 0) {
				redFlee.applyBehavior(1, dt);
				redFlee.setColor(p.color(0, 170, 120));
				redFlee.setShape(p, plt2);
			} else {
				redFlee.applyBehavior(0, dt);
				redFlee.setColor(p.color(255, 0, 0));
				redFlee.setShape(p, plt2);
			}

			redFlee.display(p, plt2);

			bluePatrol.applyBehavior(0, dt);
			bluePatrol.display(p, plt2);

			PVector sub = PVector.sub(bluePatrol.getPos(), targets.get(idx).getPos());

			if (sub.mag() <= 0.2) {
				changeEye();
			}
		} else {
			p.fill(0);
			p.text("Click to set targets", bb[1] + 40, bb[1] + 50);
			p.text("P to start", bb[1] + 63, bb[1] + 65);
		}
	}

	public void keyPressed(PApplet p) {

		if (p.key == 't') {
			if (brake) {
				brake = false;
			} else {
				brake = true;
			}
		}

		float maxSpeed = boid1.getDna().getMaxSpeed();
		float maxForce = boid1.getDna().getMaxForce();

		if (p.key == 'w') {

			boid1.getDna().setMaxSpeed(maxSpeed * 2);
		}

		if (p.key == 's') {

			boid1.getDna().setMaxSpeed(maxSpeed / 2);
		}

		if (p.key == 'a') {

			boid1.getDna().setMaxForce(maxForce * 2);
		}

		if (p.key == 'd') {

			boid1.getDna().setMaxForce(maxForce / 2);
		}
		if (p.key == 'p' && !start) {
			start = true;
			allTrackingBodiesBlue.add(this.targets.get(idx));
			bluePatrol.setEye(new Eye(allTrackingBodiesBlue, bluePatrol));
		}

	}

	@Override
	public void mousePressed(PApplet p) {
		if (!start & plt2.isInside(p.mouseX, p.mouseY)) {
			double[] w = plt2.getWorldCoord(p.mouseX, p.mouseY);

			PVector pos = new PVector((float) w[0], (float) w[1]);

			Body target = new Body(pos, new PVector(), 1f, 0.1f, p.color(0));
			targets.add(target);
		}
	}
}
