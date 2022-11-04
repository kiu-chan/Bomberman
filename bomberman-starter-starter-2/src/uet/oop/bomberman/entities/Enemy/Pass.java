package uet.oop.bomberman.entities.Enemy;

import javafx.scene.image.Image;
import uet.oop.bomberman.entities.Move.AutoMove;
import uet.oop.bomberman.graphics.Sprite;

import java.util.Random;

/**
 * Đứng yên 1 chỗ quay, khi bị nổ sẽ trở thành 1-4 Minvo.
 */
public class Pass extends AutoMove {
    Random random = new Random();
    public Pass(int x, int y, Image img) {
        super(x,y, img, 4, Sprite.pass);
        setPoint(100);
        setAmount(random.nextInt(3) + 1);
    }

    @Override
    public void update() {
        if (!check_dead) {
            movePass(random.nextInt(5));
        } else {
            DEAD();
        }
        if (getRemove() && !getSwapMonster()) {
            setSwapMonster(true);
        }
    }
}
