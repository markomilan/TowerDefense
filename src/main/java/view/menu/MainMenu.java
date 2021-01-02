package view.menu;

import view.resloader.GameGallery;
import view.resloader.MenuGallery;

import javax.swing.*;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;


public class MainMenu extends JPanel implements ActionListener {

	private JFrame mainMenuFrame;
	private JPanel mainMenuPanel;
	private MenuGallery menuGallery;
	private GameGallery gameGallery;

	JButton startButton;
	JButton loadButton;
	JButton highScoreButton;
	JButton closeButton;

	public MainMenu(JFrame mainMenuFrame){

		this.gameGallery = new GameGallery();
		this.menuGallery = new MenuGallery();
		this.mainMenuFrame = mainMenuFrame;
		
		buildMainMenu();
	}
	public MainMenu(JFrame mainMenuFrame, GameGallery gameGallery, MenuGallery menuGallery)
	{
		this.gameGallery = gameGallery;
		this.menuGallery = menuGallery;
		this.mainMenuFrame = mainMenuFrame;

		buildMainMenu();
	}

	private void buildMainMenu()
	{
		mainMenuPanel = new JPanel();
		mainMenuPanel.setLayout(null);
		mainMenuPanel.setBackground(new Color(139,69,19));

		startButton = createButton(this.menuGallery.iconStart,50);
		loadButton = createButton(this.menuGallery.iconLoad,200);
		highScoreButton = createButton(this.menuGallery.iconHighScore,350);
		closeButton = createButton(this.menuGallery.iconClose,500);

		mainMenuPanel.add(startButton);
		mainMenuPanel.add(loadButton);
		mainMenuPanel.add(highScoreButton);
		mainMenuPanel.add(closeButton);

		mainMenuFrame.getContentPane().add(mainMenuPanel);
		mainMenuFrame.setSize(700,700);

		startButton.addActionListener(this);
		loadButton.addActionListener(this);
		highScoreButton.addActionListener(this);
		closeButton.addActionListener(this);
	}
	public JPanel getMainMenuPanel(){
        return mainMenuPanel;
    }
	public JButton createButton(ImageIcon buttonIcon, int buttonYCord){
		JButton button = new JButton(buttonIcon);
		button.setBackground(new Color(139,69,19));
		button.setBorder(null);
		button.setBounds(150,buttonYCord,400,100);
		return button;
	}
	public void addActionToButtons(JFrame mainMenuFrame, JPanel targetPanel){
		mainMenuFrame.getContentPane().remove(mainMenuPanel);
		mainMenuFrame.getContentPane().add(targetPanel);
		mainMenuFrame.pack();
		mainMenuFrame.setSize(700,700);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == startButton){
			StartMenu startmenu=new StartMenu(mainMenuFrame, menuGallery, gameGallery);
			addActionToButtons(mainMenuFrame,startmenu.getStartMenuPanel());
		}else if(e.getSource() == loadButton){
			LoadMenu loadMenu=new LoadMenu(mainMenuFrame, menuGallery, gameGallery);
			addActionToButtons(mainMenuFrame,loadMenu.getLoadMenuPanel());
		}else if(e.getSource() == highScoreButton){
			HighScoreMenu highscoremenu=new HighScoreMenu(mainMenuFrame, gameGallery, menuGallery);
			addActionToButtons(mainMenuFrame,highscoremenu.getHighScoreMenuPanel());
		} else if(e.getSource() == closeButton){
			mainMenuFrame.dispatchEvent(new WindowEvent(mainMenuFrame, WindowEvent.WINDOW_CLOSING));
		}
	}
}