package uet.oop.bomberman.graphics;

import javafx.scene.Scene;
import uet.oop.bomberman.entities.Enemy.Balloom;
import uet.oop.bomberman.entities.Collide;
import uet.oop.bomberman.entities.Entity;
import uet.oop.bomberman.graphics.Sprite;
import uet.oop.bomberman.graphics.SpriteSheet;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Images {
    private String path;
    private int x;
    private int y;
    private int width;
    private int height;
    private int[][] map = new int[100][100];
    SpriteSheet sheet;
    Images image;
    private List<Sprite> list = new ArrayList<>();
    private List<Entity> stillObjects = new ArrayList<>();
    public Images(String path, int x, int y) {
        this.path = path;
        this.x = x;
        this.y = y;
    }

    public void loadImage() {
        int k = 0;
        for (int i = 0; i < this.y; i++) {
            for (int j = 0; j < this.x; j++) {
                list.add(new Sprite(Sprite.DEFAULT_SIZE, j, i, new SpriteSheet(path, Math.max(this.x, this.y) * Sprite.DEFAULT_SIZE), 16, 16));
            }
        }
    }

    public void readMap(String path, Images image, int width, int height) {
        this.image = image;
        this.width = width;
        this.height = height;
        try {
            File file = new File(path);
            FileReader fileReader = new FileReader(file);

            BufferedReader reader = new BufferedReader(fileReader);

            for (int i = 0; i < height; i++) {
                String s;
                s = reader.readLine();
                int left = 0;
                int j = 0;
                while (left < s.length()) {
                    int right = left;
                    while (right < s.length() && s.charAt(right) != ' ') {
                        right++;
                    }
                    map[j][i] = Integer.parseInt(s.substring(left, right));
                    j++;
                    left = right + 1;
                }
            }

            /*for (int i = 0; i < HEIGHT ; i ++) {
                for (int j = 0; j  < WIDTH; j++) {
                    System.out.print(tileMap[i][j] + " ");
                }
                System.out.println();
            }*/

            reader.close();
        }
        catch (Exception e) {
            e.printStackTrace();
        }

    }
    public int convert2Dto1D(int row, int column) {
        return height * column + row;
    }
    public void createMap() {
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                Entity object = new Collide(i, j, image.getList().get(map[i][j]).getFxImage());
                stillObjects.add(object);
            }
        }
    }

    public void createEntity() {
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                if (map[i][j] != 0) {
                    Entity object = new Balloom(i, j, image.getList().get(map[i][j]).getFxImage());
                    stillObjects.add(object);
                }
            }
        }
    }

    public List<Sprite> getList() {
        return list;
    }

    public List<Entity> getStillObjects() {
        return stillObjects;
    }

    public int[][] getMap() {
        return map;
    }


    public void setMap(int i, int j, int value) {
        this.map[i][j] = value;
    }
}
//1 tuong 2 gach 0 la co