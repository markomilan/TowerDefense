package view.ingame;

import controller.ingame.DestroyTurret;
import controller.ingame.GameSession;
import controller.ingame.BuildTurret;
import model.*;
import model.menu.Difficulty;
import view.database.HighScoreGui;
import view.resloader.GameGallery;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Random;
import java.util.List;

public class GameWindow extends JFrame implements ActionListener{
    private GameSession game;

    private JPanel scorePanel;
    private JPanel buildPanel;
    private PlayArea gamePanel;
    private Difficulty difficulty;

    private JLabel errorMsgText;
    private JLabel infoText;

    private List<Timer> timers;
    private Timer errorTimer;
    private int timePassed;

    private JButton restartButton;
    private JButton buildSniperTurretButton;
    private JButton buildShotgunTurretButton;
    private JButton buildMachineGunTurretButton;
    private JButton buildGeneratorButton;
    private JButton backToMenuButton;
    private JButton saveButton;
    private JButton pauseButton;
    private JButton destroyButton;

    private GameGallery gameGallery;

    public GameWindow(Difficulty difficulty, GameGallery gameGallery){
        super("Game");
        this.difficulty = difficulty;
        this.game = new GameSession(difficulty);

        buildGameWindow(this.game, gameGallery);
    }
    public GameWindow (GameSession game, GameGallery gameGallery) {
        super("Game");
        this.difficulty = game.getDifficulty();
        this.game = new GameSession(game);

        buildGameWindow(this.game, gameGallery);
    }

