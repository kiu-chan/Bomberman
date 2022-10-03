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
    private int timeToExplode = 100;
    private int timeAfterExplode = 30;
    public Bomb(int x, int y, Image img) {
        super(x, y, img);
    }
    @Override
    public void render(GraphicsContext gc) {
        if (remove == false && isExplotion == false) {
            img = Sprite.movingSprite(Sprite.bomb, Sprite.bomb_1, Sprite.bomb_2,
                    100-(timeToExplode)/2, 100).getFxImage();
            super.render(gc);
        } else if (isExplotion) {
            img = Sprite.movingSprite(Sprite.bomb_exploded1, Sprite.bomb_exploded2,
                    timeAfterExplode, 100).getFxImage();
            super.render(gc);
        }
    }
    public void update() {
        if (remove != true) {
            if (timeToExplode > 0) {
                timeToExplode--;
                System.out.println(timeToExplode+"time to explo");
            }
            else {
                isExplotion = true;
                if (timeAfterExplode > 0) {
                    timeAfterExplode--;
                    System.out.println(timeAfterExplode+"time after explo");
                } else {
                    setRemove();
                }
            }
        }
    }
}
