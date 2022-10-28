package uet.oop.bomberman.entities;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.media.AudioClip;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import uet.oop.bomberman.BombermanGame;
import uet.oop.bomberman.entities.*;
import uet.oop.bomberman.entities.Interaction.Interactive;
import uet.oop.bomberman.graphics.Images;
import uet.oop.bomberman.graphics.Sprite;
import javafx.fxml.FXML;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.*;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Label;
import javafx.stage.Stage;
public class Menu {
    private Image backgroundMenuImage = new Image("C:/Users/ngohu/IdeaProjects/BombermanGame/bomberman-starter-starter-2/res/MenuImage/background.png");
    private ImageView backgroundMenu = new ImageView(backgroundMenuImage);
    private Image playImage = new Image("C:/Users/ngohu/IdeaProjects/BombermanGame/bomberman-starter-starter-2/res/MenuImage/play.png");
    private ImageView playButton = new ImageView(playImage);
    private Image instructionImage = new Image("C:/Users/ngohu/IdeaProjects/BombermanGame/bomberman-starter-starter-2/res/MenuImage/instruction.png");
    private ImageView instructionButton = new ImageView(instructionImage);
    private Image quit = new Image("C:\\Users\\ngohu\\IdeaProjects\\BombermanGame\\bomberman-starter-starter-2\\res\\MenuImage/quit.png");
    private ImageView quitButton = new ImageView(quit);
    private Image guideImage = new Image("C:/Users/ngohu/IdeaProjects/BombermanGame\\bomberman-starter-starter-2\\res\\MenuImage/instruction_image.jpg");
    private ImageView instruction = new ImageView(guideImage);
    private Button Pause = new Button("Pause");
    private Image endGameImage = new Image("C:/Users/ngohu/IdeaProjects/BombermanGame/bomberman-starter-starter-2/res/background/over.jpg");
    private ImageView endGameMenu = new ImageView(endGameImage);
    private Button muteVolumeButton = new Button("Mute?");
    public Menu()  {
        instructionButton.setX(300);
        instructionButton.setY(200);
        quitButton.setX(300);
        quitButton.setY(400);
        playButton.setX(300);
        playButton.setY(300);
        quitButton.setX(300);
        quitButton.setY(400);
        muteVolumeButton.setLayoutY(0);
        muteVolumeButton.setLayoutX(840);
        muteVolumeButton.setMinSize(66,66);
    }

    public Button getMuteVolumeButton() {
        return muteVolumeButton;
    }

    public ImageView getBackgroundMenu() {
        return backgroundMenu;
    }

    public ImageView getEndGameMenu() {
        return endGameMenu;
    }

    public ImageView getInstructionButton() {
        return instructionButton;
    }

    public ImageView getPlayButton() {
        return playButton;
    }

    public  ImageView getInstruction() {
        return instruction;
    }

    public ImageView getQuitButton() {
        return quitButton;
    }
}

