package controller.ingame;

import model.*;
import model.menu.Difficulty;
import view.ingame.GameWindow;
import view.resloader.GameGallery;

import javax.swing.JFileChooser;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FilenameFilter;
import java.util.Scanner;

public class LoadGame {

    private GameGallery gameGallery;
    private GameWindow gameWindow;

    public LoadGame(GameGallery gameGallery)
    {
        this.gameGallery = gameGallery;
        loadGame(this.gameGallery);
    }
    public LoadGame(GameGallery gameGallery, String fileName)
    {
        this.gameGallery = gameGallery;
        loadGame(this.gameGallery, fileName);
    }
    private void loadGame(GameGallery gameGallery, String fileName){
        try (Scanner sc = new Scanner(new File(System.getProperty("user.dir") + "\\src\\main\\savedgames\\" +fileName))) {
            this.gameWindow = loadFromFile(sc);
        }
        catch (FileNotFoundException ex){
            System.out.printf("File %s not found!", fileName);
        }
    }
    private void loadGame(GameGallery gameGallery) {
        JFileChooser jfc = new JFileChooser(System.getProperty("user.dir") + "\\src\\main\\savedgames");
        jfc.setDialogTitle("Load the File");
        int returnValue = jfc.showOpenDialog(null);
        File selectedFile;
        selectedFile = jfc.getSelectedFile();
        if (selectedFile != null) {
            try (Scanner sc = new Scanner(selectedFile)) {
                this.gameWindow = loadFromFile(sc);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
    }
    private GameWindow loadFromFile(Scanner sc)
    {
        Difficulty difficulty;
        GameSession g;
        String dif = sc.nextLine();
        difficulty = loadDifficulty(dif);
        g = new GameSession(difficulty);
        int time = Integer.parseInt(sc.nextLine());
        int gold = Integer.parseInt(sc.nextLine());
        int generatorCount = Integer.parseInt(sc.nextLine());
        int improvedGeneratorCount = Integer.parseInt(sc.nextLine());
        Field[][] field = new Field[8][10];
        String line;
        String[] splitLine = new String[10];
        if (sc.hasNextLine()) {
            line = sc.nextLine();
            splitLine = line.split(" ");
        }
        String[] unit;
        String[] enemies;
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 10; j++) {
                field[i][j] = new Field();
            }
        }
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 10; j++) {
                if (!splitLine[j].equals("E")) {
                    unit = splitLine[j].split(",");
                    if (unit.length == 3 && (unit[2].equals("F") || unit[2].equals("T"))) {
                        loadStructure(field, Integer.parseInt(unit[0]), unit, i, j);
                    } else {
                        enemies = splitLine[j].split("_");
                        loadEnemy(field, unit, i, j, enemies, difficulty);
                    }
                }
            }
            if (sc.hasNextLine()) {
                line = sc.nextLine();
                splitLine = line.split(" ");
            }
        }
        g.setTime(time);
        g.getActualPlayer().setGold(gold);
        g.setWholeField(field);
        g.setGeneratorCounter(generatorCount);
        g.setImprovedGeneratorCounter(improvedGeneratorCount);
        return new GameWindow(g, gameGallery);
    }
    private void loadEnemy(Field[][] field,String[] unit,int i, int j,String[] enemies,Difficulty d){
        Enemy e;
        for(int k = 0; k < enemies.length; k++) {
            unit = enemies[k].split(",");
            switch (Integer.parseInt(unit[0])) {
                case 1:
                    e = new FastEnemy(new Pair(j, i), this.gameGallery, d);
                    e.setHealth(Integer.parseInt(unit[1]));
                    e.setPixelLocation(i * (-70));
                    field[i][j].addEnemy(e);
                    break;
                case 2:
                    e = new GenericEnemy(new Pair(j, i), this.gameGallery, d);
                    e.setHealth(Integer.parseInt(unit[1]));
                    e.setPixelLocation(i * (-70));
                    field[i][j].addEnemy(e);
                    break;
                default:
                    e = new SlowEnemy(new Pair(j, i), this.gameGallery, d);
                    e.setHealth(Integer.parseInt(unit[1]));
                    e.setPixelLocation(i * (-70));
                    field[i][j].addEnemy(e);
            }
        }
    }
    private Difficulty loadDifficulty(String dif){
        switch (dif){
            case "EASY":
                return Difficulty.EASY;
            case "MEDIUM":
                return Difficulty.MEDIUM;
            default:
                return Difficulty.HARD;
        }
    }
    private void loadStructure(Field[][] field, int structureType,String[] unit,int i, int j){
        Structure s;
        switch(structureType){
            case 1:
                s = new SniperTurret(this.gameGallery);
                s.setActualHealth(Integer.parseInt(unit[1]));
                if(unit[2].equals("T")) s.setImproved();
                field[i][j].build(s);
                break;
            case 2:
                s = new ShotgunTurret(this.gameGallery);
                s.setActualHealth(Integer.parseInt(unit[1]));
                if(unit[2].equals("T")) s.setImproved();
                field[i][j].build(s);
                break;
            case 3:
                s = new MachineGunTurret(this.gameGallery);
                s.setActualHealth(Integer.parseInt(unit[1]));
                if(unit[2].equals("T")) s.setImproved();
                field[i][j].build(s);
                break;
            default:
                s = new Generator(this.gameGallery);
                s.setActualHealth(Integer.parseInt(unit[1]));
                if(unit[2].equals("T")) s.setImproved();
                field[i][j].build(s);
        }
    }

    public GameWindow getGameWindow(){
        return gameWindow;
    }
}
