package com.example.game;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.scene.layout.Pane;
import javafx.scene.image.*;

import java.io.InputStream;
public class Main extends Application {
    Stage window;
    Pane pane = new StackPane();
    int i = 0;

    public static void main(String[] args) {
        launch(args);
    }

    public void render() {

        Image image;
        if(i > 100)
            image = new Image("file:img\\player.png");
        else
            image = new Image("file:img\\slime.png");
        i++;
        System.out.println(i);
        ImageView img = new ImageView();
        img.setImage(image);
        pane.getChildren().add(img);
    }

    @Override
    public void start(Stage primaryStage) {

        Scene scene = new Scene(pane, 600, 300);
        window = primaryStage;
        window.setScene(scene);
        window.show();
        AnimationTimer timer = new AnimationTimer() {
            @Override
            public void handle(long l) {
                render();/*
                update();*/
            }
        };
        timer.start();
    }
}
