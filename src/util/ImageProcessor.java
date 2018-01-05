package util;
import org.newdawn.slick.Image;

public class ImageProcessor {

	public static Image toNegative(Image img) {
		for (int x = 0; x < 4; x++) {
			for (int y = 0; y < 4; y++) {
				img.setAlpha(.1f);
				
			}
		}

		return img;
	}
	
	public static Image nightCycle(Image img, float alpha){
		
		img.setAlpha(alpha);
		
		return img;
	}
}
