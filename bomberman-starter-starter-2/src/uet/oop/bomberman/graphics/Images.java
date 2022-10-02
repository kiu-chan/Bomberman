package uet.oop.bomberman.graphics;

import javafx.scene.Scene;
import uet.oop.bomberman.graphics.Sprite;
import uet.oop.bomberman.graphics.SpriteSheet;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Images {
    private String path;
    private int x;
    private int y;
    public Images(String path, int x, int y) {
        this.path = path;
        this.x = x;
        this.y = y;
    }

    SpriteSheet sheet;
    private List<Sprite> list = new ArrayList<>();
    public void loadImage() {
        int k = 0;
        for (int i = 0; i < this.y; i++) {
            for (int j = 0; j < this.x; j++) {
                list.add(new Sprite(Sprite.DEFAULT_SIZE, j, i, new SpriteSheet(path, Math.max(this.x, this.y) * Sprite.DEFAULT_SIZE), 16, 16));
            }
        }
    }

    public List<Sprite> getList() {
        return list;
    }
}
