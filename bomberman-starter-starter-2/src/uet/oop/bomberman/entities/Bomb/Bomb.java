package uet.oop.bomberman.entities.Bomb;

import uet.oop.bomberman.entities.Entity;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import uet.oop.bomberman.BombermanGame;
import uet.oop.bomberman.entities.Bomber;
import uet.oop.bomberman.entities.Entity;
import uet.oop.bomberman.graphics.Sprite;
import uet.oop.bomberman.entities.Bomber;
import uet.oop.bomberman.entities.Collide;
import java.util.ArrayList;
import java.util.List;
public class Bomb extends Entity {
    private boolean isExplotion = false;
    private boolean remove = false;
    public static int radiusBomb = 1;
    private int timeToExplode = 80;
    private int timeAfterExplode = 100;
    private List<Explotion> explotionList = new ArrayList<>();

    public boolean isExplotion() {
        return isExplotion;
    }

    public Bomb(int x, int y, Image img) {
        super(x, y, img);
    }
    public boolean checkWall(int way) {
        int toadoX = this.x / 32;
        int toadoY = this.y / 32;
        int r = this.getRealRadius(way);
        if (way == 1) {
            if (BombermanGame.map.getMap()[toadoX][toadoY - r - 1] == 2
                    && r < radiusBomb) {
                return true;
            }
        }
        if (way == 2) {
            if (BombermanGame.map.getMap()[toadoX][toadoY + r + 1] == 2
                    && r < radiusBomb) {
                return true;
            }
        }
        if (way == 3) {
            if (BombermanGame.map.getMap()[toadoX - r - 1][toadoY] == 2
                    && r < radiusBomb)
                return true;
        }
        if (way == 4) {
            if (BombermanGame.map.getMap()[toadoX + r + 1][toadoY] == 2
                    && r < radiusBomb)
                return true;
        }
        return false;
    }
    public boolean isHaveBomb(List <Bomb> bombList) {
        for(int i = 0; i < bombList.size(); i++) {
            if (this.x == bombList.get(i).getX() && this.y == bombList.get(i).getY()) {
                return true;
            }
        }
        return false;
    }

