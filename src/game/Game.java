package game;

import java.util.ArrayList;
import java.util.HashMap;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.BasicGame;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;

import Entities.Cloud;
import Entities.Drop;
import Entities.Enemy;
import Entities.Hog;
import Entities.Player;
import UI.HealthBar;
import UI.InventorySlot;
import io.FileValidator;
import io.TextureLoader;
import mapUtil.GridMap;
import mapUtil.LightCaster;
import mapUtil.Region;
import menus.Dead;
import util.Controls;
import util.ErrorLogger;
import util.ImageProcessor;
import util.MovementModel;
import util.Rectangle;

@SuppressWarnings("unused")
public class Game extends BasicGame {

	public ErrorLogger errors;
	private static int fpsCap = 144;
	private static float adjustedIncrement = 144f / fpsCap;
	private static int width = 1920;
	private static int height = 1080;
	private static int currentTick = 1;
	private Camera cam;
	protected Region currentRegion;

	private Input input = new Input(height);
	private HashMap<Integer, Integer> keyCodes;
	private Player player;
	private static AppGameContainer app;
	float translateX = width / 2, translateY = height / 2;
	private long tickTime = System.currentTimeMillis();
	private long prevTick = 0;
	private HealthBar b;
	private GridMap map;

	private ArrayList<Drop> drops = new ArrayList<Drop>();

	private InventorySlot[] invSlots;

	private boolean quality;
	private boolean mute;
	private boolean fullscreen;

	private TextureLoader tLoad;

	public Game(int[] resolution, boolean quality, boolean fullscreen, boolean mute) throws SlickException {
		super("Game Title");
		width = resolution[0];
		height = resolution[1];
		this.quality = quality;
		this.mute = mute;
		this.fullscreen = fullscreen;
		app = new AppGameContainer(this);
		app.setDisplayMode(width, height, false);
		app.setFullscreen(fullscreen);
		app.setTargetFrameRate(fpsCap);
		app.start();
	}

	@Override
	public void render(GameContainer gc, Graphics g) throws SlickException {

		g.translate(-cam.getPosition().getX(), -cam.getPosition().getY());
		g.scale(cam.getZoom(), cam.getZoom());
		g.drawImage(currentRegion.getTexture(), 0f, 0f);

		g.drawImage(player.getTexture(), player.getX(), player.getY());

		renderEnemies(gc, g);
		renderClouds(gc, g);
		renderDrops(gc, g);
		renderUI(gc, g);
		renderItem(gc, g);
		if (!player.isAlive()) {
			Dead.deadScreen(g, width, height);
		}
	}

	public void renderEnemies(GameContainer gc, Graphics g) throws SlickException {
		for (int x = 0; x < currentRegion.getEnemies().size(); x++) {
			Enemy current = currentRegion.getEnemies().get(x);
			switch (current.getID()) {
			case 0:
				current = (Hog) current;
				break;
			}

			try {
				current.setPos(MovementModel.AStar(currentRegion, current.getPos(), player.getPos(), map, 1.75f, g));
			} catch (Exception e) {
				e.printStackTrace();
			}

			g.drawImage(current.getTexture(), current.getPos().getX(), current.getPos().getY());

			if (player.getHitbox().collides(current.getHitbox()) && !player.isCoolDown()) {
				int decrement = current.getAttackDamage();
				player.decrementHealth(decrement);
				player.beginCoolDown(1000);

				if (player.getX() - 16 > current.getPos().getX()) {
					player.setEnergyX(8);
				}
				if (player.getX() - 16 < current.getPos().getX()) {
					player.setEnergyX(-8);
				}
				if (player.getY() - 16 > current.getPos().getY()) {
					player.setEnergyY(8);
				}
				if (player.getY() - 16 < current.getPos().getY()) {
					player.setEnergyY(-8);
				}
			}
		}
	}

	public void setColor(Graphics g, GameContainer gc, float x, float y, float r, float gr, float b) {
		Color prev = g.getColor();
		g.setColor(new Color(r, gr, b));
		g.setColor(prev);
	}

	public void renderClouds(GameContainer gc, Graphics g) throws SlickException {
		ArrayList<Cloud> clouds = currentRegion.getClouds();

		for (int x = 0; x < clouds.size(); x++) {
			g.drawImage(clouds.get(x).getTexture(), clouds.get(x).getPos().getX(), clouds.get(x).getPos().getY());
			clouds.get(x).incrimentPosition();
			currentRegion.checkForDeadClouds();
		}
	}

