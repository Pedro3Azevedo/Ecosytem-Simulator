package ecosystem;

//TODO
public class WorldConstants {

	// World
	public final static double[] WINDOW = { -10, 10, -10, 10 };

	// Terrain
	public final static int NROWS = 25; //25
	public final static int NCOLS = 40; //40

	public static enum PatchType {
		EMPTY, OBSTACLE, FERTILE, FOOD
	}

	public final static double[] PATCH_TYPE_PROB = { .30f, .20f, .1f, .40f };
	public final static int NSTATES = PatchType.values().length;
	public static int[][] TERRAIN_COLORS = { 
			{ 100,149,237 }, { 0, 0, 0 }, { 219,219,112 }, { 33,94,33 } };
	
	public static String[] TERRAIN_IMGS = { "water.png", "obstacle.png", "sand.png", "grass.png"};
	
	public final static float[] REGENERATION_TIME = { 5.f, 10.f }; // seconds

	// Prey Population
	public final static float PREY_SIZE = .2f;
	public final static float PREY_MASS = 1f;
	public final static int INI_PREY_POPULATION = 55;
	public final static float INI_PREY_ENERGY = 10f; 
	public final static float ENERGY_FROM_PLANT = 4f;
	public final static float PREY_ENERGY_TO_REPRODUCE = 25f; 
	public static int[] PREY_COLOR = { 	250, 0 ,0 };
	public static String[] PREY_IMG = {"goldfish.png"};
	
	// Pred Population
	public final static float PRED_SIZE = .3f;
	public final static float PRED_MASS = .4f;
	public final static int INI_PRED_POPULATION = 15;
	public final static float INI_PRED_ENERGY = 50f; 
	public final static float ENERGY_FROM_PREY = 25f;
	public final static float PRED_ENERGY_TO_REPRODUCE = 200f; 
	public static int[] PRED_COLOR = { 	255, 13, 0 };
	public static String[] PRED_IMG = {"shark.png"};
	
	//Barco
	public final static float BARCO_SIZE = 0.4f; 
	public final static float BARCO_MASS = 1f; 
	public final static float INIT_BARCO_SPEED = 4f; 
	public final static int BARCO_POPULATION = 1;
	public final static float INI_BARCO_HP = 50f; 
	public final static float INI_BARCO_GAS = 200f; 
	public final static float GAS_FROM_PRED = 25f;
	public static String[] BARCO_IMG = {"barco.png"};
}
