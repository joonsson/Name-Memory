package se.academy.asgeirr;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;


@Controller
public class IndexController {
    private static Highscorelist highScore;

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String index(Model model, HttpSession session) {
        if (highScore == null) {
            highScore = new Highscorelist();
        }
            /*session.setAttribute("board", new Board(highScore, "both"));
            Board board = (Board) session.getAttribute("board");
            for (int i = 0; i < 5; i++) {
                model.addAttribute("highScore" + i, board.getHighScore().get(i).getScore());
            }*/
        return "index";
    }

    @RequestMapping(value = "/", method = RequestMethod.POST, produces = "text/plain", params = {"name", "klass"})
    @ResponseBody
    public String startGame(@RequestParam(value = "name", defaultValue = "Unknown") String name, @RequestParam(value = "klass", defaultValue = "both") String klass, HttpSession session) {
        session.setAttribute("board", new Board(highScore, klass));
        Board board = (Board) session.getAttribute("board");
        board.setPlayerName(name);
        return board.game();
    }

    @RequestMapping(value = "/", method = RequestMethod.POST, produces = "text/plain", params = "answer")
    @ResponseBody
    public String answer(@RequestParam(value = "answer") String answer, HttpSession session, Model model) {
        Board board = (Board) session.getAttribute("board");
        return board.answer(answer);
    }
}