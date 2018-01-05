package mapUtil;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;

import game.Vector2f;

public class LightCaster {

	private Vector2f source;
	private Image hitmap;
	@SuppressWarnings("unused")
	private float intensity;
	private Image map;

	public LightCaster(Vector2f source, Image hitmap, Image map, float intensity) {
		this.source = source;
		this.hitmap = hitmap;
		this.intensity = intensity / 5;
		this.map = map;
	}

	// TODO run on new thread
	public void castLight(Graphics g, Vector2f currentPos, float intensity, int brightness, float alpha, float len,
			boolean ga, boolean gright, boolean gb, boolean gl) {
		Vector2f above, right, bottom, left;
		above = new Vector2f(currentPos.getX(), currentPos.getY() - len);
		right = new Vector2f(currentPos.getX() + len, currentPos.getY());
		bottom = new Vector2f(currentPos.getX(), currentPos.getY() + len);
		left = new Vector2f(currentPos.getX() - len, currentPos.getY());

		if (above.getY() >= 0 && intensity * Math.PI > 0 && ga
				&& !hitmap.getColor((int) above.getX(), (int) above.getY()).equals(Color.black)) {
			int r, gr, b;

			for (int x = 0; x < len; x++) {
				for (int y = 0; y < len; y++) {
					Color c = map.getColor((int) above.getX() + x, (int) above.getY() + y);
					r = c.getRed() + brightness + 35;
					gr = c.getGreen() + brightness + 15;
					b = c.getBlue() + brightness;
					c = new Color(r, gr, b, alpha);

					g.setColor(c);
					g.fillRect(above.getX() + x, above.getY() + y, 1, 1);
				}
			}
			castLight(g, above, intensity - len, brightness - 10, alpha, len, true, true, false, false);
		}
		if (right.getX() <= hitmap.getWidth() && intensity * Math.PI > 0 && gright
				&& !hitmap.getColor((int) right.getX(), (int) right.getY()).equals(Color.black)) {
			int r, gr, b;

			for (int x = 0; x < len; x++) {
				for (int y = 0; y < len; y++) {
					Color c = map.getColor((int) right.getX() + x, (int) right.getY() + y);
					r = c.getRed() + brightness + 35;
					gr = c.getGreen() + brightness + 15;
					b = c.getBlue() + brightness;
					c = new Color(r, gr, b, alpha);
					g.setColor(c);
					g.fillRect(right.getX() + x, right.getY() + y, 1, 1);
				}
			}
			castLight(g, right, intensity - len, brightness - 10, alpha, len, false, true, true, false);
		}
		if (bottom.getY() <= hitmap.getHeight() && intensity * Math.PI > 0 && gb
				&& !hitmap.getColor((int) bottom.getX(), (int) bottom.getY()).equals(Color.black)) {
			int r, gr, b;

			for (int x = 0; x < len; x++) {
				for (int y = 0; y < len; y++) {
					Color c = map.getColor((int) bottom.getX() + x, (int) bottom.getY() + y);
					r = c.getRed() + brightness + 35;
					gr = c.getGreen() + brightness + 15;
					b = c.getBlue() + brightness;
					c = new Color(r, gr, b, alpha);
					g.setColor(c);
					g.fillRect(bottom.getX() + x, bottom.getY() + y, 1, 1);
				}
			}

			castLight(g, bottom, intensity - len, brightness - 10, alpha, len, false, false, true, true);
		}
		if (left.getX() >= 0 && intensity * Math.PI > 0 && gl
				&& !hitmap.getColor((int) left.getX(), (int) left.getY()).equals(Color.black)) {
			int r, gr, b;
			for (int x = 0; x < len; x++) {
				for (int y = 0; y < len; y++) {
					Color c = map.getColor((int) left.getX() + x, (int) left.getY() + y);
					r = c.getRed() + brightness + 35;
					gr = c.getGreen() + brightness + 15;
					b = c.getBlue() + brightness;
					c = new Color(r, gr, b, alpha);
					g.setColor(c);
					g.fillRect(left.getX() + x, left.getY() + y, 1, 1);
				}
			}
			castLight(g, left, intensity - len, brightness - 10, alpha, len, true, false, false, true);
		}
	}

	public Vector2f getSource() {
		return source;
	}

	public void setSource(Vector2f source) {
		this.source = source;
	}

}
