package game;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

import io.TextureLoader;
import util.Rectangle;

public class BasicSword extends Item implements SwingingWeapon{

	private float damage;
	private Rectangle hitbox;
	
	public BasicSword(int pos, Rectangle hitbox, boolean isEquipt, float damage, TextureLoader tLoad) {
		super(0, pos, hitbox, isEquipt);
		this.damage = damage;
		try {
			texture = tLoad.loadTexture("Weapons/testsword.png");
		} catch (SlickException e) {
			e.printStackTrace();
		}
		inventoryImage = texture;
		inventoryImage.rotate(45);
		this.hitbox = hitbox;
	}
	
	public float getDamage(){
		return damage;
	}
	
	public void setDamage(){
		
	}
	
	public Image getTexture(){
		return texture;
	}

	@Override
	public Image getInventoryAdaption() {
		return inventoryImage;
	}

	@Override
	public float upXPos(float x) {
		return x + 1;
	}

	@Override
	public float upYPos(float y) {
		return y + 16;
	}

	@Override
	public float downXPos(float x) {
		return x + 1;
	}

	@Override
	public float downYPos(float y) {
		return y + 53;
	}

	@Override
	public float rightXPos(float x) {
		return x + 17;
	}

	@Override
	public float rightYPos(float y) {
		return y + 38;
	}

	@Override
	public float leftXPos(float x) {
		return x - 18;
	}

	@Override
	public float leftYPos(float y) {
		return y + 32;
	}

	
}
