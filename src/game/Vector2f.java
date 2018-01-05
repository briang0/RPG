package game;

public class Vector2f {

	private float x;
	private float y;

	public Vector2f(float x, float y) {
		this.x = x;
		this.y = y;
	}

	public float length() {
		return (float) Math.sqrt(x * x + y * y);
	}

	public float dot(Vector2f r) {
		return x * r.getX() + y * r.getY();
	}

	public Vector2f normalize() {
		float length = length();

		x /= length;
		y /= length;

		return this;
	}

	public boolean above(Vector2f pos) {
		return pos.getY() < y;
	}

	public boolean below(Vector2f pos) {
		return pos.getY() > y;
	}

	public boolean right(Vector2f pos) {
		return pos.getX() > x;
	}

	public boolean left(Vector2f pos) {
		return pos.getX() < x;
	}

	public Vector2f rotate(float angle) {
		double rad = Math.toRadians(angle);
		double cos = Math.cos(rad);
		double sin = Math.sin(rad);

		return new Vector2f((float) (x * cos - y * sin), (float) (x * sin + y * cos));
	}

	public Vector2f add(Vector2f r) {
		return new Vector2f(x + r.getX(), y + r.getY());
	}

	public Vector2f add(float r) {
		return new Vector2f(x + r, y + r);
	}

	public Vector2f sub(Vector2f r) {
		return new Vector2f(x - r.getX(), y - r.getY());
	}

	public Vector2f sub(float r) {
		return new Vector2f(x - r, y - r);
	}

	public Vector2f mult(Vector2f r) {
		return new Vector2f(x * r.getX(), y * r.getY());
	}

	public Vector2f mult(float r) {
		return new Vector2f(x / r, y / r);
	}

	public Vector2f div(Vector2f r) {
		return new Vector2f(x / r.getX(), y / r.getY());
	}

	public Vector2f div(float r) {
		return new Vector2f(x / r, y / r);
	}

	public String toString() {
		return "(" + x + " " + y + ")";
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

	public boolean equals(Vector2f v2) {
		return v2.getX() == x && v2.getY() == y;
	}

	public float distance(Vector2f v2) {
		float result = (float) Math.sqrt(Math.pow((getX() - v2.getX()), 2) + Math.pow(getY() - v2.getY(), 2));
		System.out.println(result);
		return result;
	}

}