package util;
//import java.util.Random;

import java.util.ArrayList;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.tiled.TiledMap;
import org.newdawn.slick.util.pathfinding.AStarPathFinder;
import org.newdawn.slick.util.pathfinding.Path;
import org.newdawn.slick.util.pathfinding.TileBasedMap;

import Entities.Enemy;
import Entities.Player;
import game.Vector2f;
import mapUtil.Cell;
import mapUtil.GridMap;
import mapUtil.Region;

public class MovementModel {

	public static final Color NO_MOVE_COLOR = new Color(0.0f, 0.0f, 0.0f, 1.0f);
	// private static Random rand = new Random();

	// good model for seeking projectiles
	public static Vector2f applyMovementModel1(Enemy enemy, Player player, Image hitmap, float increment,
			float speedMultiplier) {
		Vector2f pos;
		boolean u = true, r = true, d = true, l = true;
		float x = enemy.getPos().getX();
		float y = enemy.getPos().getY();

		for (int i = 0; i < 32; i++) {
			if (hitmap.getColor((int) x + i + 1, (int) y).equals(NO_MOVE_COLOR)) {
				u = false;
				break;
			}
		}
		if (enemy.getHitbox().aboveCollision(player.getHitbox())) {
			u = false;
		}

		for (int i = 0; i < 32; i++) {
			if (hitmap.getColor((int) x + 1 + 32, (int) y + i + 32).equals(NO_MOVE_COLOR)) {
				r = false;
				break;
			}
		}
		if (enemy.getHitbox().rightCollision(player.getHitbox())) {
			r = false;
		}

		for (int i = 0; i < 32; i++) {
			if (hitmap.getColor((int) x + i + 1, (int) y + 32).equals(NO_MOVE_COLOR)) {
				d = false;
				break;
			}
		}
		if (enemy.getHitbox().belowCollision(player.getHitbox())) {
			d = false;
		}

		for (int i = 0; i < 32; i++) {
			if (hitmap.getColor((int) x, (int) y + i + 32).equals(NO_MOVE_COLOR)) {
				d = false;
				break;
			}
		}
		if (Math.abs(enemy.getPos().getX() - player.getPos().getX()) <= 150) {
			if (player.getPos().getY() < y && u) {
				y -= increment * speedMultiplier;
			} else if (player.getPos().getY() > y && d) {
				y += increment * speedMultiplier;
			}
		} else {
			// y += rand.nextInt(3)-1;
		}

		if (Math.abs(enemy.getPos().getY() - player.getPos().getY()) <= 150) {
			if (player.getPos().getX() > x && r) {
				x += increment * speedMultiplier;
			} else if (player.getPos().getX() < x && l) {
				x -= increment * speedMultiplier;
			}
		} else {
			// x += rand.nextInt(3)-1;
		}

		pos = new Vector2f(x, y);

		return pos;
	}

	public static Vector2f pathFind(Region region, Enemy enemy, Player player, GridMap map, float increment) {
		Vector2f pos = enemy.getPos();
		float x = pos.getX();
		float y = pos.getY();
		Cell enemyCell = new Cell((int) enemy.getPos().getX(), (int) enemy.getPos().getY());
		Cell playerCell = new Cell((int) player.getX() - (int) player.getX() % 32,
				(int) player.getY() - (int) player.getX() % 32);
		ArrayList<Cell> neighbors = map.getNeighbors(enemyCell.getX(), enemyCell.getY());
		Cell moveToward = null;
		boolean proceed = false;
		while (!proceed) { // change after implementing other properties
			moveToward = playerCell.getClosestCell(neighbors);
			if (moveToward != null && moveToward.getCellProperties().size() == 0) {
				proceed = true;
			} else {
				neighbors.remove(moveToward);
			}
			if (neighbors.size() <= 0) {
				break;
			}
		}
		if (moveToward.getX() > x) {
			x += increment;
		}
		if (moveToward.getX() < x) {
			x -= increment;
		}
		if (moveToward.getY() > y) {
			y += increment;
		}
		if (moveToward.getY() < y) {
			y -= increment;
		}

		return new Vector2f(x, y);
	}

