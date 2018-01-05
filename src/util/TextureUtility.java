package util;
import java.util.ArrayList;

import org.newdawn.slick.Image;

public class TextureUtility {

	public static ArrayList<Image> numberToTexture(int num) {
		ArrayList<Image> numbers = new ArrayList<Image>();
		try {
			Image zero = new Image("Textures/UI/0.png");
			Image one = new Image("Textures/UI/1.png");
			Image two = new Image("Textures/UI/2.png");
			Image three = new Image("Textures/UI/3.png");
			Image four = new Image("Textures/UI/4.png");
			Image five = new Image("Textures/UI/5.png");
			Image six = new Image("Textures/UI/6.png");
			Image seven = new Image("Textures/UI/7.png");
			Image eight = new Image("Textures/UI/8.png");
			Image nine = new Image("Textures/UI/9.png");
			
			while (num > 0){
				switch(num % 10){
				case 0:
					numbers.add(zero);
					break;
				case 1:
					numbers.add(one);
					break;
				case 2:
					numbers.add(two);
					break;
				case 3:
					numbers.add(three);
					break;
				case 4:
					numbers.add(four);
					break;
				case 5:
					numbers.add(five);
					break;
				case 6:
					numbers.add(six);
					break;
				case 7:
					numbers.add(seven);
					break;
				case 8:
					numbers.add(eight);
					break;
				case 9:
					numbers.add(nine);
					break;
				}
				num /= 10;
			}
			
		} catch (Exception err) {
			err.printStackTrace();
		}

		for (int x = 0; x < numbers.size()/2; x++){
			Image temp = numbers.get(x);
			numbers.set(x, numbers.get(numbers.size()-x-1));
			numbers.set(numbers.size()-x-1, temp);
		}
		
		return numbers;

	}

}
