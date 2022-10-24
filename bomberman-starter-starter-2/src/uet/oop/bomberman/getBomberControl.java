package uet.oop.bomberman;

import javafx.scene.input.KeyEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;

public class getBomberControl {
    public static boolean bomberLeft = false;
    public static boolean bomberRight = false;
    public static boolean bomberUp = false;
    public static boolean bomberDown = false;
    public static boolean bomberSpace = false;

    public static void getControl(Scene scene) {
        scene.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent keyEvent) {
                switch (keyEvent.getCode()) {
                    case LEFT: {
                       bomberLeft = true;
                    }
                    break;
                    case RIGHT: {
                        bomberRight = true;
                    }
                    break;
                    case UP: {
                        bomberUp = true;
                    }
                    break;
                    case DOWN: {
                        bomberDown = true;
                    }
                    break;
                    case SPACE: {
                        bomberSpace = true;
                    }
                    break;
                    default:
                        break;
                }
            }
        });
        scene.setOnKeyReleased(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent keyEvent) {
                switch (keyEvent.getCode()) {
                    case LEFT: {
                        bomberLeft = false;
                    }
                    break;
                    case RIGHT: {
                       bomberRight = false;
                    }
                    break;
                    case UP: {
                        bomberUp = false;
                    }
                    break;
                    case DOWN: {
                        bomberDown = false;
                    }
                    break;
                    case SPACE: {
                        bomberSpace = false;
                    }
                    break;
                    default:
                        break;
                }
            }
        });
    }
}
