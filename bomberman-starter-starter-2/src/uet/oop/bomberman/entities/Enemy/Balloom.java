package uet.oop.bomberman.entities.Enemy;

import uet.oop.bomberman.entities.AutoMove;

import javafx.scene.image.Image;
import uet.oop.bomberman.graphics.Sprite;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Balloom extends AutoMove {
    protected int speed = 10;
    Random random = new Random();

    private List<Sprite> list = new ArrayList<>();

    public Balloom(int x, int y, Image img) {
        super( x, y, img, 1, Sprite.balloom);
    }

    @Override
    public void update() {
        int way = random.nextInt(5);
        canMove(way);
    }
}
