package view.menu;

import javax.swing.JFrame;

public class Menu{
	private static JFrame mainMenuFrame;

	public static void main(String[] args){
		mainMenuFrame = new JFrame("Tower Defense");
		mainMenuFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		mainMenuFrame.setVisible(true);
		MainMenu mainMenu = new MainMenu(mainMenuFrame);
	}
}