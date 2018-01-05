package menus;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;

public class Dead {

	public static void deadScreen(Graphics g, int width, int height){
		g.setColor(Color.black);
		g.fillRect(0, 0, width, height);
		g.setColor(Color.red);
		g.drawString("Game Over", width, height);
	}
	
}
