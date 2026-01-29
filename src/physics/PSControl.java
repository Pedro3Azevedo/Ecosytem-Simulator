package physics;

import processing.core.PVector;

public class PSControl {
	private float avarageAngle;
	private float disperionAngle;
	private float minVelocity;
	private float maxVelocity;
	public float minLifetime;
	private float maxLifetime;
	private float minRadius;
	private float maxRadius;
	private float flow;
	private int color;
	public boolean infinity;
	
	public PSControl(float[] velControl, float[] lifetime, float[] radius,
			float flow, int color, boolean infinity) {
		setVelParams(velControl);
		setLifetimeParams(lifetime);
		setRadiusParams(radius);
		setFlow(flow);
		setColor(color);
		this.infinity = infinity;
	}
	
	public void setFlow(float flow) {
		this.flow = flow;
	}
	
	public float getFlow() {
		return flow;
	}
	
	public int getColor() {
		return color;
	}
	
	public void setColor(int color) {
		this.color = color;
	}
	
	public void setRadiusParams(float[] radius) {
		minRadius = radius[0];
		minRadius = radius[1];
	}
	
	public void setLifetimeParams(float[] lifetime) {
		minLifetime = lifetime[0];
		minLifetime = lifetime[1];
	}
	
	public float getRndRadius() {
		return getRnd(minRadius, maxRadius);
	}
	
	public float getRndLifetime() {
		return getRnd(minLifetime, maxLifetime);
	}
	
	public void setVelParams(float[] velControl) {
		avarageAngle = velControl[0];
		disperionAngle = velControl[1];
		minVelocity = velControl[2];
		maxVelocity = velControl[3];
	}
	
	public PVector getRndVel() {
		float angle = getRnd(avarageAngle - disperionAngle/2, avarageAngle + disperionAngle/2);
		PVector v = PVector.fromAngle(angle);
		return v.mult(getRnd(minVelocity, maxVelocity));
	}
	
	public static float getRnd(float min, float max) {
		return min + (float) Math.random() * (max-min);
	}
}
