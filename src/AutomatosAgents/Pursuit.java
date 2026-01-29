package AutomatosAgents;

import physics.Body;
import processing.core.PVector;

public class Pursuit extends Behavior{
    public Pursuit(float weight) {
        super(weight);
    }

    @Override
    public PVector getDesiredVelocity(Boid me) {
        Body bodyTarget = me.getEye().getTarget();
        PVector d = bodyTarget.getVel().mult(me.getDna().getDeltaTPursuit());
        PVector target = PVector.add(bodyTarget.getPos(), d);
        return PVector.sub(target, me.getPos()).div(2);
    }
}
