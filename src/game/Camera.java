package game;

public class Camera {

	private Vector2f position;
	private Vector2f previousPosition;
	private float mapMaxX;
	private float mapMaxY;
	private int width;
	private int height;
	private float zoom;

	public Camera(int windowWidth, int windowHeight, int mapWidth, int mapHeight, Vector2f position, float zoom) {
		this.zoom = zoom;
		float posX = position.getX() * 32 * 1;
		float posY = position.getY() * 32 * 1;
		this.position = new Vector2f(posX, posY);
		previousPosition = position;
		width = (int) (windowWidth);
		height = (int) (windowHeight);

		mapMaxX = mapWidth * zoom - windowWidth;
		mapMaxY = mapHeight * zoom - windowHeight;
		System.out.println(mapMaxX + " " + mapMaxY);
		update(position);
	}

	public void update(Vector2f pos) {
		float xPos = pos.getX() * zoom - width / 2;
		float yPos = pos.getY() * zoom - height / 2;
		if (xPos > mapMaxX) {
			xPos = mapMaxX;
		}
		if (xPos < 0) {
			xPos = 0;
		}
		if (yPos > mapMaxY) {
			yPos = mapMaxY;
		}
		if (yPos < 0) {
			yPos = 0;
		}
		if (!position.equals(previousPosition))
			previousPosition = position;
		
		position = new Vector2f(xPos, yPos);
	}

	public String toString() {
		String data = "Width: " + width + "Height: " + height + "Zoom: " + zoom;
		return data;
	}

	public Vector2f getPosition() {
		return position;
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

	public float getZoom() {
		return zoom;
	}

	public void setZoom(float zoom) {
		this.zoom = zoom;
	}

	public float getMapMaxX() {
		return mapMaxX;
	}

	public void setMapMaxX(float mapMaxX) {
		this.mapMaxX = mapMaxX;
	}

	public float getMapMaxY() {
		return mapMaxY;
	}

	public void setMapMaxY(float mapMaxY) {
		this.mapMaxY = mapMaxY;
	}

	public Vector2f getPreviousPosition() {
		return previousPosition;
	}

	public void setPreviousPosition(Vector2f previousPosition) {
		this.previousPosition = previousPosition;
	}

}
