package AutomatosAgents;

import physics.Body;
import processing.core.PApplet;
import processing.core.PConstants;
import processing.core.PImage;
import processing.core.PShape;
import processing.core.PVector;
import tools.SubPlot;

import java.util.ArrayList;
import java.util.List;

public class Boid extends Body {

    private PShape shape;
    protected DNA dna;
    protected Eye eye;
    protected List<Behavior> behaviors;

	private float phiWander;
    private double[] window;
    private float sumWeights;

    public Boid(PVector pos, float mass, float radius, int color, SubPlot plt, PApplet p) {
        super(pos, new PVector(), mass, radius, color);
        this.dna = new DNA();
        this.behaviors = new ArrayList<Behavior>();
        window = plt.getWindow();
        setShape(p, plt);

    }
    
    //Construtor para fazer load da imagem
    public Boid(PVector pos, float mass, float radius, String img, SubPlot plt, PApplet p) {
        super(pos, new PVector(), mass, radius, img);
        PImage imgLoaded = p.loadImage("imgs//" + img);
        this.img = imgLoaded;
        this.dna = new DNA();
        this.behaviors = new ArrayList<Behavior>();
        window = plt.getWindow();
        setShape(p, plt);

    }
    
    public List<Behavior> getBehaviors() {
		return behaviors;
	}
    
    public void mutateBehaviors() {
    	for(Behavior behavior : behaviors) {
    		if(behavior instanceof AvoidObstacle) {
    			behavior.weight += DNA.random(-0.5f, 0.5f);
    			behavior.weight = Math.max(0, behavior.weight);
    		}
    	}
    	updateSumWeights();
    }
    
    public void setColor(int color) {
		this.color = color;
	}

    public float getPhiWander() {
        return phiWander;
    }

    public void setPhiWander(float phiWander) {
        this.phiWander = phiWander;
    }

    public DNA getDna() {
        return dna;
    }

    public void setEye(Eye eye) {
        this.eye = eye;
    }

    public Eye getEye() {
        return eye;
    }

    public void setShape(PApplet p, SubPlot plt, float radius, int color) {
        this.radius = radius;
        this.color = color;
        setShape(p, plt);
    }

    public void setShape(PApplet p, SubPlot plt) {
        float[] rr = plt.getVectorCoord(radius, radius);

        shape = p.createShape();
        shape.beginShape();
        shape.noStroke();
        shape.fill(this.color);
        shape.vertex(-rr[0], rr[0] / 2);
        shape.vertex(rr[0], 0);
        shape.vertex(-rr[0], -rr[0] / 2);
        shape.vertex(-rr[0] / 2, 0);
        shape.endShape(PConstants.CLOSE);
    }

    private void updateSumWeights() {
        sumWeights = 0;
        for (Behavior behavior : this.behaviors) {
            sumWeights += behavior.getWeight();
        }
    }


    public void addBehavior(Behavior behavior) {
        behaviors.add(behavior);
        updateSumWeights();
    }


    public void removeBehavior(Behavior behavior) {
        if (behaviors.contains(behavior))
            behaviors.remove(behavior);
        updateSumWeights();
    }

    public void applyBehavior(int i, float dt) {
        if(eye != null)
            eye.look();
        Behavior behavior = behaviors.get(i);
        PVector vd = behavior.getDesiredVelocity(this);
        moveBoid(dt, vd);
    }

    public void applyBehaviors(float dt) {
        if (eye != null)
            eye.look();

        PVector vd = new PVector();
        for (Behavior behavior : behaviors) {
            PVector vdd = behavior.getDesiredVelocity(this);
            vdd.mult(behavior.getWeight() / this.sumWeights);
            vd.add(vdd);
        }
        moveBoid(dt, vd);
    }


    private void moveBoid(float dt, PVector vd) {
        vd.normalize().mult(dna.getMaxSpeed());
        PVector fs = PVector.sub(vd, vel); //stering force
        applyForce(fs.limit(dna.getMaxForce()));
        super.move(dt);
        if (pos.x < window[0])
            pos.x += window[1] - window[0];
        if (pos.y < window[2])
            pos.y += window[3] - window[2];
        if (pos.x >= window[1])
            pos.x -= window[1] - window[0];
        if (pos.y >= window[3])
            pos.y -= window[3] - window[2];
    }

    public void display(PApplet p, SubPlot plt) {
    	
    	float[] pp = plt.getPixelCoord(pos.x, pos.y);
        float[] vv = plt.getVectorCoord(vel.x, vel.y);
        
        //Se foi usado o construtor com imagem, faz display dela
        if (img != null){
			p.image(img, pp[0], pp[1], 100*radius, 100*radius);
			return;
		}
    	
        p.pushMatrix();      
        PVector vaux = new PVector(vv[0], vv[1]);
        p.translate(pp[0], pp[1]);
        p.rotate(-vaux.heading());
        p.shape(shape);
        p.popMatrix();
    }
}
