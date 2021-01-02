package view.resloader;

import javax.swing.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class MenuGallery {
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////
    //BUTTONS
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////
    public BufferedImage imageStart;
    public BufferedImage imageHighScore;
    public BufferedImage imageLoad;
    public BufferedImage imageClose;
    public BufferedImage imageBack;
    public BufferedImage imageEasy;
    public BufferedImage imageMedium;
    public BufferedImage imageHard;
    public BufferedImage imageGeneratorButton;

    public ImageIcon iconGeneratorButton;
    public ImageIcon iconBack;
    public ImageIcon iconStart;
    public ImageIcon iconHighScore;
    public ImageIcon iconLoad;
    public ImageIcon iconClose;
    public ImageIcon iconEasy;
    public ImageIcon iconMedium;
    public ImageIcon iconHard;

    public MenuGallery(){
        try {
            imageStart = ImageLoader.readImage("/images/buttons/button_start.png");
            imageHighScore = ImageLoader.readImage("/images/buttons/button_highscores.png");
            imageLoad = ImageLoader.readImage("/images/buttons/button_load.png");
            imageClose = ImageLoader.readImage("/images/buttons/button_close.png");
            imageBack = ImageLoader.readImage("/images/buttons/button_back.png");
            imageEasy = ImageLoader.readImage("/images/buttons/button_easy.png");
            imageMedium = ImageLoader.readImage("/images/buttons/button_medium.png");
            imageHard = ImageLoader.readImage("/images/buttons/button_hard.png");
            imageGeneratorButton = ImageLoader.readImage("/images/buttons/button_buildgenerator.png");

            iconGeneratorButton = new ImageIcon(imageGeneratorButton);
            iconBack = new ImageIcon(imageBack);
            iconStart = new ImageIcon(imageStart);
            iconHighScore = new ImageIcon(imageHighScore);
            iconLoad = new ImageIcon(imageLoad);
            iconClose = new ImageIcon(imageClose);
            iconEasy = new ImageIcon(imageEasy);
            iconMedium = new ImageIcon(imageMedium);
            iconHard = new ImageIcon(imageHard);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
