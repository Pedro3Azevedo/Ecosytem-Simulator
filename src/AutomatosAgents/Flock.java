package AutomatosAgents;

import physics.Body;
import processing.core.PApplet;
import processing.core.PVector;
import tools.SubPlot;

import java.util.ArrayList;
import java.util.List;

public class Flock {
    private List<Boid> boids;

    public Flock(int nboids, float mass, float radius, int color, float[] sacWeights, PApplet p, SubPlot plt) {
        this.boids = new ArrayList<Boid>(nboids);
        double[] w = plt.getWindow();
        for (int i = 0; i < nboids; i++) {
            float x = p.random((float) w[0], (float) w[1]);
            float y = p.random((float) w[2], (float) w[3]);
            Boid boid = new Boid(new PVector(x, y), mass, radius, color, plt, p);
            boid.addBehavior(new Separate(sacWeights[0]));
            boid.addBehavior(new Align(sacWeights[1]));
            boid.addBehavior(new Cohesion(sacWeights[2]));
            this.boids.add(boid);
        }
        List<Body> bodies = boidList2BodyList(this.boids);
        for (Boid boid : boids) {
            boid.setEye(new Eye(bodies, boid));
        }
    }

    private List<Body> boidList2BodyList(List<Boid> boids) {
        List<Body> bodies = new ArrayList<Body>();
        for (Boid boid : boids) {
            bodies.add(boid);
        }
        return bodies;
    }

    public void applyBehavior(float dt) {
        for (Boid boid : boids) {
            boid.applyBehaviors(dt);
        }
    }

    public List<Boid> getBoids() {
        return this.boids;
    }
    
    public Boid getBoid(int i) {
        return this.boids.get(i);
    }

    public void display(PApplet p, SubPlot plt) {
        for (Boid boid : this.boids) {
            boid.display(p, plt);
        }
    }
}
