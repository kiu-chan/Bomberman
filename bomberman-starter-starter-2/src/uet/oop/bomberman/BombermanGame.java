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

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class BombermanGame extends Application {

    public static final int WIDTH = 31;
    public static final int HEIGHT = 13;
    private boolean win = false;
    private static final int MAX_LEVEL = 1;


    private Canvas canvas = new Canvas(Sprite.SCALED_SIZE * WIDTH, Sprite.SCALED_SIZE * HEIGHT);
    public int level = 0;
    private GraphicsContext gc = canvas.getGraphicsContext2D();
    public boolean check_play = false;
    private int isPlay = 0;
    private boolean playMusic = true;
    private boolean loseGame = false;

    private long startLevel = 0;
    private static final long TIME_NEXT_LEVEL = 1500;
    private boolean canNextLevel = false;
    private int cntDownTime = 10000;
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
    private Text textHeart2;
    private int cntTextHeart = 0;
    private List<Text> textList = new ArrayList<>();
    private Menu Menu = new Menu();
    AnimationTimer timer;

    // private Stage stage1;

    public static void main(String[] args) {
        Application.launch(BombermanGame.class);
    }

    @Override
    public void start(Stage stage) {
        //   stage1 = stage;
        mainMenu(stage);
    }
    public void playGame(Stage stage) {
        clear();
        Group root = new Group();
        root.getChildren().add(canvas);
        // Tao scene
        Scene scene = new Scene(root);
        // Them scene vao stage
        stage.setScene(scene);
        stage.show();
        player.loadImage();
        bomberman = new Bomber(1, 1, player.getList().get(1).getFxImage(), 2);
        entities.add(bomberman);
        try {
            load();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        bomberman.setHeart(2);
        cntTextHeart = 0;
        getBomberControl.getControl(scene);
        createTextHeart();
        root.getChildren().add(Menu.getGbPlay());
        root.getChildren().add(Menu.getHeartPlayer());
        root.getChildren().add(textHeart2);
        timer = new AnimationTimer() {
            @Override
            public void handle(long l) {
                if (bomberman.getHeart() == 1) {
                    root.getChildren().remove(textHeart2);
                    if (cntTextHeart < 1) {
                        ++cntTextHeart;
                        root.getChildren().add(textHeart1);
                    }
                }
                if (getBomberControl.clearEnemy) {
                    for (int i = 1; i < entities.size(); i++) {
                        entities.remove(i);
                    }
                }
                render();
                update();
                if (loseGame) {
                    root.getChildren().clear();
                    audio.stopAudio(Audio.audio.backgroundMusic.value);
                    audio.playAudio(Audio.audio.gameOver.value);
                    timer.stop();
                    endGame(stage);
                }
                if (canNextLevel && win == false) {
                    nextLevel(stage);
                }
                if (win == true || level > MAX_LEVEL) {
                    winGame(stage);
                    timer.stop();
                }
                if (canNextLevel) {
                    //timer.stop();
                    nextLevel(stage);
                }
            }
        };
        timer.start();
    }

    public void createTextHeart() {
        textHeart1 = new Text(" : 1" );
        textHeart1.setFont(Font.font(null, FontWeight.BOLD, 15));
        //Setting the color of the text
        textHeart1.setFill(Color.CRIMSON);
        //setting the position of the text
        textHeart1.setX(30);
        textHeart1.setY(25);
        textHeart2 = new Text(" : 2");
        textHeart2.setFont(Font.font(null, FontWeight.BOLD, 15));
        //Setting the color of the text
        textHeart2.setFill(Color.CRIMSON);
        //setting the position of the text
        textHeart2.setX(30);
        textHeart2.setY(25);

    }

    public List<Entity> updateEntity() {
        List<Entity> list = new ArrayList<>();
        list.add(bomberman);
        list.addAll(monster.getStillObjects());
        return list;
    }

    public void load() throws FileNotFoundException {
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
                ++this.level;
                if (this.level > MAX_LEVEL) {
                    win = true;
                }
                interactive.setSwapMap(false);
                bomberman.setPosition();
                clear();
                //  entities.add(bomberman);
                canNextLevel = true;
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
      //  timer.stop();
        Group root = new Group();
        Text text = new Text("Level " +(this.level+1) );
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
        if (this.level >= MAX_LEVEL) {
            canNextLevel = false;
            win = true;
        }

        if (endLevel - startLevel >= TIME_NEXT_LEVEL) {
            timer.stop();
            canNextLevel = false;
            startLevel = 0;
            root.getChildren().clear();
            playGame(stage);
        }
    }
    public void endGame(Stage stage) {
        timer.stop();
        Group root = new Group();
        root.getChildren().add(Menu.getEndGameMenu());
        root.getChildren().add(Menu.getPlayAgainButton());
        root.getChildren().add(Menu.getQuitButton());
        Scene scene = new Scene(root);
        // Them scene vao stage
        stage.setScene(scene);
        stage.show();
        Menu.getPlayAgainButton().setOnMouseClicked(mouseEvent -> {
            loseGame = false;
            cntMenu = 0;
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
    public void winGame(Stage stage) {
        Group root = new Group();
        root.getChildren().add(Menu.getWinImg());
        root.getChildren().add(Menu.getQuitButton());
        root.getChildren().add(Menu.getPlayAgainButton());
        Scene scene = new Scene(root);
        // Them scene vao stage
        stage.setScene(scene);

        Menu.getQuitButton().setOnMouseClicked(mouseEvent -> {
            audio.playAudio(Audio.audio.buttonClick.value);
            audio.audioStopTime(Audio.audio.buttonClick.value, 70);
            stage.close();
        });
    }
}

