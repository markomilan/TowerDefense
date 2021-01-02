package view.menu;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import view.database.ConnectionFactory;
import view.resloader.GameGallery;
import view.resloader.MenuGallery;


public class HighScoreMenu{

	private JPanel highScoreMenuPanel;

	public HighScoreMenu(JFrame highScoreFrame, GameGallery gameGallery, MenuGallery menuGallery){

		ConnectionFactory connectionFactory = new ConnectionFactory();
		highScoreMenuPanel = new JPanel();
		highScoreMenuPanel.setLayout(null);
		highScoreMenuPanel.setBackground(new Color(139,69,19));

		JButton backButton = new JButton(menuGallery.iconBack);
		backButton.setBackground(new Color(139,69,19));
		backButton.setBorder(null);
		backButton.setBounds(150,500,400,100);

		JTable highScoreTable = new JTable(connectionFactory.getData(), connectionFactory.getColumn());
		highScoreTable.setEnabled(false);
		highScoreTable.setBackground(new Color(139,69,19));
		highScoreTable.setShowGrid(false);
		highScoreTable.setFont(new Font("Cooper Black", Font.BOLD, 20));
		highScoreTable.setForeground(Color.white);

		DefaultTableModel model = (DefaultTableModel) highScoreTable.getModel();
		model.setRowCount(5);

		JScrollPane tablePane = new JScrollPane(highScoreTable);
		tablePane.setBounds(50,10,600,103);

		highScoreMenuPanel.add(tablePane);
		highScoreMenuPanel.add(backButton);

		backButton.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e){
				MainMenu mainmenu=new MainMenu(highScoreFrame, gameGallery, menuGallery);
				highScoreFrame.getContentPane().remove(highScoreMenuPanel);
				highScoreFrame.getContentPane().add(mainmenu.getMainMenuPanel());
				highScoreFrame.pack();
				highScoreFrame.setSize(700,700);
				connectionFactory.close();
			}
		});


	}

	public JPanel getHighScoreMenuPanel(){
        return highScoreMenuPanel;
    }
}