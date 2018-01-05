package io;

import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

public class TextureLoader {

	private String dir;
	private boolean quality;
	
	public TextureLoader(String dir, boolean quality){
		this.dir = dir;
		this.quality = quality;
	}
	
	public Image loadTexture(String subDir) throws SlickException{
		Image texture = new Image(dir + subDir);
		
		if (quality){
			texture.setFilter(Image.FILTER_NEAREST);
		}
		return texture;
	}
	
}
