package physics;

import java.util.ArrayList;
import java.util.List;

import processing.core.PApplet;
import processing.core.PVector;
import tools.SubPlot;

public class SolorSystemApp implements IProcessingApp {

	private List<Body> planetas = new ArrayList<Body>();
	private List<Body> disparos = new ArrayList<Body>();
	private List<ParticleSystem> explosoes = new ArrayList<ParticleSystem>();

	// Mercurio
	private float mercMass = 0.33e24f;
	private float distMercSun = 5.7e10f;
	private float mercSpeed = 48e3f;

	// Venus
	private float venusMass = 4.867e24f;
	private float distVenusSun = 1.082e11f;
	private float venusSpeed = 35e3f;

	// Terra
	private float earthMass = 5.97e24f;
	private float distEarthSun = 1.496e11f;
	private float earthSpeed = 3e4f;

	// Marte
	private float marsMass = 6.41e23f;
	private float distMarsSun = 2.279e11f;
	private float marsSpeed = 24e3f;

	// Jupiter
	private float jupMass = 1.898e27f;
	private float distJupSun = 7.785e11f;
	private float jupSpeed = 13e3f;

	// Saturno
	private float satMass = 5.683e26f;
	private float distSatSun = 1.434e12f;
	private float satSpeed = 9e3f;

	// Urano
	private float uraMass = 8.681e25f;
	private float distUraSun = 2.871e12f;
	private float uraSpeed = 6e3f;

	// Neptuno
	private float nepMass = 1.024e26f;
	private float distNepSun = 4.495e12f;
	private float nepSpeed = 5e3f;

	// Sol
	private float sunMass = 1.989e30f;

	private float[] viewport = { 0, 0, 1, 1 };
	private float dist = 2;
	private double[] window = { -dist * distEarthSun, dist * distEarthSun, -dist * distEarthSun, dist * distEarthSun };

	private SubPlot plt;
	private Body sun, earth;
	private Body arma;

	private boolean atingiu;

	private float distArma = -distMarsSun - 5e10f;

	private float speedUp = 60 * 60 * 24 * 30;

	private int zoom = 0;

	// Particulas
	private ParticleSystem ps;

	// PSC Control
	private float[] velParams = { PApplet.radians(180), PApplet.radians(360), distMercSun / 5, distMercSun / 5 };
	private float[] lifetimeParams = { 1.3f, 1.3f };
	private float[] radiusParams = { distMercSun / 30, distMercSun / 30 };
	private float flow = 1000;

	@Override
	public void setup(PApplet p) {

		planetas = new ArrayList<Body>();
		plt = new SubPlot(window, viewport, p.width, p.height);

		// Sol
		sun = new Body(new PVector(), new PVector(), sunMass, distEarthSun / 10, p.color(255, 128, 0));

		// Terra
		earth = new Body(new PVector(0, distEarthSun), new PVector(earthSpeed, 0), earthMass, distEarthSun / 5,
				p.color(46, 58, 146));
		planetas.add(earth);

		// Mercurio
		addPlanet(distMercSun, mercSpeed, mercMass, p.color(206, 204, 209));
		// Venus
		addPlanet(distVenusSun, venusSpeed, venusMass, p.color(187, 183, 171));
		// Marte
		addPlanet(distMarsSun, marsSpeed, marsMass, p.color(193, 68, 14));
		// Jupiter
		addPlanet(distJupSun, jupSpeed, jupMass, p.color(200, 139, 58));
		// Saturno
		addPlanet(distSatSun, satSpeed, satMass, p.color(197, 171, 110));
		// Urano
		addPlanet(distUraSun, uraSpeed, uraMass, p.color(213, 251, 252));
		// Neptuno
		addPlanet(distNepSun, nepSpeed, nepMass, p.color(96, 129, 255));

		PSControl psc = new PSControl(velParams, lifetimeParams, radiusParams, flow, p.color(255, 128, 0), true);
		ps = new ParticleSystem(new PVector(0, 0), new PVector(mercSpeed, 0), 1f, .2f, psc);

		arma = new Body(new PVector(0, distArma), new PVector(0, marsSpeed), marsMass, distMarsSun / 40,
				p.color(0, 0, 0));

	}

	public void addPlanet(float distSun, float speed, float mass, int color) {

		Body planeta = new Body(new PVector(0, distSun), new PVector(speed, 0), mass, distSun / 5, color);
		this.planetas.add(planeta);
	}

