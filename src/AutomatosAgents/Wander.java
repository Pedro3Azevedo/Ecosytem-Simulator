package AutomatosAgents;

import processing.core.PVector;

public class Wander extends Behavior{
    public Wander(float weight) {
        super(weight);
    }

    @Override
    public PVector getDesiredVelocity(Boid me) {
        PVector center = me.getPos().copy();
        center.add(me.getVel().copy().mult(me.getDna().getDeltaTWander()));
        PVector target = new PVector(me.getDna().getRadiusWander() * (float) Math.cos(me.getPhiWander()),
                me.getDna().getRadiusWander() * (float) Math.sin(me.getPhiWander()));

        target.add(center);
        float x = (float) (2 * (Math.random() - 0.5) * me.getDna().getDeltaPhiWander());
        float y = me.getPhiWander();
        me.setPhiWander(x + y);

        return PVector.sub(target, me.getPos());
    }
}
