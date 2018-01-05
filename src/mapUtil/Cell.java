package mapUtil;

import java.util.ArrayList;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;

import game.Vector2f;
import util.MovementModel;
import util.Rectangle;


public class Cell {

	private int x;
	private int y;
	private int cellX;
	private int cellY;
	private boolean containsImpassable;
	ArrayList<MapProperty> cellProperties;

	public Cell(int x, int y) {
		if (x % 32 != 0){
			this.x = x - x % 32;
		}else{
			this.x = x;
		}
		if (y % 32 != 0){
			this.y = y - y % 32;
		}else{
			this.y = y;
		}
		containsImpassable = false;
		cellProperties = new ArrayList<MapProperty>();
		initCellProperties(GridMap.hitmap);
		cellX = x / 32;
		cellY = y / 32;
	}

	public void initCellProperties(Image hitmap) {
		for (int x = this.x; x < 16 + this.x; x++) {
			for (int y = this.y; y < 16 + this.y; y++) {
				if (hitmap.getColor(x, y).equals(MovementModel.NO_MOVE_COLOR)) {
					cellProperties.add(new Impassable());
					containsImpassable = true;
				}
			}
		}
	}

	public void drawCell(Graphics g) {
		if (cellProperties.size() >= 1) {
			g.fillRect(x, y, 32, 32);
		} else {
			g.drawRect(x, y, 32, 32);
		}
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public int getCellX() {
		return cellX;
	}

	public void setCellX(int cellX) {
		this.cellX = cellX;
	}

	public int getCellY() {
		return cellY;
	}

	public void setCellY(int cellY) {
		this.cellY = cellY;
	}

	public ArrayList<MapProperty> getCellProperties() {
		return cellProperties;
	}

	public Cell getClosestCell(ArrayList<Cell> cells) {
		if (cells.size() > 0) {
			float shortest = 9000000f;
			Cell closest = cells.get(0);
			for (int x = 0; x < cells.size(); x++) {
				float currentDistance = (float) Math
						.sqrt(Math.pow((cells.get(x).getX() - this.x), 2) + Math.pow(cells.get(x).getY() - this.y, 2));
				if (currentDistance < shortest) {
					shortest = currentDistance;
					closest = cells.get(x);
				}
			}
			System.out.println(shortest);
			return closest;
		}else{
			return null;
		}
	}
	
	public boolean containsPlayer(Vector2f player){
		Rectangle p = new Rectangle(player.getX(), player.getY(), (int)player.getX() + 32, (int)player.getX() + 64);
		Rectangle c = new Rectangle(x, y, 32, 32);
		return p.collides(c);
		
	}

	public boolean isContainsImpassable() {
		return containsImpassable;
	}

	public void setContainsImpassable(boolean containsImpassable) {
		this.containsImpassable = containsImpassable;
	}

}