	public static Vector2f AStar(Region region, Vector2f start, Vector2f stop, GridMap map, float increment,
			Graphics g) {
		int xStart = (int) start.getX();
		int yStart = (int) start.getY();
		int xEnd = (int) stop.getX() / 32;
		int yEnd = (int) stop.getY() / 32;
		AStarPathFinder find = new AStarPathFinder(map, 100, false);
		Path path = new Path();

		path = find.findPath(null, xStart / 32, yStart / 32, xEnd , yEnd + 2);
		if (path != null) {
			int length = path.getLength();
			if (length < 20) {
				
				 for (int x = 0; x < path.getLength(); x++) { new
				  Cell(path.getStep(x).getX() * 32, path.getStep(x).getY() *
				 32).drawCell(g); }
				 
				
				float x = path.getStep(1).getX() * 32;
				float y = path.getStep(1).getY() * 32;

				Vector2f result = start;

				if (x < xStart) {
					xStart--;
				}
				if (x > xStart) {
					xStart++;
				}
				if (y < yStart) {
					yStart--;
				}
				if (y > yStart) {
					yStart++;
				}

				result = new Vector2f(xStart, yStart);

				return result;
			}
		}
		return start;

	}

	public static Vector2f applyMovementModel2(Region region, Enemy enemy, Player player, Image hitmap, float increment,
			float speedMultiplier) {
		Vector2f tilePos = enemy.getTileIndex();
		Vector2f standardPos = new Vector2f(tilePos.getX() * 32, tilePos.getY() * 32);
		Vector2f standardPosPlayer = player.getPos();
		Vector2f upMost, rightMost, bottomMost, leftMost;
		Vector2f upMostP, rightMostP, bottomMostP, leftMostP;

		upMost = region.getHighestBoundTile(standardPos);
		upMost = new Vector2f(upMost.getX() * 32, upMost.getY() * 32);
		rightMost = region.getRightmostBoundTile(standardPos);
		rightMost = new Vector2f(rightMost.getX() * 32, rightMost.getY() * 32);
		bottomMost = region.getLowestBoundTile(standardPos);
		bottomMost = new Vector2f(bottomMost.getX() * 32, bottomMost.getY() * 32);
		leftMost = region.getLeftmostBoundTile(standardPos);

		upMostP = region.getHighestBoundTile(standardPosPlayer);
		upMostP = new Vector2f(upMostP.getX() * 32, upMostP.getY() * 32);
		rightMostP = region.getRightmostBoundTile(standardPosPlayer);
		rightMostP = new Vector2f(rightMostP.getX() * 32, rightMostP.getY() * 32);
		bottomMostP = region.getLowestBoundTile(standardPosPlayer);
		bottomMostP = new Vector2f(bottomMostP.getX() * 32, bottomMostP.getY() * 32);
		leftMostP = region.getLeftmostBoundTile(standardPosPlayer);

		// bottom
		if (standardPosPlayer.getY() > standardPos.getY() && standardPosPlayer.getY() <= bottomMost.getY()) {
			standardPos.setY(standardPos.getY() + increment * speedMultiplier);
		}
		if (standardPosPlayer.getY() < standardPos.getY() && standardPosPlayer.getY() >= upMost.getY()) {
			standardPos.setY(standardPos.getY() - increment * speedMultiplier);
		}
		if (standardPosPlayer.getX() > standardPos.getX() && standardPosPlayer.getX() <= rightMost.getX()) {
			standardPos.setX(standardPos.getX() + increment * speedMultiplier);
		}
		if (standardPosPlayer.getX() < standardPos.getX() && standardPosPlayer.getX() >= leftMost.getX()) {
			standardPos.setX(standardPos.getX() - increment * speedMultiplier);
		} else if (standardPos.getX() > leftMost.getX() && leftMost.getX() > leftMostP.getX()) {
			standardPos.setX(standardPos.getX() + increment * speedMultiplier);
		}

		return standardPos;
	}

	public static Vector2f bounceBackPlayer(Player player, Region region, float incrementX, float incrementY,
			float multiplier) {
		Vector2f pos = player.getPos();
		pos = new Vector2f(pos.getX() + incrementX * multiplier, pos.getY() + incrementY * multiplier);

		return pos;
	}

}
