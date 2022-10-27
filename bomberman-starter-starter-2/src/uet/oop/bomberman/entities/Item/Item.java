package uet.oop.bomberman.entities.Item;

import javafx.scene.image.Image;

import uet.oop.bomberman.BombermanGame;
import uet.oop.bomberman.entities.Entity;
import uet.oop.bomberman.graphics.Sprite;

import java.util.Random;

public class Item extends Entity {
    public static final long ITEM_TIME_LIMIT = 5000;
    public static final int SPEED_UPDATE = 1;
    public int order;
    private int item;
    private int cntItem = 0;
    private long start = -1;
    private boolean end = false;

    private boolean powerup_wallpass = false;
    private boolean powerup_speed = false;

    public Item(int x, int y, Image img, int order) {
        super(x, y, img);
        this.order = order;
    }


    public void setStart(long start) {
        this.start = start;
    }

    public boolean timeItem(long startTime) {
        if (start == -1) {
            start = startTime;
        }

        long endTime = System.currentTimeMillis();

        //System.out.println(endTime - start);
        if (endTime - start >= ITEM_TIME_LIMIT) {
            start = -1;
            end = true;
            return true;
        }

        return false;
    }

    public void item() {
        timeItem(System.currentTimeMillis());
        if (order == Sprite.powerup_wallpass) {
            powerup_wallpass = true;
            powerupWallpass();
        }

        if(order == Sprite.powerup_bombpass) {

        }

        if (order == Sprite.powerup_detonator) {

        }

        if (order == Sprite.powerup_speed) {
            powerup_speed = true;
            powerupSpeed();
        }

        if (order == Sprite.powerup_flamepass) {

        }

        if (order == Sprite.random_item) {
            randomItem();
        }
    }

    public void randomItem() {
        Random random = new Random();
        order = random.nextInt(5);
        item();
    }

    public void powerupWallpass() {
        if (end) {
            powerup_wallpass = false;
            BombermanGame.bomberman.setMoveItem(0);
        }
        if (powerup_wallpass) {
            BombermanGame.bomberman.setMoveItem(1);
        }
    }

    public void powerupBombpass() {

    }

    public void powerupSpeed() {
        if (end) {
            powerup_speed = false;
            BombermanGame.bomberman.setBomberSpeed(-SPEED_UPDATE);
        }
        if (cntItem == 0) {
            cntItem++;
            BombermanGame.bomberman.setBomberSpeed(SPEED_UPDATE);//System.out.println(1);
        }
    }

    public void portal() {

    }

    public boolean getEnd() {
        return this.end;
    }

    public int getOrder() {
        return this.order;
    }
    @Override
    public void update() {

    }
}