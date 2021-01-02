package view.menu;

import javax.swing.*;
import java.awt.BorderLayout;
import java.awt.Color;

import controller.menu.StartMenuButtonClicked;
import model.menu.Difficulty;
import view.resloader.GameGallery;
import view.resloader.MenuGallery;


public class StartMenu{
	
	private JPanel startMenuPanel;
	JButton easyButton;
	JButton mediumButton;
	JButton hardButton;
	JButton backButton;
	public StartMenu(JFrame startMenuFrame, MenuGallery menuGallery, GameGallery gameGallery){
		startMenuPanel = new JPanel();
		startMenuPanel.setLayout(null);
		startMenuPanel.setBackground(new Color(139,69,19));

		easyButton = createButton(menuGallery.iconEasy,50);
		mediumButton = createButton(menuGallery.iconMedium,200);
		hardButton = createButton(menuGallery.iconHard,350);
		backButton = createButton(menuGallery.iconBack,500);

		startMenuPanel.add(easyButton);
		startMenuPanel.add(mediumButton);
		startMenuPanel.add(hardButton);
		startMenuPanel.add(backButton);
		
		easyButton.addActionListener(new StartMenuButtonClicked(Difficulty.EASY, gameGallery));
		mediumButton.addActionListener(new StartMenuButtonClicked(Difficulty.MEDIUM, gameGallery));
		hardButton.addActionListener(new StartMenuButtonClicked(Difficulty.HARD, gameGallery));

		backButton.addActionListener(e -> {
			MainMenu mainMenu = new MainMenu(startMenuFrame, gameGallery, menuGallery);
			startMenuFrame.getContentPane().remove(startMenuPanel);
			startMenuFrame.getContentPane().add(BorderLayout.CENTER, mainMenu.getMainMenuPanel());
			startMenuFrame.pack();
			startMenuFrame.setSize(700,700);
		});
	}
	public JPanel getStartMenuPanel(){
        return startMenuPanel;
    }
	public JButton createButton(ImageIcon buttonIcon, int buttonYCord){
		JButton button = new JButton(buttonIcon);
		button.setBackground(new Color(139,69,19));
		button.setBorder(null);
		button.setBounds(150,buttonYCord,400,100);
		return button;
	}
}