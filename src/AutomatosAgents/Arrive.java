package AutomatosAgents;

import processing.core.PVector;

public class Arrive extends Behavior{

    public Arrive(float weight) {
        super(weight);
    }

    @Override
    public PVector getDesiredVelocity(Boid me) {
        PVector vd = PVector.sub(me.getEye().getTarget().getPos(), me.getPos());
        float dist = vd.mag();
        float radius = me.getDna().getRadiusArrive();
        if(dist < radius)
            vd.mult(dist/radius);
        return vd;
    }
}
