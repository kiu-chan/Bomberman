package uet.oop.bomberman;

import javafx.scene.control.Button;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import uet.oop.bomberman.entities.*;
import uet.oop.bomberman.entities.Interaction.Interactive;
import uet.oop.bomberman.graphics.Images;
import uet.oop.bomberman.graphics.Sprite;
import uet.oop.bomberman.entities.Menu;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;

public class BombermanGame extends Application {

    public static final int WIDTH = 31;
    public static final int HEIGHT = 13;


    private Canvas canvas = new Canvas(Sprite.SCALED_SIZE * WIDTH, Sprite.SCALED_SIZE * HEIGHT);
    public int level = 1;
    private GraphicsContext gc = canvas.getGraphicsContext2D();
    public boolean check_play = false;
    private int isPlay = 0;
    private boolean playMusic = true;
    private boolean loseGame = false;

    private long startLevel = 0;
    private static final long TIME_NEXT_LEVEL = 1500;
    private boolean nextLevel = false;
    private int cntAddTextHeart = 0;
    private int cntMenu = 0;
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
    private Text textHeart1;
    private List<Text> textList = new ArrayList<>();
    private Menu Menu = new Menu();

    private Button button;
    private Stage stage1;

    public static void main(String[] args) {
        Application.launch(BombermanGame.class);
    }

    @Override
    public void start(Stage stage) {
        player.loadImage();
        stage1 = stage;
        mainMenu(stage);
    }
    public void playGame(Stage stage) {
            Group root = new Group();
            root.getChildren().add(canvas);
            // Tao scene
            Scene scene = new Scene(root);
            // Them scene vao stage
            stage.setScene(scene);
            stage.show();
            //  audio.playAudioFull(Audio.audio.backgroundMusic.value);
         //   player.loadImage();
            bomberman = new Bomber(1, 1, player.getList().get(1).getFxImage(), 1);
            entities.add(bomberman);
            load();
            getBomberControl.getControl(scene);
            createTextHeart();
            root.getChildren().add(textHeart1);
            /*button = new Button();
            button.intersects(1000, 0, 100, 100);
            root.getChildren().add(button);*/
                AnimationTimer timer = new AnimationTimer() {
                    @Override
                    public void handle(long l) {
                        root.getChildren().remove(textHeart1);
                        createTextHeart();
                        root.getChildren().add(textHeart1);
                            render();
                            update();
                            if (loseGame && cntMenu <=1) {
                                endGame(stage);
                                ++cntMenu;
                        }
                        if (nextLevel) {
                            nextLevel(stage);
                        }
                    }
                };
                timer.start();
    }

    public void createTextHeart() {
        textHeart1 = new Text("Heart: " + bomberman.getHeart() + " ");
        textHeart1.setFont(Font.font(null, FontWeight.BOLD, 15));
        //Setting the color of the text
        textHeart1.setFill(Color.CRIMSON);
        //setting the position of the text
        textHeart1.setX(5);
        textHeart1.setY(30);
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

        getBomberControl.bomberDown = false;
        getBomberControl.bomberUp = false;
        getBomberControl.bomberRight = false;
        getBomberControl.bomberLeft = false;
        getBomberControl.bomberSpace = false;
    }

    public void update() {
        if (isPlay%2 == 0) {
            if (bomberman.getHeart() <= 0) {
                loseGame = true;
            }
            entities.forEach(Entity::update);
            interactive.itemHandling();
            interactive.collideWithEnemy(bomberman, entities);
            entities = interactive.monsterDead(bomberman, entities);
            listItem = interactive.removeItem(bomberman, listItem, entities);

            if (interactive.getSwapMap()) {
                this.level++;
                interactive.setSwapMap(false);
                bomberman.setPosition();
                clear();
                entities.add(bomberman);
                nextLevel = true;
            }
        }
    }

    public void render() {
        gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
        listPadding.forEach(g -> g.render(gc));
        listItem.forEach(g -> g.render(gc));
        stillObjects.forEach(g -> g.render(gc));
        entities.forEach(g -> g.render(gc));
    }

