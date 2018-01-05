package Entities;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

import game.Vector2f;
import util.Rectangle;

public class Cloud {

	private Vector2f pos;
	private float vx;
	private float vy;
	private Image texture;
	private Rectangle hitbox;
	
	public Cloud(Vector2f pos, float vx, float vy){
		this.pos = pos;
		this.vx = vx;
		this.vy = vy;
		try {
			texture = new Image("Textures/Environment/cloud.png");
			texture.setFilter(Image.FILTER_NEAREST);
			texture.setAlpha(.3f);
		} catch (SlickException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		hitbox = new Rectangle(pos.getX(), pos.getY(), texture.getWidth(), texture.getHeight());
	}
	
	public void incrimentPosition(){
		pos.setX(pos.getX() + vx);
		pos.setY(pos.getY() + vy);
		
	}

	public Vector2f getPos() {
		return pos;
	}

	public void setPos(Vector2f pos) {
		this.pos = pos;
	}

	public float getVx() {
		return vx;
	}

	public void setVx(float vx) {
		this.vx = vx;
	}

	public float getVy() {
		return vy;
	}

	public void setVy(float vy) {
		this.vy = vy;
	}

	public Image getTexture() {
		return texture;
	}

	public void setTexture(Image texture) {
		this.texture = texture;
	}

	public Rectangle getHitbox() {
		return hitbox;
	}

	public void setHitbox(Rectangle hitbox) {
		this.hitbox = hitbox;
	}
	
}
