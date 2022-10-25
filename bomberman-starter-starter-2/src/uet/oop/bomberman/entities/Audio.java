package uet.oop.bomberman.entities;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

import java.io.File;
public class Audio {
    private String path = "";

    MediaPlayer mediaPlayer;
    public Audio(String path) {
        this.path = path;
        try {
            File mediaFile = new File(path);System.out.println(path);
            Media media = new Media(mediaFile.toURI().toString());
            //mediaPlayer = new MediaPlayer(media);
        } catch (Exception e) {
            System.out.println("audio error");
        }
    }
    public void playAudio() {
        mediaPlayer.setAutoPlay(true);
    }
}