    public void mainMenu(Stage stage) {
        Group root = new Group();
        root.getChildren().add(Menu.getBackgroundMenu());
        root.getChildren().add(Menu.getPlayButton());
        root.getChildren().add(Menu.getInstructionButton());
        root.getChildren().add(Menu.getQuitButton());
        root.getChildren().add(Menu.getMuteVolumeButton());
        // Tao scene
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
        if (playMusic == true) {
            audio.playAudio(Audio.audio.backgroundMusic.value);
        }
        Menu.getMuteVolumeButton().setOnMouseClicked(mouseEvent -> {
            audio.playAudio(Audio.audio.buttonClick.value);
            audio.audioStopTime(Audio.audio.buttonClick.value, 70);
            playMusic = false;
            audio.stopAudio(Audio.audio.backgroundMusic.value);
        });
        Menu.getPlayButton().setOnMouseClicked(mouseEvent -> {
            audio.playAudio(Audio.audio.buttonClick.value);
            audio.audioStopTime(Audio.audio.buttonClick.value, 70);
            root.getChildren().clear();
            playGame(stage);
        });
        Menu.getInstructionButton().setOnMouseClicked(mouseEvent -> {
            audio.playAudio(Audio.audio.buttonClick.value);
            audio.audioStopTime(Audio.audio.buttonClick.value, 70);
            root.getChildren().clear();
            guideMenu(stage);
        });
        Menu.getQuitButton().setOnMouseClicked(mouseEvent -> {
            audio.playAudio(Audio.audio.buttonClick.value);
            audio.audioStopTime(Audio.audio.buttonClick.value, 70);
            root.getChildren().clear();
            stage.close();
        });
    }
    public void guideMenu(Stage stage) {
        Button backButton = new Button("Back");
        backButton.setMinSize(66,50);
        Group root = new Group();
        root.getChildren().add(Menu.getInstruction());
        root.getChildren().add(backButton);
        // Tao scene
        Scene scene = new Scene(root);
        // Them scene vao stage
        stage.setScene(scene);
        stage.show();
        backButton.setOnMouseClicked(mouseEvent -> {
            audio.playAudio(Audio.audio.buttonClick.value);
            audio.audioStopTime(Audio.audio.buttonClick.value, 70);
           root.getChildren().clear();
            mainMenu(stage);
        });
    }

    public void nextLevel(Stage stage) {
        Group root = new Group();
        Text text = new Text("Level " + this.level);
        text.setFont(Font.font(null, FontWeight.BOLD, 50));
        text.setFill(Color.WHITE);
        text.setX((WIDTH * Sprite.SCALED_SIZE)/2 - 150);
        text.setY((HEIGHT * Sprite.SCALED_SIZE)/2 + 20);
        root.getChildren().add(Menu.getLevel());
        root.getChildren().add(text);

        Scene scene = new Scene(root);

        stage.setScene(scene);
        stage.show();
        if (startLevel == 0) {
            startLevel = System.currentTimeMillis();
        }

        long endLevel = System.currentTimeMillis();

        if (endLevel - startLevel >= TIME_NEXT_LEVEL) {
            nextLevel = false;
            startLevel = 0;
            playGame(stage);
        }
    }
    public void endGame(Stage stage) {
        Group root = new Group();
        root.getChildren().add(Menu.getEndGameMenu());
        root.getChildren().add(Menu.getPlayAgainButton());
        root.getChildren().add(Menu.getQuitButton());
        Scene scene = new Scene(root);
        // Them scene vao stage
        stage.setScene(scene);
        stage.show();
        Menu.getPlayAgainButton().setOnMouseClicked(mouseEvent -> {
            clear();
            bomberman.setHeart(2);
            loseGame = false;
            cntMenu = 0;
            cntAddTextHeart = 0;
            audio.playAudio(Audio.audio.buttonClick.value);
            audio.audioStopTime(Audio.audio.buttonClick.value, 70);
            root.getChildren().clear();
            playGame(stage);
        });
        Menu.getQuitButton().setOnMouseClicked(mouseEvent -> {
            audio.playAudio(Audio.audio.buttonClick.value);
            audio.audioStopTime(Audio.audio.buttonClick.value, 70);
            stage.close();
        });
    }
}

