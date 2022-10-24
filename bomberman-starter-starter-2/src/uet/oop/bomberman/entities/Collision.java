package uet.oop.bomberman.entities;

import uet.oop.bomberman.graphics.Sprite;

public class Collision {

    public Collision() {

    }

    public boolean CheckCollision(Entity a, Entity b) {
        int left_a = a.x;
        int right_a = a.x + a.w;
        int top_a = a.y;
        int bottom_a = a.y + a.h;

        int left_b = b.x;
        int right_b = b.x + b.w;
        int top_b = b.y;
        int bottom_b = b.y + b.h;

        // Case 1: size object 1 < size object 2
        if (left_a > left_b && left_a < right_b)
        {
            if (top_a > top_b && top_a < bottom_b)
            {
                return true;
            }
        }

        if (left_a > left_b && left_a < right_b)
        {
            if (bottom_a > top_b && bottom_a < bottom_b)
            {
                return true;
            }
        }

        if (right_a > left_b && right_a < right_b)
        {
            if (top_a > top_b && top_a < bottom_b)
            {
                return true;
            }
        }

        if (right_a > left_b && right_a < right_b)
        {
            if (bottom_a > top_b && bottom_a < bottom_b)
            {
                return true;
            }
        }

        // Case 2: size object 1 < size object 2
        if (left_b > left_a && left_b < right_a)
        {
            if (top_b > top_a && top_b < bottom_a)
            {
                return true;
            }
        }

        if (left_b > left_a && left_b < right_a)
        {
            if (bottom_b > top_a && bottom_b < bottom_a)
            {
                return true;
            }
        }

        if (right_b > left_a && right_b < right_a)
        {
            if (top_b > top_a && top_b < bottom_a)
            {
                return true;
            }
        }

        if (right_b > left_a && right_b < right_a)
        {
            if (bottom_b > top_a && bottom_b < bottom_a)
            {
                return true;
            }
        }

        // Case 3: size object 1 = size object 2
        if (top_a == top_b && right_a == right_b && bottom_a == bottom_b)
        {
            return true;
        }
        return false;
    }

    public boolean CheckMapCollision(int x1, int y1, int w1, int h1, int[][] map)
    {
        int left_a = (x1 + 1) / Sprite.SCALED_SIZE;
        int right_a = (x1 + w1 - 1) / Sprite.SCALED_SIZE;
        int top_a = (y1 + 1) / Sprite.SCALED_SIZE;
        int bottom_a = (y1 + h1 - 1) / Sprite.SCALED_SIZE;
       // System.out.println(left_a + " " + top_a);

        try {
            if (map[left_a][top_a] != 0)
                return true;
            if (map[left_a][bottom_a] != 0)
                return true;
            if (map[right_a][top_a] != 0)
                return true;
            if (map[right_a][bottom_a] != 0)
                return true;
        } catch (Exception e) {
            return false;
        }
        return false;
    }
}
