package AutomatosAgents;

public class DNA {
	// é melhor por private
	private float maxSpeed;
	private float maxForce;
	private float visionDistance;
	private float visionSafeDistance;
	private float visionAngle;
	private float deltaTPursuit;
	private float radiusArrive;
	private float deltaTWander;
	private float radiusWander;
	private float deltaPhiWander;

	public DNA() {
		// Physics
		this.maxSpeed = random(1f, 2f);
		this.maxForce = random(4f, 7f);
		// Vision
		this.visionDistance = random(1.5f, 2.5f);
		this.visionSafeDistance = .25f * this.visionDistance;
		this.visionAngle = (float) Math.PI * 0.3f;
		// Pursuit
		this.deltaTPursuit = random(.5f, 1f);
		// Arrive
		this.radiusArrive = random(3, 5);
		// wader
		this.deltaTWander = random(.3f, .6f);
		this.radiusWander = random(1f, 3f);
		this.deltaPhiWander = (float) (Math.PI / 8);

	}

	public DNA(DNA dna, boolean mutate) {
		maxSpeed = dna.maxSpeed;
		maxForce = dna.maxForce;

		visionDistance = dna.visionDistance;
		visionSafeDistance = dna.visionSafeDistance;
		visionAngle = dna.visionAngle;

		deltaTPursuit = dna.deltaTPursuit;
		radiusArrive = dna.radiusArrive;

		deltaTWander = dna.deltaTWander;
		deltaPhiWander = dna.deltaPhiWander;
		radiusWander = dna.radiusWander;
		
		if (mutate) mutate();
	}
	
	private void mutate() {
		maxSpeed += random(-0.2f, 0.2f);
		maxSpeed = Math.max(0, maxSpeed);
	}

	public static float random(float min, float max) {
		return min + (float) (Math.random() * (max - min));
	}

	public float getDeltaTWander() {
		return deltaTWander;
	}

	public float getRadiusWander() {
		return radiusWander;
	}

	public float getDeltaPhiWander() {
		return deltaPhiWander;
	}

	public float getRadiusArrive() {
		return radiusArrive;
	}

	public float getDeltaTPursuit() {
		return deltaTPursuit;
	}

	public float getMaxSpeed() {
		return maxSpeed;
	}

	public void setMaxSpeed(float maxSpeed) {
		this.maxSpeed = maxSpeed;
	}

	public float getMaxForce() {
		return maxForce;
	}

	public void setMaxForce(float maxForce) {
		this.maxForce = maxForce;
	}

	public float getVisionDistance() {
		return visionDistance;
	}

	public void setVisionDistance(float visionDistance) {
		this.visionDistance = visionDistance;
	}

	public float getVisionSafeDistance() {
		return visionSafeDistance;
	}

	public void setVisionSafeDistance(float visionSafeDistance) {
		this.visionSafeDistance = visionSafeDistance;
	}

	public float getVisionAngle() {
		return visionAngle;
	}

	public void setVisionAngle(float visionAngle) {
		this.visionAngle = visionAngle;
	}
}
