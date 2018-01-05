package game;

import org.newdawn.slick.Animation;
import org.newdawn.slick.Image;

public class Attack {
	
	private int damage;
	private Animation animation;
	private Image texture;
	private Vector2f vec;
	private AttackModifier mod;

	public Attack(int damage, Animation animation, Image texture, Vector2f vec, AttackModifier mod){
		this.damage = damage;
		this.animation = animation;
		this.texture = texture;
		this.vec = vec;
		this.mod = mod;
	}
	
	public Attack(int damage){
		this.damage = damage;;
	}

	public int getDamage() {
		return damage;
	}

	public void setDamage(int damage) {
		this.damage = damage;
	}

	public Animation getAnimation() {
		return animation;
	}

	public void setAnimation(Animation animation) {
		this.animation = animation;
	}

	public Image getTexture() {
		return texture;
	}

	public void setTexture(Image texture) {
		this.texture = texture;
	}

	public Vector2f getVec() {
		return vec;
	}

	public void setVec(Vector2f vec) {
		this.vec = vec;
	}

	public AttackModifier getMod() {
		return mod;
	}

	public void setMod(AttackModifier mod) {
		this.mod = mod;
	}
	
	
	
}
