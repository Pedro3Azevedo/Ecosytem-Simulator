package dla;

import java.util.List;

import processing.core.PApplet;
import processing.core.PVector;

public class Walker {

	public enum State {
		STOPPED, WANDER
	}

	private PVector pos;
	private State state;
	private int colour;
	private static int radius = 4;
	public static int num_wanders = 0;
	public static int num_stopped = 0;

	public Walker(PApplet p) {
		pos = new PVector(p.width / 2, p.height / 2);
		PVector r = PVector.random2D();
		r.mult(p.width / 2);
		pos.add(r);
		setState(p, State.WANDER);
	}

	public Walker(PApplet p, PVector pos) {
		this.pos = pos;
		setState(p, State.STOPPED);
		colour = p.color(215, 241, 113);
	}

	public PVector getPos() {
		return this.pos;
	}

	public State getState() {
		return state;
	}

	public void setState(PApplet p, State state) {
		this.state = state;
		if (state == State.STOPPED) {
			colour = p.color(181, 232, 119);
			num_stopped++;
		} else {
			colour = p.color(255);
			num_wanders++;
		}
	}

	public void updateState(PApplet p, List<Walker> walkers) {
		if (state == State.STOPPED)
			return;

		for (Walker w : walkers) {
			if (w.state == State.STOPPED) {
				float dist = PVector.dist(pos, w.pos);
				if (dist < 2 * radius) {
					setState(p, State.STOPPED);
					num_wanders--;

					// Distancia ao walker central para definir cor
					float distCenter = PVector.dist(pos, walkers.get(0).pos);

					if (distCenter > 50 && distCenter <= 100) {
						colour = p.color(149, 221, 125);
					} else if (distCenter > 100 && distCenter <= 150) {
						colour = p.color(91, 196, 137);
					} else if (distCenter > 150 && distCenter <= 200) {
						colour = p.color(0, 156, 143);
					} else if (distCenter > 200 && distCenter <= 250) {
						colour = p.color(0, 127, 134);
					} else if (distCenter > 250) {
						colour = p.color(28, 99, 115);
					}
					break;
				}
			}
		}
	}

	public void wander(PApplet p) {
		PVector step = PVector.random2D();
		pos.add(step);
		pos.lerp(new PVector(p.width / 2, p.height / 2), 0.0002f);
		pos.x = PApplet.constrain(pos.x, 0, p.width);
		pos.y = PApplet.constrain(pos.y, 0, p.height);
	}

	public void display(PApplet p) {
		p.fill(colour);
		p.circle(pos.x, pos.y, 2 * radius);
	}
}
