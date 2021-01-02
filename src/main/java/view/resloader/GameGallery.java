package view.resloader;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class GameGallery {
    public BufferedImage imageBuildGenerator;
    public BufferedImage imageDestroy;
    public BufferedImage imageBuildSniper;
    public BufferedImage imageBuildShotgun;
    public BufferedImage imageBuildMachineGun;
    public BufferedImage imageBackMenu;
    public BufferedImage imagePause;
    public BufferedImage imageRestart;
    public BufferedImage imageSave;

    public ImageIcon iconBuildGenerator;
    public ImageIcon iconDestroy;
    public ImageIcon iconBuildSniper;
    public ImageIcon iconBuildShotgun;
    public ImageIcon iconBuildMachineGun;
    public ImageIcon iconBackMenu;
    public ImageIcon iconPause;
    public ImageIcon iconRestart;
    public ImageIcon iconSave;

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////
    //FIELDS
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////
    public BufferedImage imageField;
    public BufferedImage imageRock;
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////
    //ENEMY
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////
    private ArrayList<BufferedImage> imagesGenericEnemyRun;
    private ArrayList<BufferedImage> imagesGenericEnemyAtk;
    private ArrayList<BufferedImage> imagesGenericEnemyDie;
    public Animation animationGenericEnemyRun;
    public Animation animationGenericEnemyAtk;
    public Animation animationGenericEnemyDie;
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////
    private ArrayList<BufferedImage> imagesSlowEnemyRun;
    private ArrayList<BufferedImage> imagesSlowEnemyAtk;
    private ArrayList<BufferedImage> imagesSlowEnemyDie;
    public Animation animationSlowEnemyRun;
    public Animation animationSlowEnemyAtk;
    public Animation animationSlowEnemyDie;
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////
    private ArrayList<BufferedImage> imagesFastEnemyRun;
    private ArrayList<BufferedImage> imagesFastEnemyAtk;
    private ArrayList<BufferedImage> imagesFastEnemyDie;
    public Animation animationFastEnemyRun;
    public Animation animationFastEnemyAtk;
    public Animation animationFastEnemyDie;
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////
    //STRUCTURE
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////
    private ArrayList<BufferedImage> imagesGeneratorBaseMove;
    private ArrayList<BufferedImage> imagesSniperBaseMove;
    private ArrayList<BufferedImage> imagesMachineGunBaseMove;
    private ArrayList<BufferedImage> imagesShotGunBaseMove;

    public Animation animationGeneratorBaseMove;
    public Animation animationSniperBaseMove;
    public Animation animationMachineGunBaseMove;
    public Animation animationShotGunBaseMove;
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////
    private ArrayList<BufferedImage> imagesGeneratorImpMove;
    private ArrayList<BufferedImage> imagesSniperImpMove;
    private ArrayList<BufferedImage> imagesMachineGunImpMove;
    private ArrayList<BufferedImage> imagesShotGunImpMove;

    public Animation animationGeneratorImpMove;
    public Animation animationSniperImpMove;
    public Animation animationMachineGunImpMove;
    public Animation animationShotGunImpMove;
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////
    public GameGallery() {
        ////////////////////////////////////////////////////////////////////////////////////////////////////////////
        //ENEMY
        ////////////////////////////////////////////////////////////////////////////////////////////////////////////
        File dirGenericEnemyDie = new File("src/main/resources/images/genericenemy/die");
        File dirGenericEnemyAtk = new File("src/main/resources/images/genericenemy/atk");
        File dirGenericEnemyRun = new File("src/main/resources/images/genericenemy/run");
        ////////////////////////////////////////////////////////////////////////////////////////////////////////////
        File dirSlowEnemyDie = new File("src/main/resources/images/slowenemy/die");
        File dirSlowEnemyAtk = new File("src/main/resources/images/slowenemy/atk");
        File dirSlowEnemyRun = new File("src/main/resources/images/slowenemy/run");
        ////////////////////////////////////////////////////////////////////////////////////////////////////////////
        File dirFastEnemyDie = new File("src/main/resources/images/fastenemy/die");
        File dirFastEnemyAtk = new File("src/main/resources/images/fastenemy/atk");
        File dirFastEnemyRun = new File("src/main/resources/images/fastenemy/run");
        ////////////////////////////////////////////////////////////////////////////////////////////////////////////
        //STRUCTURE
        ////////////////////////////////////////////////////////////////////////////////////////////////////////////
        File dirGeneratorBaseMove = new File("src/main/resources/images/generator/base");
        File dirMachineGunBaseMove = new File("src/main/resources/images/machinegunturret/base");
        File dirSniperBaseMove = new File("src/main/resources/images/sniperturret/base");
        File dirShotGunBaseMove = new File("src/main/resources/images/shotgunturret/base");

        File dirGeneratorImpMove = new File("src/main/resources/images/generator/improve");
        File dirMachineGunImpMove = new File("src/main/resources/images/machinegunturret/improve");
        File dirSniperImpMove = new File("src/main/resources/images/sniperturret/improve");
        File dirShotGunImpMove = new File("src/main/resources/images/shotgunturret/improve");
        ////////////////////////////////////////////////////////////////////////////////////////////////////////////
        try {
            ////////////////////////////////////////////////////////////////////////////////////////////////////////////
            //BUTTONS
            ////////////////////////////////////////////////////////////////////////////////////////////////////////////
            imageBuildGenerator = ImageLoader.readImage("/images/buttons/button_buildgenerator.png");
            imageDestroy = ImageLoader.readImage("/images/buttons/button_destroy.png");
            imageBuildMachineGun = ImageLoader.readImage("/images/buttons/button_buildmachinegun.png");
            imageBuildShotgun = ImageLoader.readImage("/images/buttons/button_buildshotgun.png");
            imageBuildSniper = ImageLoader.readImage("/images/buttons/button_buildsniper.png");
            imageBackMenu = ImageLoader.readImage("/images/buttons/button_backmenu.png");
            imageRestart = ImageLoader.readImage("/images/buttons/button_restart.png");
            imagePause = ImageLoader.readImage("/images/buttons/button_pause.png");
            imageSave = ImageLoader.readImage("/images/buttons/button_save.png");

            iconBuildGenerator = new ImageIcon(imageBuildGenerator);
            iconDestroy = new ImageIcon(imageDestroy);
            iconBuildMachineGun = new ImageIcon(imageBuildMachineGun);
            iconBuildShotgun = new ImageIcon(imageBuildShotgun);
            iconBuildSniper = new ImageIcon(imageBuildSniper);
            iconBackMenu = new ImageIcon(imageBackMenu);
            iconRestart = new ImageIcon(imageRestart);
            iconPause = new ImageIcon(imagePause);
            iconSave = new ImageIcon(imageSave);
            ////////////////////////////////////////////////////////////////////////////////////////////////////////////
            //FIELDS
            ////////////////////////////////////////////////////////////////////////////////////////////////////////////
            imageField = ImageLoader.readImage("/images/FIELD.png");
            imageRock = ImageLoader.readImage("/images/ROCK.png");
            ////////////////////////////////////////////////////////////////////////////////////////////////////////////
            //ENEMY
            ////////////////////////////////////////////////////////////////////////////////////////////////////////////
            imagesGenericEnemyRun = ImageLoader.readAnimation(dirGenericEnemyRun);
            imagesGenericEnemyAtk = ImageLoader.readAnimation(dirGenericEnemyAtk);
            imagesGenericEnemyDie = ImageLoader.readAnimation(dirGenericEnemyDie);
            ////////////////////////////////////////////////////////////////////////////////////////////////////////////
            imagesSlowEnemyRun = ImageLoader.readAnimation(dirSlowEnemyRun);
            imagesSlowEnemyAtk = ImageLoader.readAnimation(dirSlowEnemyAtk);
            imagesSlowEnemyDie = ImageLoader.readAnimation(dirSlowEnemyDie);
            ////////////////////////////////////////////////////////////////////////////////////////////////////////////
            imagesFastEnemyRun = ImageLoader.readAnimation(dirFastEnemyRun);
            imagesFastEnemyAtk = ImageLoader.readAnimation(dirFastEnemyAtk);
            imagesFastEnemyDie = ImageLoader.readAnimation(dirFastEnemyDie);
            ////////////////////////////////////////////////////////////////////////////////////////////////////////////
            //STRUCTURE
            ////////////////////////////////////////////////////////////////////////////////////////////////////////////
            imagesGeneratorBaseMove = ImageLoader.readAnimation(dirGeneratorBaseMove);
            imagesMachineGunBaseMove = ImageLoader.readAnimation(dirMachineGunBaseMove);
            imagesSniperBaseMove = ImageLoader.readAnimation(dirSniperBaseMove);
            imagesShotGunBaseMove = ImageLoader.readAnimation(dirShotGunBaseMove);

            imagesGeneratorImpMove = ImageLoader.readAnimation(dirGeneratorImpMove);
            imagesMachineGunImpMove = ImageLoader.readAnimation(dirMachineGunImpMove);
            imagesSniperImpMove = ImageLoader.readAnimation(dirSniperImpMove);
            imagesShotGunImpMove = ImageLoader.readAnimation(dirShotGunImpMove);
            ////////////////////////////////////////////////////////////////////////////////////////////////////////////
        } catch (IOException e) {
            e.printStackTrace();
        }
        ////////////////////////////////////////////////////////////////////////////////////////////////////////////
        //ENEMY
        ////////////////////////////////////////////////////////////////////////////////////////////////////////////
        animationGenericEnemyRun = new Animation(imagesGenericEnemyRun,100);
        animationGenericEnemyAtk = new Animation(imagesGenericEnemyAtk,100);
        animationGenericEnemyDie = new Animation(imagesGenericEnemyDie,100);
        ////////////////////////////////////////////////////////////////////////////////////////////////////////////
        animationSlowEnemyRun = new Animation(imagesSlowEnemyRun,140);
        animationSlowEnemyAtk = new Animation(imagesSlowEnemyAtk,140);
        animationSlowEnemyDie = new Animation(imagesSlowEnemyDie,140);
        ////////////////////////////////////////////////////////////////////////////////////////////////////////////
        animationFastEnemyRun = new Animation(imagesFastEnemyRun,80);
        animationFastEnemyAtk = new Animation(imagesFastEnemyAtk,80);
        animationFastEnemyDie = new Animation(imagesFastEnemyDie,80);
        ////////////////////////////////////////////////////////////////////////////////////////////////////////////
        //STRUCTURE
        ////////////////////////////////////////////////////////////////////////////////////////////////////////////
        animationGeneratorBaseMove = new Animation(imagesGeneratorBaseMove,80);
        animationMachineGunBaseMove = new Animation(imagesMachineGunBaseMove,80);
        animationSniperBaseMove = new Animation(imagesSniperBaseMove,80);
        animationShotGunBaseMove = new Animation(imagesShotGunBaseMove,80);

        animationGeneratorImpMove = new Animation(imagesGeneratorImpMove,60);
        animationMachineGunImpMove = new Animation(imagesMachineGunImpMove,60);
        animationSniperImpMove = new Animation(imagesSniperImpMove,60);
        animationShotGunImpMove = new Animation(imagesShotGunImpMove,60);
    }
}
