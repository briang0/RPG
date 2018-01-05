package Entities;

import util.Rectangle;

import game.Attack;
import game.Vector2f;
import io.TextureLoader;

public class Hog extends Enemy{

	public Hog(Vector2f pos, int type, int width, int height, String textureDir, TextureLoader tLoad,
			Attack[] attacks) {
		super(pos, type, width, height, textureDir, tLoad, attacks);
		hitbox = new Rectangle(pos.getX()-1, pos.getY()+16, 34, 17); 
	}

	@Override
	public int getAttackDamage() {
		return 1;
	}

}
