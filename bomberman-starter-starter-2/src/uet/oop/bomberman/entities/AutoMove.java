package uet.oop.bomberman.entities;

import javafx.scene.image.Image;
import uet.oop.bomberman.entities.MoveEntity;

public class AutoMove extends MoveEntity {
    private boolean up = false;
    private boolean down = false;
    private boolean left = false;
    private boolean right = false;

    private int speed;
    private int start =5;

    private long timeNow = 0;
    private long timeAgo = 0;

    public static final long timeWay = 1000;

    public static enum move {
        UP(0), DOWN(1), LEFT(2), RIGHT(3), STOP(4);

        private final int value;
        move(int value) {
            this.value = value;
        }
    }


    public AutoMove(int x, int y, Image img, int speed) {
        super( x, y, img,speed);
        this.speed = speed;
    }

    @Override
    public boolean canMove(int way) {
        timeNow = System.currentTimeMillis();
        if (timeAgo == 0) {
            timeAgo = System.currentTimeMillis();
            start = 5;
        }
        if(timeNow - timeAgo >= timeWay) {
            timeAgo = timeNow;
            start = way;
            return true;
        }
        direction(start);
        return false;
    }

    public void direction(int way) {
        if (way == move.UP.value) {
            moveUp();
        }
        if (way == move.DOWN.value) {
            moveDown();
        }
        if (way == move.LEFT.value) {
            moveLeft();
        }
        if (way == move.RIGHT.value) {
            moveRight();
        }
    }

    @Override
    public void moveUp() {
        y -= speed;
    }

    @Override
    public void moveDown() {
        y += speed;
    }

    @Override
    public void moveLeft() {
        x -= speed;
    }

    @Override
    public void moveRight() {
        x += speed;
    }

    @Override
    public void update() {
    }
}
