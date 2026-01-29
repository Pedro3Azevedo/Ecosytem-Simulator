package ecosystem;

import java.util.List;

import AutomatosAgents.Boid;
import physics.Body;

public interface IAnimal {
	public Animal reproduce(boolean mutate);
	public void eat(Terrain terrain);
	public Boid eat(List<Body> allPrey); //Metodo para comer boids
	public void energy_consumption(float dt, Terrain terrain);
	public boolean die();
}
