package uet.oop.bomberman.entities;


import uet.oop.bomberman.BombermanGame;
import uet.oop.bomberman.entities.Bomb.Bomb;
import uet.oop.bomberman.entities.Bomb.Explotion;
import uet.oop.bomberman.entities.Enemy.Minvo;
import uet.oop.bomberman.graphics.Sprite;

import java.util.ArrayList;
import java.util.List;


/**
 * Chuyên tương tác giữa các thành phần
 */
public class Interactive {
    public boolean check_swapMap = false;
    public List<Item> listItem = new ArrayList<>();
    Collision collision = new Collision();
    public List<Entity> removeItem(Entity a, List<Entity> list, List<Entity> monster) {
        try {
            for (int i = 0; i < list.size(); i++) {
                if(collision.CheckCollision(a, list.get(i))) {
                    if (!collision.CheckMapCollision(list.get(i).x, list.get(i).y, list.get(i).w, list.get(i).h, BombermanGame.map.getMap())) {
                        //System.out.println(a.x + " " +  a.y + " " + list.get(i).x + " " + list.get(i).y);
                        Item item = (Item) list.get(i);
                        if (item.getOrder() + 1 != Sprite.portal) {
                            item.setStart(System.currentTimeMillis());
                            listItem.add(item);
                            list.remove(i);
                        } else {
                            swapMap(monster, list, i);
                        }
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
                if (bom.isExplotion() && collision.CheckMapCollision(list.get(i).x, list.get(i).y, list.get(i).w, list.get(i).h, mapBom)) {
                        list.get(i).setCheckDead(true);
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
                if (list.get(i).getSwapMonster()) {//System.out.println(list.get(i).getRemove());
                    int monster_x = list.get(i).getX() / Sprite.SCALED_SIZE;
                    int monster_y = list.get(i).getY() / Sprite.SCALED_SIZE;
                    list.remove(i);
                    Entity minvo = new Minvo(monster_x, monster_y, BombermanGame.monster.getList().get(Sprite.minvo).getFxImage());
                    list.add(minvo);
                } else {
                    list.remove(i);
                }
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

    public void swapMap(List<Entity> list, List<Entity> item, int itemLocation) {
        if (list.size() == 1) {
            setSwapMap(true);//System.out.println(1);
            item.remove(itemLocation);
        } else {
            setSwapMap(false);//System.out.println(2);
        }
    }
    public void collideWithEnemy(Bomber bomber, List<Entity> enemy) {
        for (int i = 0; i < enemy.size(); i++) {
            if (enemy.get(i) instanceof Bomber == false) {
                if (collision.CheckCollision(bomber, enemy.get(i))) {
                    bomber.reduceHeart();
                    if (bomber.getHeart() >= 1) {
                        bomber.setPosition();
                    }
                }
            }
        }
    }

    public boolean getSwapMap() {
        return this.check_swapMap;
    }

    public void setSwapMap(boolean check_swapMap) {
        this.check_swapMap = check_swapMap;
    }
}
