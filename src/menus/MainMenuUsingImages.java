package menus;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

public class MainMenuUsingImages {

	String dir = "Textures/Menu";
	Image mainMenu, loadSave, startNewGame, settings, saveSlot0, saveSlot1, saveSlot2, saveSlot3, saveSlot4, highQuality, lowQuality;
	
	
	
	public MainMenuUsingImages(){
		try {
			mainMenu = new Image(dir + "mainmenubackground.png");
			loadSave = new Image(dir + "loadSave.png");
			
			
		} catch (SlickException e) {
			e.printStackTrace();
		}
	}
	
	public void update(Graphics g, int mX, int mY){
	}
	
}
