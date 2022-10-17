package uet.oop.bomberman.entities;

import javafx.scene.image.Image;
import uet.oop.bomberman.BombermanGame;

import java.util.Random;

public class Item extends Entity {
    public static final long ITEM_TIME_LIMIT = 10000;
    public final int order;
    private int item;
    private long start = -1;
    private boolean end = false;

    private boolean powerup_wallpass = false;

    public Item(int x, int y, Image img, int order) {
        super(x, y, img);
        this.order = order;
    }

    public void setStart(long start) {
        this.start = start;
    }

    public void item() {
        if (order == 0) {
            powerup_wallpass = true;
            powerupWallpass(System.currentTimeMillis());
        }
    }

    public void randomItem() {
        Random random = new Random();
        item = random.nextInt(5);
    }

    public void powerupWallpass(long startTime) {
        if (start == -1) {
            start = startTime;
        }

        long endTime = System.currentTimeMillis();
        if (endTime - start >= ITEM_TIME_LIMIT) {
            powerup_wallpass = false;
            start = -1;
            end = true;
            BombermanGame.bomberman.setMoveItem(0);
        }

        if (powerup_wallpass) {
            System.out.println(endTime - start);
            BombermanGame.bomberman.setMoveItem(1);
        }
    }

    public void powerupBombpass() {

    }

    public boolean getEnd() {
        return this.end;
    }
    @Override
    public void update() {

    }
}
