package se.academy.asgeirr;

import org.springframework.core.io.*;

import java.io.*;
import java.util.*;
import java.util.List;

public class Board {
    private static final int OPTIONS = 4;
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
    private Highscorelist highscorelist;
    private String klass;

    public Board(Highscorelist highScoreList, String klass) {
        this.klass = klass;
        this.highscorelist = highScoreList;
        highScore = this.highscorelist.getHighscoreList();
        CREATED_AT = System.currentTimeMillis();
        startTime = System.currentTimeMillis();
        try {
            InputStream inputStream;
            BufferedReader in;
            String s;
            allPersons = new ArrayList<>();
            if (klass.equals("java") || klass.equals("both")) {
                Resource javatxt = new ClassPathResource("/static/text/java.txt");
                inputStream = javatxt.getInputStream();
                in = new BufferedReader(new InputStreamReader(inputStream));
                s = in.readLine();
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
            }
            if (klass.equals("csharp") || klass.equals("both")) {
                Resource csharptxt = new ClassPathResource("/static/text/csharp.txt");
                inputStream = csharptxt.getInputStream();
                in = new BufferedReader(new InputStreamReader(inputStream));
                s = in.readLine();
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
            }
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
            maleNames.remove(0);
            maleNames.add(0, "Bo");
            inputStream.close();
            in.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        persons = new ArrayList<>(allPersons);
        //readHighscore();
        rand = new Random();
        Collections.sort(highScore);
        lastAnswer = "none";
        correct = "n";
        wrongAnswers = 0;
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
            return answerBuilder.toString() + ":" + correct + ":" + lastAnswer + ":" + lastCorrectAnswer + ":" + wrongAnswers;
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
        /*readHighscore();
        Collections.sort(highScore);
        if (klass.equals("both") && time < highScore.get(HIGHSCORESIZE - 1).getTime()) {
            h = "y";
            highScore.add(new Highscore(playerName, time));
            Collections.sort(highScore);
            highScore.remove(highScore.size() - 1);
            highscorelist.setHighscoreList(highScore);
        }*/
            return " : : : : :g:" + h + ":" + wrongAnswers;
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
