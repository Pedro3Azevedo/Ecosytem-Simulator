package AutomatosAgents;

import physics.Body;
import processing.core.PVector;

public class Align extends Behavior{
    public Align(float weight) {
        super(weight);
    }

    @Override
    public PVector getDesiredVelocity(Boid me) {
        PVector vd = me.getVel().copy();
        for(Body body : me.getEye().getFarSight()){
            vd.add(body.getVel());
        }
        return vd.div(me.getEye().getFarSight().size()+1);
    }
}
