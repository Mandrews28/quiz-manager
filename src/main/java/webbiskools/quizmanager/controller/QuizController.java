package webbiskools.quizmanager.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import webbiskools.quizmanager.model.Answer;
import webbiskools.quizmanager.model.Question;
import webbiskools.quizmanager.model.Quiz;
import webbiskools.quizmanager.service.QuizService;

import javax.validation.Valid;
import java.util.Map;
import java.util.NoSuchElementException;

@CrossOrigin
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
    Iterable<Question> getOneQuiz(@PathVariable(value = "quiz-number") int quizOrderNum) {
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
            @PathVariable(value = "quiz-number") int quizOrderNum,
            @PathVariable(value = "question-number") int questionOrderNum) {
        try {
            return quizService.getQuestion(quizOrderNum, questionOrderNum);
        } catch (NoSuchElementException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

    @ApiOperation(value = "Create a quiz")
    @PostMapping("/quiz/quiz{quiz-number}")
    public @ResponseBody
    Iterable<Quiz> createQuiz(
            @PathVariable(value = "quiz-number") int quizOrderNum,
            @RequestBody Map<String, Object> quizInput) {
        try {
            return quizService.createQuiz(quizOrderNum, quizInput);
        } catch (NoSuchElementException | IllegalArgumentException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

    @ApiOperation(value = "Delete a quiz")
    @DeleteMapping("/quiz/quiz{quiz-number}")
    public @ResponseBody
    Iterable<Quiz> deleteQuiz(@PathVariable(value = "quiz-number") int quizOrderNum) {
        try {
            return quizService.deleteQuiz(quizOrderNum);
        } catch (NoSuchElementException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

    @ApiOperation(value = "Edit the title of a quiz")
    @PatchMapping("/quiz/quiz{quiz-number}")
    public @ResponseBody
    Iterable<Quiz> editQuizTitle(
            @PathVariable(value = "quiz-number") int quizOrderNum,
            @Valid @RequestBody Map<String, String> quizInput) {
        try {
            return quizService.editQuizTitle(quizOrderNum, quizInput);
        } catch (NoSuchElementException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

    @ApiOperation(value = "Add a question to a quiz")
    @PostMapping("/quiz/quiz{quiz-number}/question{question-number}")
    public @ResponseBody
    Iterable<Question> addQuestionToQuiz(
            @PathVariable(value = "quiz-number") int quizOrderNum,
            @PathVariable(value = "question-number") int questionOrderNum,
            @Valid @RequestBody Map<String, String> questionInput) {
        try {
            return quizService.addQuestionToQuiz(quizOrderNum, questionOrderNum, questionInput);
        } catch (NoSuchElementException | IllegalArgumentException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

    @ApiOperation(value = "Delete a question from a quiz")
    @DeleteMapping("/quiz/quiz{quiz-number}/question{question-number}")
    public @ResponseBody
    Iterable<Question> deleteQuestionFromQuiz(
            @PathVariable(value = "quiz-number") int quizOrderNum,
            @PathVariable(value = "question-number") int questionOrderNum) {
        try {
            return quizService.deleteQuestionFromQuiz(quizOrderNum, questionOrderNum);
        } catch (NoSuchElementException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

    @ApiOperation(value = "Edit the text of a question")
    @PatchMapping("/quiz/quiz{quiz-number}/question{question-number}")
    public @ResponseBody
    Iterable<Question> editQuestionValue(
            @PathVariable(value = "quiz-number") int quizOrderNum,
            @PathVariable(value = "question-number") int questionOrderNum,
            @Valid @RequestBody Map<String, String> questionInput) {
        try {
            return quizService.editQuestionValue(quizOrderNum, questionOrderNum, questionInput);
        } catch (NoSuchElementException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

    @ApiOperation(value = "Add an answer to a question")
    @PostMapping("/quiz/quiz{quiz-number}/question{question-number}/answer{answer-number}")
    public @ResponseBody
    Iterable<Answer> addAnswerToQuestion(
            @PathVariable(value = "quiz-number") int quizOrderNum,
            @PathVariable(value = "question-number") int questionOrderNum,
            @PathVariable(value = "answer-number") int answerOrderNum,
            @Valid @RequestBody Map<String, String> answerInput) {
        try {
            return quizService.addAnswerToQuestion(quizOrderNum, questionOrderNum, answerOrderNum, answerInput);
        } catch (NoSuchElementException | IllegalArgumentException | IllegalStateException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

    @ApiOperation(value = "Delete an answer from a question")
    @DeleteMapping("/quiz/quiz{quiz-number}/question{question-number}/answer{answer-number}")
    public @ResponseBody
    Iterable<Answer> deleteAnswerFromQuestion(
            @PathVariable(value = "quiz-number") int quizOrderNum,
            @PathVariable(value = "question-number") int questionOrderNum,
            @PathVariable(value = "answer-number") int answerOrderNum) {
        try {
            return quizService.deleteAnswerFromQuestion(quizOrderNum, questionOrderNum, answerOrderNum);
        } catch (NoSuchElementException | IllegalStateException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

    @ApiOperation(value = "Edit the text of an answer")
    @PatchMapping("/quiz/quiz{quiz-number}/question{question-number}/answer{answer-number}")
    public @ResponseBody
    Iterable<Answer> editAnswerValue(
            @PathVariable(value = "quiz-number") int quizOrderNum,
            @PathVariable(value = "question-number") int questionOrderNum,
            @PathVariable(value = "answer-number") int answerOrderNum,
            @Valid @RequestBody Map<String, String> answerInput) {
        try {
            return quizService.editAnswerValue(quizOrderNum, questionOrderNum, answerOrderNum, answerInput);
        } catch (NoSuchElementException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }
}
