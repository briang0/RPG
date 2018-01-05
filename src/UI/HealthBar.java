package UI;

import java.util.ArrayList;

import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

import game.Vector2f;
import io.TextureLoader;

public class HealthBar {

	public Image FULL_HEART;
	public Image HALF_HEART;
	public Image EMPTY_HEART;

	private ArrayList<Image> hearts;
	private int healthMax;
	private int currentHealth;
	private int numHearts;

	private Vector2f position;
	
	private TextureLoader tLoad;
	
	public HealthBar(int healthMax, Vector2f position, TextureLoader tLoad) {
		this.tLoad = tLoad;
		this.healthMax = healthMax;
		currentHealth = healthMax;
		this.position = position;
		numHearts = healthMax/2;
		try {
			initImages();
		} catch (SlickException e) {
			e.printStackTrace();
		}
		setHealth(currentHealth);
	}

	public HealthBar(int healthMax, int currentHealth, TextureLoader tLoad) {
		this.tLoad = tLoad;
		this.healthMax = healthMax;
		this.currentHealth = currentHealth;
		numHearts = healthMax/2;
		try {
			initImages();
		} catch (SlickException e) {
			e.printStackTrace();
		}
		setHealth(currentHealth);
	}

	private void initImages() throws SlickException {
		FULL_HEART = tLoad.loadTexture("UI/fullHealth.png");
		HALF_HEART = tLoad.loadTexture("UI/halfHealth.png");
		EMPTY_HEART = tLoad.loadTexture("UI/emptyHeart.png");
		hearts = new ArrayList<Image>();
	}

	public void setHealth(int currentHealth) {
		this.currentHealth = currentHealth;
		int difference = healthMax - currentHealth;
		int numFull = numHearts - (numHearts - currentHealth/2);
		int numHalf; 
		if (difference % 2 == 0){
			numHalf = 0;
		}else{
			numHalf = 1;
		}
		int numEmpty = numHearts - numFull - numHalf;
		
		hearts.clear();

		for (int x = 0; x < numFull; x++) {
			hearts.add(FULL_HEART);
		}
		for (int x = 0; x < numHalf; x++) {
			hearts.add(HALF_HEART);
		}
		for (int x = 0; x < numEmpty; x++) {
			hearts.add(EMPTY_HEART);
		}

	}

	public ArrayList<Image> getHearts() {
		setHealth(currentHealth);
		return hearts;
	}

	public Vector2f getPosition() {
		return position;
	}

	public void setPosition(Vector2f position) {
		this.position = position;
	}

}
