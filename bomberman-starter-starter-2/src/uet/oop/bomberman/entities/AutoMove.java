package uet.oop.bomberman.entities;

import uet.oop.bomberman.BombermanGame;
import uet.oop.bomberman.graphics.Sprite;

import javafx.scene.image.Image;

import java.util.Random;


public class AutoMove extends MoveEntity {
    private int start = 5;

    private long imageTimeAgo = 0;
    private long imageTimeNow = 0;
    private long timeImg = 300;
    private int swapImg = 0;

    private long timeNow = 0;
    private long timeAgo = 0;
    private long timeDead = 0;

    public static final long timeWay = 500;
    public static final long TIME_DEAD = 1500;

    private int startImg;
    private int checkView = 1;

    private int player_x;
    private int player_y;
    private int check_direction = 0;

    public static final double RANGE = 3 * Sprite.SCALED_SIZE;
    private Collision collision = new Collision();

    Bomber bomber;

    public enum move {
        UP(0), DOWN(1), LEFT(2), RIGHT(3), STOP(4);

        public final int value;
        move(int value) {
            this.value = value;
        }
    }

    public enum view {
        LEFT(-1), RIGHT(1), DIED(0);

        private final int value;
        view(int value) {
            this.value = value;
        }
    }

    public enum direction {
        LEFT(1) , LEFT_UP(2), UP(3), UP_RIGHT(4), RIGHT(5), RIGHT_DOWN(6), DOWN(7), DOWN_LEFT(8);

        private final int value;
        direction(int value) {
            this.value = value;
        }
    }

    public AutoMove(int x, int y, Image img, int speed, int startImg) {
        super( x, y, img,speed);
        this.speed = speed;
        this.startImg = startImg;
    }

    public void moveMinvoRotate() {
        //thay đổi tầm nhìn
        if (canMove(1)) {
            if (checkView == view.LEFT.value) {
                checkView = view.RIGHT.value;
            } else {
                checkView = view.LEFT.value;
            }
        }

        moveIMG();

    }

    public void moveKondoria(int way) {
        canMove(way);

        move(start);
    }

    public void moveMinvo(int way) {
        canMove(way);

        if(checkPlayer()) {
            direction(SimpleMoveToPlayer());
        }
        else {
            check_direction = 0;
            direction(start);
        }
    }

    public void moveOneal(int way) {
        if (canMove(way)) {
            Random random = new Random();
            int new_speed = Math.abs(random.nextInt()) % 2 + 1;
            setSpeed(new_speed);//System.out.println(new_speed);
        }

        if(checkPlayer()) {
            direction(SimpleMoveToPlayer());
        }
        else {
            check_direction = 0;
            direction(start);
        }
    }

    public void moveBalloom(int way) {
        canMove(way);
        direction(start);
    }

    public void moveGhost(int way) {
        canMove(way);
        if(checkPlayer()) {
            move(SimpleMoveToPlayer());
        }
        else {
            check_direction = 0;
            move(start);
        }
    }
    @Override
    public boolean canMove(int way) {
        timeNow = System.currentTimeMillis();
        if (timeAgo == 0) {
            timeAgo = System.currentTimeMillis();
            start = 5;
        }
        if(timeNow - timeAgo >= timeWay) {
            timeAgo = timeNow;
            start = way;
            return true;
        }

        /*if(!checkPlayer()) {
            check_direction = 0;
            direction(start);
        }
        else {
            direction(SimpleMoveToPlayer());
        }*/
        return false;
    }

    //di chuyển không va chạm
    public void move(int way) {
        if (way == move.UP.value && y - speed >= 0) {
            moveUp();
        }

        if (way == move.DOWN.value && y + speed <= Sprite.SCALED_SIZE * (BombermanGame.HEIGHT - 1)) {
            moveDown();
        }

        if (way == move.LEFT.value && x - speed >= 0) {
            moveLeft();
            checkView = view.LEFT.value;
        }

        if (way == move.RIGHT.value && x + speed <= Sprite.SCALED_SIZE * (BombermanGame.WIDTH - 1)) {
            moveRight();
            checkView = view.RIGHT.value;
        }

        if (way == move.STOP.value) {
            moveStop();
        }

        moveIMG();
    }

