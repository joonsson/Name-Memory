package se.academy.asgeirr;

import org.springframework.core.io.*;

import java.io.*;
import java.util.*;
import java.util.List;

public class Board {
    private static final int FRAME_WIDTH = 1280;
    private static final int FRAME_HEIGHT = 720;
    private static final int OPTIONS = 4;
    private static final int REGULARGAME = 0;
    private static final int PLAYONCE = 1;
    private static final int TIMEDGAME = 2;
    private static final int HIGHSCORESIZE = 5;
    private final long CREATED_AT;
    private List<Person> persons;
    private List<Person> allPersons;
    private List<String> femaleNames;
    private List<String> maleNames;
    private int wrongAnswers;
    private int timeBonus;
    private int time;
    private long startTime;
    private Random rand;
    private Person p;
    private Person p2;
    private List<String> answers;
    private List<Highscore> highScore;
    private String playerName;
    private String lastAnswer;
    private String correct;
    private String correctAnswer;
    private String lastCorrectAnswer;
    private ResourceLoader resourceLoader;
    private Highscorelist highscorelist;

    public void setResourceLoader(ResourceLoader resourceLoader) {
        this.resourceLoader = resourceLoader;
    }

    public Board(Highscorelist highScoreList) {
        this.highscorelist = highScoreList;
        highScore = this.highscorelist.getHighscoreList();
        CREATED_AT = System.currentTimeMillis();
        startTime = System.currentTimeMillis();
        try {
            allPersons = new ArrayList<>();
            Resource javatxt = new ClassPathResource("/static/text/java.txt");
            InputStream inputStream = javatxt.getInputStream();
            BufferedReader in = new BufferedReader(new InputStreamReader(inputStream));
            //BufferedReader in = new BufferedReader(new FileReader("classpath:static/text/java.txt"));
            String s = in.readLine();
            while (s != null) {
                Scanner scanner = new Scanner(s);
                String firstName = scanner.next();
                String lastName = scanner.next();
                Boolean female = scanner.nextBoolean();
                allPersons.add(new Person("../images/" + firstName + lastName + ".jpg", firstName, lastName, female));
                scanner.close();
                s = in.readLine();
            }
            inputStream.close();
            in.close();
            femaleNames = new ArrayList<>();
            Resource fNames = new ClassPathResource("/static/text/femaleNames.txt");
            inputStream = fNames.getInputStream();
            in = new BufferedReader(new InputStreamReader(inputStream));
            s = in.readLine();
            while (s != null) {
                femaleNames.add(s);
                s = in.readLine();
            }
            inputStream.close();
            in.close();
            maleNames = new ArrayList<>();
            Resource mNames = new ClassPathResource("/static/text/maleNames.txt");
            inputStream = mNames.getInputStream();
            in = new BufferedReader(new InputStreamReader(inputStream));
            s = in.readLine();
            while (s != null) {
                maleNames.add(s);
                s = in.readLine();
            }
            inputStream.close();
            in.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        persons = new ArrayList<>(allPersons);
        readHighscore();
        rand = new Random();
        Collections.sort(highScore);
        lastAnswer = "none";
        correct = "n";
    }

    public String game() {
        if (persons.size() < 1) {
            return gameOver();
        } else {
            int k;
            int j = rand.nextInt(persons.size());
            p = persons.get(j);
            answers = new ArrayList<>();
            int r = rand.nextInt(OPTIONS);
            answers.add(p.getFirstName());
            correctAnswer = p.getFirstName();
            while (true) {
                k = rand.nextInt(OPTIONS);
                if (k != r) {
                    break;
                }
            }
            while (true) {
                j = rand.nextInt(allPersons.size());
                p2 = allPersons.get(j);
                if (!p.equals(p2) && ((p.isFemale() && p2.isFemale()) || (!p.isFemale() && !p2.isFemale()))) {
                    answers.add(p2.getFirstName());
                    break;
                }
            }
            for (int i = 0; i < OPTIONS - 2; i++) {
                if (p.isFemale()) {
                    while (true) {
                        r = rand.nextInt(femaleNames.size());
                        if (!answers.contains(femaleNames.get(r))) {
                            answers.add(femaleNames.get(r));
                            break;
                        }
                    }
                } else {
                    while (true) {
                        r = rand.nextInt(maleNames.size());
                        if (!answers.contains(maleNames.get(r))) {
                            answers.add(maleNames.get(r));
                            break;
                        }
                    }
                }
            }
            Collections.shuffle(answers);
            StringBuilder answerBuilder = new StringBuilder();
            for (String a : answers) {
                answerBuilder.append(a);
                answerBuilder.append(":");
            }
            answerBuilder.append(p.getImage());
            checkTime();
            return answerBuilder.toString() + ":" + correct + ":" + lastAnswer + ":" + lastCorrectAnswer + ":" + time;
        }
    }

    public String answer(String a) {
        lastAnswer = a;
        if (correctAnswer.equals(a)) {
            correct = "y";
            persons.remove(p);
        } else {
            wrongAnswers++;
            timeBonus += 10000;
            correct = "n";
        }
        lastCorrectAnswer = correctAnswer;
        return game();
    }

    private void readHighscore() {
        highScore = highscorelist.getHighscoreList();
    }

    private String gameOver() {
        checkTime();
        String h = "n";
        readHighscore();
        Collections.sort(highScore);
        if (time < highScore.get(HIGHSCORESIZE - 1).getTime()) {
            h = "y";
            highScore.add(new Highscore(playerName, time));
            Collections.sort(highScore);
            highScore.remove(highScore.size() - 1);
            highscorelist.setHighscoreList(highScore);
        }
            return " : : : : :g:" + h + ":" + time;
    }

    public void checkTime() {
        time = (int) (System.currentTimeMillis() - startTime) + timeBonus;
    }

    //region Getters and Setters

    public long getCREATED_AT() {
        return CREATED_AT;
    }

    public String getPlayerName() {
        return playerName;
    }

    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }

    public List<Highscore> getHighScore() {
        return highScore;
    }

    public void setHighScore(List<Highscore> highScore) {
        this.highScore = highScore;
    }

    public int getTime() {
        return time;
    }

    //endregion
}
