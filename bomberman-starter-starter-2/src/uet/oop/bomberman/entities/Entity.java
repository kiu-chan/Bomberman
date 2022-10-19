package uet.oop.bomberman.entities;

import uet.oop.bomberman.graphics.Sprite;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

public abstract class Entity {
    //Tọa độ X tính từ góc trái trên trong Canvas
    protected int x;

    //Tọa độ Y tính từ góc trái trên trong Canvas
    protected int y;
    protected int w;
    protected int h;
    public void setX(int x) {
        this.x = x;
    }
    public void setY(int y) {
        this.y = y;
    }

    public void setW(int w) {
        this.w = w;
    }


    public void setH(int h) {
        this.h = h;
    }

    public int getX() {
        return x;
    }
    public int getY() {
        return y;
    }
    protected Image img;
    protected boolean remove = false;

    //Khởi tạo đối tượng, chuyển từ tọa độ đơn vị sang tọa độ trong canvas
    public Entity( int xUnit, int yUnit, Image img) {
        this.x = xUnit * Sprite.SCALED_SIZE;
        this.y = yUnit * Sprite.SCALED_SIZE;
        this.w = Sprite.SCALED_SIZE;
        this.h = Sprite.SCALED_SIZE;
        this.img = img;
    }
    public boolean getRemove() {
        return this.remove;
    }
    public void setRemove() {
        this.remove = true;
    }
    protected void setImg(Image img) {
        this.img = img;
    }


    public void render(GraphicsContext gc) {
        gc.drawImage(img, x, y);
    }
    public abstract void update();
}



