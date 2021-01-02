package controller.ingame;

import model.Field;
import model.Pair;
import model.Player;
import model.Structure;
import model.menu.Difficulty;
import view.ingame.GameWindow;
import view.resloader.GameGallery;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class GameSession {
    private Field[][] field;
    private Difficulty difficulty;
    private int time;
    private boolean isPause;
    private Player actualPlayer;
    private int generatorCounter;
    private int improvedGeneratorCounter;

    public GameSession(Difficulty difficulty){
        field = new Field[8][10];
        for (int i = 0; i < 8; ++i)
            for (int j = 0; j < 10; ++j)
                field[i][j] = new Field();
        this.difficulty = difficulty;
        time = 0;
        isPause = false;
        actualPlayer = new Player();
    }

    public GameSession(GameSession game){
        field = new Field[8][10];
        for (int i = 0; i < 8; ++i)
            for (int j = 0; j < 10; ++j)
                field[i][j] = game.getField(i,j);
        this.difficulty = game.getDifficulty();
        time = game.getTime();
        isPause = false;
        actualPlayer = new Player();
        actualPlayer.setGold(game.getActualPlayer().getGold());
        generatorCounter = game.getGeneratorCounter();
        improvedGeneratorCounter = game.getImprovedGeneratorCounter();
    }

    public void setGeneratorCounter(int value){generatorCounter = value;}

    public int getGeneratorCounter(){return generatorCounter;}

    public void setImprovedGeneratorCounter(int value){improvedGeneratorCounter = value;}

    public int getImprovedGeneratorCounter(){return improvedGeneratorCounter;}

    public void setField(Pair location, Field f){
        field[location.getY()][location.getX()] = f;
    }

    public Field[][] getField() {
        return field;
    }

    public Field getField(int i , int j){return field[i][j];}

    public Field getField(Pair p) {
        int x = p.getX();
        int y = p.getY();
        if (y < 0 || y > 7 || x < 0 || x > 9 )
            return null;
        return field[p.getY()] [p.getX()];
    }

    public void setWholeField(Field[][] f){
        for(int i = 0; i < 8 ; i++){
            System.arraycopy(f[i], 0, field[i], 0, 10);
        }
    }

    public Difficulty getDifficulty() {
        return difficulty;
    }

    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }

    public boolean isPause() {
        return isPause;
    }

    public void setPause() {
        isPause = !isPause;
    }

    public void setPauseOnly(){isPause = true;}

    public Player getActualPlayer() {
        return actualPlayer;
    }

    public GameWindow restart() {
        return new GameWindow(difficulty, new GameGallery());
    }

    public boolean checkWin(){
        int winTime = 10;
        switch (difficulty){
            case EASY:
                winTime += 20;
                break;
            case MEDIUM:
                winTime += 40;
                break;
            case HARD:
                winTime += 60;
                break;
        }
        return time >= winTime;
    }

    public void saveGame(String fileName) {
        String unit;
        String s = "src\\main\\savedgames\\";
        File f = new File(s);
        if (!f.isDirectory()){
            new File(s).mkdirs();
        }
        String finalFileName = s + fileName + ".txt";
        try {
            FileWriter fw = new FileWriter(finalFileName);
            fw.write(difficulty+"\n");
            fw.write(time+"\n");
            fw.write(actualPlayer.getGold() + "\n");
            fw.write(generatorCounter + "\n");
            fw.write(String.valueOf(improvedGeneratorCounter));
            for (int i = 0; i < 8; i++) {
                fw.write("\n");
                for(int j = 0; j < 10;j++){
                    if(field[i][j].getStructure() == null && field[i][j].getEnemyCount() == 0){
                        fw.write("E ");
                    }else if(field[i][j].getStructure() != null && field[i][j].getEnemyCount() == 0){
                        Structure struct = field[i][j].getStructure();
                        unit = String.valueOf(struct.getId()) + "," + struct.getActualHealth() + "," + struct.isImprovedToChar() + " ";
                        fw.write(unit);
                    } else if (field[i][j].getStructure() == null && field[i][j].getEnemyCount() > 0) {
                        Field act = field[i][j];
                        unit = "";
                        for(int k = 0; k < act.getEnemyCount() ; k++){
                            unit+=String.valueOf(act.getEnemy(k).getId()) + "," + act.getEnemy(k).getHealth() + "_";
                        }
                        char[] c = unit.toCharArray();
                        c[c.length - 1] = ' ';
                        fw.write(c);
                    }

                }
            }
            fw.close();
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }

    public void timePassed(boolean slowTick) {
        for (int i = 1; i < 6; ++i)
        {
            for (int j = 0; j < 10; ++j)
            {
                if (field[i][j].getStructure() != null)
                    field[i][j].getStructure().tryShoot(slowTick, new Pair(j, i), this);
            }
        }
    }
}
