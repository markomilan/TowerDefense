package view.ingame;

import controller.ingame.GameSession;
import model.Field;
import model.Pair;
import view.resloader.GameGallery;

import javax.swing.JPanel;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.io.IOException;

public class PlayArea extends JPanel {
    private Pair currentSelected;   // Currently selected field. If no field is selected, it is -1,-1.
    private GameSession gameSession;
    private GameGallery gameGallery;

    public PlayArea(GameSession gameSession)
    {
        super();
        currentSelected = new Pair(-1,-1);
        this.gameSession = gameSession;
        gameGallery = new GameGallery();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2D = (Graphics2D) g;
        drawBackground(g2D);
        if (currentSelected.getX() != -1)
            select(g2D, currentSelected);   // Select the new field, unless the user clicked on the already selected field
        Field[][] field = gameSession.getField();
        int cellWidth = this.getSize().width / 10;
        int cellHeight = this.getSize().height / 8;
        for (int i = 0; i < 8; ++i)
        {
            for (int j = 0; j < 10; ++j)
            {
                try {
                    field[i][j].draw(g2D,cellWidth,cellHeight,new Pair(j,i), gameGallery);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private void drawBackground(Graphics2D g) {
        g.setColor(Color.lightGray);  // Background color
        g.fillRect(0, 0, 1000, 560);    // Background rectangle
        this.setSize(1000,600);
        g.setColor(Color.BLACK);    // Line color
        int cellWidth = this.getSize().width / 10;
        int cellHeight = this.getSize().height / 8;
        int panelHeight = this.getSize().height;
        int panelWidth = this.getSize().width;
        for (int i = 0; i < 10; ++i)
        {
            for(int j=0;j<8;++j) {
                g.drawImage(gameGallery.imageField, i * cellWidth, j * cellHeight, cellWidth, cellHeight, null);
            }

            g.drawImage(gameGallery.imageRock,i * cellWidth ,6 * cellHeight, cellHeight , cellWidth/2, null );
            g.drawImage(gameGallery.imageRock,i * cellWidth ,7 * cellHeight - 25 , cellHeight , cellWidth , null );
        }
    }

    private void select(Graphics2D g, Pair p)
    {
        g.setColor(Color.RED);
        int cellWidth = this.getSize().width / 10;
        int cellHeight = this.getSize().height / 8;
        // Care: Don't draw on the lines!
        g.drawRect(p.getX() * cellWidth, p.getY() * cellHeight, cellWidth, cellHeight);
    }
    public Pair getCurrentSelected()
    {
        return currentSelected;
    }

    public void setCurrentSelected(Pair currentSelected)
    {
        this.currentSelected = currentSelected;
    }
}
