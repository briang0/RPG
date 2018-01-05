package Entities;

import game.Item;
import util.Rectangle;

public class Drop {

	private Item I;
	private float x, y;
	private float scale;
	private boolean ascend;
	private Rectangle hitbox;

	public Drop(Item I, float x, float y) {
		this.setI(I);
		this.x = x;
		this.y = y;
		scale = 1;
		hitbox = new Rectangle(x, y, I.getTexture().getWidth(), I.getTexture().getHeight());
	}

	public float getScale() {
		if (System.currentTimeMillis() % 10 <= 1) {
			if (ascend){
				scale += 0.03125f;
				x += 0.03125f;
				y += 0.03125f;
			}else{
				scale -= 0.03125f;
				x -= 0.03125f;
				y -= 0.03125f;
			}
			if (scale == .75f){
				ascend = true;
			}
			if (scale == 1.00){
				ascend = false;
			}
			hitbox = new Rectangle(x, y, I.getTexture().getWidth(), I.getTexture().getHeight());
		}
		return scale;
	}

	public Item getI() {
		return I;
	}

	public void setI(Item i) {
		I = i;
	}

	public float getX() {
		return x;
	}

	public float getY() {
		return y;
	}

	public Rectangle getHitbox() {
		return hitbox;
	}

	public void setHitbox(Rectangle hitbox) {
		this.hitbox = hitbox;
	}

}
