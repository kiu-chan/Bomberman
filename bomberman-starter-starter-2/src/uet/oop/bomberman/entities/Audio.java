package uet.oop.bomberman.entities;

import javafx.scene.media.AudioClip;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class Audio {
    private String path = "";

    public static List<AudioClip> list = new ArrayList<>();

    public Audio() {
        try {
            AudioClip audioClip = new AudioClip("Audio/test.mp3");
            //list.add(audioClip);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void playAudio() {
    }
}