	// Verifica se algum disparo acertou um body
	public void hitBody(PApplet p, float dt) {
		atingiu = false;

		// Quando a bala acerta o planeta ou sol, ela desaparece
		for (int i = disparos.size() - 1; i > 0; i--) {

			// Disparo no sol
			float distSol = PVector.dist(disparos.get(i).pos, sun.pos);
			atingiu = distSol < sun.radius;
			if (atingiu) {
				disparos.remove(i);
				break;
			}

			// Disparo nos planetas
			for (Body planeta : this.planetas) {
				float dist = PVector.dist(disparos.get(i).pos, planeta.pos);
				atingiu = dist < planeta.radius;
				if (atingiu) {
					// Cria as particulas de explosão
					PSControl psc = new PSControl(velParams, lifetimeParams, radiusParams, flow, planeta.color, false);
					ParticleSystem explosao = new ParticleSystem(disparos.get(i).pos, new PVector(mercSpeed, 0), 1f,
							.2f, psc);
					explosoes.add(explosao);
					disparos.remove(i);
					break;
				}
			}

			// Se foi atingido nao dá display
			if (atingiu)
				break;
			disparos.get(i).move(dt * speedUp);
			disparos.get(i).display(p, plt);
		}
	}

	@Override
	public void draw(PApplet p, float dt) {

		float[] pp = plt.getBoudingBox();

		p.fill(56, 89, 172);
		p.rect(pp[0], pp[1], pp[2], pp[3]);

		sun.display(p, plt);

		ps.move(dt);
		ps.display(p, plt);

		for (Body planeta : this.planetas) {
			PVector f = sun.attraction(planeta);
			planeta.applyForce(f);

			planeta.move(dt * speedUp);
			planeta.display(p, plt);
		}

		p.fill(0);
		p.text("Zoom = " + this.zoom, pp[0] + 10, pp[1] + 15);
		p.text("Backwards -> Press b", pp[0] + 10, pp[1] + 30);
		p.text("Sun mass x2 -> Press m", pp[0] + 10, pp[1] + 45);

		// Só é possivel "jogar" no primeiro modo de zoom
		if (dist == 2) {
			p.text("W -> shoot", pp[0] + 10, pp[1] + 60);
			p.text("A and D -> move", pp[0] + 10, pp[1] + 75);

			arma.move(dt);
			arma.display(p, plt);

			hitBody(p, dt);

			for (int i = explosoes.size() - 1; i >= 0; i--) {
				ParticleSystem ps = explosoes.get(i);
				ps.move(dt);
				ps.display(p, plt);
			}
		}
	}

	@Override
	public void mousePressed(PApplet p) {

	}

	@Override
	public void keyPressed(PApplet p) {
		if (p.key == 'z' && this.zoom == 0) {
			dist = 10;
			double[] window = { -dist * distEarthSun, dist * distEarthSun, -dist * distEarthSun, dist * distEarthSun };
			plt = new SubPlot(window, viewport, p.width, p.height);
			this.zoom++;
		} else if (p.key == 'z' && this.zoom == 1) {
			dist = 40;
			double[] window = { -dist * distEarthSun, dist * distEarthSun, -dist * distEarthSun, dist * distEarthSun };
			plt = new SubPlot(window, viewport, p.width, p.height);
			this.zoom = 2;
		} else if (p.key == 'z' && this.zoom == 2) {
			dist = 2;
			double[] window = { -dist * distEarthSun, dist * distEarthSun, -dist * distEarthSun, dist * distEarthSun };
			plt = new SubPlot(window, viewport, p.width, p.height);
			this.zoom = 0;
		}

		if (p.key == 'b') {
			for (Body planeta : this.planetas) {
				planeta.setVel(planeta.getVel().mult(-1));
			}
		}

		if (p.key == 'm') {
			sun.mass = sun.mass * (2);
		}
		if (p.key == 'w') {
			Body bd = new Body(arma.pos, new PVector(0, marsSpeed * 10), marsMass, distMarsSun / 40,
					p.color(255, 255, 255));
			disparos.add(bd);
		}

		if (p.key == 'd') {
			arma.pos.add(new PVector(5e9f, 0));
		}
		if (p.key == 'a') {
			arma.pos.add(new PVector(-5e9f, 0));
		}
	}

}
