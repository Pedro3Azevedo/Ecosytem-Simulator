package fractals;
import processing.core.PApplet;

public class ProcessingSetup extends PApplet {
	
	public static IProcessingApp app;
	private int lastUpdate;
	
	@Override
	public void settings() {
		size(800,600);
	}
	
	@Override
	public void setup() {
		app.setup(this);
		lastUpdate = millis(); //Numero de segundos desde que a app começou
	}
	
	@Override
	public void draw() {
		int now = millis();
		float dt = (now - lastUpdate)/1000f; //Tempo entre duas frames consecutivas
		lastUpdate = now;
		app.draw(this, dt);
	}
	
	@Override
	public void mousePressed() {
		app.mousePressed(this);
	}
	
	@Override
	public void mouseReleased() {
		app.mouseReleased(this);
	}
	
	@Override
	public void mouseDragged() {
		app.mouseDragged(this);
	}
	
	@Override
	public void keyPressed() {
		app.keyPressed(this);
	}
	
	public static void main(String[] args) {
		app = new LSystemApp();
		PApplet.main(ProcessingSetup.class);
	}

}
