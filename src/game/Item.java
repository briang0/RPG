package game;

import org.newdawn.slick.Image;

import Entities.Droppable;
import util.Rectangle;

public abstract class Item implements Droppable{

	protected int id;
	protected int pos;
	protected Rectangle hitbox;
	protected boolean isEquipt;
	protected Image texture;
	protected Image inventoryImage;
	
	public Item(int id, int pos, Rectangle hitbox, boolean isEquipt) {
		this.id = id;
		this.pos = pos;
		this.hitbox = hitbox;
		this.isEquipt = isEquipt;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Rectangle getHitbox() {
		return hitbox;
	}

	public void setHitbox(Rectangle hitbox) {
		this.hitbox = hitbox;
	}

	public boolean isEquipt() {
		return isEquipt;
	}

	@Override
	public Image getTexture() {
		return null;
	}
	
	public abstract Image getInventoryAdaption();

	
}
