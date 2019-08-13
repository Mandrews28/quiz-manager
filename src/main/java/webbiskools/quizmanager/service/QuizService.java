package webbiskools.quizmanager.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import webbiskools.quizmanager.ErrorMessages;
import webbiskools.quizmanager.model.Answer;
import webbiskools.quizmanager.model.Question;
import webbiskools.quizmanager.model.Quiz;
import webbiskools.quizmanager.repository.AnswerRepository;
import webbiskools.quizmanager.repository.QuestionRepository;
import webbiskools.quizmanager.repository.QuizRepository;

import java.util.Map;
import java.util.NoSuchElementException;

@Service
public class QuizService {

    private QuizRepository quizRepository;
    private QuestionRepository questionRepository;
    private AnswerRepository answerRepository;

    @Autowired
    public QuizService(QuizRepository quizRepository, QuestionRepository questionRepository, AnswerRepository answerRepository) {
        this.quizRepository = quizRepository;
        this.questionRepository = questionRepository;
        this.answerRepository = answerRepository;
    }

    public Iterable<Quiz> getAllQuizzes() {
        return quizRepository.findAll();
    }

    public Iterable<Question> getQuiz(int quizOrderNum) {
        Quiz quiz = quizRepository.findByOrder(quizOrderNum);

        if (quiz == null) {
            throw new NoSuchElementException(ErrorMessages.quizNotFound(quizOrderNum));
        }

        return questionRepository.findAllByQuiz(quiz);
    }

    public Iterable<Answer> getQuestion(int quizOrderNum, int questionOrderNum) {
        Quiz quiz = quizRepository.findByOrder(quizOrderNum);
        if (quiz == null) {
            throw new NoSuchElementException(ErrorMessages.quizNotFound(quizOrderNum));
        }

        Question question = questionRepository.findByQuizAndOrder(quiz, questionOrderNum);
        if (question == null) {
            throw new NoSuchElementException(ErrorMessages.questionNotFound(quizOrderNum, questionOrderNum));
        }

        return answerRepository.findAllByQuestion(question);
    }

    public Iterable<Quiz> deleteQuiz(int quizOrderNum) {
        Quiz quiz = quizRepository.findByOrder(quizOrderNum);
        if (quiz == null) {
            throw new NoSuchElementException(ErrorMessages.quizNotFound(quizOrderNum));
        }

        quizRepository.delete(quiz);

        return quizRepository.findAll();
    }

    public Iterable<Question> addQuestionToQuiz(int quizOrderNum, int questionOrderNum,  Map<String, String> preferenceInput) {
        if (questionOrderNum < 1) {
            throw new IllegalArgumentException(ErrorMessages.questionOrderNumTooLow(questionOrderNum));
        }

        Quiz quiz = quizRepository.findByOrder(quizOrderNum);
        if (quiz == null) {
            throw new NoSuchElementException(ErrorMessages.quizNotFound(quizOrderNum));
        }

        String questionText = preferenceInput.get("value");

        Question currentLastQuestion = questionRepository.findFirstByQuizOrderByOrderDesc(quiz);
        int currentNumOfQuestions;
        if (currentLastQuestion == null) {
            currentNumOfQuestions = 0;
        } else {
            currentNumOfQuestions = questionRepository.findFirstByQuizOrderByOrderDesc(quiz).getOrder();
        }

        if (questionOrderNum > currentNumOfQuestions + 1) {
            throw new IllegalArgumentException(ErrorMessages.questionOrderNumTooHigh(quizOrderNum, questionOrderNum));
        } else if (questionOrderNum == currentNumOfQuestions + 1) {
        } else {
            for (int i = currentNumOfQuestions; i >= questionOrderNum; i--) {
                Question existingQuestion = questionRepository.findByQuizAndOrder(quiz, i);
                existingQuestion.setOrder(i + 1);
                questionRepository.save(existingQuestion);
            }
        }

        questionRepository.save(new Question(questionText, quiz, questionOrderNum));

        return questionRepository.findAllByQuiz(quiz);
    }
}