	public void renderUI(GameContainer gc, Graphics g) throws SlickException {
		ArrayList<Image> img = b.getHearts();
		float xAxis, yAxis;

		if (cam.getPosition().getY() != cam.getPreviousPosition().getY()) {
			yAxis = cam.getZoom() * player.getY() / 2 - 250;
		} else {
			yAxis = b.getPosition().getY();
		}

		if (cam.getPosition().getX() != cam.getPreviousPosition().getX()) {
			xAxis = cam.getZoom() * player.getX() / 2 - 400;
		} else {
			xAxis = b.getPosition().getX();
		}

		b.setPosition(new Vector2f(xAxis, yAxis));
		for (int x = 0; x < img.size(); x++) {
			g.drawImage(b.getHearts().get(x), xAxis + x * 16, yAxis);
		}
		for (int x = 0; x < invSlots.length; x++) {
			g.drawImage(new Image("Textures/UI/itemSlot.png"), xAxis + ((x * 32) + 250), yAxis);
			invSlots[x].setX(xAxis + ((x * 32) + 250));
			invSlots[x].setY(yAxis);
			invSlots[x].render(g);
		}
	}

	public void renderDrops(GameContainer gc, Graphics g) throws SlickException {
		for (int x = 0; x < drops.size(); x++) {
			Item i = drops.get(x).getI();
			g.drawImage(i.getTexture().getScaledCopy(drops.get(x).getScale()), drops.get(x).getX(),
					drops.get(x).getY());
			if (player.getHitbox().collides(drops.get(x).getHitbox())) {
				if (player.getInventory().add(i)) {
					drops.remove(drops.get(x));
				}
			}
		}
	}

	public void renderItem(GameContainer gc, Graphics g) throws SlickException {
		Item i = player.getInventory().getSelectedItem();
		if (i != null) {
			Image img = i.getTexture();
			float x = 0, y = 0;
			if (player.isCoolDownW() && player.getInventory().getSelectedItem() instanceof SwingingWeapon) {
				SwingingWeapon wep = (SwingingWeapon) i;
				if (player.isUp()) {
					img.rotate(90);
					x = wep.upXPos(player.getX());
					y = wep.upYPos(player.getY());
				} else if (player.isDown()) {
					img.rotate(-90);
					x = wep.downXPos(player.getX());
					y = wep.downYPos(player.getY());
				} else if (player.isRight()) {
					img.rotate(180);
					x = wep.rightXPos(player.getX());
					y = wep.rightYPos(player.getY());
				} else if (player.isLeft()) {
					x = wep.leftXPos(player.getX());
					y = wep.leftYPos(player.getY());
				}
				g.drawImage(img, x, y);
				img.rotate(-img.getRotation());
			}
		}
	}

	@Override
	public void init(GameContainer gc) throws SlickException {
		tLoad = new TextureLoader("Textures/", quality);
		invSlots = new InventorySlot[10];
		FileValidator.validateFiles();
		keyCodes = Controls.keyMap();
		b = new HealthBar(10, 7, tLoad);

		currentRegion = new Region("gameData/Maps/testmap.txt");

		cam = new Camera(width, height, currentRegion.getWidth(), currentRegion.getHeight(),
				currentRegion.getPlayerPos(), currentRegion.getZoom());

		player = new Player(new Vector2f(currentRegion.getPlayerPos().getX(), currentRegion.getPlayerPos().getY()), 10,
				adjustedIncrement, tLoad);

		currentRegion.addCloud(new Vector2f(0, 500), .3f, 0);
		b.setPosition(new Vector2f(cam.getZoom() * player.getX() / 2 - 400, cam.getZoom() * player.getY() / 2 - 250));
		map = new GridMap(32, currentRegion.getHitmap(), player.getPos());

		drops.add(new Drop(new BasicSword(0, new Rectangle(0, 0, 0, 0), false, 1, tLoad), 200, 200));
		for (int x = 0; x < invSlots.length; x++) {
			invSlots[x] = new InventorySlot(0, 0, null, tLoad);
		}
		invSlots[0].setSelected(true);
	}

