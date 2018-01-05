package game;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.BasicGame;
//import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;

public class test extends BasicGame {

	public test() {
		super("ham");
	}

	private static AppGameContainer app;
	@Override
	public void render(GameContainer gc, Graphics g) throws SlickException {

	
	}

	
	@Override
	public void init(GameContainer gc) throws SlickException {
		
	}

	@Override
	public void update(GameContainer gc, int delta) throws SlickException {

		

	}

	public static void main(String[] args) throws SlickException {
		//app = new AppGameContainer(new Game());
		//app.setDisplayMode(width, height, false);
		app.setFullscreen(false);
		//app.setTargetFrameRate(fpsCap);
		app.start();
	}

}