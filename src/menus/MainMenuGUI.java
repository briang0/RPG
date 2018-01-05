package menus;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ButtonGroup;
import javax.swing.ButtonModel;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;

public class MainMenuGUI {

	private JFrame mainFrame;
	private JButton continueGame, load, settings, graphics, audio, back;
	private JPanel mainPanel, loadPanel, settingsPanel, graphicsPanel, audioPanel, backPanel;
	// mainMenu
	private JPanel mmContinue, mmLoad, mmSettings;
	// settings
	private JPanel sGraphics, sAudio;
	// graphics
	private JPanel gRes, gQuality, gFullscreen, gAspect;

	private JComboBox<String> resolution, quality;

	private ButtonGroup aspectButtons;

	private JRadioButton[] aspectRadioButtons;

	private JCheckBox fullscreen;

	protected boolean ready;
	protected boolean fullscreenMode;
	protected boolean highQuality;
	protected boolean mute;
	protected int resX;
	protected int resY;
	
	private Dimension defaultRes;

	public MainMenuGUI() {

	}

	public void init() {
		defaultRes = Toolkit.getDefaultToolkit().getScreenSize();
		mainFrame = new JFrame("Temp Title");
		mainFrame.setSize(new Dimension(380, 550));
		continueGame = new JButton("Load Previous Save");
		load = new JButton("Load Save");
		settings = new JButton("Settings");
		graphics = new JButton("Graphics");
		audio = new JButton("Audio");
		mmContinue = new JPanel();
		mmLoad = new JPanel();
		mmSettings = new JPanel();
		ready = false;
		resX = 1920;
		resY = 1080;
		highQuality = true;
		fullscreenMode = false;
		mute = false;
		mainFrame.setVisible(true);
		mainPanel = new JPanel();
		mainFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		graphicsMenu();
		mainMenu();
	}