	public void initMap(String map) {

		currentRegion = new Region(map);
		cam = new Camera(width, height, currentRegion.getWidth(), currentRegion.getHeight(),
				currentRegion.getPlayerPos(), currentRegion.getZoom());
		player.setPos(currentRegion.getPlayerPos());

	}

	@Override
	public void update(GameContainer gc, int delta) throws SlickException {

		setColor(gc.getGraphics(), gc, player.getX(), player.getY(), 1, 0, 0);

		if (System.nanoTime() - tickTime >= 50) {
			currentTick++;
			float alpha = currentRegion.getCycle().getAlpha();
			currentRegion.setTexture(ImageProcessor.nightCycle(currentRegion.getTexture(), alpha));
			currentRegion.getCycle().setAlpha(currentRegion.getCycle().getCurrentAlpha());
			Image i = player.getTexture();

			i.setColor(0, alpha, alpha, alpha, i.getAlpha());
			i.setColor(1, alpha, alpha, alpha, i.getAlpha());
			i.setColor(2, alpha, alpha, alpha, i.getAlpha());
			i.setColor(3, alpha, alpha, alpha, i.getAlpha());

			player.setTexture(i);

			tickTime = System.currentTimeMillis();
		}

		input.poll(width, height);
		input.isKeyDown(Input.KEY_SPACE);
		boolean[] keysDown = { input.isKeyDown(keyCodes.get(Controls.UP_KEY_ID)),
				input.isKeyDown(keyCodes.get(Controls.RIGHT_KEY_ID)),
				input.isKeyDown(keyCodes.get(Controls.DOWN_KEY_ID)),
				input.isKeyDown(keyCodes.get(Controls.LEFT_KEY_ID)),
				input.isKeyDown(keyCodes.get(Controls.ACTION_KEY_ID)),
				input.isKeyDown(keyCodes.get(Controls.INTERACTION_KEY_ID)),
				input.isKeyDown(keyCodes.get(Controls.INVENTORY_KEY_ID)) };

		boolean[] invKeysDown = { input.isKeyDown(keyCodes.get(Controls.INVENTORY_KEY_1)),
				input.isKeyDown(keyCodes.get(Controls.INVENTORY_KEY_2)),
				input.isKeyDown(keyCodes.get(Controls.INVENTORY_KEY_3)),
				input.isKeyDown(keyCodes.get(Controls.INVENTORY_KEY_4)),
				input.isKeyDown(keyCodes.get(Controls.INVENTORY_KEY_5)),
				input.isKeyDown(keyCodes.get(Controls.INVENTORY_KEY_6)),
				input.isKeyDown(keyCodes.get(Controls.INVENTORY_KEY_7)),
				input.isKeyDown(keyCodes.get(Controls.INVENTORY_KEY_8)),
				input.isKeyDown(keyCodes.get(Controls.INVENTORY_KEY_9)),
				input.isKeyDown(keyCodes.get(Controls.INVENTORY_KEY_0)) };

		if (input.isKeyDown(keyCodes.get(Controls.ACTION_KEY_ID))) {
			player.beginCoolDownW(500);
		}

		for (int x = 0; x < invKeysDown.length; x++) {
			if (invKeysDown[x]) {
				clearInvSelections();
				invSlots[x].setSelected(true);
				player.getInventory().setSelectedIndex(x);
			}
			Item i = player.getInventory().getItem(x);

			if (i != null) {
				invSlots[x].setItem(i.getInventoryAdaption());
			}
		}

		player.movement(keysDown, currentRegion.getHitmap());

		String transitionStatus = currentRegion.checkTransition(player);
		if (!transitionStatus.equals("")) {
			initMap(transitionStatus);
		}
		if (player.isCoolDown()) {
			player.updateCoolDown();
		}
		if (player.isCoolDownW()) {
			player.updateCoolDownW();
		}
		b.setHealth(player.getHealth());
		cam.update(player.getPos());

	}

	private void clearInvSelections() {
		for (InventorySlot inv : invSlots) {
			inv.setSelected(false);
		}
	}

	/*
	 * public static void main(String[] args) throws SlickException {
	 * 
	 * app = new AppGameContainer(new Game()); app.setDisplayMode(width, height,
	 * false); app.setFullscreen(false); app.setTargetFrameRate(fpsCap);
	 * app.start(); }
	 */
}