    public void direction(int way) {
        if (way == move.UP.value) {
            if (!collision.CheckMapCollision(x, y - speed, Sprite.SCALED_SIZE, Sprite.SCALED_SIZE, BombermanGame.map.getMap()))
                moveUp();
        }

        if (way == move.DOWN.value) {
            if (!collision.CheckMapCollision(x, y + speed, Sprite.SCALED_SIZE, Sprite.SCALED_SIZE, BombermanGame.map.getMap()))
                moveDown();
        }

        if (way == move.LEFT.value) {
            if (!collision.CheckMapCollision(x - speed, y, Sprite.SCALED_SIZE, Sprite.SCALED_SIZE, BombermanGame.map.getMap()))
                moveLeft();
            checkView = view.LEFT.value;
        }

        if (way == move.RIGHT.value) {
            if (!collision.CheckMapCollision(x + speed, y, Sprite.SCALED_SIZE, Sprite.SCALED_SIZE, BombermanGame.map.getMap()))
                moveRight();
            checkView = view.RIGHT.value;
        }

        if (way == move.STOP.value) {
            moveStop();
        }

        moveIMG();
    }


    public int SimpleMoveToPlayer() {
        Random random = new Random();
        int way = 5;
        checkDirection();

        //player bên trái
        if (check_direction == direction.LEFT.value) {
            way = move.LEFT.value;
            if (y > player_y) {
                way = move.UP.value;
            }
            if (y < player_y) {
                way = move.DOWN.value;
            }
        }

        //player góc trên bên trái
        if (check_direction == direction.LEFT_UP.value) {
            if (!collision.CheckMapCollision(x - speed, y, Sprite.SCALED_SIZE, Sprite.SCALED_SIZE, BombermanGame.map.getMap())) {
                way = move.LEFT.value;
            } else if (!collision.CheckMapCollision(x, y - speed, Sprite.SCALED_SIZE, Sprite.SCALED_SIZE, BombermanGame.map.getMap())) {
                way = move.UP.value;
            }
        }

        //player bên trên
        if (check_direction == direction.UP.value) {
            way = move.UP.value;
            if (x > player_x) {
                way = move.LEFT.value;
            }
        }

        //player góc trên bên phải
        if (check_direction == direction.UP_RIGHT.value) {
            if (!collision.CheckMapCollision(x + speed, y, Sprite.SCALED_SIZE, Sprite.SCALED_SIZE, BombermanGame.map.getMap())) {
                way = move.RIGHT.value;
            } else if (!collision.CheckMapCollision(x, y - speed, Sprite.SCALED_SIZE, Sprite.SCALED_SIZE, BombermanGame.map.getMap())) {
                way = move.UP.value;
            }
        }

        //player bên phải
        if(check_direction == direction.RIGHT.value) {
            way = move.RIGHT.value;
            if (y > player_y) {
                way = move.UP.value;
            }
            if (y < player_y) {
                way = move.DOWN.value;
            }
        }

        //player góc dưới bên phải
        if (check_direction == direction.RIGHT_DOWN.value) {
            if (!collision.CheckMapCollision(x + speed, y, Sprite.SCALED_SIZE, Sprite.SCALED_SIZE, BombermanGame.map.getMap())) {
                way = move.RIGHT.value;
            } else if (!collision.CheckMapCollision(x, y + speed, Sprite.SCALED_SIZE, Sprite.SCALED_SIZE, BombermanGame.map.getMap())) {
                way = move.DOWN.value;
            }
        }

        //player bên dưới
        if (check_direction == direction.DOWN.value) {
            way = move.DOWN.value;//System.out.println(1);
            if (x < player_x) {
                way = move.RIGHT.value;
            }
            if (x > player_x) {
                way = move.LEFT.value;
            }
        }

        //player góc dưới bên trái
        if (check_direction ==direction.DOWN_LEFT.value) {
            if (!collision.CheckMapCollision(x - speed, y, Sprite.SCALED_SIZE, Sprite.SCALED_SIZE, BombermanGame.map.getMap())) {
                way = move.LEFT.value;
            } else if (!collision.CheckMapCollision(x, y + speed, Sprite.SCALED_SIZE, Sprite.SCALED_SIZE, BombermanGame.map.getMap())) {
                way = move.DOWN.value;
            }
        }

        return way;
    }

