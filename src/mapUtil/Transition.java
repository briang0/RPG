package mapUtil;

import java.util.ArrayList;

import game.Vector2f;

public class Transition {

	private int ID;
	private ArrayList<Vector2f> vecs;
	
	public Transition(ArrayList<Vector2f> vecs, int ID){
		this.vecs = vecs;
		this.ID = ID;
	}

	public int getID() {
		return ID;
	}

	public void setID(int iD) {
		ID = iD;
	}

	public ArrayList<Vector2f> getVecs() {
		return vecs;
	}

	public void setVecs(ArrayList<Vector2f> vecs) {
		this.vecs = vecs;
	}
	
	public boolean isIn(Vector2f input){
		boolean inX = false;
		boolean inY = false;
		for (int x = 0; x < vecs.size(); x++){
			if (vecs.get(x).getX() - input.getX() <= .1 && vecs.get(x).getX() - input.getX() >= -.1){
				inX = true;
			}
			if (vecs.get(x).getY() - input.getY() <= .1 && vecs.get(x).getY() - input.getY() >= -.1){
				inY = true;
			}
		}
		return inX && inY;
	}
	
	public String getMapData(){
		switch(ID){
		case 0:
			return "gameData/Maps/testmap.txt";
		case 1:
			return "gameData/Maps/testmap2.txt";
		default:
			return "gameData/Maps/testmap.txt";
		}
	}
	
}
