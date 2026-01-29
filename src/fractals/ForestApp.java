package fractals;

import java.util.ArrayList;
import java.util.List;

import processing.core.PApplet;
import processing.core.PVector;
import tools.SubPlot;

public class ForestApp implements IProcessingApp {

	private double[] window = { -15, 15, 0, 15 };
	private float[] viewport = { 0f, 0f, 1, 1 };
	private SubPlot plt;
	private List<Tree> forest;

	@Override
	public void setup(PApplet p) {

		plt = new SubPlot(window, viewport, p.width, p.height);
		forest = new ArrayList<Tree>();
	}

	@Override
	public void draw(PApplet p, float dt) {
		float[] bb = plt.getBoudingBox();
		p.rect(bb[0], bb[1], bb[2], bb[3]);

		for (Tree tree : forest) {
			tree.grow(dt);
			tree.display(p, plt);
		}
		
//		p.fill(70);
//		p.text("Press Left Mouse to plant", bb[0], bb[2]);
	}

	@Override
	public void keyPressed(PApplet p) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mousePressed(PApplet p) {
		double[] w = plt.getWorldCoord(p.mouseX, p.mouseY);
		PVector pos = new PVector((float)w[0], (float) w[1]);
		Tree tree;
		float rnd = p.random(100);
		if(rnd < 33) {
			Rule[] rules = new Rule[2];
			rules[0] = new Rule('X', "F[+X]F[-X]+X");
			rules[1] = new Rule('F', "FF");
			tree = new Tree("X", rules, pos, .4f, PApplet.radians(20f), 6, 0.5f, 1f, p);
		}else if (rnd >= 33 && rnd < 66) {
			Rule[] rules = new Rule[2];
			rules[0] = new Rule('X', "F[+X][-X]FX");
			rules[1] = new Rule('F', "FF");
			tree = new Tree("X", rules, pos, .4f, PApplet.radians(22.7f), 6, 0.5f, 1f, p);
		}else { //Arvore de frutos
			Rule[] rules = new Rule[2];
			rules[0] = new Rule('F', "G[+F]-F");
			rules[1] = new Rule('G', "GG");
			tree = new Tree("F", rules, pos, .4f, PApplet.radians(32.5f), 6, 0.5f, 1f, p);
		}
		forest.add(tree);
	}

	@Override
	public void mouseReleased(PApplet p) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseDragged(PApplet p) {
		// TODO Auto-generated method stub
		
	}
}
