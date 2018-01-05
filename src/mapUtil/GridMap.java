package mapUtil;

import java.util.ArrayList;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.util.pathfinding.PathFindingContext;
import org.newdawn.slick.util.pathfinding.TileBasedMap;

import game.Vector2f;

public class GridMap implements TileBasedMap{

	private int pLength;
	private int pHeight;
	private Cell[][] map;
	private Vector2f playerpos;

	protected static Image hitmap;

	public GridMap(int tileSize, Image hitmap, Vector2f playerpos) {
		this.playerpos = playerpos;
		this.pLength = hitmap.getWidth() / tileSize;
		this.pHeight = hitmap.getHeight() / tileSize;
		GridMap.hitmap = hitmap;
		map = getCellMap();
	}

	public Cell[][] getCellMap() {
		Cell[][] map = new Cell[pLength][pHeight];
		for (int x = 0; x < pLength; x++) {
			for (int y = 0; y < pHeight; y++) {
				map[x][y] = new Cell(x * 32, y * 32);
			}
		}
		return map;
	}

	public void drawGrid(Graphics g) {
		for (int x = 0; x < map.length; x++) {
			for (int y = 0; y < map[x].length; y++) {
				map[x][y].drawCell(g);
			}
		}
	}

	public void drawNeighbors(Cell cell, Graphics g) {
		ArrayList<Cell> neighbors = getNeighbors(cell.getX(), cell.getY());
		for (int x = 0; x < neighbors.size(); x++) {
			neighbors.get(x).drawCell(g);
		}
	}

	public ArrayList<Cell> getNeighbors(int x, int y) {
		ArrayList<Cell> neighbors = new ArrayList<Cell>();

		neighbors.add(new Cell(x - 32, y));

		neighbors.add(new Cell(x + 32, y));

		neighbors.add(new Cell(x, y - 32));

		neighbors.add(new Cell(x, y + 32));

		return neighbors;
	}

	/*
	 * public ArrayList<Cell> getNeighbors(Cell cell){ ArrayList<Cell> neighbors
	 * = new ArrayList<Cell>(); int x = cell.getCellX(); int y =
	 * cell.getCellY(); System.out.println(x + ", " + y); if (x > 0){
	 * neighbors.add(map[x-1][y]); } if (x < pLength){
	 * neighbors.add(map[x+1][y]); } if (y > 0){ neighbors.add(map[x][y-1]); }
	 * if (y < pLength){ neighbors.add(map[x][y+1]); }
	 * 
	 * return neighbors; }
	 */
	public Cell[][] getMap() {
		return map;
	}

	@Override
	public boolean blocked(PathFindingContext arg0, int x, int y) {
		return map[x][y].isContainsImpassable() && !map[x][y].containsPlayer(playerpos);
	}

	@Override
	public float getCost(PathFindingContext arg0, int x, int y) {
		return 1.0f;
	}

	@Override
	public int getHeightInTiles() {
		return pHeight;
	}

	@Override
	public int getWidthInTiles() {
		return pLength;
	}

	@Override
	public void pathFinderVisited(int arg0, int arg1) {}
}
