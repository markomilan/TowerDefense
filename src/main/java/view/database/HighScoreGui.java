package view.database;

import javax.swing.*;
import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.WindowEvent;

import static java.lang.String.valueOf;

public class HighScoreGui {
    private JFrame Fr;
    private JTextField FieldN;

    public HighScoreGui(int gold) {
        JMenuBar menubar = new JMenuBar();
        Fr = new JFrame("HighScore");
        Fr.setJMenuBar(menubar);
        Fr.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        Fr.setSize(350, 350);

        JPanel panelHS = new JPanel();
        panelHS.setLayout(new GridLayout(5, 1));

        FieldN = new JTextField();
        panelHS.add(FieldN);

        JButton buttonS = new JButton("Save");

        buttonS.addActionListener(e -> {
            ConnectionFactory connectionFactory = new ConnectionFactory();
            connectionFactory.post(FieldN.getText(),gold);
            Fr.dispatchEvent(new WindowEvent(Fr, WindowEvent.WINDOW_CLOSING));

        });

        panelHS.add(buttonS);

        JLabel labelT = new JLabel(valueOf(gold));
        panelHS.add(labelT);

        Fr.setVisible(true);
        Fr.getContentPane().add(BorderLayout.SOUTH, panelHS);
    }
}
