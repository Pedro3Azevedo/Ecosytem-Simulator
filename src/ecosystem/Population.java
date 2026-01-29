package ecosystem;

import java.util.ArrayList;
import java.util.List;

import AutomatosAgents.AvoidObstacle;
import AutomatosAgents.Boid;
import AutomatosAgents.Brake;
import AutomatosAgents.Evade;
import AutomatosAgents.Eye;
import AutomatosAgents.Pursuit;
import AutomatosAgents.Seek;
import AutomatosAgents.Wander;
import physics.Body;
import physics.PSControl;
import physics.ParticleSystem;
import processing.core.PApplet;
import processing.core.PVector;
import tools.SubPlot;

//TODO
public class Population {

	public List<Animal> allAnimals;
	public List<Body> allPrey;
	public List<Body> allPred;

	public Boat boat;
	public Body boatTarget;
	private List<Body> allTrackingBodies;

	private double[] window;
	private boolean mutate = false;

	private int cor;
	private float[] velParams = { PApplet.radians(180), PApplet.radians(360), 1, 1 };
	private float[] lifetimeParams = { .5f, .5f };
	private float[] radiusParams = { .1f, .1f };
	private float flow = 50;
	public ParticleSystem ps;
	public List<ParticleSystem> pss;
	public List<Body> obstacles;

	public Population(PApplet parent, SubPlot plt, Terrain terrain) {
		window = plt.getWindow();
		allAnimals = new ArrayList<Animal>();
		allPrey = new ArrayList<Body>();
		allPred = new ArrayList<Body>();

		pss = new ArrayList<ParticleSystem>();
		cor = parent.color(255, 0, 0);

		obstacles = terrain.getObstacles();

		// Criação das presas
		preyBirth(parent, plt);

		// Criação dos predadores
		predBirth(parent, plt);

		// Criação do barco e target
		boatBirth(parent, plt);
	}

	// Criação das presas
	public void preyBirth(PApplet parent, SubPlot plt) {
		for (int i = 0; i < WorldConstants.INI_PREY_POPULATION; i++) {
			PVector pos = new PVector(parent.random((float) window[0], (float) window[1]),
					parent.random((float) window[2], (float) window[3]));

			Animal presa = new Prey(pos, WorldConstants.PREY_MASS, WorldConstants.PREY_SIZE, WorldConstants.PREY_IMG[0],
					parent, plt);
			presa.addBehavior(new Wander(1));
			presa.addBehavior(new AvoidObstacle(30));

			Eye eye = new Eye(obstacles, presa);
			presa.setEye(eye);
			presa.getDna().setVisionDistance(3.5f);

			allPrey.add(presa);
			allAnimals.add(presa);
		}
	}

	public void predBirth(PApplet parent, SubPlot plt) {
		// Criação dos predadores
		for (int i = 0; i < WorldConstants.INI_PRED_POPULATION; i++) {
			PVector pos = new PVector(parent.random((float) window[0], (float) window[1]),
					parent.random((float) window[2], (float) window[3]));

			Animal pred = new Predator(pos, WorldConstants.PRED_MASS, WorldConstants.PRED_SIZE,
					WorldConstants.PRED_IMG[0], parent, plt);
			pred.addBehavior(new Wander(1));
			pred.addBehavior(new Seek(30));

			Eye eye = new Eye(allPrey, pred);
			pred.setEye(eye);

			allPred.add(pred);
			allAnimals.add(pred);
		}
	}

	public void boatBirth(PApplet parent, SubPlot plt) {
		// Barco e o seu target
		boat = new Boat(new PVector(), WorldConstants.BARCO_MASS, WorldConstants.BARCO_SIZE,
				WorldConstants.BARCO_IMG[0], plt, parent);
		boat.getDna().setMaxSpeed(WorldConstants.INIT_BARCO_SPEED);
		boat.addBehavior(new Seek(1f));

		allTrackingBodies = new ArrayList<Body>();
		boatTarget = new Body(new PVector(), new PVector(), 1f, 0.1f, parent.color(255, 0, 0));
		allTrackingBodies.add(boatTarget);
		Eye eye = new Eye(allTrackingBodies, boat);
		boat.setEye(eye);
		allAnimals.add(boat);
	}

