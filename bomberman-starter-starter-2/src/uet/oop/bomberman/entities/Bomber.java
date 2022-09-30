package uet.oop.bomberman.entities;

import javafx.scene.SnapshotParameters;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;

public class Bomber extends MoveEntity {
    protected int bomberSpeed = 1;
    public Bomber(int x, int y, Image img, int speed) {
        super( x, y, img,speed);
    }

    @Override
    public void update() {
        this.moveDown();
        this.moveRight();
    }

    @Override
    public boolean canMove(int way) {
        return true;
    }

    @Override
    public void moveUp() {
        if (canMove(1)) {
            y -= bomberSpeed;
        }
    }
    public void moveDown() {
        if (canMove(2)) {
            y += bomberSpeed;
        }
    }
    public void moveLeft() {
        if (canMove(3)) {
            x -= bomberSpeed;
        }
    }
    public void moveRight() {
        if (canMove(4)) {
            x += bomberSpeed;
        }
    }
}
