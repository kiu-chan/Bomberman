package uet.oop.bomberman.entities;
import javafx.scene.image.Image;
import javafx.scene.image.PixelReader;
import uet.oop.bomberman.BombermanGame;
import uet.oop.bomberman.graphics.Sprite;

import java.awt.*;
import java.nio.channels.spi.SelectorProvider;
import java.util.ArrayList;
import java.util.HashSet;

public abstract class MoveEntity extends Entity {
    protected int speed;
    protected int left = 0;
    protected int right = 0;
    protected int up = 0;
    protected int down = 0;
    protected int dieTime = 0;
    protected boolean isDead = false;
    MoveEntity(int x, int y, Image img, int speed) {
        super(x,y,img);
        this.speed = speed;
    }
    public abstract void moveUp();
    public abstract void moveDown();
    public abstract void moveLeft();
    public abstract void moveRight();
    public abstract boolean canMove(int way);
    public boolean checkCollision(Entity entity) {
        if (this.x <= entity.x + 32 && this.x >= entity.x) {
            if (this.y <= entity.y + 32 && this.x >= entity.y) return true;
            if (this.y + 32 <= entity.y + 32 && this.y +32 >= entity.y) return true;
        }
        if (this.x +32 <= entity.x + 32 && this.x +32 >= entity.x) {
            if (this.y <= entity.y + 32 && this.x >= entity.y) return true;
            if (this.y + 32 <= entity.y + 32 && this.y +32 >= entity.y) return true;
        }
        return false;
    }
    /*
    1: di len --
    2:di xuong--
    3:sang trai ----
    -4:sang phai
     */
}
