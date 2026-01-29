package projeto;

import ecosystem.Population;
import ecosystem.Terrain;
import ecosystem.WorldConstants;
import physics.ParticleSystem;
import processing.core.PApplet;
import processing.core.PFont;
import processing.core.PImage;
import processing.core.PVector;
import tools.SubPlot;

public class app implements IProcessingApp {

	private float[] viewport = { 0.05f, 0.15f, 0.7f, 0.7f };
	private SubPlot plt;

	private Terrain terrain;
	private Population population;

	private boolean iniciar = false;

	private PImage photo;
	private String img = "imgs//home.jpg";

	private PImage photoInstructions;
	private String instructions = "imgs//instructions.jpg";

	private PImage home, fish, shark, gasTank;
	private int[] posImgs;
	private float[] imgsSize = new float[6];
	private float posyF = 300;
	private float aux1 = posyF;

	int loadingTime = 7000;
	int time;

	int imgSPosX, imgFPosX, imgFPosY, imgSPosY, imgGPosX, imgGPosY;

	float lastGas;

	@Override
	public void setup(PApplet p) {

		plt = new SubPlot(WorldConstants.WINDOW, viewport, p.width, p.height);

		terrain = new Terrain(p, plt);
		// terrain.setStateColors(getColors(p));
		terrain.setStateImgs(getImgs(p));
		terrain.initRandomCostom(WorldConstants.PATCH_TYPE_PROB);
		for (int i = 0; i < 3; i++)
			terrain.majorityRule();

		population = new Population(p, plt, terrain);

		// Load de imagens
		photo = p.loadImage(img);
		photoInstructions = p.loadImage(instructions);

		posImgs = new int[] { 150, p.height };
		home = p.loadImage("imgs//home.png");
		imgsSize[0] = home.width / 2;
		imgsSize[1] = home.height / 2;

		fish = p.loadImage("imgs//goldfish.png");
		shark = p.loadImage("imgs//shark.png");
		gasTank = p.loadImage("imgs//gasTank.png");

		imgSPosX = 920;
		imgFPosX = imgSPosX + (shark.width / 5 - fish.width / 10);
		imgFPosY = 140;
		imgSPosY = imgFPosY + fish.height / 10;
		imgGPosX = imgSPosX + shark.width / 10;
		imgGPosY = imgSPosY + shark.height / 5 + 35;

		time = p.millis();
	}

	@Override
	public void draw(PApplet p, float dt) {

		p.background(p.color(0, 128, 128));

		float[] bb = plt.getBoudingBox();

		p.fill(255);
		p.strokeWeight(10);
		p.rect(bb[0], bb[1], bb[2], bb[3]);

		p.image(photo, bb[0], bb[1], bb[2], bb[3]);

		if (iniciar) {

			int timeStamp = p.millis() - time;

			p.image(photoInstructions, bb[0], bb[1], bb[2], bb[3]);

			if (timeStamp > loadingTime) {

				if (plt.isInside(p.mouseX, p.mouseY)) {
					double[] w = plt.getWorldCoord(p.mouseX, p.mouseY);
					population.boatTarget.setPos(new PVector((float) w[0], (float) w[1]));
				}

				terrain.regenerate();
				population.update(dt, terrain);

				terrain.display(p);
				population.display(p, plt);

				pssDisplay(dt, p);

				// Status do jogo
				p.fill(0);
				PFont mono = p.createFont("font//manaspc.ttf", 30);
				p.textFont(mono);
				p.text("Ecossistema", bb[2] / 2 - 53, bb[1] - 20);

				mono = p.createFont("font//manaspc.ttf", 14);
				p.textFont(mono);

				p.image(fish, imgFPosX, imgFPosY, fish.width / 10, fish.height / 10);
				p.text(": " + this.population.allPrey.size(), imgFPosX + fish.width / 10 + 1,
						imgFPosY + fish.height / 20);

				p.image(shark, imgSPosX, imgSPosY, shark.width / 5, shark.height / 5);
				p.text(": " + this.population.allPred.size(), imgSPosX + shark.width / 5 + 1,
						imgSPosY + (shark.height / 5) / 1.5f);

				p.text("Estado do Barco", imgGPosX - 15, imgGPosY - 10);

				p.fill(102, 0, 0);
				p.strokeWeight(1);
				float map = PApplet.map(population.boat.gas, 0, WorldConstants.INI_BARCO_GAS, 0, 80);
				p.rect(imgGPosX + 15, imgGPosY + 100, gasTank.width / 5 - 28, -map, 12);
				p.image(gasTank, imgGPosX, imgGPosY, gasTank.width / 5, gasTank.height / 5);

				p.noFill();
				p.text("HP", imgGPosX - 15, imgGPosY + gasTank.height / 5 + 20);
				p.fill(255, 0, 0);
				p.rect(imgGPosX + 5, imgGPosY + gasTank.height / 5 + 10, 84, 19, 12);
				p.fill(3, 255, 3);
				p.strokeWeight(1);
				float mapHealth = PApplet.map(population.boat.hp, 0, WorldConstants.INI_BARCO_HP, 0, 84);
				p.rect(imgGPosX + 5, imgGPosY + gasTank.height / 5 + 10, mapHealth, 19, 12);

				checkEnd(p);
			}
		} else {
			if (posyF + imgsSize[1] - 3 <= 0)
				aux1 = 300;
			else
				aux1 -= 3;

			posyF = bb[3] + (aux1);

			float posxF = bb[2] + posImgs[0];

			p.image(home, posxF, posyF, imgsSize[0], imgsSize[1]);
		}
	}

	private void checkEnd(PApplet p) {

		if (this.population.allPrey.size() == 0) {
			this.img = "imgs//presasMortas.jpg";
			this.iniciar = false;
		}

		else if (this.population.boat.hp <= 0) {
			this.img = "imgs//barcoHp.jpg";
			this.iniciar = false;
		}

		else if (this.population.boat.gas <= 0) {
			this.img = "imgs//barcoGas.jpg";
			this.iniciar = false;
		}

		else if (this.population.allPred.size() == 0) {
			this.img = "imgs//vitoria.jpg";
			this.iniciar = false;
		}

		if (!iniciar)
			this.setup(p);

	}

	private int[] getColors(PApplet p) {
		int[] colors = new int[WorldConstants.NSTATES];
		for (int i = 0; i < WorldConstants.NSTATES; i++) {
			colors[i] = p.color(WorldConstants.TERRAIN_COLORS[i][0], WorldConstants.TERRAIN_COLORS[i][1],
					WorldConstants.TERRAIN_COLORS[i][2]);
		}
		return colors;
	}

	// Método para obter o array com os paths das imagens do terreno
	private PImage[] getImgs(PApplet p) {
		PImage[] imgs = new PImage[WorldConstants.NSTATES];
		for (int i = 0; i < WorldConstants.NSTATES; i++) {
			String path = "imgs//" + WorldConstants.TERRAIN_IMGS[i];
			imgs[i] = p.loadImage(path);
		}
		return imgs;
	}

	private void pssDisplay(float dt, PApplet p) {
		for (int i = population.pss.size() - 1; i >= 0; i--) {
			ParticleSystem ps = population.pss.get(i);
			ps.move(dt);
			ps.display(p, plt);
		}
	}

	@Override
	public void keyPressed(PApplet p) {

		if (p.key == ' ') {

			this.iniciar = true;
		}
	}

	@Override
	public void mousePressed(PApplet p) {

	}

}