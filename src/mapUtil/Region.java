 package mapUtil;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

import Entities.Cloud;
import Entities.Enemy;
import Entities.Hog;
import Entities.Player;
import game.Attack;
import game.Vector2f;
import io.FileLoader;
import io.TextureLoader;
import util.DayCycler;
import util.Rectangle;

public class Region {

	public final float TIME_MAX = 144000f;
	public final float TIME_MIN = 0f;
	public final int tileDim = 32;

	private int id;
	private int width;
	private int height;
	private int tileWidth;
	private int tileHeight;
	private float zoom;
	private float mult;
	private Image texture;
	private Image hitmap;
	private String sound;
	private Vector2f playerPos;
	private ArrayList<Enemy> enemies;
	private float windSpeed;
	private float cloudFrequency = 100;
	private ArrayList<Cloud> clouds = new ArrayList<Cloud>();
	private Cloud modelCloud;
	private float alpha;
	private DayCycler cycle;
	private Rectangle[][] regionTiles;
	private GridMap gridMap;
	private ArrayList<Transition> transitions = new ArrayList<Transition>();

	public Region(String dir) {
		ArrayList<String> loadedData = new ArrayList<String>();
		enemies = new ArrayList<Enemy>();
		try {
			loadedData = FileLoader.getTextData(dir);
		} catch (IOException e) {
			// TODO make error window
			System.exit(2);
			e.printStackTrace();
		}
		id = Integer.parseInt(loadedData.get(0).split(":")[1]);
		tileWidth = Integer.parseInt(loadedData.get(1).split(":")[1]);
		width = tileWidth * 32;
		tileHeight = Integer.parseInt(loadedData.get(2).split(":")[1]);
		height = tileHeight * 32;

		zoom = Float.parseFloat(loadedData.get(3).split(":")[1]);
		mult = Float.parseFloat(loadedData.get(4).split(":")[1]);
		try {
			//TODO turn true into quality
			TextureLoader tLoad = new TextureLoader(loadedData.get(5).split(":")[1], true);
			texture = tLoad.loadTexture("");
		} catch (SlickException e) {
			// TODO throw error window
			System.exit(2);
			e.printStackTrace();
		}
		try {
			hitmap = new Image(loadedData.get(6).split(":")[1]);
		} catch (SlickException e) {
			// TODO throw error window
			System.exit(2);
			e.printStackTrace();
		}
		// sound = loadedData.get(7).split(":")[1];

		String positionData = (loadedData.get(8).split(":")[1]);
		float px = Float.parseFloat(positionData.split(",")[0]);
		float py = Float.parseFloat(positionData.split(",")[1]);

		playerPos = new Vector2f(px*32, py*32);
		try {
			String enemyData = (loadedData.get(9).split(":")[1]);
			String[] specificEnemyData = enemyData.split("&");
			for (int x = 0; x < specificEnemyData.length; x++) {
				int enemyType = 0;
				String[] typeAndCoords = specificEnemyData[x].split("#");
				if (typeAndCoords[0].equals("hog")) {
					enemyType = 0;
				}
				String[] coords = typeAndCoords[1].split("#");
				float x1 = Float.parseFloat(coords[0].split(",")[0]) * 32;
				float x2 = Float.parseFloat(coords[0].split(",")[1]) * 32;
				//TODO turn true into quality
				Attack[] attack = {new Attack(1)};
				enemies.add(new Hog(new Vector2f(x1, x2), enemyType, 32, 32, "Enemies/Warthog/warthogStillDown.png", new TextureLoader("Textures/", true), attack));
			}
		} catch (Exception err) {
		}
		String[] trans = loadedData.get(10).split(":");
		ArrayList<String> vecGroup = new ArrayList<String>();
		for (int x = 1; x < trans.length; x++) {
			vecGroup.add(trans[x]);
		}
		for (int x = 0; x < vecGroup.size(); x++) {
			String[] data = vecGroup.get(x).split("&");
			String[] data2 = data[0].split("#");
			ArrayList<Vector2f> transitionVectors = new ArrayList<Vector2f>();
			for (int y = 0; y < data2.length; y++) {
				String[] vec = data2[x].split(",");
				transitionVectors.add(new Vector2f(Float.parseFloat(vec[0]), Float.parseFloat(vec[1])));
			}
			transitions.add(new Transition(transitionVectors, Integer.parseInt(data[1])));
		}

		modelCloud = new Cloud(new Vector2f(-5, -5), 5, 0);
		alpha = 1f;
		cycle = new DayCycler(TIME_MAX, TIME_MIN, 12000, alpha, 50);

		regionTiles = new Rectangle[tileWidth][tileHeight];
		for (int x = 0; x < tileWidth; x++) {
			for (int y = 0; y < tileHeight; y++) {
				regionTiles[x][y] = new Rectangle(x * tileDim, y * tileDim, tileDim, tileDim);
			}
		}

	}

	public void addCloud(Vector2f pos, float vx, float vy) {
		clouds.add(new Cloud(pos, vx, vy));
	}

