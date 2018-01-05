package util;

public class ErrorWindowBuilder {

	private int x, y;
	private String text;
	
	public ErrorWindowBuilder(int x, int y, String text){
		this.setX(x);
		this.setY(y);
		this.setText(text);
		
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}
	
	
}
