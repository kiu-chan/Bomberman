package uet.oop.bomberman.entities;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import uet.oop.bomberman.BombermanGame;
import uet.oop.bomberman.graphics.Sprite;


public class Menu {
    private int cntPause = 0;
    private Image backgroundMenuImage = new Image("MenuImage/background.png");
    private ImageView backgroundMenu = new ImageView(backgroundMenuImage);
    private Image playImage = new Image("MenuImage/button_play.png");
    private ImageView playButton = new ImageView(playImage);
    private Image instructionImage = new Image("MenuImage/button_instructions.png");
    private ImageView instructionButton = new ImageView(instructionImage);
    private Image quit = new Image("MenuImage/button_quit.png");
    private ImageView quitButton = new ImageView(quit);
    private Image guideImage = new Image("MenuImage/hduong.jpg");
    private ImageView instruction = new ImageView(guideImage);
    private Button Pause = new Button("Pause");
    private Image endGameImage = new Image("MenuImage/end.png");
    private ImageView endGameMenu = new ImageView(endGameImage);
    private Image playAgain = new Image("MenuImage/playag.png");
    private ImageView playAgainButton = new ImageView(playAgain);
    private Image levelimg = new Image("MenuImage/level.png");
    private Image quit2 = new Image("MenuImage/quit.png");
    private ImageView quit2Button = new ImageView(quit2);
    private ImageView level = new ImageView(levelimg);
    private Image back = new Image("MenuImage/button_back.png");
    private ImageView backButton = new ImageView(back);
    private Image next = new Image("MenuImage/next.jpg");
    private ImageView nextButton = new ImageView(next);
    private Image volumOn = new Image("MenuImage/button_music1.png");
    private Image volumOff = new Image("MenuImage/button_music2.png");
    private ImageView volumOnButton = new ImageView(volumOn);
    private ImageView volumOffButton = new ImageView(volumOff);
    private Image continueIMG = new Image("MenuImage/con.png");

    private Image pauseIMG = new Image("MenuImage/pau.png");
    private ImageView pauseButton = new ImageView(continueIMG);
    private Image heart = new Image("MenuImage/green.png");
    private Image winImage = new Image("MenuImage/win.jpg");
    private Image home = new Image("MenuImage/home.jpg");
    private ImageView homeButton = new ImageView(home);
    private Image hd2 = new Image("MenuImage/q1.png");
    private Image hd3 = new Image("MenuImage/q2.png");
    private ImageView hd2Img = new ImageView(hd2);
    private Image soundOnPlay = new Image("MenuImage/soundon.jpg");

    private Image soundOffPlay = new Image("MenuImage/soundoff.jpg");
    private Image pauseIMGScreen = new Image("MenuImage/pause.jpg");
    private ImageView pauseScreen = new ImageView(pauseIMGScreen);

    private ImageView soundPlayButton = new ImageView(soundOnPlay);
    private ImageView hd3Img = new ImageView(hd3);
    private ImageView winImg = new ImageView(winImage);
    private ImageView heartPlayer = new ImageView(heart);

    public Menu()  {
        instructionButton.setX(200);
        instructionButton.setY(380);
        playButton.setX(200);
        playButton.setY(300);
        quitButton.setX(200);
        quitButton.setY(460);
        playAgainButton.setX(150);
        playAgainButton.setY(400);
        quit2Button.setX(850);
        quit2Button.setY(400);
        heartPlayer.setX(10);
        heartPlayer.setY(525);
        backButton.setY(5);
        backButton.setX(5);
        nextButton.setY(5);
        nextButton.setX(1040);
        volumOnButton.setX(1040);
        volumOnButton.setY(5);
        volumOffButton.setX(1040);
        volumOffButton.setY(5);
        homeButton.setX(1025);
        homeButton.setY(10);
        soundPlayButton.setX(1050);
        soundPlayButton.setY(515);
        pauseButton.setX(980);
        pauseButton.setY(515);
    }

    public ImageView getBackButton() {
        return backButton;
    }

    public ImageView getSoundPlayButton() {
        return soundPlayButton;
    }

    public ImageView getVolumOffButton() {
        return volumOffButton;
    }

    public ImageView getVolumOnButton() {
        return volumOnButton;
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

    public ImageView getPlayAgainButton() {
        return playAgainButton;
    }

    public ImageView getLevel() {
        return level;
    }


    public ImageView getHeartPlayer() {
        return this.heartPlayer;
    }

    public ImageView getWinImg() {
        return winImg;
    }
    public ImageView getQuit2Button() {
        return quit2Button;
    }

    public ImageView getHomeButton() {
        return homeButton;
    }

    public ImageView getHd2Img() {
        return hd2Img;
    }

    public ImageView getNextButton() {
        return nextButton;
    }

    public ImageView getHd3Img() {
        return hd3Img;
    }

    public ImageView getPauseButton() {
        return pauseButton;
    }

    public void setSound() {
        soundPlayButton.setOnMouseClicked(mouseEvent -> {
            BombermanGame.cntSound++;
        });
        if (BombermanGame.cntSound % 2 == 0) {
            soundPlayButton.setImage(soundOnPlay);
        } else {
            soundPlayButton.setImage(soundOffPlay);
        }
    }
    public void setPause(Group group) {
        pauseButton.setOnMouseClicked(mouseEvent -> {
            BombermanGame.cntPlay++;
        });
        if (BombermanGame.cntPlay % 2 == 0) {
            group.getChildren().remove(pauseScreen);
            pauseButton.setImage(continueIMG);
            cntPause = 0;
        } else {
            if (cntPause < 1) {
                group.getChildren().add(pauseScreen);
                cntPause++;
            }
            pauseButton.setImage(pauseIMG);
        }
    }
}


