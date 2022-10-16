package uet.oop.bomberman.entities;

import javafx.scene.image.Image;

import java.util.Random;

public class Item extends Entity {
    private int item;
    public Item(int x, int y, Image img) {
        super(x, y, img);
    }

    public void randomItem() {
        Random random = new Random();
        item = random.nextInt(5);
    }

    public void powerupWallpass() {

    }

    public void powerupBombpass() {

    }
    @Override
    public void update() {

    }
}
