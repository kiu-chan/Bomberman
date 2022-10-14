package uet.oop.bomberman.entities.Enemy;

import javafx.scene.image.Image;
import uet.oop.bomberman.entities.AutoMove;
import uet.oop.bomberman.graphics.Sprite;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Ghost extends AutoMove {
    protected int speed = 8;

    Random random = new Random();

    public Ghost(int x, int y, Image img) {
        super( x, y, img, 1, Sprite.ghost);
    }

    @Override
    public void update() {
        int way = random.nextInt(5);
        moveGhost(way);
    }
}
