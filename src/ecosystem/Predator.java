package ecosystem;

import java.util.List;

import AutomatosAgents.Boid;
import physics.Body;
import processing.core.PApplet;
import processing.core.PVector;
import tools.SubPlot;

//TODO
public class Predator extends Animal {

	private PApplet parent;
	private SubPlot plt;
	private String img;
	
	public Predator(PVector pos, float mass, float radius, int color, PApplet parent, SubPlot plt) {
		super(pos, mass, radius, color, parent, plt);
		this.parent = parent;
		this.plt = plt;
		energy = WorldConstants.INI_PRED_ENERGY;
	}
	
	public Predator(Predator predator,boolean mutate, PApplet parent, SubPlot plt) {
		super(predator,mutate, parent, plt);
		this.parent = parent;
		this.plt = plt;
		energy = WorldConstants.INI_PRED_ENERGY;
	}
	
	//Construtor para usar imagem no boid
	public Predator(PVector pos, float mass, float radius, String img, PApplet parent, SubPlot plt) {
		super(pos, mass, radius, img, parent, plt);
		this.parent = parent;
		this.plt = plt;
		this.img = img;
		energy = WorldConstants.INI_PRED_ENERGY;
	}

	//Construtor para usar imagem no boid reproduzido
	public Predator(Predator prey, boolean mutate, String img, PApplet parent, SubPlot plt) {
		super(prey, mutate, img, parent, plt);
		this.parent = parent;
		this.plt = plt;
		this.img = img;
		energy = WorldConstants.INI_PRED_ENERGY;
	}

	@Override
	public void eat(Terrain terrain) {
	}
	
	public Boid eat(List<Body> allPrey) {
		
		//Verifica se o predador consegue comer alguma presa, removendo-a da lista e ganhando energia
		for(Body presa : allPrey) {
			
			PVector auxThis = new PVector(pos.x + (this.radius), pos.y - (this.radius));
			PVector auxPresas = new PVector(presa.pos.x + (presa.radius), presa.pos.y - (presa.radius));
			
			PVector sub = PVector.sub(auxThis, auxPresas);

			if (sub.mag() <= 0.2) {
				energy += WorldConstants.ENERGY_FROM_PREY;
				return (Boid)presa;
			}
		}
		return null;
	}
	
	@Override
	public void energy_consumption(float dt, Terrain terrain) {
		energy -= dt; //basic metabolism
		energy -= mass*Math.pow(vel.mag(), 2)*dt;
		Patch patch = (Patch)terrain.world2Cell(pos.x + (this.radius), pos.y - (this.radius));
		if(patch.getState() == WorldConstants.PatchType.OBSTACLE.ordinal()) {
			energy -= 0.25f;
		}
	}
	
	public Animal reproduce(boolean mutate) {
		Animal child = null;
		if(energy > WorldConstants.PRED_ENERGY_TO_REPRODUCE) {
			energy -= WorldConstants.INI_PRED_ENERGY;
			
			//Se foi usado o construtor com imagem, cria uma nova presa com o construtor com imagem
			if (img != null) {
				child = new Predator(this, mutate, img, parent, plt);
			} else
				child = new Predator(this, mutate, parent, plt);
			if (mutate)
				child.mutateBehaviors();

			if (mutate) child.mutateBehaviors();
		}
		return child;
	}

}
