package uet.oop.bomberman.entities;

import uet.oop.bomberman.BombermanGame;
import uet.oop.bomberman.getBomberControl;
import uet.oop.bomberman.graphics.Sprite;
import uet.oop.bomberman.entities.Bomb.Bomb;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

import java.util.ArrayList;
import java.util.List;

public class Bomber extends MoveEntity {
    public static boolean isDead = false;
    private final int maxAnimation = 20;
    protected int bomberSpeed = 1;
    private int cntLeft = 1;
    private int cntRight = 1;
    private int cntUp = 1;
    private int cntDown = 1;
    private int maxBomb = 4;
    public int act = 0;
    //di chuyển sau khi lấy được item
    public int moveItem = 0;
    public enum status {
        STOP(0), LEFT(1), RIGHT(2), UP(3), DOWN(4);

        private final int value;
        status(int value) {
            this.value = value;
        }
    }
    public static int bombRadius = 1;
    private List<Bomb> bombs = new ArrayList<>();
    private Collision collision = new Collision();

    public Bomber(int x, int y, Image img, int speed) {
        super( x, y, img,speed);
        System.out.println("toa do x:"+ x + "toa do y:" + y);
        //chênh lệch chiều rộng
        setW(Sprite.SCALED_SIZE - 7);
        //chênh lệch chiều cao
        setH(Sprite.SCALED_SIZE - 6);

    }

    @Override
    public void update() {
        this.moveBomber();
        this.addBomb();
        for (int i = 0; i < bombs.size(); i++) {
            bombs.get(i).update();
            if (bombs.get(i).getRemove()) {
                //System.out.println("xoa bom" + bombs.get(i).getX()+"-"+bombs.get(i).getY());
                bombs.remove(i);
            }
        }
    }
    private void addBomb() {
        if (getBomberControl.bomberSpace == true ) {
            int xBomber = (this.getX() + 10) / 32;
            int yBomber = (this.getY() + 10) / 32;
            Bomb newBom = new Bomb(xBomber, yBomber, Sprite.bomb.getFxImage());
            if (newBom.isHaveBomb(bombs) == false) {
                bombs.add(newBom);
            }
            newBom.makeExplotion();
        }
    }

    /*
    1: di len --
    2:di xuong--
    3:sang trai ----
    -4:sang phai
     */
    public static enum move {
        UP(1), DOWN(2), LEFT(3), RIGHT(4);

        private final int value;
        move(int value) {
            this.value = value;
        }
    }

    @Override
    public boolean canMove(int way) {
        if (moveItem == 1) {
            return true;
        }
        if (way == move.UP.value) {
            return !collision.CheckMapCollision(x, y - speed, w, h, BombermanGame.map.getMap());
        }

        if (way == move.DOWN.value) {
             return !collision.CheckMapCollision(x, y + speed, w, h, BombermanGame.map.getMap());
        }

        if (way == move.LEFT.value) {
             return !collision.CheckMapCollision(x - speed, y, w, h, BombermanGame.map.getMap());
        }

        if (way == move.RIGHT.value) {
             return !collision.CheckMapCollision(x + speed, y, w, h, BombermanGame.map.getMap());
        }

        return true;
    }
    public void moveBomber() {
        act = status.STOP.value;;
        if (getBomberControl.bomberLeft) {
            getBomberControl.bomberDown = false;
            getBomberControl.bomberRight = false;
            getBomberControl.bomberUp = false;
            act = status.LEFT.value;
            this.moveLeft();
        }
        if (getBomberControl.bomberRight) {
            getBomberControl.bomberDown = false;
            getBomberControl.bomberLeft = false;
            getBomberControl.bomberUp = false;
            act = status.RIGHT.value;
            this.moveRight();
        }
        if (getBomberControl.bomberUp) {
            getBomberControl.bomberDown = false;
            getBomberControl.bomberRight = false;
            getBomberControl.bomberLeft = false;
            act = status.UP.value;
            this.moveUp();
        }
        if (getBomberControl.bomberDown) {
            getBomberControl.bomberLeft = false;
            getBomberControl.bomberRight = false;
            getBomberControl.bomberUp = false;
            act = status.DOWN.value;
            this.moveDown();
        }
    }

    @Override
    public void moveUp() {

        cntDown = 1;
        cntLeft = 1;
        cntRight =1;
        if (cntUp < maxAnimation) cntUp +=1;
        else cntUp = 1;
        img = Sprite.movingSprite(BombermanGame.player.getList().get(0),BombermanGame.player.getList().get(1),
                BombermanGame.player.getList().get(2),cntUp,maxAnimation).getFxImage();
        if (canMove(1)) {
            y -= bomberSpeed;
            if (this.y/32 < 1) {
                //System.out.println("vao tuong tren");
                this.setY(32);
            }
        }
    }
    public void moveDown() {
        cntUp = 1;
        cntLeft = 1;
        cntRight =1;
        if (cntDown < maxAnimation) cntDown ++;
        else cntDown = 1;
        img = Sprite.movingSprite(BombermanGame.player.getList().get(6),BombermanGame.player.getList().get(7),
                BombermanGame.player.getList().get(8),cntDown,maxAnimation).getFxImage();
        if (canMove(2)) {
            y += bomberSpeed;
            if (this.y/32 > 10) {
                //System.out.println("vao tuong duoi");
                this.setY(32*11);
            }
        }
    }
    public void moveLeft() {
        cntDown = 1;
        cntUp = 1;
        cntRight =1;
        if (cntLeft < maxAnimation) cntLeft +=1;
        else cntLeft = 1;
        img = Sprite.movingSprite(BombermanGame.player.getList().get(9),BombermanGame.player.getList().get(10),
                BombermanGame.player.getList().get(11),cntLeft,maxAnimation).getFxImage();
        if (canMove(3)) {
            x -= bomberSpeed;
            if (this.x/32 < 1) {
                //System.out.println("vao tuong trai");
                this.setX(32);
            }
        }
    }
    public void moveRight() {
        cntDown = 1;
        cntLeft = 1;
        cntUp = 1;
        if (cntRight < maxAnimation) cntRight += 1;
        else cntRight = 1;
        img = Sprite.movingSprite(BombermanGame.player.getList().get(3),BombermanGame.player.getList().get(4),
                BombermanGame.player.getList().get(5),cntRight,maxAnimation).getFxImage();
        if (canMove(4)) {
            x += bomberSpeed;
            if (this.x / 32 > 28) {
                //System.out.println("vao tuong phai");
                this.setX(32*29);
            }
        }
    }

    public void setBomberSpeed(int speed) {
        this.bomberSpeed += speed;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
    public void setMoveItem(int item) {
        this.moveItem = item;
    }
    @Override
    public void render(GraphicsContext gc) {
        super.render(gc);
        for (int i = 0; i < bombs.size(); i++) {
            bombs.get(i).render(gc);
        }
    }
}
