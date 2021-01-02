package view.resloader;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class Animation {
    public ArrayList<BufferedImage> images;
    int interval,index;
    long timer,now,lastTime;

    public  Animation(ArrayList<BufferedImage> images, int interval){
        this.images = images;
        this.interval = interval;
        index = 0;
        timer = 0;
        now = 0;
        lastTime = System.currentTimeMillis();
    }

    public void tick(){
        now = System.currentTimeMillis();
        timer += now - lastTime;
        lastTime = now;
        if(timer >= interval){
            index++;
            timer = 0;

            if(index >= images.size()){
                index = 0;
            }
        }
    }

    public void render(Graphics g , int x , double y, int width, int height){
        g.drawImage(images.get(index), x, (int)y, width, height, null);
    }
}
