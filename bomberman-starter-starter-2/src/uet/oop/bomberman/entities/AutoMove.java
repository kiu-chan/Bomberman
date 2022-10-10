package uet.oop.bomberman.entities;

import javafx.scene.image.Image;
import uet.oop.bomberman.BombermanGame;
import uet.oop.bomberman.entities.MoveEntity;
import uet.oop.bomberman.graphics.Sprite;
import uet.oop.bomberman.BombermanGame;

import java.util.ArrayList;
import java.util.List;

public class AutoMove extends MoveEntity {
    private boolean up = false;
    private boolean down = false;
    private boolean left = false;
    private boolean right = false;

    private int speed;
    private int start = 5;

    private long imageTimeAgo = 0;
    private long imageTimeNow = 0;
    private long timeImg = 300;
    private int swapImg = 0;

    private long timeNow = 0;
    private long timeAgo = 0;

    public static final long timeWay = 500;

    private int startImg;
    private int checkView = 1;

    private Collision collision = new Collision();

    public static enum move {
        UP(0), DOWN(1), LEFT(2), RIGHT(3), STOP(4);

        private final int value;
        move(int value) {
            this.value = value;
        }
    }

    public static enum view {
        LEFT(-1), RIGHT(1), DIED(0);

        private final int value;
        view(int value) {
            this.value = value;
        }
    }

    public AutoMove(int x, int y, Image img, int speed, int startImg) {
        super( x, y, img,speed);
        this.speed = speed;
        this.startImg = startImg;
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
            if (!collision.CheckMapCollision(x, y - speed, BombermanGame.map.getMap()))
                moveUp();
        }

        if (way == move.DOWN.value) {
            if (!collision.CheckMapCollision(x, y + speed, BombermanGame.map.getMap()))
                moveDown();
        }

        if (way == move.LEFT.value) {
            if (!collision.CheckMapCollision(x - speed, y, BombermanGame.map.getMap()))
                moveLeft();
            checkView = view.LEFT.value;
        }

        if (way == move.RIGHT.value) {
            if (!collision.CheckMapCollision(x + speed, y, BombermanGame.map.getMap()))
                moveRight();
            checkView = view.RIGHT.value;
        }

        if (way == move.STOP.value) {
            moveStop();
        }

        moveIMG();
    }

    public void moveIMG() {
        imageTimeNow = System.currentTimeMillis();
        if (imageTimeNow - imageTimeAgo > timeImg) {
            swapImg++;
            imageTimeAgo = System.currentTimeMillis();
        }
        if (swapImg >= 2)
            swapImg = 0;
        if (checkView == view.LEFT.value)
            img = BombermanGame.monster.getList().get(swapImg + startImg).getFxImage();
        else
            img = BombermanGame.monster.getList().get(swapImg + startImg + 4).getFxImage();
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

    public void moveStop() {

    }

    @Override
    public void update() {
    }
}
