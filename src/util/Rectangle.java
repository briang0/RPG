package util;

import org.newdawn.slick.Color;
import org.newdawn.slick.Image;

public class Rectangle {

	private float x;
	private float y;
	private int width;
	private int height;

	public Rectangle(float x, float y, int width, int height) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
	}

	public boolean rightCollision(Rectangle r) {
		if (r.getX() >= width / 2 + x && r.getX() <= x + width) {
			return true;
		}
		return false;
	}

	public boolean leftCollision(Rectangle r) {
		if (r.getX() + width >= x && r.getX() <= x + width / 2) {
			return true;
		}
		return false;
	}

	public boolean aboveCollision(Rectangle r) {
		if (r.getY() + height >= y + height / 2 && r.getY() <= y) {
			return true;
		}
		return false;
	}

	public boolean belowCollision(Rectangle r) {
		if (r.getY() + height <= y + height / 2 && r.getY() >= y) {
			return true;
		}
		return false;
	}

	public boolean collides(Rectangle r) {
		java.awt.Rectangle a = new java.awt.Rectangle((int)x, (int)y, width, height);
		java.awt.Rectangle b = new java.awt.Rectangle((int)r.getX(), (int)r.getY(), r.getWidth(), r.getHeight());
		return a.intersects(b);
	}

	public boolean containsColor(Image img, Color c) {

		for (int x = 0; x < getWidth(); x++) {
			for (int y = 0; y < getHeight(); y++) {
				if (img.getColor(x + (int) getX(), y + (int) getY()).equals(c)) {
					return true;
				}
			}
		}
		return false;
	}

	public boolean containsColor2(Image img, Color c) {

		for (int i = (int) x; i < getWidth(); i++) {
			for (int j = (int) y; j < getHeight() + y; j++) {
				System.out.println(i + " " + j);
				if (img.getColor(i, j).equals(c)) {
					System.out.println(i + " " + j);
					return true;
				}
			}
		}
		return false;
	}

	public int getArea() {
		return width * height;
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

	public String toString() {
		return "x: " + x + "y: " + y + "width: " + width + "height: " + height;
	}

}
