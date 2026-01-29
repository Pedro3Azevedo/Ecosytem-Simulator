package dla;

import java.util.ArrayList;
import java.util.List;

import dla.Walker.State;
import processing.core.PApplet;
import processing.core.PVector;
import dla.IProcessingApp;

public class DLA implements IProcessingApp {

	private List<Walker> walkers;
	private int NUM_WALKERS = 100;
	private int NUM_STEPS_PER_FRAME = 100;
	private Walker wCenter;
	private boolean select = false;

	@Override
	public void setup(PApplet p) {
		walkers = new ArrayList<Walker>();

		wCenter = new Walker(p, new PVector(p.width / 2, p.height / 2));
		walkers.add(wCenter);

		for (int i = 0; i < NUM_WALKERS; i++) {
			Walker w = new Walker(p);
			walkers.add(w);
		}
	}

	@Override
	public void draw(PApplet p, float dt) {
		p.background(190);

		int mortos = 0;
		for (int i = 0; i < NUM_STEPS_PER_FRAME; i++) {
			for (Walker w : walkers) {
				if (w.getState() == State.WANDER) {
					w.wander(p);
					w.updateState(p, walkers);
					if (w.getState() == State.STOPPED) {
						if (!(walkers.size() > 1200)) { // Não nascer mais depois dos 1200
							mortos++;
						}
					}
				}
			}
		}

		for (int i = 0; i < mortos; i++) { // Fazer com que voltem walkers
			Walker walker = new Walker(p);
			walkers.add(walker);
		}

		for (Walker w : walkers) {
			w.display(p);
		}

		System.out.println("Stopped = " + Walker.num_stopped + " Wander = " + Walker.num_wanders);
		System.out.println("Walkers :" + walkers.size());

	}

	@Override
	public void mousePressed(PApplet p) {
		// TODO Auto-generated method stub

	}

	public void keyPressed(PApplet p) {
		if (p.key == ' ') {
			if (!select) {
				select = true;
				p.noLoop();
			}else {
				select = false;
				p.loop();
			}
		}
	}

}
