package uet.oop.bomberman;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import uet.oop.bomberman.entities.*;
import uet.oop.bomberman.graphics.Images;
import uet.oop.bomberman.graphics.Sprite;
import uet.oop.bomberman.getBomberControl;

import java.io.*;
import java.nio.file.ReadOnlyFileSystemException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.Scanner;
import java.util.logging.Level;

public class BombermanGame extends Application {
    
    public static final int WIDTH = 31;
    public static final int HEIGHT = 13;

    private GraphicsContext gc;
    private Canvas canvas;
    private List<Entity> entities = new ArrayList<>();
    private List<Entity> stillObjects = new ArrayList<>();
    private Images map = new Images("/map/Map_set.png", 7, 1);
    public static Images player = new Images("/map/player1.png", 3, 5);
    private int[][] tileMap = new int[100][100];

    public static void main(String[] args) {
        Application.launch(BombermanGame.class);
    }

    @Override
    public void start(Stage stage) {
        // Tao Canvas
        canvas = new Canvas(Sprite.SCALED_SIZE * WIDTH, Sprite.SCALED_SIZE * HEIGHT);
        gc = canvas.getGraphicsContext2D();

        // Tao root container
        Group root = new Group();
        root.getChildren().add(canvas);

        // Tao scene
        Scene scene = new Scene(root);

        // Them scene vao stage
        stage.setScene(scene);
        stage.show();

        AnimationTimer timer = new AnimationTimer() {
            @Override
            public void handle(long l) {
                render();
                update();
            }
        };
        timer.start();
        player.loadImage();
        Entity bomberman = new Bomber(1, 1, player.getList().get(0).getFxImage(), 1);
        entities.add(bomberman);
        getBomberControl.getControl(scene);
        map.loadImage();
        createMap("bomberman-starter-starter-2/res/map/Tile_map.txt");

        /*entities.add(bomberman);
        entities.add(bomberman2);*/
    }

    public void createMap(String path) {
        try {
            File file = new File(path);
            FileReader fileReader = new FileReader(file);

            BufferedReader reader = new BufferedReader(fileReader);

            for (int i = 0; i < HEIGHT; i++) {
                String s;
                s = reader.readLine();
                int left = 0;
                int j = 0;
                while (left < s.length()) {
                    int right = left;
                    while (right < s.length() && s.charAt(right) != ' ') {
                        right++;
                    }
                    tileMap[j][i] = Integer.parseInt(s.substring(left, right));
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

        for (int i = 0; i < WIDTH; i++) {
            for (int j = 0; j < HEIGHT; j++) {
                Entity object = new Collide(i, j, map.getList().get(tileMap[i][j]).getFxImage());
                stillObjects.add(object);
            }
        }
    }

    public void update() {
        entities.forEach(Entity::update);
    }

    public void render() {
        gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
        stillObjects.forEach(g -> g.render(gc));
        entities.forEach(g -> g.render(gc));
    }
}
