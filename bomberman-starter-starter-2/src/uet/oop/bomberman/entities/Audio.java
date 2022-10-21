package uet.oop.bomberman.entities;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

import java.io.File;
public class Audio {
    private String path = "";

    MediaPlayer mediaPlayer;
    Audio(String path) {
        this.path = path;
        File mediaFile = new File(path);
        Media media = new Media(mediaFile.toURI().toString());
        mediaPlayer = new MediaPlayer(media);
    }
    public void playAudio() {
        mediaPlayer.setAutoPlay(true);
    }
}
