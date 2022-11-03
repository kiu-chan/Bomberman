package uet.oop.bomberman;

import uet.oop.bomberman.entities.Interaction.Collision;

import java.awt.*;
import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.logging.Logger;

public class Point {
    public static final int TOP = 3;
    public int score = 0;
    public String name;
    public int time;
    public Point() {
    }

    public Point(String name, int score, int time) {
        this.name = name;
        this.score = score;
        this.time = time;
    }

    public List<Point> list = new ArrayList<>();

    public void addScore(int score) {
        this.score += score;
    }

    public int getScore() {
        return this.score;
    }

    public String getName() {
        return this.name;
    }

    public int getTime() {
        return this.time;
    }

    public void topScore(String path) {
        try {
            File file = new File(path);
            FileReader fileReader = new FileReader(file);
            BufferedReader reader = new BufferedReader(fileReader);

            for (int i = 0; i < TOP; i++) {
                String _name;
                int _score;
                int _time;
                _name = reader.readLine();
                _score = Integer.parseInt(reader.readLine());
                _time = Integer.parseInt(reader.readLine());
                list.add(new Point(_name, _score, _time));
                //list.add(this);
                //sortScore();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        /*for (int i = 0; i < TOP; i++) {
            writeScore(path, list.get(i).getName() + "/n");
            writeScore(path, list.get(i).getScore() + "/n");
            writeScore(path, list.get(i).getTime() + "/n");
        }

        for (int i = 0; i < TOP; i++) {
            System.out.println(list.get(i).getName());
        }*/
    }

    public void sortScore() {
        Collections.sort(list, new Comparator<Point>() {
            @Override
            public int compare(Point o1, Point o2) {
                if (o1.getScore() > o2.getScore()){
                    return 1;
                }

                if (o1.getScore() < o2.getScore()) {
                    return -1;
                }

                if (o1.getTime() > o2.getTime()) {
                    return 1;
                }

                if (o1.getTime() < o2.getTime()) {
                    return -1;
                }

                if (o1.getName().compareTo(o2.getName()) > 0) {
                    return 1;
                }

                if (o1.getName().compareTo(o2.getName()) < 0) {
                    return -1;
                }

                return 0;
            }
        });
    }

    public void writeScore(String path, String text) {
        try {
            FileWriter fileWriter = new FileWriter(path);
            fileWriter.write(text);
            fileWriter.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
