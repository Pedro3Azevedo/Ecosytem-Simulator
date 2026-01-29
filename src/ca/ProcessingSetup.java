package ca;

import processing.core.PApplet;

public class ProcessingSetup extends PApplet {
	
	public static IProcessingApp app;
	private int lastUpdate;
	
	public void settings() {
		size(800, 600);
	}
	
	public void setup() {
		app.setup(this);
		lastUpdate = millis();
	}
	
	public void draw() {
		
		int now = millis();
		float dt = (now - lastUpdate)/1000f;
		lastUpdate = now;
		app.draw(this, dt);
	}
	
	public void keyPressed(){
		app.keyPressed(this);}

	public void mousePressed() {
		app.mousePressed(this);
	}

	
	public static void main(String[] args) {
		app = new TestMajorityCA();
		PApplet.main(ProcessingSetup.class);
	}

}
