package ecosystem;

import AutomatosAgents.Behavior;
import AutomatosAgents.Boid;
import AutomatosAgents.DNA;
import AutomatosAgents.Eye;
import processing.core.PApplet;
import processing.core.PVector;
import tools.SubPlot;

public abstract class Animal extends Boid implements IAnimal {
	
	public float energy;
	
	protected Animal(PVector pos, float mass, float radius, int color, PApplet p, SubPlot plt) {
		super(pos, mass, radius, color, plt, p);
	}

	protected Animal(Animal a,boolean mutate, PApplet p,SubPlot plt) {
		super(a.pos, a.mass, a.radius, a.color, plt, p);
		for(Behavior b : a.behaviors) {
			this.addBehavior(b);
		}
		if(a.eye !=null) {
			eye = new Eye(this, a.eye);
		}
		dna = new DNA(a.dna, mutate);
	}
	
	//Construtor para usar imagem no boid
	protected Animal(PVector pos, float mass, float radius, String img, PApplet p, SubPlot plt) {
		super(pos, mass, radius, img, plt, p);
	}
	
	//Construtor para usar imagem no boid reproduzido
	protected Animal(Animal a,boolean mutate,String img, PApplet p,SubPlot plt) {
		super(a.pos, a.mass, a.radius, img, plt, p);
		for(Behavior b : a.behaviors) {
			this.addBehavior(b);
		}
		if(a.eye !=null) {
			eye = new Eye(this, a.eye);
		}
		dna = new DNA(a.dna, mutate);
	}

	@Override
	public void energy_consumption(float dt, Terrain terrain) {
		energy -= dt; //basic metabolism
		energy -= mass*Math.pow(vel.mag(), 2)*dt;
		Patch patch = (Patch)terrain.world2Cell(pos.x, pos.y);
		if(patch.getState() == WorldConstants.PatchType.OBSTACLE.ordinal()) {
			energy -= 50*dt;
		}
	}

	@Override
	public boolean die() {
		// TODO Auto-generated method stub
		return (energy < 0);
	}
}
