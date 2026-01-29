package AutomatosAgents;

import physics.Body;
import processing.core.PApplet;
import processing.core.PVector;
import tools.SubPlot;

import java.util.ArrayList;
import java.util.List;

public class ReynoldsTestApp implements IProcessingApp {

    private Boid wander, seeker, pursuit;
    private Flock flock;
    private float[] sacWeights = {1f,5f,15f};
    private Body target;
    private int inx = 0;

    private double[] window = {-10, 10, -10, 10};

    private float[] view1 = {.02f, .51f, .96f, .47f};
    private float[] view2 = {.02f, .02f, .47f, .47f};
    private float[] view3 = {.51f, .02f, .47f, .47f};

    private SubPlot plt1;
    private SubPlot plt2;
    private SubPlot plt3;

    @Override
    public void setup(PApplet p) {
        plt1 = new SubPlot(window, view1, p.width, p.height);
        plt2 = new SubPlot(window, view2, p.width, p.height);
        plt3 = new SubPlot(window, view3, p.width, p.height);
        flock = new Flock(200, .1f, .3f, p.color(0, 100, 200), this.sacWeights, p, plt1);

        wander = new Boid(new PVector(p.random((float) window[0], (float) window[1]), p.random((float) window[0], (float) window[1])),
                .5f, .5f, p.color(255, 0, 0), plt2, p);
        wander.addBehavior(new Wander(1f));

        pursuit = new Boid(new PVector(p.random((float) window[0], (float) window[1]), p.random((float) window[0], (float) window[1])),
                .5f, .5f, p.color(0, 255, 0), plt2, p);
        pursuit.addBehavior(new Pursuit(1f));
        List<Body> allTrackingBodies = new ArrayList<Body>();
        allTrackingBodies.add(wander);
        pursuit.setEye(new Eye(allTrackingBodies, pursuit));

        target = new Body(new PVector(), new PVector(), 1f, .3f, p.color(0));
        seeker = new Boid(new PVector(p.random((float) window[0], (float) window[1]), p.random((float) window[0], (float) window[1])),
                .5f, .5f, p.color(0,0, 255), plt3, p);
        seeker.addBehavior(new Seek(1f));
        //seeker.addBehavior(new Wander(1f));
        seeker.addBehavior(new Flee(1f));
        allTrackingBodies = new ArrayList<Body>();
        allTrackingBodies.add(target);
        seeker.setEye(new Eye(allTrackingBodies, seeker));
    }

    @Override
    public void draw(PApplet p, float dt) {
        p.background(255);
        float[] bb = plt1.getBoudingBox();
        p.fill(255, 100);
        p.rect(bb[0], bb[1], bb[2], bb[3]);


        bb = plt2.getBoudingBox();
        p.fill(120,170,145, 100);
        p.rect(bb[0], bb[1], bb[2], bb[3]);

        bb = plt3.getBoudingBox();
        p.fill(190,170,45, 100);
        p.rect(bb[0], bb[1], bb[2], bb[3]);

        wander.applyBehaviors(dt);
        pursuit.applyBehaviors(dt);
        seeker.applyBehavior(inx, dt);
        flock.applyBehavior(dt);

        wander.display(p, plt2);
        pursuit.display(p, plt2);
        seeker.display(p, plt3);
        target.display(p, plt3);
        flock.display(p, plt1);
    }

    @Override
    public void keyPressed(PApplet p) {
        if(p.key == 't'){
            inx = (inx + 1) % 2;
        }
    }

    @Override
    public void mousePressed(PApplet p) {
        if(plt3.isInside(p.mouseX, p.mouseY)){
            double[] w = plt3.getWorldCoord(p.mouseX, p.mouseY);
            target.setPos(new PVector((float) w[0], (float) w[1]));
        }
    }
}
