package uet.oop.bomberman.entities.EntityOfMap;
import uet.oop.bomberman.entities.Entity;
import uet.oop.bomberman.graphics.Sprite;
import uet.oop.bomberman.graphics.Images;

public class Brick extends Entity {
    public Brick(int x, int y) {
        super(x,y, Sprite.brick.getFxImage());
    }

    @Override
    public void update() {

    }
}
