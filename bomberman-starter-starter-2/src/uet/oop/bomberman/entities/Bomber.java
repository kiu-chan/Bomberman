package uet.oop.bomberman.entities;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import uet.oop.bomberman.BombermanGame;
import uet.oop.bomberman.getBomberControl;
import uet.oop.bomberman.graphics.Sprite;
import uet.oop.bomberman.entities.Bomb.Bomb;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class Bomber extends MoveEntity {
    public static boolean isDead = false;
    private final int maxAnimation = 20;
    protected int bomberSpeed = 2;
    private int cntLeft = 1;
    private int cntRight = 1;
    private int cntUp = 1;
    private int cntDown = 1;
    private int maxBomb = 2;
    private Collision collision = new Collision();

    public List<Entity> listBom = new ArrayList<>();
    public int act = 0;

    public long imageTimeNow = 0;
    public long imageTimeAgo = 0;
    public static final long TIME_IMG = 100;

    private int swapImg = 0;

    //di chuyển sau khi lấy được item
    public int moveItem = 0;
    /**
     * hướng nhìn
     */
    public enum status {
        STOP(0), LEFT(1), RIGHT(2), UP(3), DOWN(4);

        private final int value;
        status(int value) {
            this.value = value;
        }
    }

    public static int bombRadius = 1;
    private List<Bomb> bombs = new ArrayList<>();

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
        if (!isDead) {
            this.moveBomber();
            this.addBomb();
            for (int i = 0; i < bombs.size(); i++) {
                bombs.get(i).update();
                if (this.collision.CheckCollision(this, bombs.get(i)) && bombs.get(i).isExplotion()) {
                    isDead = true;
                    System.out.println("cham bom roi");
                }
                for (int j = 0; j < bombs.get(i).getExplotionList().size(); j++) {
                    if (collision.CheckCollision(bombs.get(i).getExplotionList().get(j), this) && bombs.get(i).isExplotion()) {
                        Bomber.isDead = true;
                        System.out.println("cham vu no roi ");
                        System.out.println(isDead);
                    }
                }
                if (bombs.get(i).getRemove()) {
                    bombs.remove(i);
                }
            }
        } else {
            img = Sprite.movingSprite(Sprite.player_dead1, Sprite.player_dead2, Sprite.player_dead3, 30,60).getFxImage();
            for (int i = 0; i < bombs.size(); i++) {
                bombs.get(i).update();
                if (bombs.get(i).getRemove()) {
                    bombs.remove(i);
                }
            }
        }
    }

    private void addBomb() {
        if (getBomberControl.bomberSpace == true && bombs.size() < maxBomb ) {
            int xBomber = (this.getX() + 10) / 32;
            int yBomber = (this.getY() + 10) / 32;
            Bomb newBom = new Bomb(xBomber, yBomber, Sprite.bomb.getFxImage());
            if (newBom.isHaveBomb(bombs) == false) {
                bombs.add(newBom);
                newBom.makeExplotion(1);
                newBom.makeExplotion(2);
                newBom.makeExplotion(3);
                newBom.makeExplotion(4);
                listBom.addAll(newBom.getExplotionList());
            }
        }
    }

    public List<Entity> getListBom() {
        return this.listBom;
    }
    public void remoteListBom() {
        this.listBom.clear();
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
        act = status.STOP.value;
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
        //Audio audio = new Audio("audio/Click.mp3");
        //audio.playAudio();

        cntDown = 1;
        cntLeft = 1;
        cntRight =1;
        if (cntUp < maxAnimation) cntUp += 1;
        else cntUp = 1;
        moveIMG();
        if (canMove(1)) {
            y -= bomberSpeed;
           /* if (this.y/32 < 1) {
                //System.out.println("vao tuong tren");
                this.setY(32);
            }*/
        }
    }

    public void moveDown() {
        cntUp = 1;
        cntLeft = 1;
        cntRight =1;
        if (cntDown < maxAnimation) cntDown ++;
        else cntDown = 1;
        moveIMG();
        if (canMove(2)) {
            y += bomberSpeed;
          /* if (this.y/32 > 10) {
                //System.out.println("vao tuong duoi");
                this.setY(32*11);
            }*/
        }
    }
    public void moveLeft() {
        cntDown = 1;
        cntUp = 1;
        cntRight =1;
        if (cntLeft < maxAnimation) cntLeft +=1;
        else cntLeft = 1;
        moveIMG();
        if (canMove(3)) {
            x -= bomberSpeed;
         /*   if (this.x/32 < 1) {
                //System.out.println("vao tuong trai");
                this.setX(32);
            }*/
        }
    }
    public void moveRight() {
        cntDown = 1;
        cntLeft = 1;
        cntUp = 1;
        if (cntRight < maxAnimation) cntRight += 1;
        else cntRight = 1;
        moveIMG();
        if (canMove(4)) {
            x += bomberSpeed;
          /*  if (this.x / 32 > 28) {
                //System.out.println("vao tuong phai");
                this.setX(32*29);
            }*/
        }
    }

    public void moveIMG() {
        imageTimeNow = System.currentTimeMillis();
        if (imageTimeNow - imageTimeAgo > TIME_IMG) {
            swapImg++;
            imageTimeAgo = System.currentTimeMillis();
        }
        if (swapImg >= 2)
            swapImg = 0;
        if (act == status.LEFT.value)
            img = BombermanGame.player.getList().get(swapImg + 9).getFxImage();
        if (act == status.RIGHT.value)
            img = BombermanGame.player.getList().get(swapImg + 3).getFxImage();
        if (act == status.UP.value)
            img = BombermanGame.player.getList().get(swapImg).getFxImage();
        if (act == status.DOWN.value)
            img = BombermanGame.player.getList().get(swapImg + 6).getFxImage();
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
