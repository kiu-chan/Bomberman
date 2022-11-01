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

    public static final int WIDTH = 35;
    public static final int HEIGHT = 17;
    private boolean win = false;
    private static final int MAX_LEVEL = 3;

    private Canvas canvas = new Canvas(Sprite.SCALED_SIZE * WIDTH, Sprite.SCALED_SIZE * HEIGHT);
    public int level = 1;
    private GraphicsContext gc = canvas.getGraphicsContext2D();
    public boolean check_play = false;
    private int isPlay = 0;
    private boolean playMusic = true;
    private boolean loseGame = false;

    private long startLevel = 0;
    private static final long TIME_NEXT_LEVEL = 1500;
    private boolean canNextLevel = false;
    private long time = 0;
    private long timeReal;
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

    public static final String Map = "bomberman-starter-starter-2/res/TileMap/Map";
    public static final String mapMonster = "bomberman-starter-starter-2/res/TileMap/Tile_monster";
    public static final String mapItem = "bomberman-starter-starter-2/res/TileMap/Tile_item.txt";

    private StopWatch cntLose = new StopWatch(1987);
    private Text textHeart = new Text (":");
    private Text textTime = new Text("time");
    private Text textLevel = new Text("Level:");
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
        loseGame = false;
        player.loadImage();
        bomberman = new Bomber(1, 1, player.getList().get(1).getFxImage(), 2);
        bomberman.setHeart(2);
        entities.add(bomberman);
        Group root = new Group();
        root.getChildren().add(canvas);
        setText();
        root.getChildren().add(Menu.getHeartPlayer());
        root.getChildren().add(textHeart);
        root.getChildren().add(textTime);
        root.getChildren().add(textLevel);
        // Tao scene
        Scene scene = new Scene(root);
        // Them scene vao stage
        stage.setScene(scene);
        stage.show();
        try {
            load();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        getBomberControl.getControl(scene);
        timer = new AnimationTimer() {
            @Override
            public void handle(long l) {
                if (getBomberControl.clearEnemy) {
                    for (int i = 1; i < entities.size(); i++) {
                        entities.remove(i);
                    }
                }
                render();
                update();
                /*textTime.setText("Time: " + timeReal);
                textHeart.setText(": " + bomberman.getHeart());*/
                if (loseGame) {
                    audio.playAudio(Audio.audio.playerDead.value);
                    if (cntLose.checkEnd()) {
                        audio.stopAudio(Audio.audio.playerDead.value);
                        root.getChildren().clear();
                        timer.stop();
                        endGame(stage);
                        cntLose.setStart();
                    }
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

    public void setText() {
        textHeart.setFont(Font.font(null, FontWeight.BOLD, 25));
        //Setting the color of the text
        textHeart.setFill(Color.LIMEGREEN);
        //setting the position of the text
        textHeart.setX(35);
        textHeart.setY(540);
        textTime.setFont(Font.font(null, FontWeight.BOLD, 25));
        textTime.setX(200);
        textTime.setY(540);
        textTime.setFill(Color.BLUE);
        textLevel.setFont(Font.font(null, FontWeight.BOLD, 25));
        textLevel.setX(450);
        textLevel.setY(540);
        textLevel.setFill(Color.YELLOWGREEN);
    }

    public List<Entity> updateEntity() {
        List<Entity> list = new ArrayList<>();
        list.add(bomberman);
        list.addAll(monster.getStillObjects());
        return list;
    }

    public void load() throws FileNotFoundException {
        padding.loadImage();
        padding.createPadding(padding, WIDTH, HEIGHT - 1);
        listPadding.addAll(padding.getStillObjects()); //tạm thời lấy mỗi cỏ

        map.loadImage();
        map.readMap(Map + this.level + ".txt", map, WIDTH, HEIGHT - 1);
        map.createMap();
        stillObjects.addAll(map.getStillObjects());

        item.loadImage();
        //item.readMap(mapItem, item, WIDTH, HEIGHT - 1);
        item.randomItem(mapItem, item, WIDTH, HEIGHT - 1);
        item.createItem();
        listItem.addAll(item.getStillObjects());

        monster.loadImage();
        monster.readMap(mapMonster + this.level + ".txt", monster, WIDTH, HEIGHT - 1);
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
        time++;
        timeReal = time / 50;
        textTime.setText("Time: " + timeReal);
        textHeart.setText(": " + bomberman.getHeart());
        textLevel.setText("Level: " + this.level );
        if (isPlay % 2 == 0) {
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
        root.getChildren().add(Menu.getVolumOnButton());
        // Tao scene
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
        if (playMusic == true) {
            audio.playAudio(Audio.audio.backgroundMusic.value);
        }
        Menu.getVolumOnButton().setOnMouseClicked(mouseEvent -> {
            root.getChildren().remove(Menu.getVolumOnButton());
            root.getChildren().add(Menu.getVolumOffButton());
            audio.playAudio(Audio.audio.buttonClick.value);
            audio.audioStopTime(Audio.audio.buttonClick.value, 70);
            playMusic = false;
            audio.stopAudio(Audio.audio.backgroundMusic.value);
        });
        Menu.getVolumOffButton().setOnMouseClicked(mouseEvent -> {
            root.getChildren().remove(Menu.getVolumOffButton());
            root.getChildren().add(Menu.getVolumOnButton());
            audio.playAudio(Audio.audio.buttonClick.value);
            audio.audioStopTime(Audio.audio.buttonClick.value, 70);
            playMusic = true;
            audio.playAudio(Audio.audio.backgroundMusic.value);
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
        Group root = new Group();
        root.getChildren().add(Menu.getInstruction());
        root.getChildren().add(Menu.getBackButton());
        // Tao scene
        Scene scene = new Scene(root);
        // Them scene vao stage
        stage.setScene(scene);
        stage.show();
        Menu.getBackButton().setOnMouseClicked(mouseEvent -> {
            audio.playAudio(Audio.audio.buttonClick.value);
            audio.audioStopTime(Audio.audio.buttonClick.value, 70);
            root.getChildren().clear();
            mainMenu(stage);
        });
    }

    public void nextLevel(Stage stage) {
        //  timer.stop();
        Group root = new Group();
        Text text = new Text("Level " + (this.level));
        text.setFont(Font.font(null, FontWeight.BOLD, 50));
        text.setFill(Color.WHITE);
        text.setX((WIDTH * Sprite.SCALED_SIZE) / 2 - 150);
        text.setY((HEIGHT * Sprite.SCALED_SIZE) / 2 + 20);
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
        root.getChildren().add(Menu.getQuit2Button());
        root.getChildren().add(Menu.getHomeButton());
        Scene scene = new Scene(root);
        // Them scene vao stage
        stage.setScene(scene);
        stage.show();
        Menu.getPlayAgainButton().setOnMouseClicked(mouseEvent -> {
            time = 0;
            loseGame = false;
            audio.playAudio(Audio.audio.buttonClick.value);
            audio.audioStopTime(Audio.audio.buttonClick.value, 70);
            root.getChildren().clear();
            playGame(stage);
        });
        Menu.getQuit2Button().setOnMouseClicked(mouseEvent -> {
            audio.playAudio(Audio.audio.buttonClick.value);
            audio.audioStopTime(Audio.audio.buttonClick.value, 70);
            stage.close();
        });
        Menu.getHomeButton().setOnMouseClicked(mouseEvent -> {
            audio.playAudio(Audio.audio.buttonClick.value);
            audio.audioStopTime(Audio.audio.buttonClick.value, 70);
            mainMenu(stage);
        });
    }

    public void winGame(Stage stage) {
        Group root = new Group();
        root.getChildren().add(Menu.getWinImg());
        root.getChildren().add(Menu.getQuit2Button());
        root.getChildren().add(Menu.getPlayAgainButton());
        root.getChildren().add(Menu.getHomeButton());
        Scene scene = new Scene(root);
        // Them scene vao stage
        stage.setScene(scene);
        Menu.getQuit2Button().setOnMouseClicked(mouseEvent -> {
            stage.close();
        });
        Menu.getPlayAgainButton().setOnMouseClicked(mouseEvent -> {
            time = 0;
            clear();
            win = false;
            this.level = 1;
            root.getChildren().clear();
            audio.playAudio(Audio.audio.buttonClick.value);
            audio.audioStopTime(Audio.audio.buttonClick.value, 70);
            root.getChildren().clear();
            playGame(stage);
        });
        Menu.getHomeButton().setOnMouseClicked(mouseEvent -> {
            time = 0;
            win = false;
            level = 0;
            audio.playAudio(Audio.audio.buttonClick.value);
            audio.audioStopTime(Audio.audio.buttonClick.value, 70);
            mainMenu(stage);
        });
    }
}

