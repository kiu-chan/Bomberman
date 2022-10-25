package uet.oop.bomberman.entities.Enemy;

import uet.oop.bomberman.entities.AutoMove;
import uet.oop.bomberman.graphics.Sprite;

import javafx.scene.image.Image;

import java.util.Random;

/**
 * loại quái có tốc độ di chuyển nhanh gấp đôi quái khác.
 */
public class Minvo extends AutoMove {
    Random random = new Random();
    public Minvo(int x, int y, Image img) {
        super(x,y, img, 2, Sprite.minvo);//System.out.println(1);
    }

    @Override
    public void update() {
        int way = random.nextInt(5);
        if (!check_dead) {
            moveMinvo(way);
        } else {
            DEAD();
        }
    }
}