	public void update(float dt, Terrain terrain) {

		move(terrain, dt);
		eat(terrain);
		energy_consumption(dt, terrain);
		reproduce(mutate);
		die();
	}

	private void move(Terrain terrain, float dt) {
		for (Body a : allPrey) {
			((Animal) a).applyBehaviors(dt);
			System.out.println(((Animal) a).getEye().getNearSight().size());
		}

		// Atualiza o target de cada predador a cada move para verficiar o near sight
		for (Body pred : allPred) {
			Boid aux = (Boid) pred;
			aux.getEye().look();
			// Se não tiver nenhum no near sight, faz wander
			if (aux.getEye().getNearSight().size() == 0) {
				aux.applyBehavior(0, dt);
			} else {
				aux.applyBehavior(1, dt);
			}
		}

		// Aplica o seek a cada move
		boat.applyBehaviors(dt);
	}

	private void eat(Terrain terrain) {
		for (Body a : allPrey)
			((Animal) a).eat(terrain);

		// Remove a presa retornada do eat da lista
		for (Body pred : allPred) {

			Boid aux = ((Animal) pred).eat(allPrey);

			allPrey.remove(aux);
			allAnimals.remove(aux);

			if (aux != null) {
				// Se morreu alguma presa faz um particle system
				PSControl psc = new PSControl(velParams, lifetimeParams, radiusParams, flow, cor, false);
				ps = new ParticleSystem(pred.pos, new PVector(0, 0), 1f, .2f, psc);
				pss.add(ps);
			}
		}

		// Remove os pred através do barco
		Boid pred = boat.eat(allPred);
		allPred.remove(pred);
		allAnimals.remove(pred);
	}

	private void energy_consumption(float dt, Terrain terrain) {
		for (Animal a : allAnimals)
			a.energy_consumption(dt, terrain);
	}

	private void die() {
		for (int i = allAnimals.size() - 1; i >= 0; i--) {
			Animal a = allAnimals.get(i);
			// Se morreu e era um predador, remove-o da sua lista
			if (a.die() && a instanceof Predator) {
				allPred.remove(a);
			}
			// Se morreu e era uma prey, remove-o da sua lista
			if (a.die() && a instanceof Prey) {
				allPrey.remove(a);
			}
			if (a.die()) {
				allAnimals.remove(a);
			}
		}
	}

	private void reproduce(boolean mutate) {
		for (int i = allAnimals.size() - 1; i >= 0; i--) {
			Animal a = allAnimals.get(i);
			Animal child = a.reproduce(mutate);
			// Se nasceu um filho adiciona-o à respetiva lista
			if (child != null) {
				if (child instanceof Prey) {
					allPrey.add(child);
				} else if (child instanceof Predator) {
					allPred.add(child);
				}
				allAnimals.add(child);
			}
		}
	}

	public void display(PApplet p, SubPlot plt) {
		for (Animal a : allAnimals)
			a.display(p, plt);
	}

	public int getNumAnimals() {
		return allAnimals.size();
	}

	public float getMeanMaxSpeed() {
		float sum = 0;
		for (Animal a : allAnimals)
			sum += a.getDna().getMaxSpeed();
		return sum / allAnimals.size();
	}

	public float getStdMaxSpeed() {
		float mean = getMeanMaxSpeed();
		float sum = 0;
		for (Animal a : allAnimals)
			sum += Math.pow(a.getDna().getMaxSpeed() - mean, 2);
		return (float) Math.sqrt(sum / allAnimals.size());
	}

	public float[] getMeanWeights() {
		float[] sums = new float[2];
		for (Animal a : allAnimals) {
			sums[0] += a.getBehaviors().get(0).getWeight();
			sums[1] += a.getBehaviors().get(1).getWeight();
		}
		sums[0] /= allAnimals.size();
		sums[1] /= allAnimals.size();
		return sums;
	}
}
