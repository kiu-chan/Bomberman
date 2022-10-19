package uet.oop.bomberman.entities.Bomb;

import uet.oop.bomberman.entities.Entity;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import uet.oop.bomberman.BombermanGame;
import uet.oop.bomberman.entities.Bomber;
import uet.oop.bomberman.entities.Entity;
import uet.oop.bomberman.graphics.Sprite;
import uet.oop.bomberman.entities.Bomber;

import java.util.ArrayList;
import java.util.List;
public class Bomb extends Entity {
    private boolean isExplotion = false;
    private boolean remove = false;
    public static int radiusBomb = 5;
    private int timeToExplode = 80;
    private int timeAfterExplode = 60;
    private List<Explotion> explotionList = new ArrayList<>();
    public Bomb(int x, int y, Image img) {
        super(x, y, img);
    }

    public int getRealRadius(int way) {
        int cnt = 0;
        switch (way) {
            case 1 : {
                int toadoX = this.x / 32;
                int toadoY = this.y / 32 -1;
                while (cnt < radiusBomb) {
                    if (BombermanGame.map.getMap()[toadoX][toadoY - cnt] == 0) {
                        cnt++;
                    } else if (BombermanGame.map.getMap()[toadoX][toadoY - cnt] == 1)
                        return cnt;
                    else if (BombermanGame.map.getMap()[toadoX][toadoY - cnt] == 2) {
                     //   BombermanGame.map.setMap(toadoX, toadoY - cnt, 0);
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
                    //    BombermanGame.map.setMap(toadoX, toadoY + cnt, 0);
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
                      //  BombermanGame.map.setMap(toadoX-cnt, toadoY, 0);
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
                       // BombermanGame.map.setMap(toadoX+cnt, toadoY, 0);
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
        for (int i = 1; i <= 4; i++) {
            if (i == 1) {
                for (int j = 1; j <= this.getRealRadius(i); j++) {
                    if (j == this.getRealRadius(i))
                        explotionList.add(new Explotion(this.x, this.y -32*j, i, true));
                    else
                        explotionList.add(new Explotion(this.x, this.y -32*j, i, false ));
                }
            }
            if (i == 2) {
                for (int j = 1; j <= this.getRealRadius(i); j++) {
                    if (j == this.getRealRadius(i))
                        explotionList.add(new Explotion(this.x, this.y + 32*j, i, true));
                    else
                        explotionList.add(new Explotion(this.x, this.y + 32*j, i, false ));
                }
            }
            if (i == 3) {
                for (int j = 1; j <= this.getRealRadius(i); j++) {
                    if (j == this.getRealRadius(i))
                        explotionList.add(new Explotion(this.x - 32*j, this.y, i, true));
                    else
                        explotionList.add(new Explotion(this.x - 32*j, this.y , i, false ));
                }
            }
            if (i == 4) {
                for (int j = 1; j <= this.getRealRadius(i); j++) {
                    if (j == this.getRealRadius(i))
                        explotionList.add(new Explotion(this.x+32*j, this.y , i, true));
                    else
                        explotionList.add(new Explotion(this.x+32*j, this.y, i, false ));
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
            }
            else {
                for(int i = 0; i < explotionList.size(); i++) {
                    explotionList.get(i).update(timeAfterExplode);
                }
                }
                isExplotion = true;
                if (timeAfterExplode > 0) {
                    timeAfterExplode--;
                } else {
                    for(int i = 0; i < explotionList.size(); i++) {
                        System.out.println("co the phai");
                        explotionList.get(i).setRemove();
                    }
                    setRemove();
                }
            }
        }
}
