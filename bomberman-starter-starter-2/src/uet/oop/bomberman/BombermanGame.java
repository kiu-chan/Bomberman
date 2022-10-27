package uet.oop.bomberman;

import javafx.scene.media.AudioClip;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import uet.oop.bomberman.entities.*;
import uet.oop.bomberman.entities.Interaction.Interactive;
import uet.oop.bomberman.graphics.Images;
import uet.oop.bomberman.graphics.Sprite;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.stage.Stage;
import javafx.fxml.FXML;

import java.io.File;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class BombermanGame extends Application {

    public static final int WIDTH = 31;
    public static final int HEIGHT = 13;

    private GraphicsContext gc;
    private Canvas canvas;
    public int level = 1;

    public boolean check_play = false;
    public boolean check_pause = false;

    public static Audio audio = new Audio();

    private Interactive interactive = new Interactive();
    private List<Entity> entities = new ArrayList<>();
    public static List<Entity> stillObjects = new ArrayList<>();
    private List<Entity> listItem = new ArrayList<>();
    private List<Entity> listPadding = new ArrayList<>();
    public static Images map = new Images("/Images/Map_set1.png", 7, 1);
    public static Images player = new Images("/Player/Player2.png", 3, 5);
    public static Images monster = new Images("/Images/monster.png", 9, 8);
    public static Images item = new Images("/Images/item.png", 4, 4);
    //lớp đệm của map như cỏ
    public static Images padding = new Images("/Images/Padding.png", 1, 1);

    public static Bomber bomberman;

    public static final String Padding = "";
    public static final String Map = "bomberman-starter-starter-2/res/TileMap/Map";
    public static final String mapMonster = "bomberman-starter-starter-2/res/TileMap/Tile_monster";
    public static final String mapItem = "bomberman-starter-starter-2/res/TileMap/Tile_item.txt";
    private Text textHeart;
    private List <Text> textList = new ArrayList<>();

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
                        if (check_play) {
                            clear();

                            //audio.stopAudio(Audio.audio.backgroundMusic.value);

                            playGame(scene);
                            check_play = false;
                        }
                }
            };

            timer.start();

            playGame(scene);

    }

    public void playGame(Scene scene) {
        audio.playAudioFull(Audio.audio.backgroundMusic.value);

        player.loadImage();
        bomberman = new Bomber(1, 1, player.getList().get(1).getFxImage(), 4);
        entities.add(bomberman);
        getBomberControl.getControl(scene);
        load();
    }

    public void createText() {
        textHeart = new Text("Heart:" + bomberman.getHeart());
        textHeart.setFont(Font.font(null, FontWeight.BOLD, 15));

        //Setting the color of the text
        textHeart.setFill(Color.CRIMSON);

        //setting the position of the text
        textHeart.setX(100);
        textHeart.setY(500);
    }

    public List<Entity> updateEntity() {
        List<Entity> list = new ArrayList<>();
        list.add(bomberman);
        list.addAll(monster.getStillObjects());
        return list;
    }

    public void load() {
        padding.loadImage();
        padding.createPadding(padding, WIDTH, HEIGHT);
        listPadding.addAll(padding.getStillObjects()); //tạm thời lấy mỗi cỏ

        map.loadImage();
        map.readMap(Map + this.level + ".txt", map, WIDTH, HEIGHT);
        map.createMap();
        stillObjects.addAll(map.getStillObjects());

        item.loadImage();
        //item.readMap(mapItem, item, WIDTH, HEIGHT);
        item.randomItem(mapItem, item, WIDTH, HEIGHT);
        item.createItem();
        listItem.addAll(item.getStillObjects());

        monster.loadImage();
        monster.readMap(mapMonster + this.level + ".txt", monster, WIDTH, HEIGHT);
        monster.createEntity();
        entities.addAll(monster.getStillObjects());
    }

    public void clear() {
        stillObjects.clear();
        listItem.clear();
        entities.clear();

        map.clearMap();
        map.clearStillObjects();
        map.clearList();

        item.clearMap();
        item.clearStillObjects();
        item.clearList();

        monster.clearMap();
        monster.clearStillObjects();
        monster.clearList();
    }


    public void update() {
        //entities = updateEntity();
        if (!check_pause) {
            entities.forEach(Entity::update);
            interactive.itemHandling();
            interactive.collideWithEnemy(bomberman,entities);
            entities = interactive.monsterDead(bomberman, entities);
            listItem = interactive.removeItem(bomberman, listItem, entities);
            if (interactive.getSwapMap()) {
                this.level++;
                System.out.println(this.level);
                interactive.setSwapMap(false);
                bomberman.setPosition();
                clear();
                entities.add(bomberman);
                load();
            }
        }

        /*if (getBomberControl.bomberSpace)
            check_pause = !check_pause;*/
    }

    public void render() {
        gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
        listPadding.forEach(g -> g.render(gc));
        listItem.forEach(g -> g.render(gc));
        stillObjects.forEach(g -> g.render(gc));
        entities.forEach(g -> g.render(gc));
    }
}