	public void mainMenu() {
		mainFrame.getContentPane().removeAll();
		mainFrame.setLocationRelativeTo(null);
		mmContinue.add(continueGame);
		mmLoad.add(load);
		mmSettings.add(settings);
		mmContinue.add(continueGame);
		mmLoad.add(load);
		mmSettings.add(settings);
		mainPanel.add(mmContinue);
		mainPanel.add(mmLoad);
		mainPanel.add(mmSettings);
		mainFrame.add(mainPanel);
		mainPanel.setLayout(new GridLayout(3, 1));

		continueGame.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO find previous slot and run
				ready = true;
				mainFrame.getContentPane().removeAll();
				mainFrame.setVisible(false);
			}

		});

		load.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				loadMenu();
			}

		});

		settings.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				settingsMenu();
			}

		});
		
		mainFrame.setVisible(true);
		mainFrame.revalidate();
		mainFrame.repaint();

	}

	public void settingsMenu() {
		System.out.println("Reach");
		mainFrame.getContentPane().removeAll();
		settingsPanel = new JPanel();
		backPanel = new JPanel();
		sGraphics = new JPanel();
		sAudio = new JPanel();
		back = new JButton("Back");
		settingsPanel.setLayout(new GridLayout(3, 1));

		mainFrame.add(settingsPanel);

		settingsPanel.add(sGraphics);
		settingsPanel.add(sAudio);
		settingsPanel.add(backPanel);

		sGraphics.add(graphics);
		sAudio.add(audio);
		backPanel.add(back);

		graphics.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				graphicsMenu();
			}
		});
		audio.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				audioMenu();
			}
		});

		back.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				mainMenu();
			}
		});

		mainFrame.setVisible(true);
		mainFrame.revalidate();
		mainFrame.repaint();
	}

	public void graphicsMenu() {
		mainFrame.getContentPane().removeAll();
		back = new JButton("Back");
		graphicsPanel = new JPanel();
		gAspect = new JPanel();
		gRes = new JPanel();
		gQuality = new JPanel();
		gFullscreen = new JPanel();
		backPanel = new JPanel();
		JCheckBox autoDetect = new JCheckBox();
		JLabel autoDetectText = new JLabel("Auto-Detect Res. ");
		JRadioButton button43 = new JRadioButton("4:3");
		JRadioButton button1610 = new JRadioButton("16:10");
		JRadioButton button169 = new JRadioButton("16:9");
		aspectRadioButtons = new JRadioButton[3];
		aspectRadioButtons[0] = button43;
		aspectRadioButtons[1] = button1610;
		aspectRadioButtons[2] = button169;
		aspectButtons = new ButtonGroup();
		
		graphicsPanel.setLayout(new GridLayout(6, 1));
		
		autoDetect.setEnabled(true);

		aspectRadioButtons[2].setSelected(true);
		for (JRadioButton button : aspectRadioButtons) {
			aspectButtons.add(button);
		}

		String[] qualities = {"High","Low"};

		if (resolutionsByRatio() != null){
			resolution = new JComboBox<String>(resolutionsByRatio());
		}else{
			resolution = new JComboBox<String>();
		}

		quality = new JComboBox<String>(qualities);
		fullscreen = new JCheckBox("Fullscreen");

		resolution.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {

			}
		});

		quality.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent a) {
				if (quality.getSelectedIndex() == 0) {
					highQuality = false;
				} else {
					highQuality = true;
				}
			}
		});

		back.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				settingsMenu();
			}
		});
		
		fullscreen.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e){
				fullscreenMode = fullscreen.isEnabled();
			}
		});
		
		autoDetect.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				autoDetectCase(autoDetect.isSelected());
			}			
		});

		for (int x = 0; x < aspectRadioButtons.length; x++) {
			aspectRadioButtons[x].addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent a) {
					applyItemsToResolution();
				}
			});
		}
		resolution.setSelectedIndex(0);
		
		autoDetect.setSelected(true);
		autoDetectCase(autoDetect.isSelected());
		
		gQuality.add(quality);
		gAspect.add(aspectRadioButtons[0]);
		gAspect.add(aspectRadioButtons[1]);
		gAspect.add(aspectRadioButtons[2]);
		gRes.add(resolution);
		gRes.add(autoDetectText);
		gRes.add(autoDetect);
		gFullscreen.add(fullscreen);
		backPanel.add(back);
		
		
		
		graphicsPanel.add(gQuality);
		graphicsPanel.add(gAspect);
		graphicsPanel.add(gRes);
		graphicsPanel.add(gFullscreen);
		graphicsPanel.add(backPanel);
		mainFrame.add(graphicsPanel);


		mainFrame.repaint();
		mainFrame.setVisible(true);

	}
	
	private void applyItemsToResolution(){
		String[] arr = resolutionsByRatio();
		resolution.removeAllItems();
		for (int x = 0; x < arr.length; x++){
			resolution.addItem(arr[x]);
		}
	}

	private void autoDetectCase(boolean enabled){
		if (enabled){
			resolution.setEnabled(false);
			aspectRadioButtons[0].setEnabled(false);
			aspectRadioButtons[1].setEnabled(false);
			aspectRadioButtons[2].setEnabled(false);
			String res = (int)defaultRes.getWidth() + "x" + (int)defaultRes.getHeight();
			
			resolution.removeAllItems();
			resolution.addItem(res);
			resolution.setSelectedIndex(0);
		}else{
			resolution.setEnabled(true);
			aspectRadioButtons[0].setEnabled(true);
			aspectRadioButtons[1].setEnabled(true);
			aspectRadioButtons[2].setEnabled(true);
			applyItemsToResolution();
		}
	}
	
	private String[] resolutionsByRatio() {
		String[] output = null;
		if (aspectRadioButtons[0].isSelected()) {
			String[] res = { "640x480", "800x600", "960x720", "1024x768", "1280x960", "1400x1050", "1440x1080",
					"1600x1200", "1856x1392", "1920x1440", "2048x1536" };
			output = res;
		} else if (aspectRadioButtons[1].isSelected()) {
			String[] res = { "1280x800", "1440x900", "1680x1050", "1680x1050", "1920x1200", "2560x1600" };
			output = res;
		} else if (aspectRadioButtons[2].isSelected()) {
			String[] res = { "1024x576", "1152x648", "1280x720", "1366x768", "1600x900", "1920x1080", "2560x1440",
					"3840x2160" };
			output = res;
		}
		return output;
	}

	public void audioMenu() {
		mainFrame.removeAll();

	}

	public void controlsMenu() {
		mainFrame.removeAll();

	}

	public void loadMenu() {
		mainFrame.removeAll();

	}

	public boolean getReady() {
		return ready;
	}

	public int[] getResolution() {
		String str = (String) resolution.getSelectedItem();
		String[] strSplit = str.split("x");
		int x = Integer.parseInt(strSplit[0]);
		int y = Integer.parseInt(strSplit[1]);
		int[] res = { x, y };
		return res;
	}

	public boolean isFullscreen() {
		return fullscreenMode;
	}

	public boolean isFullscreenMode() {
		return fullscreenMode;
	}

	public boolean isHighQuality() {
		return highQuality;
	}

	public boolean isMute() {
		return mute;
	}

}
