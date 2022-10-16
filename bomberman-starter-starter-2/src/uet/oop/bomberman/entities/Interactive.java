package uet.oop.bomberman.entities;


import java.util.ArrayList;
import java.util.List;

public class Interactive {
    public List<Entity> list = new ArrayList<>();
    Collision collision = new Collision();
    public List<Entity> remoteItem(Entity a, List<Entity> list) {
        try {
            for (int i = 0; i < list.size(); i++) {
                if(collision.CheckCollision(a.x, a.y, list.get(i).x, list.get(i).y)) {
                    list.remove(i);
                    //System.out.println(a.x + " " +  a.y + " " + list.get(i).x + " " + list.get(i).y);
                }
            }
        } catch (Exception e) {
            return list;
        }
        return list;
    }
}
