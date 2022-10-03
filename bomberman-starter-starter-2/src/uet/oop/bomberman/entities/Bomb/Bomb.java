package uet.oop.bomberman.entities.Bomb;

import uet.oop.bomberman.entities.Entity;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import uet.oop.bomberman.BombermanGame;
import uet.oop.bomberman.entities.Bomber;
import uet.oop.bomberman.entities.Entity;
import uet.oop.bomberman.graphics.Sprite;
import uet.oop.bomberman.entities.Bomber;

public class Bomb extends Entity {
    private boolean isExplotion = false;
    private boolean remove = false;
    private int radius;
    int time = 100;
    public Bomb(int x, int y, Image img) {
        super(x, y, img);
    }
    @Override
    public void render(GraphicsContext gc) {
        int time = 100;
       /* if (time > 0 && isExplotion == false) {
            super.render(gc);
            time--;
        }
        else if (time == 0) {
            int timeAfter = 100;
            isExplotion = true;
            img = Sprite.movingSprite(Sprite.bomb_1,Sprite.bomb_2,time,50).getFxImage();
            timeAfter--;
            if (timeAfter == 0) {
                remove = true;
            }
        }*/
        img = Sprite.movingSprite(Sprite.bomb_1,Sprite.bomb_2,time,50).getFxImage();
        super.render(gc);
    }
    public void update() {
    }
}
