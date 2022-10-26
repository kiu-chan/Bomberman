package uet.oop.bomberman.entities.Enemy;

import javafx.scene.image.Image;
import uet.oop.bomberman.entities.Move.AutoMove;
import uet.oop.bomberman.graphics.Sprite;

/**
 * Đứng yên 1 chỗ quay, khi bị nổ sẽ trở thành Minvo.
 */
public class MinvoRotate extends AutoMove {
    public MinvoRotate(int x, int y, Image img) {
        super(x,y, img, 1, Sprite.minvoRotate);
    }

    @Override
    public void update() {
        if (!check_dead) {
            moveMinvoRotate();
        } else {
            DEAD();
        }
        //System.out.println(1);
        if (getRemove() && !getSwapMonster()) {
            setSwapMonster(true);
        }
    }
}
