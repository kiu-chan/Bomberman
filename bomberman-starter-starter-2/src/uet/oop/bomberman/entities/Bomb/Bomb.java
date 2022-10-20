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
    public static int radiusBomb = 2;
    private int timeToExplode = 80;
    private int timeAfterExplode = 60;
    private List<Explotion> explotionList = new ArrayList<>();

    public Bomb(int x, int y, Image img) {
        super(x, y, img);
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
        int tmp = 0;
        switch (way) {
            case 1 : {
                int toadoX = this.x / 32;
                int toadoY = this.y / 32 -1;
                while (cnt < radiusBomb ) {
                    if (BombermanGame.map.getMap()[toadoX][toadoY - cnt] == 0
                            && BombermanGame.map.getMap()[toadoX][toadoY - cnt + 1] != 2) {
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
                while ( cnt < radiusBomb) {
                    if (BombermanGame.map.getMap()[toadoX][toadoY + cnt] == 0 &&
                            BombermanGame.map.getMap()[toadoX][toadoY + cnt - 1] != 2) {
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
                    } else if (BombermanGame.map.getMap()[toadoX + cnt][toadoY] == 1)
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
    public void makeExplotion() {
        int toadoX = this.x / 32;
        int toadoY = this.y / 32;
        for (int i = 1; i <= 4; i++) {
            if (i == 1) {
                int r = this.getRealRadius(i);
                for (int j = 1; j <= this.getRealRadius(i); j++) {
                    if (j == this.getRealRadius(i))
                        explotionList.add(new Explotion(this.x, this.y -32*j, i, true));
                    else
                        explotionList.add(new Explotion(this.x, this.y -32*j, i, false ));
                }
                if (BombermanGame.map.getMap()[toadoX][toadoY - this.getRealRadius(i) - 1] == 2
                        && this.getRealRadius(i) < radiusBomb) {
                    BombermanGame.map.setMap(toadoX, toadoY - this.getRealRadius(i) - 1, 0);
                    explotionList.add(new Explotion(this.x/32, this.y /32 -r -1));
                    BombermanGame.stillObjects.set(this.x/32 * 13 + this.y /32 -r -1,new Collide(0,0,BombermanGame.map.getList().get(0).getFxImage()));
                }
            }
            if (i == 2) {
                int r = this.getRealRadius(i);
                for (int j = 1; j <= this.getRealRadius(i); j++) {
                    if  (j == this.getRealRadius(i)) {
                        explotionList.add(new Explotion(this.x, this.y + 32 * j, i, true));
                    } else {
                        explotionList.add(new Explotion(this.x, this.y + 32 * j, i, false));
                    }
                }
                if (BombermanGame.map.getMap()[toadoX][toadoY + this.getRealRadius(i) + 1] == 2
                        && this.getRealRadius(i) < radiusBomb) {
                    BombermanGame.map.setMap(toadoX, toadoY + this.getRealRadius(i) + 1, 0);
                    explotionList.add(new Explotion(this.x/32,this.y /32 + r +1));
                    BombermanGame.stillObjects.set(this.x/32 * 13 + this.y /32 + r +1,new Collide(0,0,BombermanGame.map.getList().get(0).getFxImage()));
                }
            }
            if (i == 3) {
                int r = this.getRealRadius(i);
                for (int j = 1; j <= this.getRealRadius(i); j++) {
                    if (j == this.getRealRadius(i))
                        explotionList.add(new Explotion(this.x - 32*j, this.y, i, false));
                    else
                        explotionList.add(new Explotion(this.x - 32*j, this.y , i, true ));
                }
                if (BombermanGame.map.getMap()[toadoX - this.getRealRadius(i) - 1][toadoY] == 2
                        && this.getRealRadius(i) < radiusBomb) {
                    BombermanGame.map.setMap(toadoX - 1 - this.getRealRadius(i), toadoY, 0);
                    explotionList.add(new Explotion(this.x/32 - r -1, this.y/32));
                    BombermanGame.stillObjects.set((toadoX -r -1) * 13 + this.y /32,new Collide(0,0,BombermanGame.map.getList().get(0).getFxImage()));
                }
            }
            if (i == 4) {
                int r = this.getRealRadius(i);
                for (int j = 1; j <= this.getRealRadius(i); j++) {
                    if (j == this.getRealRadius(i))
                        explotionList.add(new Explotion(this.x+32*j, this.y , i, true));
                    else
                        explotionList.add(new Explotion(this.x+32*j, this.y, i, false ));
                }
                if (BombermanGame.map.getMap()[toadoX + this.getRealRadius(i) + 1][toadoY] == 2
                        && this.getRealRadius(i) < radiusBomb) {
                    BombermanGame.map.setMap(toadoX + this.getRealRadius(i) + 1, toadoY, 0);
                    explotionList.add(new Explotion(this.x/32 + r +1, this.y/32));
                    BombermanGame.stillObjects.set((toadoX + r + 1) * 13 + this.y /32,new Collide(0,0,BombermanGame.map.getList().get(0).getFxImage()));
                }
            }
        }
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
