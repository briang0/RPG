package Entities;

import org.newdawn.slick.Animation;
import org.newdawn.slick.Color;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

import game.Inventory;
import game.SwingingWeapon;
import game.Vector2f;
import io.TextureLoader;
import util.Rectangle;

public class Player {

	public final static Color NO_MOVE_COLOR = new Color(0.0f, 0.0f, 0.0f, 1.0f);

	public final int TEXTURE_STILL_DOWN = 0;
	public final int TEXTURE_DOWN_STEP1 = 1;
	public final int TEXTURE_DOWN_STEP2 = 2;
	public final int TEXTURE_DOWN_SWORD = 3;
	public final int TEXTURE_STILL_LEFT = 4;
	public final int TEXTURE_STILL_RIGHT = 5;
	public final int TEXTURE_RIGHT_STEP1 = 6;
	public final int TEXTURE_RIGHT_STEP2 = 7;
	public final int TEXTURE_LEFT_STEP1 = 8;
	public final int TEXTURE_LEFT_STEP2 = 9;
	public final int TEXTURE_STILL_TOP = 10;
	public final int TEXTURE_UP_STEP1 = 11;
	public final int TEXTURE_UP_STEP2 = 12;
	public final int TEXTURE_RIGHT_SWORD = 13;
	public final int TEXTURE_LEFT_SWORD = 14;
	public final int TEXTURE_UP_SWORD = 15;

	private Animation rightWalking;
	private Animation leftWalking;
	// private Animation upWalking;
	private Animation downWalking;
	private Animation activeAnimation;

	private Image texture;
	private Image tsd;
	private Image tds1;
	private Image tds2;
	private Image tds;
	private Image tsl;
	private Image tsr;
	// private Image trs1;
	// private Image trs2;
	private Image tls1;
	private Image tls2;

	private Vector2f pos;

	private int health;
	private int money;

	private float speedMax;
	private float energyX;
	private float energyY;

	private boolean up;
	private boolean right;
	private boolean down;
	private boolean left;
	private boolean coolDown;
	private boolean coolDownW;
	private boolean alive;
	
	private long coolDownDuration;
	private long coolDownStart;
	private long coolDownCurrent;
	private long coolDownDurationW;
	private long coolDownStartW;
	private long coolDownCurrentW;

	private Inventory inventory;

	private Rectangle hitbox;

	private int step;

	public Player(Vector2f pos, int health, float initEnergy, TextureLoader tLoad) {
		energyX = 0;
		energyY = 0;
		money = 0;
		this.pos = pos;
		this.health = health;
		step = 0;
		alive = health == 0 ? false : true;
		try {
			tsd = tLoad.loadTexture("Characters/MainCharacter/StillDown.png");
			tds1 = tLoad.loadTexture("Characters/MainCharacter/DownStep1.png");
			tds2 = tLoad.loadTexture("Characters/MainCharacter/DownStep2.png");
			tds = tLoad.loadTexture("Characters/MainCharacter/DownSword.png");
			tsl = tLoad.loadTexture("Characters/MainCharacter/StillLeft.png");
			tsr = tLoad.loadTexture("Characters/MainCharacter/StillRight.png");
			tls1 = tLoad.loadTexture("Characters/MainCharacter/LeftStep1.png");
			tls2 = tLoad.loadTexture("Characters/MainCharacter/LeftStep2.png");
			
			/*tds1 = new Image("Textures/Characters/MainCharacter/DownStep1.png");
			tds2 = new Image("Textures/Characters/MainCharacter/DownStep2.png");
			tds = new Image("Textures/Characters/MainCharacter/DownSword.png");
			tsl = new Image("Textures/Characters/MainCharacter/StillLeft.png");
			tsr = new Image("Textures/Characters/MainCharacter/StillRight.png");
			tls1 = new Image("Textures/Characters/MainCharacter/LeftStep1.png");
			tls2 = new Image("Textures/Characters/MainCharacter/LeftStep2.png");*/
		} catch (SlickException e) {
			e.printStackTrace();
		}

		activeAnimation = new Animation();
		leftWalking = new Animation(new Image[] { tsl, tls1, tls2 }, 500);
		leftWalking.setAutoUpdate(true);
		leftWalking.setLooping(true);
		rightWalking = new Animation(new Image[] { tsr }, 500);
		rightWalking.setAutoUpdate(true);
		rightWalking.setLooping(true);
		downWalking = new Animation(new Image[] { tsd, tds1, tds2 }, 500);
		downWalking.setAutoUpdate(true);
		downWalking.setLooping(true);
		inventory = new Inventory(10);
		hitbox = new Rectangle(pos.getX(), pos.getY(), 32, 64);
		texture = tsd;
		try {
			this.texture.setFilter(Image.FILTER_NEAREST);
		} catch (SlickException e) {
			e.printStackTrace();
		}
		speedMax = 1.5f;
	}
	
