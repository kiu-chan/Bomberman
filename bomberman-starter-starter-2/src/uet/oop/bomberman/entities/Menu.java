package uet.oop.bomberman.entities;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
public class Menu {
    private Image backgroundMenuImage = new Image("MenuImage/background.png");
    private ImageView backgroundMenu = new ImageView(backgroundMenuImage);
    private Image playImage = new Image("MenuImage/button_play.png");
    private ImageView playButton = new ImageView(playImage);
    private Image instructionImage = new Image("MenuImage/button_instructions.png");
    private ImageView instructionButton = new ImageView(instructionImage);
    private Image quit = new Image("MenuImage/button_quit.png");
    private ImageView quitButton = new ImageView(quit);
    private Image guideImage = new Image("MenuImage/instruction_image.jpg");
    private ImageView instruction = new ImageView(guideImage);
    private Button Pause = new Button("Pause");
    private Image endGameImage = new Image("MenuImage/end.png");
    private ImageView endGameMenu = new ImageView(endGameImage);
    private Button muteVolumeButton = new Button("Mute?");
    private Image playAgain = new Image("MenuImage/playag.png");
    private ImageView playAgainButton = new ImageView(playAgain);
    private Image levelimg = new Image("background/level.png");
    private Image quit2 = new Image("MenuImage/quit.png");
    private ImageView quit2Button = new ImageView(quit2);
    private ImageView level = new ImageView(levelimg);

  //  private Image bg = new Image("MenuImage/buttonPlayer.png");
  //  private ImageView gbPlay = new ImageView(bg);

    private Image heart = new Image("MenuImage/HeartPlayer.png");
    private Image winImage = new Image("MenuImage/win.jpg");

    private ImageView winImg = new ImageView(winImage);
    private ImageView heartPlayer = new ImageView(heart);

    public Menu()  {
        instructionButton.setX(200);
        instructionButton.setY(320);
        quitButton.setX(200);
        quitButton.setY(400);
        playButton.setX(200);
        playButton.setY(250);
        quitButton.setX(200);
        quitButton.setY(390);
        muteVolumeButton.setLayoutY(0);
        muteVolumeButton.setLayoutX(840);
        muteVolumeButton.setMinSize(66,66);
        playAgainButton.setX(100);
        playAgainButton.setY(330);
        quit2Button.setX(700);
        quit2Button.setY(330);
        heartPlayer.setX(5);
        heartPlayer.setY(5);
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
}

