package uet.oop.bomberman.entities;


import uet.oop.bomberman.BombermanGame;
import uet.oop.bomberman.entities.Bomb.Bomb;
import uet.oop.bomberman.entities.Bomb.Explotion;
import uet.oop.bomberman.graphics.Sprite;

import java.util.ArrayList;
import java.util.List;


/**
 * Chuyên tương tác giữa các thành phần
 */
public class Interactive {
    public List<Item> listItem = new ArrayList<>();
    Collision collision = new Collision();
    public List<Entity> remoteItem(Entity a, List<Entity> list) {
        try {
            for (int i = 0; i < list.size(); i++) {
                if(collision.CheckCollision(a, list.get(i))) {
                    if (!collision.CheckMapCollision(list.get(i).x, list.get(i).y, list.get(i).w, list.get(i).h, BombermanGame.map.getMap())) {
                        //System.out.println(a.x + " " +  a.y + " " + list.get(i).x + " " + list.get(i).y);
                        Item item = (Item) list.get(i);
                        item.setStart(System.currentTimeMillis());
                        listItem.add(item);
                        list.remove(i);
                    }
                }
            }
        } catch (Exception e) {
            return list;
        }
        return list;
    }

    public void itemHandling() {
        for (int i = 0; i < listItem.size(); i++) {
            listItem.get(i).item();
            if (listItem.get(i).getEnd())
                listItem.remove(i);
        }
    }

    public List<Entity> monsterDead(Bomber player, List<Entity> list) {

        //lưu vị trí nổ
        for (Bomb bom : player.getBombs()) {
            int[][] mapBom = new int[100][100];
            for (Explotion explotion : bom.getExplotionList())
                mapBom[explotion.x / Sprite.SCALED_SIZE][explotion.y / Sprite.SCALED_SIZE] = 1;

            for (int i = 0; i < list.size(); i++) {
                MoveEntity entity = (MoveEntity) list.get(i);
                //lấy hướng di chuyển của quái
                int speed_x = 0;
                int speed_y = 0;

                if (entity.getWay() == AutoMove.move.UP.value) {
                    speed_y = entity.getSpeed();
                }
                if (entity.getWay() == AutoMove.move.DOWN.value) {
                    speed_y = -entity.getSpeed();
                }
                if (entity.getWay() == AutoMove.move.LEFT.value) {
                    speed_x = -entity.getSpeed();
                }
                if (entity.getWay() == AutoMove.move.RIGHT.value) {
                    speed_x = entity.getSpeed();
                }

                if (bom.isExplotion() && collision.CheckMapCollision(list.get(i).x + speed_x, list.get(i).y + speed_y, list.get(i).w, list.get(i).h, mapBom)) {
                    list.get(i).setCheckDead();
                }
            }
        }

        //for (Entity bom : player.getListBom()) {
            //System.out.println(bom.w + " " + bom.h);
            /*for (int i = 0; i < list.size(); i++) {
                MoveEntity entity = (MoveEntity) list.get(i);
                //lấy hướng di chuyển của quái
                int speed_x = 0;
                int speed_y = 0;

                if (entity.getWay() == AutoMove.move.UP.value) {
                    speed_y = entity.getSpeed();
                }
                if (entity.getWay() == AutoMove.move.DOWN.value) {
                    speed_y = -entity.getSpeed();
                }
                if (entity.getWay() == AutoMove.move.LEFT.value) {
                    speed_x = -entity.getSpeed();
                }
                if (entity.getWay() == AutoMove.move.RIGHT.value) {
                    speed_x = entity.getSpeed();
                }

                if (isExplotion && collision.CheckMapCollision(list.get(i).x + speed_x, list.get(i).y + speed_y, list.get(i).w, list.get(i).h, mapBom)) {
                    list.get(i).setCheckDead();
                }
           // }
        }*/

        //player.remoteListBom();
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).getRemove()) {
                list.remove(i);
            }
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
