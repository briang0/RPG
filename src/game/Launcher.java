package game;

import org.newdawn.slick.SlickException;

import menus.MainMenuGUI;

public class Launcher {

	protected static MainMenuGUI menu;
	protected static Thread GUIThread;

	private static boolean ignoreGUI = false;

	public static void main(String[] args) {
		try {
			launchMainMenu();
		} catch (InterruptedException e) {
			System.exit(0);
			e.printStackTrace();
		}
	}

	private static void launchMainMenu() throws InterruptedException {
		if (!ignoreGUI) {
			menu = new MainMenuGUI();
			menu.init();
			while (!menu.getReady()) {
				Thread.sleep(100);
			}
			launchGame(menu.getResolution(), menu.isHighQuality(), menu.isFullscreen(), menu.isMute());
		}else{
			int[] res = {1920, 1080};
			launchGame(res, true, false, false);
		}
	}

	private static void launchGame(int[] resolution, boolean quality, boolean fullscreen, boolean mute) {
		try {
			new Game(resolution, quality, fullscreen, mute);
		} catch (SlickException e) {
			e.printStackTrace();
		}
	}

}