    public int getRealRadius(int way) {
        int cnt = 0;
        switch (way) {
            case 1 : {
                int toadoX = this.x / 32;
                int toadoY = this.y / 32 - 1;
                while (cnt < radiusBomb) {
                    if (BombermanGame.map.getMap()[toadoX][toadoY - cnt] == 0) {
                        cnt++;
                    } else if (BombermanGame.map.getMap()[toadoX][toadoY - cnt] == 1)
                        return cnt;
                    else if (BombermanGame.map.getMap()[toadoX][toadoY - cnt] == 2) {
                        return cnt;
                    }
                }
            }
            break;

            case 2 : {
                int toadoX = this.x / 32;
                int toadoY = this.y / 32 + 1;
                while (cnt < radiusBomb) {
                    if (BombermanGame.map.getMap()[toadoX][toadoY + cnt] == 0) {
                        cnt++;
                    } else if (BombermanGame.map.getMap()[toadoX][toadoY + cnt] == 1)
                        return cnt;
                    else if (BombermanGame.map.getMap()[toadoX][toadoY + cnt] == 2) {
                        return cnt;
                    }
                }
            }
            break;

            case 3 : {
                int toadoX = this.x / 32 - 1;
                int toadoY = this.y / 32;
                while (cnt < radiusBomb) {
                    if (BombermanGame.map.getMap()[toadoX - cnt][toadoY] == 0) {
                        cnt++;
                    } else if (BombermanGame.map.getMap()[toadoX - cnt][toadoY] == 1)
                        return cnt;
                    else if (BombermanGame.map.getMap()[toadoX - cnt][toadoY] == 2) {
                        return cnt;
                    }
                }
            }
            break;

            case 4 : {
                int toadoX = this.x / 32 + 1;
                int toadoY = this.y / 32;
                while (cnt < radiusBomb) {
                    if (BombermanGame.map.getMap()[toadoX + cnt][toadoY] == 0) {
                        cnt++;
                    } else
                    if (BombermanGame.map.getMap()[toadoX + cnt][toadoY] == 1)
                        return cnt;
                    else if (BombermanGame.map.getMap()[toadoX + cnt][toadoY] == 2) {
                        return cnt;
                    }
                }
            }
            break;
            default: break;
        }
        return cnt;
    }
    public void makeExplotion(int way) {
        int toadoX = this.x / 32;
        int toadoY = this.y / 32;
        int r = this.getRealRadius(way);
        if (way == 1) {
            for (int j = 1; j <= r; j++) {
                if (j == r)
                    explotionList.add(new Explotion(this.x, this.y - 32 * j, way, true));
                else
                    explotionList.add(new Explotion(this.x, this.y - 32 * j, way, false));
            }
            if (BombermanGame.map.getMap()[toadoX][toadoY - r - 1] == 2
                    && r < radiusBomb) {
                explotionList.add(new Explotion(toadoX, toadoY - r - 1));
                return;
            }
        }
        if (way == 2) {
            for (int j = 1; j <= r; j++) {
                if  (j == r) {
                    explotionList.add(new Explotion(this.x, this.y + 32 * j, way, true));
                } else {
                    explotionList.add(new Explotion(this.x, this.y + 32 * j, way, false));
                }
            }
            if (BombermanGame.map.getMap()[toadoX][toadoY + r + 1] == 2
                    && r < radiusBomb) {
                explotionList.add(new Explotion(toadoX,toadoY + r + 1));
                return;
            }
        }
        if (way == 3) {
            for (int j = 1; j <= r; j++) {
                if (j == r)
                    explotionList.add(new Explotion(this.x - 32*j, this.y, way, true));
                else
                    explotionList.add(new Explotion(this.x - 32*j, this.y , way, false ));
            }
            if (BombermanGame.map.getMap()[toadoX - r - 1][toadoY] == 2
                    && r < radiusBomb) {
                explotionList.add(new Explotion(toadoX - r - 1, toadoY));
                return;
            }
        }
        if (way == 4) {
            for (int j = 1; j <= r; j++) {
                if (j == r)
                    explotionList.add(new Explotion(this.x + 32*j, this.y , way, true));
                else
                    explotionList.add(new Explotion(this.x + 32*j, this.y, way, false ));
            }
            if (BombermanGame.map.getMap()[toadoX + r + 1][toadoY] == 2
                    && r < radiusBomb) {
                explotionList.add(new Explotion(toadoX + r + 1, toadoY));
                return;
            }
        }
    }
    public void setWallExplotion(int way) {
        int toadoX = this.x / 32;
        int toadoY = this.y / 32;
        int r = this.getRealRadius(way);
        if (checkWall(way)) {
            if (way == 1) {
                BombermanGame.map.setMap(toadoX, toadoY - r - 1, 0);
                BombermanGame.stillObjects.set(toadoX * 13 + toadoY -r -1,new Collide(0,0,BombermanGame.map.getList().get(0).getFxImage()));
            }
            if (way == 2) {
                BombermanGame.map.setMap(toadoX, toadoY + r + 1, 0);
                BombermanGame.stillObjects.set(toadoX * 13 + toadoY + r +1,new Collide(0,0,BombermanGame.map.getList().get(0).getFxImage()));
            }
            if (way == 3) {
                BombermanGame.map.setMap(toadoX - 1 - r, toadoY, 0);
                BombermanGame.stillObjects.set((toadoX - r - 1) * 13 + toadoY,new Collide(0,0,BombermanGame.map.getList().get(0).getFxImage()));
            }
            if(way == 4) {
                BombermanGame.map.setMap(toadoX + 1 + r, toadoY, 0);
                BombermanGame.stillObjects.set((toadoX + r + 1) * 13 + toadoY,new Collide(0,0,BombermanGame.map.getList().get(0).getFxImage()));
            }
        }
    }

    public List<Explotion> getExplotionList() {
        return this.explotionList;
    }
    @Override
    public void render(GraphicsContext gc) {
        if (remove == false && isExplotion == false) {
            img = Sprite.movingSprite(Sprite.bomb_2, Sprite.bomb_1, Sprite.bomb,
                    60-(timeToExplode), 80).getFxImage();
            super.render(gc);
        } else if (isExplotion) {
            img = Sprite.movingSprite(Sprite.bomb_exploded, Sprite.bomb_exploded1, Sprite.bomb_exploded2,
                    60-timeAfterExplode, 80).getFxImage();
            super.render(gc);
            for(int i = 0; i < explotionList.size(); i++) {
                explotionList.get(i).render(gc);
            }
        }
    }

    public void update() {
        if (remove != true) {
            if (timeToExplode > 0) {
                timeToExplode--;
            } else {
                for(int i = 1; i <=4 ; i++) {
                        this.setWallExplotion(i);
                }
                for (int i = 0; i < explotionList.size(); i++) {
                    explotionList.get(i).update(timeAfterExplode);
                }
                isExplotion = true;
                if (timeAfterExplode > 0) {
                    timeAfterExplode--;
                } else {
                    for (int i = 0; i < explotionList.size(); i++) {
                        explotionList.get(i).setRemove();
                    }
                    setRemove();
                }
            }
        }
    }
}
