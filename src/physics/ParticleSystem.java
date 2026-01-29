package physics;

import java.util.ArrayList;
import java.util.List;

import processing.core.PApplet;
import processing.core.PVector;
import tools.SubPlot;

public class ParticleSystem extends Body {

	private List<Particle> particles;
	private PSControl psc;
	private float timer;
	private boolean stop;

	public ParticleSystem(PVector pos, PVector vel, float mass, float radius, PSControl psc) {

		super(pos, vel, mass, radius, 0);
		this.psc = psc;
		this.particles = new ArrayList<Particle>();
		timer = 0;
		stop = false;

	}

	public boolean isDead() {
		if(psc.infinity) {
			return false;
		}
		return timer > psc.minLifetime;
	}

	public PSControl getPSControl() {
		return psc;
	}

	@Override
	public void move(float dt) {

		super.move(dt);
		timer += dt;
		
		//Se passou o lifetime para de criar novas particulas
		if (!stop)
			addParticles(dt);

		for (int i = particles.size() - 1; i >= 0; i--) {
			Particle p = particles.get(i);
			p.move(dt);

			if (p.isDead()) {
				particles.remove(i);
			}
			if (isDead()) {
				stop = true;
			}
		}
	}

	private void addParticles(float dt) {

		float particlesPerFrame = psc.getFlow() * dt;
		int n = (int) particlesPerFrame;
		float f = particlesPerFrame - n;
		for (int i = 0; i < n; i++) {
			addOneParticle();
		}
		if (Math.random() < f) {
			addOneParticle();
		}
	}

	private void addOneParticle() {

		// Cria velocidade random
		Particle particle = new Particle(pos, psc.getRndVel(), psc.getRndRadius(), psc.getColor(),
				psc.getRndLifetime());
		particles.add(particle);
	}

	public void display(PApplet p, SubPlot plt) {

		for (Particle particle : particles) {
			particle.display(p, plt);
		}
	}
}
