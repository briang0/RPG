package Entities;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.util.pathfinding.Mover;

import game.Attack;
import game.Vector2f;
import io.TextureLoader;
import util.Rectangle;

public abstract class Enemy implements Mover{
	
	public final int HOG = 0;

	private Image texture;
	private Vector2f pos;
	private int dmg;
	private int width;
	private int height;
	private int id;
	protected Rectangle hitbox;
	
	//hog 32x32
	
	public Enemy(Vector2f pos, int id, int width, int height, String textureDir, TextureLoader tLoad, Attack[] attacks){
		this.pos = pos;
		this.id = id;
			try {
				texture = tLoad.loadTexture(textureDir);
			} catch (SlickException e) {
				// TODO make error window
				System.exit(0);
				e.printStackTrace();
			}
		
	}
	
	public Vector2f getTileIndex(){
		float xPos = pos.getX()/width;
		float yPos = pos.getY()/height;
		
		return new Vector2f(xPos, yPos);
	}
	
	public void move(boolean canMove){
		
	}

	public Image getTexture() {
		return texture;
	}

	public void setTexture(Image texture) {
		this.texture = texture;
	}

	public Vector2f getPos() {
		return pos;
	}

	public void setPos(Vector2f pos) {
		hitbox = new Rectangle(pos.getX(), pos.getY(), width, height);
		this.pos = pos;
	}

	public int getDmg() {
		return dmg;
	}

	public void setDmg(int dmg) {
		this.dmg = dmg;
	}

	public void setHitbox(Rectangle hitbox) {
		this.hitbox = hitbox;
	}

	public Rectangle getHitbox(){
		return hitbox;
	}
	
	public int getID(){
		return id;
	}
	
	public abstract int getAttackDamage();
}
