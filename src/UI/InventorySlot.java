package UI;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

import io.TextureLoader;

public class InventorySlot {

	private float x;
	private float y;
	private Image item;
	private Image img0, img1;
	private boolean selected;

	public InventorySlot(float x, float y, Image item, TextureLoader tLoad) {
		this.x = x;
		this.y = y;
		this.item = item;
		selected = false;
		try {
			img0 = tLoad.loadTexture("UI/itemSlot.png");
			img1 = tLoad.loadTexture("UI/selectedItemSlot.png");
		} catch (SlickException e) {
			e.printStackTrace();
		}
	}

	public float getX() {
		return x;
	}

	public void setX(float x) {
		this.x = x;
	}

	public float getY() {
		return y;
	}

	public void setY(float y) {
		this.y = y;
	}

	public Image getItem() {
		return item;
	}

	public void setItem(Image item) {
		this.item = item;
	}

	public void render(Graphics g) {
		if (selected){
			g.drawImage(img1, x, y);
		}else{
			g.drawImage(img0, x, y);
		}
		if (item != null){
			g.drawImage(item, x, y+12);
		}
	}

	public void setSelected(boolean selected) {
		this.selected = selected;
	}

}
