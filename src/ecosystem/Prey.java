package ecosystem;

import java.util.ArrayList;
import java.util.List;

import AutomatosAgents.Boid;
import physics.Body;
import processing.core.PApplet;
import processing.core.PVector;
import tools.SubPlot;

public class Prey extends Animal {

	private PApplet parent;
	private SubPlot plt;
	private String img;

	public Prey(PVector pos, float mass, float radius, int color, PApplet parent, SubPlot plt) {
		super(pos, mass, radius, color, parent, plt);
		this.parent = parent;
		this.plt = plt;
		energy = WorldConstants.INI_PREY_ENERGY;
	}

	public Prey(Prey prey, boolean mutate, PApplet parent, SubPlot plt) {
		super(prey, mutate, parent, plt);
		this.parent = parent;
		this.plt = plt;
		energy = WorldConstants.INI_PREY_ENERGY;
	}

	//Construtor para usar imagem no boid
	public Prey(PVector pos, float mass, float radius, String img, PApplet parent, SubPlot plt) {
		super(pos, mass, radius, img, parent, plt);
		this.parent = parent;
		this.plt = plt;
		this.img = img;
		energy = WorldConstants.INI_PREY_ENERGY;
	}

	//Construtor para usar imagem no boid reproduzido
	public Prey(Prey prey, boolean mutate, String img, PApplet parent, SubPlot plt) {
		super(prey, mutate, img, parent, plt);
		this.parent = parent;
		this.plt = plt;
		this.img = img;
		energy = WorldConstants.INI_PREY_ENERGY;
	}

	@Override
	public void eat(Terrain terrain) {
		Patch patch = (Patch) terrain.world2Cell(pos.x + (this.radius), pos.y - (this.radius));
		if (patch.getState() == WorldConstants.PatchType.FOOD.ordinal()) {
			energy += WorldConstants.ENERGY_FROM_PLANT;
			patch.setFertile();
		}
	}
	
	@Override
	public void energy_consumption(float dt, Terrain terrain) {
		energy -= dt; //basic metabolism
		energy -= mass*Math.pow(vel.mag(), 2)*dt;
		Patch patch = (Patch)terrain.world2Cell(pos.x + (this.radius), pos.y - (this.radius));
		if(patch.getState() == WorldConstants.PatchType.OBSTACLE.ordinal()) {
			energy -= 0.05f;
		}
	}

	@Override
	public Animal reproduce(boolean mutate) {
		Animal child = null;
		if (energy > WorldConstants.PREY_ENERGY_TO_REPRODUCE) {
			energy -= WorldConstants.INI_PREY_ENERGY;

			//Se foi usado o construtor com imagem, cria uma nova presa com o construtor com imagem
			if (img != null) {
				child = new Prey(this, mutate, img, parent, plt);
			} else
				child = new Prey(this, mutate, parent, plt);
			if (mutate)
				child.mutateBehaviors();
		}
		return child;
	}

	@Override
	public Boid eat(List<Body> allPrey) {
		return null;
	}
}
