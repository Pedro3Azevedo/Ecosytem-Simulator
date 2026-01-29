package AutomatosAgents;

import physics.Body;
import processing.core.PVector;

import java.util.ArrayList;
import java.util.List;

public class Eye {
	private List<Body> allTrackingBodies;
	private List<Body> farSight;
	private List<Body> nearSight;
	private Boid me;
	private Body target;

	public Eye(List<Body> allTrackingBodies, Boid me) {
		this.allTrackingBodies = allTrackingBodies;
		this.me = me;
		if (allTrackingBodies.size() > 0)
			target = allTrackingBodies.get(0);
	}

	public Eye(Boid me, Eye eye) {
		allTrackingBodies = eye.allTrackingBodies;
		this.me = me;
		target = eye.target;
	}

	public Body getTarget() {
		return target;
	}

	public List<Body> getFarSight() {
		return farSight;
	}

	public List<Body> getNearSight() {
		return nearSight;
	}

	public void look() {
		farSight = new ArrayList<Body>();
		nearSight = new ArrayList<Body>();
		for (Body body : this.allTrackingBodies) {
			if (farSightCalc(body.getPos()))
				this.farSight.add(body);
			if (nearSightCalc(body.getPos()))
				this.nearSight.add(body);
		}

		// Altera o target, caso o antigo não esteja no campo de visão
		boolean existe = false;
		
		if (this.nearSight.size() > 0) {
			for (int i = 0; i < this.nearSight.size(); i++) {
				if (this.target.equals(this.nearSight.get(i))) {
					existe = true;
					break;
				}
			}
			if (!existe)
				this.target = this.nearSight.get(0); // TODO
		}
	}

	protected boolean inSight(PVector t, float maxDistance, float maxAngle) {
		PVector r = PVector.sub(t, me.getPos()); // vetor que vai do boid ao target
		float d = r.mag();// distancia
		float angle = PVector.angleBetween(r, me.getVel());
		return (d > 0) && (d < maxDistance) && (angle < maxAngle);
	}

	private boolean farSightCalc(PVector t) {
		return inSight(t, me.getDna().getVisionDistance(), me.getDna().getVisionAngle());
	}

	private boolean nearSightCalc(PVector t) {
		return inSight(t, me.getDna().getVisionDistance(), (float) Math.PI);
	}
}
