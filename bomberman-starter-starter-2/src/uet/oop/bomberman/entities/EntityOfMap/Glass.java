package uet.oop.bomberman.entities.EntityOfMap;

import uet.oop.bomberman.entities.Entity;
import uet.oop.bomberman.graphics.Sprite;
import uet.oop.bomberman.graphics.Images;

public class Glass extends Entity {
    private Images mapImage = new Images("/Images/Map_set1.png", 7, 1);
    public Glass(int x,int y) {
        super(x, y, null);
        mapImage.loadImage();
        this.img = mapImage.getList().get(0).getFxImage();
    }
    @Override
    public void update() {

    }
}