	public void beginCoolDown(long duration){
		coolDown = true;
		coolDownDuration = duration;
		coolDownStart = System.currentTimeMillis();
		coolDownCurrent = System.currentTimeMillis();
	}
	
	public void updateCoolDown(){
		coolDownCurrent = System.currentTimeMillis();
		if (coolDownCurrent - coolDownStart >= coolDownDuration){
			coolDown = false;
		}
	}
	
	public void beginCoolDownW(long duration){
		coolDownW = true;
		coolDownDurationW = duration;
		coolDownStartW = System.currentTimeMillis();
		coolDownCurrentW = System.currentTimeMillis();
	}
	
	public void updateCoolDownW(){
		coolDownCurrentW = System.currentTimeMillis();
		if (coolDownCurrentW - coolDownStartW >= coolDownDurationW){
			coolDownW = false;
		}
	}
	
	public boolean isCoolDownW(){
		return coolDownW;
	}

	public Vector2f getPos() {
		return pos;
	}

	public void setPos(Vector2f pos) {
		hitbox = new Rectangle(pos.getX(), pos.getY(), 32, 60);
		this.pos = pos;
	}

	public Vector2f getTileIndex(Vector2f pos) {
		float xPos = getX() / 32;
		float yPos = getY() / 32;

		return new Vector2f(xPos, yPos);
	}

	public int getHealth() {
		return health;
	}

	public void setHealth(int health) {
		this.health = health;
	}

	public float getX() {
		return pos.getX();
	}

	public float getY() {
		return pos.getY();
	}

	public boolean isUp() {
		return up;
	}

	public void setUp(boolean up) {
		this.up = up;
	}

	public boolean isRight() {
		return right;
	}

	public void setRight(boolean right) {
		this.right = right;
	}

	public boolean isDown() {
		return down;
	}

	public void setDown(boolean down) {
		this.down = down;
	}

	public boolean isLeft() {
		return left;
	}

	public void setLeft(boolean left) {
		this.left = left;
	}

	public boolean canMoveUp(Image hitmap, int x, int y) {
		
		Rectangle r = new Rectangle(getX(), getY()+48, 27, 2);
		if (r.containsColor(hitmap, NO_MOVE_COLOR)) {
			return false;
		}
		return true;
	}

	public boolean canMoveRight(Image hitmap, int x, int y) {

		Rectangle r = new Rectangle(getX()+2, getY() + 51, 27, 9);
		if (r.containsColor(hitmap, NO_MOVE_COLOR)) {
			return false;
		}
		return true;
	}

	public boolean canMoveDown(Image hitmap, int x, int y) {

		Rectangle r = new Rectangle(getX(), getY()+63, 27, 1);
		if (r.containsColor(hitmap, NO_MOVE_COLOR)) {
			return false;
		}
		return true;
	}

	public boolean canMoveLeft(Image hitmap, int x, int y) {
		Rectangle r = new Rectangle(getX(), getY() + 51, 1, 9);
		if (r.containsColor(hitmap, NO_MOVE_COLOR)) {
			return false;
		}
		return true;
	}

	public Inventory getInventory() {
		return inventory;
	}

	public void setInventory(Inventory i) {
		this.inventory = i;
	}

	public Image getTexture() {
		if (coolDown){
			texture.setAlpha(0.5f);
		}else{
			texture.setAlpha(1f);
		}
		return texture;
	}

	public void setTexture(Image texture) {
		this.texture = texture;
		try {
			this.texture.setFilter(Image.FILTER_NEAREST);
		} catch (SlickException e) {
			e.printStackTrace();
		}
	}

	public void setTexture(int val) {
		switch (val) {
		case TEXTURE_STILL_DOWN:
			texture = tsd;
			break;
		case TEXTURE_DOWN_STEP1:
			texture = tds1;
			break;
		case TEXTURE_DOWN_STEP2:
			texture = tds2;
			break;
		case TEXTURE_DOWN_SWORD:
			texture = tds;
			break;
		case TEXTURE_STILL_LEFT:
			texture = tsl;
			break;
		case TEXTURE_STILL_RIGHT:
			texture = tsr;
			break;
		case TEXTURE_LEFT_STEP1:
			texture = tls1;
			break;
		case TEXTURE_LEFT_STEP2:
			texture = tls2;
			break;
		default:
			texture = tsd;

		}
		// texture.setColor(0, .3f, .3f, .3f);
		// texture.setColor(1, .3f, .3f, .3f);
		// texture.setColor(2, .3f, .3f, .3f);
		// texture.setColor(3, .3f, .3f, .3f);
	}

