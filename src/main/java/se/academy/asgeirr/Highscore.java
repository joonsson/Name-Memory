package se.academy.asgeirr;

public class Highscore implements Comparable<Highscore> {
    private int time;
    private String name;

    public Highscore(String name, int time) {
        this.time = time;
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public String getScore() {
        return name + " " + time/60000 + "min" + " " + (time%60000)/1000 + "sec";
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }

    public int compareTo(Highscore hs) {
        return this.time - hs.time;
    }
}

