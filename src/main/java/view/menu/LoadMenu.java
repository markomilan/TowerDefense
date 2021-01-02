package view.menu;

import controller.ingame.LoadGame;
import view.resloader.GameGallery;
import view.resloader.MenuGallery;

import javax.swing.*;
import java.awt.Color;


public class LoadMenu{
	
	private JPanel loadMenuPanel;
	JButton backButton;
	JButton loadButton;

	public LoadMenu(JFrame loadMenuFrame, MenuGallery menuGallery, GameGallery gameGallery){

		loadMenuPanel = new JPanel();
		loadMenuPanel.setLayout(null);
		loadMenuPanel.setBackground(new Color(139,69,19));

		loadButton = createButton(menuGallery.iconLoad,100);
		backButton = createButton(menuGallery.iconBack,500);

		loadMenuPanel.add(loadButton);
		loadMenuPanel.add(backButton);
		backButton.addActionListener(e -> {
			MainMenu mainMenu = new MainMenu(loadMenuFrame, gameGallery, menuGallery);
			loadMenuFrame.getContentPane().remove(loadMenuPanel);
			loadMenuFrame.getContentPane().add(mainMenu.getMainMenuPanel());
			loadMenuFrame.pack();
			loadMenuFrame.setSize(700,700);
		});
		loadButton.addActionListener(e -> {LoadGame loadGame = new LoadGame(gameGallery);});
	}
	public JPanel getLoadMenuPanel(){
        return loadMenuPanel;
    }
	private JButton createButton(ImageIcon buttonIcon, int buttonYCord){
		JButton button = new JButton(buttonIcon);
		button.setBackground(new Color(139,69,19));
		button.setBorder(null);
		button.setBounds(150,buttonYCord,400,100);
		return button;
	}
}