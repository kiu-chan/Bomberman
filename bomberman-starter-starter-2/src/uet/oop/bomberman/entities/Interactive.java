package uet.oop.bomberman.entities;


import uet.oop.bomberman.BombermanGame;
import uet.oop.bomberman.graphics.Sprite;

import java.util.ArrayList;
import java.util.List;

public class Interactive {
    public List<Entity> list = new ArrayList<>();
    Collision collision = new Collision();
    public List<Entity> remoteItem(Entity a, List<Entity> list) {
        try {
            for (int i = 0; i < list.size(); i++) {
                if(collision.CheckCollision(a, list.get(i))) {
                    if (!collision.CheckMapCollision(list.get(i).x, list.get(i).y, list.get(i).w, list.get(i).h, BombermanGame.map.getMap())) {
                        System.out.println(a.x + " " +  a.y + " " + list.get(i).x + " " + list.get(i).y);
                        list.remove(i);
                    }
                }
            }
        } catch (Exception e) {
            return list;
        }
        return list;
    }

    public List<Entity> screen(List<Entity> list) {
        int player_x = BombermanGame.bomberman.x;
        int player_y = BombermanGame.bomberman.y;
        int screen_x = BombermanGame.WIDTH * Sprite.SCALED_SIZE;
        int screen_y = BombermanGame.HEIGHT * Sprite.SCALED_SIZE;
        int speed = BombermanGame.bomberman.speed;

        if (player_x + speed >= screen_x / 2) {
            for (int i = 0; i < list.size(); i++) {
                list.get(i).x -= speed;
            }
        }

        if (player_x - speed <= screen_x / 2 && BombermanGame.bomberman.act == 1) {
            for (int i = 0; i < list.size(); i++) {
                list.get(i).x += speed;
            }
        }

        return list;
    }
}