    private void buildGameWindow(GameSession game, GameGallery gameGallery) {
        this.setPreferredSize(new Dimension(1000,750));
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.addWindowListener(new WindowAdapter(){
            @Override
            public void windowClosing(WindowEvent evt) {
                game.setPauseOnly();
            }
        });
        this.setResizable(false);
        this.gameGallery = gameGallery;
        //timer
        addTimers();
        //panels
        scorePanel = new JPanel();
        setScorePanel();
        gamePanel = new PlayArea(game);
        // Mouse listener for the play area
        gamePanel.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                if(!game.isPause()){
                    java.awt.EventQueue.invokeLater(() -> {
                        // Currently we have a 8x10 playing field, so we divide by those.
                        Pair selected = new Pair(e.getX() / (gamePanel.getSize().width / 10), e.getY() / (gamePanel.getSize().height / 8));

                        if (gamePanel.getCurrentSelected().equals(selected))     // If we clicked on the selected field
                        {
                            gamePanel.setCurrentSelected(new Pair(-1, -1));    // (-1,-1) = unset
                            switchBuildMenuState(false);
                        } else {
                            checkValidField(selected);
                            gamePanel.setCurrentSelected(new Pair(selected));
                        }
                    });
                }
            }
        });
        buildPanel = new JPanel();
        setBuildPanel();

        this.getContentPane().add(BorderLayout.NORTH, scorePanel);
        this.getContentPane().add(BorderLayout.CENTER, gamePanel);
        this.getContentPane().add(BorderLayout.SOUTH, buildPanel);
        this.pack();
        this.setVisible(true);

        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosed(WindowEvent e) {
                for (Timer t : timers)
                {
                    t.stop();
                }
                timers.clear();
            }
        });
    }
    private void addTimers(){
        timers = new ArrayList<>();

        errorTimer = new Timer(2000, e -> {
            if(!game.isPause()){
                errorMsgText.setText("");
                scorePanel.repaint();
                errorTimer.stop();
            }
        });
        Timer gameTimer = new Timer(500, e -> gameTimerTick());
        gameTimer.start();
        timers.add(gameTimer);

        Timer animationTimer = new Timer(50, e -> {
            if (!game.isPause()) {
                if (gamePanel != null)  // Only happens in unit tests, where the frame is closed almost instantly, but the timers tick a final time
                    gamePanel.repaint();
            }
        });
        animationTimer.start();
        timers.add(animationTimer);
    }
    private void setScorePanel(){
        scorePanel.setPreferredSize(new Dimension(1000,50));
        scorePanel.setBackground(new Color(131,75,2));
        FlowLayout scoreLayout = new FlowLayout();
        scorePanel.setLayout(scoreLayout);
        infoText = new JLabel("eltelt idő: " + game.getTime() + ", Gold: " + game.getActualPlayer().getGold());
        infoText.setForeground(Color.white);
        errorMsgText = new JLabel();
        errorMsgText.setForeground(Color.white);

        restartButton = createButtonToPanels(gameGallery.iconRestart);
        restartButton.addActionListener(this);
        backToMenuButton = createButtonToPanels(gameGallery.iconBackMenu);
        backToMenuButton.addActionListener(this);
        saveButton = createButtonToPanels(gameGallery.iconSave);
        saveButton.addActionListener(this);
        pauseButton = createButtonToPanels(gameGallery.iconPause);
        pauseButton.addActionListener(this);

        scorePanel.add(infoText);
        scorePanel.add(errorMsgText);
        scorePanel.add(restartButton);
        scorePanel.add(pauseButton);
        scorePanel.add(saveButton);
        scorePanel.add(backToMenuButton);
        this.pack();
    }
    private void setBuildPanel(){
        buildPanel.setPreferredSize(new Dimension(1000,61));
        buildPanel.setBackground(new Color(131,75,2));
        buildSniperTurretButton = createButtonToPanels(gameGallery.iconBuildSniper);
        buildShotgunTurretButton = createButtonToPanels(gameGallery.iconBuildShotgun);
        buildGeneratorButton = createButtonToPanels(gameGallery.iconBuildGenerator);
        buildMachineGunTurretButton = createButtonToPanels(gameGallery.iconBuildMachineGun);
        destroyButton = createButtonToPanels(gameGallery.iconDestroy);

        buildSniperTurretButton.addActionListener(new BuildTurret(this, gamePanel, 1, gameGallery));
        buildShotgunTurretButton.addActionListener(new BuildTurret(this, gamePanel, 2, gameGallery));
        buildMachineGunTurretButton.addActionListener(new BuildTurret(this, gamePanel, 3, gameGallery));
        buildGeneratorButton.addActionListener(new BuildTurret(this, gamePanel, 4, gameGallery));
        destroyButton.addActionListener(new DestroyTurret(this, game, gamePanel));

        buildPanel.add(buildSniperTurretButton);
        buildPanel.add(buildShotgunTurretButton);
        buildPanel.add(buildMachineGunTurretButton);
        buildPanel.add(buildGeneratorButton);
        buildPanel.add(destroyButton);
        switchBuildMenuState(false);
        this.pack();
    }
    private void switchBuildMenuState(boolean state) {
        for (Component c : buildPanel.getComponents())
            c.setEnabled(state);
    }
    public void setErrorText(String errorMsg){

        errorTimer.start();
        errorMsgText.setText(errorMsg);
    }
    public GameSession getGame(){
        return game;
    }
    public PlayArea getPlayArea() {return gamePanel;}
    private void gameOver(){
        game.setPauseOnly();
        setAllBuildButtonNotEnable();
        JOptionPane.showMessageDialog(this, "         You lost this game..\n Click the Ok button to restart the game!");
        this.dispose();
        new GameWindow(game.getDifficulty(), gameGallery);
    }
    private void setAllBuildButtonNotEnable(){
        buildSniperTurretButton.setEnabled(false);
        buildShotgunTurretButton.setEnabled(false);
        buildMachineGunTurretButton.setEnabled(false);
        buildGeneratorButton.setEnabled(false);
        destroyButton.setEnabled(false);
    }
    private void gameWin(int gold){
        game.setPauseOnly();
        setAllBuildButtonNotEnable();
        JOptionPane.showMessageDialog(this, "         You win this game!");
        new HighScoreGui(gold);
        this.dispose();
    }
    private void checkValidField(Pair selected){
        if(selected.getY() > 5 || selected.getY() == 0){
            switchBuildMenuState(false);
        }else{
            switchBuildMenuState(true);
        }
    }
    public String getErrorMsg()
    {
        return errorMsgText.getText();
    }
    private JButton createButtonToPanels(ImageIcon buttonIcon) {
        JButton button = new JButton(buttonIcon);
        button.setBorder(null);
        button.setBackground(new Color(131, 75, 2));
        return button;
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == restartButton) {
            dispose();
            game.setPauseOnly();
            setAllBuildButtonNotEnable();
            game.restart();
        } else if (e.getSource() == backToMenuButton) {
            game.setPauseOnly();
            setAllBuildButtonNotEnable();
            dispose();
        } else if (e.getSource() == saveButton) {
            game.setPauseOnly();
            setAllBuildButtonNotEnable();
            new SaveWindow(game);
        } else if (e.getSource() == pauseButton) {
            game.setPause();
            if(game.isPause()){ setAllBuildButtonNotEnable();}
        }
    }
    private void gameTimerTick() {
        if (!game.isPause()) {
            timePassed = (++timePassed % 4);
            if (timePassed % 2 == 0) {
                game.timePassed(true);
                game.setTime(game.getTime() + 1);
                moveEnemies();
                if (game.getTime() % 3 == 0) {
                    spawnEnemy();
                }
            }
            else {
                game.timePassed(false);
            }
            goldGeneration();
            infoText.setText("Eltelt idő: " + game.getTime() + ", Gold: " + game.getActualPlayer().getGold());
        }
    }
    private void moveEnemies() {
        int time = game.getTime();
        Field dummy = new Field();
        for (int i = 0; i < 10; ++i) {
            game.getField(new Pair(i, 0)).move(time, dummy);
        }
        if (dummy.getEnemyCount() > 0) {
            gameOver();
        }
        for (int i = 0; i < 10; ++i) {
            for (int j = 1; j < 8; ++j) {
                game.getField(new Pair(i, j)).move(time, game.getField(new Pair(i, j - 1)));
            }
        }
    }
    private void spawnEnemy() {
        Random r = new Random();
        int type = r.nextInt();
        Enemy enemy;
        Pair location = new Pair(Math.floorMod(type, 10), 7);
        switch (Math.floorMod(type, 3)) {
            case 1:
                enemy = new FastEnemy(location, gameGallery, difficulty);
                break;
            case 2:
                enemy = new SlowEnemy(location, gameGallery, difficulty);
                break;
            default:
                enemy = new GenericEnemy(location, gameGallery, difficulty);
                break;
        }
        int tries = 0;
        while (!game.getField(location).canFitEnemy(enemy) && tries < 10)
        {
            location = new Pair(Math.floorMod(r.nextInt(), 10), 7);
            enemy.setLocation(location);
            ++tries;
        }
        if (game.getField(location).canFitEnemy(enemy)){
            game.getField(location).addEnemy(enemy);
        }
        scorePanel.repaint();
        if (game.checkWin()) gameWin(game.getActualPlayer().getGold());
    }
    private void goldGeneration() {
        if (timePassed % 4 == 0)
        {
            game.getActualPlayer().addGold(1);
            game.getActualPlayer().addGold(game.getGeneratorCounter());
        }
        else if (timePassed % 3 == 0)
        {
            game.getActualPlayer().addGold(game.getImprovedGeneratorCounter());
        }
    }
}
