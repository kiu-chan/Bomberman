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
    public static Image backgroundMenuImage = new Image("MenuImage/background.png");
    public static ImageView backgroundMenu = new ImageView(backgroundMenuImage);
    public static Image playImage = new Image("MenuImage/play.png");
    public static ImageView playButton = new ImageView(playImage);
    public static Image instructionImage = new Image("MenuImage/instruction.png");
    public static ImageView instructionButton = new ImageView(instructionImage);
    public static Image quit = new Image("MenuImage/quit.png");
    public static ImageView quitButton = new ImageView(quit);
    public static Image guideImage = new Image("MenuImage/instruction_image.jpg");
    public static ImageView instruction = new ImageView(guideImage);
    public static Button Pause = new Button("Pause");
    public static Image endGameImage = new Image("background/over.jpg");
    public static ImageView endGameMenu = new ImageView(endGameImage);
    public Menu()  {

    }
}

