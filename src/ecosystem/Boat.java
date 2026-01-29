package ecosystem;

import java.util.List;

import AutomatosAgents.Boid;
import physics.Body;
import processing.core.PApplet;
import processing.core.PVector;
import tools.SubPlot;

public class Boat extends Animal {

	public float hp;
	public float gas;

	public Boat(PVector pos, float mass, float radius, String img, SubPlot plt, PApplet p) {
		super(pos, mass, radius, img, p, plt);
		hp = WorldConstants.INI_BARCO_HP;
		gas = WorldConstants.INI_BARCO_GAS;
	}

	@Override
	public Animal reproduce(boolean mutate) {
		return null;
	}

	@Override
	public void eat(Terrain terrain) {
	}

	@Override
	public Boid eat(List<Body> allPred) {
		// Verifica se o predador consegue comer alguma presa, removendo-a da lista e
		// ganhando energia
		for (Body pred : allPred) {

			PVector sub = PVector.sub(this.getPos(), pred.getPos());

			if (sub.mag() <= 0.4) {
				if (gas + WorldConstants.GAS_FROM_PRED <= WorldConstants.INI_BARCO_GAS)
					gas += WorldConstants.GAS_FROM_PRED; // Ganha gasolina ao obter pred
				return (Boid) pred;
			}
		}
		return null;
	}

	@Override
	public void energy_consumption(float dt, Terrain terrain) {
		gas -= dt; // basic metabolism
		gas -= mass * Math.pow(vel.mag(), 2) * dt;
		Patch patch = (Patch) terrain.world2Cell(pos.x + (this.radius), pos.y - (this.radius)); // TODO
		if (patch.getState() == WorldConstants.PatchType.OBSTACLE.ordinal()) {
			hp -= 20 * dt;

			if (this.getDna().getMaxSpeed() > 1.5)
				this.getDna().setMaxSpeed((this.hp * WorldConstants.INIT_BARCO_SPEED) / WorldConstants.INI_BARCO_HP);
		}
	}

	@Override
	public boolean die() {
		// TODO Auto-generated method stub
		return (gas <= 0 || hp <= 0);
	}

}
