package uet.oop.bomberman.entities;
import java.util.ArrayList;
import java.util.List;
import javafx.scene.SnapshotParameters;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import uet.oop.bomberman.BombermanGame;
import uet.oop.bomberman.getBomberControl;
import uet.oop.bomberman.graphics.Images;
import uet.oop.bomberman.graphics.Sprite;
import uet.oop.bomberman.graphics.SpriteSheet;
import uet.oop.bomberman.entities.Bomb.Bomb;
public class Bomber extends MoveEntity {
    private final int maxAnimation = 8;
    protected int bomberSpeed = 1;
    private int cntLeft = 1;
    private int cntRight = 1;
    private int cntUp = 1;
    private int cntDown = 1;
    private int maxBomb = 4;
    private List<Bomb> bombs = new ArrayList<>();

    public Bomber(int x, int y, Image img, int speed) {
        super( x, y, img,speed);
    }

    @Override
    public void update() {
        this.moveBomber();
        this.addBomb();
        for (int i = 0; i < bombs.size(); i++) {
            bombs.get(i).update();
            if (bombs.get(i).getRemove()) {
                System.out.println("xoa bom" + bombs.get(i).getX()+"-"+bombs.get(i).getY());
                bombs.remove(i);
            }
        }
    }
    private void addBomb() {
        if (getBomberControl.bomberSpace == true) {
            int xBomber = this.getX()/32;
            int yBomber = this.getY()/32;
            Bomb newBom = new Bomb(xBomber, yBomber, Sprite.balloom_left3.getFxImage());
            bombs.add(newBom);
        }
    }
    /*
    1: di len --
    2:di xuong--
    3:sang trai ----
    -4:sang phai
     */
    @Override
    public boolean canMove(int way) {
        int k = BombermanGame.map.convert2Dto1D(this.x/32,this.y/32);
        if (way == 1) {
            if (BombermanGame.map.getMap()[this.x/32][this.y/32-1] != 0 &&
                    this.checkCollision(BombermanGame.map.getStillObjects().get(k-1))) {
                System.out.println("va cham tren roi");
                return false;
            }
            else
                return true;
        }
        if (way == 2) {
            if (BombermanGame.map.getMap()[this.x/32][this.y/32+1] != 0  )
                return false;
            else
                return true;
        }
        if (way == 3) {
            if (BombermanGame.map.getMap()[this.x/32-1][this.y/32] != 0  )
                return false;
            else
                return true;
        }
        if (way == 4) {
            if (BombermanGame.map.getMap()[this.x/32+1][this.y/32] != 0  )
                return false;
            else
                return true;
        }
        return true;
    }
    public void moveBomber() {
        if (getBomberControl.bomberLeft) {
            this.moveLeft();
        }
        if (getBomberControl.bomberRight) {
            this.moveRight();
        }
        if (getBomberControl.bomberUp) {
            this.moveUp();
        }
        if (getBomberControl.bomberDown) {
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
        }
    }
    public void moveRight() {
        cntDown = 1;
        cntLeft = 1;
        cntUp =1;
        if (cntRight < maxAnimation) cntRight +=1;
        else cntRight = 1;
        img = Sprite.movingSprite(BombermanGame.player.getList().get(3),BombermanGame.player.getList().get(4),
                BombermanGame.player.getList().get(5),cntRight,maxAnimation).getFxImage();
        if (canMove(4)) {
            x += bomberSpeed;
        }
    }
    @Override
    public void render(GraphicsContext gc) {
        super.render(gc);
        for (int i = 0; i < bombs.size(); i++) {
            bombs.get(i).render(gc);
        }
    }
}