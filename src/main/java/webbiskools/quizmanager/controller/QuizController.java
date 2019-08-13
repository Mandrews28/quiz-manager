package webbiskools.quizmanager.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
import webbiskools.quizmanager.model.Answer;
import webbiskools.quizmanager.model.Question;
import webbiskools.quizmanager.model.Quiz;
import webbiskools.quizmanager.service.QuizService;

import java.util.List;
import java.util.NoSuchElementException;

@Api(description = "API to get, create, delete and modify quizzes")
@RestController
public class QuizController {

    private QuizService quizService;

    @Autowired
    public QuizController(QuizService quizService) {
        this.quizService = quizService;
    }

    @ApiOperation(value = "Get list of quizzes")
    @GetMapping("/quizzes")
    public @ResponseBody
    Iterable<Quiz> getQuizzes() {
        return quizService.getAllQuizzes();
    }

    @ApiOperation(value = "Get a specific quiz with all questions")
    @GetMapping("/quiz/quiz{quiz-number}")
    public @ResponseBody
    Iterable<Question> getOneQuiz(@PathVariable (value = "quiz-number") int quizOrderNum) {
        try {
            return quizService.getQuiz(quizOrderNum);
        } catch (NoSuchElementException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

    @ApiOperation(value = "Get all answers to a specific question")
    @GetMapping("/quiz/quiz{quiz-number}/question{question-number}")
    public @ResponseBody
    Iterable<Answer> getOneQuestion(
            @PathVariable (value = "quiz-number") int quizOrderNum,
            @PathVariable (value = "question-number") int questionOrderNum) {
        try {
            return quizService.getQuestion(quizOrderNum, questionOrderNum);
        } catch (NoSuchElementException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

}