    public void moveIMG() {
        imageTimeNow = System.currentTimeMillis();
        if (imageTimeNow - imageTimeAgo > timeImg) {
            swapImg++;
            imageTimeAgo = System.currentTimeMillis();
        }
        if (swapImg >= 2)
            swapImg = 0;
        if (checkView == view.LEFT.value)
            img = BombermanGame.monster.getList().get(swapImg + startImg).getFxImage();
        if (checkView == view.RIGHT.value)
            img = BombermanGame.monster.getList().get(swapImg + startImg + 4).getFxImage();
        if (checkView == view.DIED.value)
            img = BombermanGame.monster.getList().get(startImg + 3).getFxImage();
    }

    @Override
    public void moveUp() {
        y -= speed;
    }

    @Override
    public void moveDown() {
        y += speed;
    }

    @Override
    public void moveLeft() {
        x -= speed;
    }

    @Override
    public void moveRight() {
        x += speed;
    }

    public void moveStop() {

    }

    public boolean canRemove(long time) {
        if (timeDead == 0) {
            timeDead = time;
        }

        long timeRemove = System.currentTimeMillis();

        if (timeRemove - timeDead > TIME_DEAD) {
            return true;
        }
        return false;
    }
    public void DEAD() {
        checkView = view.DIED.value;
        moveStop();
        if (canRemove(System.currentTimeMillis())) {
            setRemove(true);

        }
        moveIMG();
    }

    public boolean checkPlayer() {
        player_x = BombermanGame.bomberman.getX();
        player_y = BombermanGame.bomberman.getY();
        int distance_x = Math.abs(x - player_x);
        int distance_y = Math.abs(y - player_y);

        double distance = Math.sqrt((distance_x * distance_x) + (distance_y * distance_y));
        if (distance <= RANGE) {
            checkDirection();
            return true;
        }
        check_direction = 0;
        return false;
    }

    public void checkDirection() {
        //bên trái
        if (x > player_x + Sprite.DEFAULT_SIZE) {
            //trên
            if (y > player_y + Sprite.SCALED_SIZE) {
                check_direction = direction.LEFT_UP.value;
            }

            //trái
            if (y <= player_y + Sprite.SCALED_SIZE && y + Sprite.SCALED_SIZE >= player_y) {
                check_direction = direction.LEFT.value;
            }

            //dưới
            if (y + Sprite.SCALED_SIZE < player_y) {
                check_direction = direction.DOWN_LEFT.value;
            }
        }

        //trùng tọa độ x
        if (x <= player_x + Sprite.DEFAULT_SIZE && x + Sprite.DEFAULT_SIZE >= player_x) {
            //trên
            if (y > player_y + Sprite.DEFAULT_SIZE) {
                check_direction = direction.UP.value;
            }

            //dưới
            if (y < player_y) {
                check_direction = direction.DOWN.value;
            }
        }

        //bên phải
        if (x + Sprite.DEFAULT_SIZE < player_x) {
            //trên
            if (y > player_y + Sprite.SCALED_SIZE) {
                check_direction = direction.UP_RIGHT.value;
            }

            //phải
            if (y <= player_y + Sprite.SCALED_SIZE && y + Sprite.SCALED_SIZE >= player_y) {
                check_direction = direction.RIGHT.value;
            }

            //dưới
            if (y + Sprite.SCALED_SIZE < player_y) {
                check_direction = direction.RIGHT_DOWN.value;
            }
        }
    }



    @Override
    public void update() {
    }
}
