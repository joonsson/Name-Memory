package se.academy.asgeirr;

import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Highscorelist {
    private List<Highscore> highscoreList;
    public Highscorelist () {
        highscoreList = new ArrayList<>();
        try {
            Resource hScore = new ClassPathResource("/static/text/highScore.txt");
            InputStream inputStream = hScore.getInputStream();
            BufferedReader in = new BufferedReader(new InputStreamReader(inputStream));
            String s = in.readLine();
            while (s != null) {
                Scanner scanner = new Scanner(s);
                highscoreList.add(new Highscore(scanner.next(), scanner.nextInt()));
                s = in.readLine();
            }
            inputStream.close();
            in.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public List<Highscore> getHighscoreList() {
        return highscoreList;
    }

    public void setHighscoreList(List<Highscore> highscoreList) {
        this.highscoreList = highscoreList;
    }
}