	public void checkForDeadClouds() {
		for (int x = 0; x < clouds.size(); x++) {
			if (clouds.get(x).getPos().getX() >= width) {
				clouds.remove(x);
			}
		}
		if (clouds.size() <= cloudFrequency) {
			Random rand = new Random();
			float randHeight = rand.nextFloat() * height;
			Rectangle r = new Rectangle(modelCloud.getTexture().getWidth() * -1, randHeight,
					modelCloud.getTexture().getWidth(), modelCloud.getTexture().getHeight());
			boolean canMakeCloud = true;

			for (int x = 0; x < clouds.size(); x++) {
				if (r.collides(clouds.get(x).getHitbox()) ||  clouds.size() > 3) {
					canMakeCloud = false;
				}
			}
			if (canMakeCloud) {
				clouds.add(new Cloud(new Vector2f(modelCloud.getTexture().getWidth() * -1, randHeight),
						.3f, 0));
			}
		}
	}

	public Vector2f getLowestBoundTile(Vector2f pos) {
		float tileY = pos.getY() / tileDim;
		float tileX = pos.getX() / tileDim;
		Rectangle r = new Rectangle(tileX * tileDim, tileY * tileDim, tileDim, tileDim);

		while (!r.containsColor(hitmap, Player.NO_MOVE_COLOR) && tileY <= tileHeight) {
			tileY++;
			r = new Rectangle(tileX * tileDim, tileY * tileDim, tileDim, tileDim);
		}

		return new Vector2f(tileX, tileY + 1);
	}

	public Vector2f getHighestBoundTile(Vector2f pos) {
		float tileY = pos.getY() / tileDim;
		float tileX = pos.getX() / tileDim;
		Rectangle r = new Rectangle(tileX * tileDim, tileY * tileDim, tileDim, tileDim);

		while (!r.containsColor(hitmap, Player.NO_MOVE_COLOR) && tileY >= 0) {
			tileY--;
			r = new Rectangle(tileX * tileDim, tileY * tileDim, tileDim, tileDim);
		}

		return new Vector2f(tileX, tileY);
	}

	public Vector2f getRightmostBoundTile(Vector2f pos) {
		float tileY = pos.getY() / tileDim;
		float tileX = pos.getX() / tileDim;
		Rectangle r = new Rectangle(tileX * tileDim, tileY * tileDim, tileDim, tileDim);

		while (!r.containsColor(hitmap, Player.NO_MOVE_COLOR) && tileX <= tileWidth) {
			tileX++;
			r = new Rectangle(tileX * tileDim, tileY * tileDim, tileDim, tileDim);
		}

		return new Vector2f(tileX + 1, tileY);
	}

	public Vector2f getLeftmostBoundTile(Vector2f pos) {
		float tileY = pos.getY() / tileDim;
		float tileX = pos.getX() / tileDim;
		Rectangle r = new Rectangle(tileX * tileDim, tileY * tileDim, tileDim, tileDim);
		while (!r.containsColor(hitmap, Player.NO_MOVE_COLOR) && tileX >= 0) {
			tileX--;
			r = new Rectangle(tileX * tileDim, tileY * tileDim, tileDim, tileDim);
		}

		return new Vector2f((int) tileX, (int) tileY);
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public float getZoom() {
		return zoom;
	}

	public void setZoom(float zoom) {
		this.zoom = zoom;
	}

	public float getMult() {
		return mult;
	}

	public void setMult(float mult) {
		this.mult = mult;
	}

	public Image getTexture() {
		return texture;
	}

	public void setTexture(Image texture) {
		this.texture = texture;
	}

	public Image getHitmap() {
		return hitmap;
	}

	public void setHitmap(Image hitmap) {
		this.hitmap = hitmap;
	}

	public String getSound() {
		return sound;
	}

	public void setSound(String sound) {
		this.sound = sound;
	}

	public Vector2f getPlayerPos() {
		return playerPos;
	}

	public void setPlayerPos(Vector2f playerPos) {
		this.playerPos = playerPos;
	}

	public ArrayList<Enemy> getEnemies() {
		return enemies;
	}

	public void setEnemies(ArrayList<Enemy> enemies) {
		this.enemies = enemies;
	}

	public float getWindSpeed() {
		return windSpeed;
	}

	public void setWindSpeed(float windSpeed) {
		this.windSpeed = windSpeed;
	}

	public float getCloudFrequency() {
		return cloudFrequency;
	}

	public void setCloudFrequency(float cloudFrequency) {
		this.cloudFrequency = cloudFrequency;
	}

	public ArrayList<Cloud> getClouds() {
		return clouds;
	}

	public void setClouds(ArrayList<Cloud> clouds) {
		this.clouds = clouds;
	}

	public DayCycler getCycle() {
		return cycle;
	}

	public void setCycle(DayCycler cycle) {
		this.cycle = cycle;
	}

	public Rectangle[][] getRegionTiles() {
		return regionTiles;
	}

	public void setRegionTiles(Rectangle[][] regionTiles) {
		this.regionTiles = regionTiles;
	}
	
	public float getAlpha(){
		return alpha;
	}

	public String checkTransition(Player p) {
		float xVal = (float) Math.round(p.getX() / tileDim);
		float yVal = (float) Math.round(p.getY() / tileDim);
		Vector2f inVec = new Vector2f(xVal, yVal);
		for (int x = 0; x < transitions.size(); x++) {
			if (transitions.get(x).isIn(inVec)) {
				return transitions.get(x).getMapData();
			}
		}
		return "";
	}

	public GridMap getGridMap() {
		return gridMap;
	}

	public void setGridMap(GridMap gridMap) {
		this.gridMap = gridMap;
	}

}