	public int getStep() {
		return step;
	}

	public void setStep(int step) {
		this.step = step;
	}

	public Rectangle getHitbox() {
		return hitbox;
	}

	public void setHitbox(Rectangle hitbox) {
		this.hitbox = hitbox;
	}

	public int getMoney() {
		return money;
	}

	public void setMoney(int money) {
		this.money = money;
	}

	public float getEnergyX() {
		return energyX;
	}

	public void setEnergyX(float energyX) {
		this.energyX = energyX;
	}

	public float getEnergyY() {
		return energyY;
	}

	public void setEnergyY(float energyY) {
		this.energyY = energyY;
	}

	public void movement(boolean[] downKeys, Image hitmap) {
		boolean move = canMoveUp(hitmap, (int) getX(), (int) getY()-(int)energyY);
		if (downKeys[0] && !downKeys[2] && move) {
			System.out.println(!downKeys[2] && energyY <= 0);
			if (energyY >= -speedMax) {
				energyY -= .25;
			}
			setUp(true);
			setDown(false);
		} else if (!downKeys[2] && energyY <= 0) {
			energyY = 0;
		}
		if (!move){
			energyY = 0;
		}
		move = canMoveRight(hitmap, (int) ((int) getX()+energyX), (int) getY());
		if (downKeys[1] && !downKeys[3] && move) {
			if (energyX <= speedMax) {
				energyX += .25;
			}
			if (activeAnimation != rightWalking) {
				activeAnimation = rightWalking;
				activeAnimation.start();
			}
			setRight(true);
			setLeft(false);
		} else if (!downKeys[3] && energyX >= 0) {
			energyX = 0;
		}
		if (!move){
			energyX = 0;
		}
		if (activeAnimation == rightWalking && energyX == 0) {
			activeAnimation.stop();
			texture = activeAnimation.getImage(0);
			activeAnimation = new Animation();
		}
		move = canMoveDown(hitmap, (int) getX() + 1, (int) getY()+(int)energyY);
		if (downKeys[2] && !downKeys[0] && move) {
			if (energyY <= speedMax) {
				energyY += .25;
			}

			if (activeAnimation != downWalking) {
				activeAnimation = downWalking;
				activeAnimation.start();
			}
			setDown(true);
			setUp(false);
		} else if (!downKeys[0] && energyY >= 0) {
			energyY = 0;
		}
		if (activeAnimation == downWalking && energyY == 0) {
			activeAnimation.stop();
			texture = activeAnimation.getImage(0);
			activeAnimation = new Animation();
		}
		if (!move){
			energyY = 0;
		}
		move = canMoveLeft(hitmap, (int) getX() - (int)energyX, (int) getY());
		if (downKeys[3] && !downKeys[1] && move) {
			if (energyX >= -speedMax) {
				energyX -= .25;
			}
			if (activeAnimation != leftWalking) {
				activeAnimation = leftWalking;
				activeAnimation.start();
			}
			setLeft(true);
			setRight(false);
		} else if (!downKeys[1] && energyX <= 0) {
			energyX  = 0;
		}
		if (activeAnimation == leftWalking && energyX == 0) {
			activeAnimation.stop();
			texture = activeAnimation.getImage(0);
			activeAnimation = new Animation();
		}

		if (energyX >= speedMax) {
			energyX -= .25;
		}

		if (energyY >= speedMax) {
			energyY -= .25;
		}
		/*if (!move){
			energyX = 0;
		}*/
		setPos(new Vector2f(getX() + energyX, getY() + energyY));
		if (activeAnimation != null && activeAnimation.getFrameCount() > 0) {
			texture = activeAnimation.getCurrentFrame();
			activeAnimation.update(25);
		}
	}

	public float getSpeedMax() {
		return speedMax;
	}

	public void setSpeedMax(float speedMax) {
		this.speedMax = speedMax;
	}

	public boolean isCoolDown() {
		return coolDown;
	}

	public void setCoolDown(boolean coolDown) {
		this.coolDown = coolDown;
	}
	
	public void decrementHealth(int health){
		this.health -= health;
		
		if (this.health <= 0){
			kill();
		}
	}
	
	public void kill(){
		health = 0;
		alive = false;
	}

	public boolean isAlive() {
		return alive;
	}

	public void setAlive(boolean alive) {
		this.alive = alive;
	}
	

}
