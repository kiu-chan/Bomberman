package uet.oop.bomberman.entities;

import javafx.scene.media.AudioClip;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class Audio {

    public List<MediaPlayer> list = new ArrayList<>();

    public enum audio {
        backgroundMusic(0),
        bomExplode(1),
        bomSet(2),
        bobDropped(3),
        buttonClick(4),
        gameOver(5),
        gameStart(6),
        playerDead(7),
        powerUp(8),
        ;

        public final int value;
        audio(int value) {
            this.value = value;
        }
    }

    public String path1 = "bomberman-starter-starter-2/res/Audio/BG_MUSIC.wav";
    public String path2 = "bomberman-starter-starter-2/res/Audio/BOM_EXPLODE.wav";
    public String path3 = "bomberman-starter-starter-2/res/Audio/BOM_SET.wav";
    public String path4 = "bomberman-starter-starter-2/res/Audio/bombDropped.wav";
    public String path5 = "bomberman-starter-starter-2/res/Audio/BUTTON_CLICK.wav";
    public String path6 = "bomberman-starter-starter-2/res/Audio/GAME_OVER.wav";
    public String path7 = "bomberman-starter-starter-2/res/Audio/GAME_START.wav";
    public String path8 = "bomberman-starter-starter-2/res/Audio/PLAYER_DEAD.wav";
    public String path9 = "bomberman-starter-starter-2/res/Audio/POWER_UP.wav";

    public Audio() {
        loadAudio(path1);
        loadAudio(path2);
        loadAudio(path3);
        loadAudio(path4);
        loadAudio(path5);
        loadAudio(path6);
        loadAudio(path7);
        loadAudio(path8);
        loadAudio(path9);
    }
    public void loadAudio(String path) {
        File file = new File(path);
        Media media = new Media(file.toURI().toString());
        list.add(new MediaPlayer(media));
    }

    public void playAudioFull(int i) {
        list.get(i).setCycleCount(MediaPlayer.INDEFINITE);
        list.get(i).play();
    }

    public void playAudio(int i) {
        list.get(i).play();
    }


    public void stopAudio(int i) {
        list.get(i).stop();
    }
}
