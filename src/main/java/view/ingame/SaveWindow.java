package view.ingame;

import controller.ingame.GameSession;

import javax.swing.*;
import java.awt.BorderLayout;

public class SaveWindow extends JFrame {

    private Timer errorTimer;

    private JTextField fileNameTextField;
    private JLabel errorMsgText;

    public SaveWindow(GameSession game){
        super("Save");
        this.setSize(320,130);
        this.setVisible(true);
        this.setResizable(false);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        errorMsgText = new JLabel();
        JLabel infoText = new JLabel("filename:");
        fileNameTextField = new JTextField(10);

        errorTimer = new Timer(1500, e -> {
                errorMsgText.setText("");
                errorTimer.stop();

        });

        JButton saveButton = new JButton("Save");
        saveButton.addActionListener(e -> {
            String fileName = fileNameTextField.getText();
            if(fileName.isEmpty() || fileName.length() > 10){

                setErrorText("The filename has to be between 1 and 10 characters");
            }else if(!fileName.equals(fileName.replaceAll("\\s+", ""))){
                setErrorText("The filename can't contains whitespace");
            }else{
                game.saveGame(fileName);
                closeWindow();
            }

        });
        JButton backToGameButton = new JButton("Back");
        backToGameButton.addActionListener(e -> backToGame());

        JPanel textFieldPanel = new JPanel();
        textFieldPanel.add(infoText);
        textFieldPanel.add(fileNameTextField);
        JPanel labelPanel = new JPanel();
        labelPanel.add(errorMsgText);
        JPanel buttonPanel = new JPanel();
        buttonPanel.add(saveButton);
        buttonPanel.add(backToGameButton);
        this.getContentPane().add(BorderLayout.NORTH, textFieldPanel);
        this.getContentPane().add(BorderLayout.CENTER, labelPanel);
        this.getContentPane().add(BorderLayout.SOUTH, buttonPanel);
    }
    private void backToGame(){
        this.dispose();
    }
    private void closeWindow(){
        this.dispose();
    }
    public void setErrorText(String errorMsg){
        errorMsgText.setText(errorMsg);
        errorTimer.start();

    }
}
