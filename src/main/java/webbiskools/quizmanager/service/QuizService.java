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

    public static final int MINIMUM_NUMBER_OF_ANSWERS = 3;
    public static final int MAXIMUM_NUMBER_OF_ANSWERS = 5;

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
        Quiz quiz = findQuizByOrder(quizOrderNum);

        quizRepository.delete(quiz);

        int orderOfLastQuiz = getOrderNumOfLastQuiz();

        if (quizOrderNum < orderOfLastQuiz) {
            for (int i = quizOrderNum + 1; i <= orderOfLastQuiz; i++) {
                Quiz existingQuiz = quizRepository.findByOrder(i);
                existingQuiz.setOrder(i - 1);
                quizRepository.save(existingQuiz);
            }
        }

        return quizRepository.findAll();
    }

    public Iterable<Question> addQuestionToQuiz(int quizOrderNum, int questionOrderNum, Map<String, String> questionInput) {
        if (questionOrderNum < 1) {
            throw new IllegalArgumentException(ErrorMessages.questionOrderNumTooLow(questionOrderNum));
        }
        Quiz quiz = findQuizByOrder(quizOrderNum);

        String questionText = questionInput.get("value");

        int orderOfLastQuestion = getOrderNumOfLastQuestion(quiz);

        if (questionOrderNum > orderOfLastQuestion + 1) {
            throw new IllegalArgumentException(ErrorMessages.questionOrderNumTooHigh(quizOrderNum, questionOrderNum));
        } else if (questionOrderNum == orderOfLastQuestion + 1) {
        } else {
            for (int i = orderOfLastQuestion; i >= questionOrderNum; i--) {
                Question existingQuestion = questionRepository.findByQuizAndOrder(quiz, i);
                existingQuestion.setOrder(i + 1);
                questionRepository.save(existingQuestion);
            }
        }

        questionRepository.save(new Question(questionText, quiz, questionOrderNum));

        return questionRepository.findAllByQuiz(quiz);
    }

    public Iterable<Question> deleteQuestionFromQuiz(int quizOrderNum, int questionOrderNum) {
        Quiz quiz = findQuizByOrder(quizOrderNum);
        Question question = findQuestionByQuizAndOrder(quiz, questionOrderNum);

        questionRepository.delete(question);

        int orderOfLastQuestion = getOrderNumOfLastQuestion(quiz);

        if (questionOrderNum < orderOfLastQuestion) {
            for (int i = questionOrderNum + 1; i <= orderOfLastQuestion; i++) {
                Question existingQuestion = questionRepository.findByQuizAndOrder(quiz, i);
                existingQuestion.setOrder(i - 1);
                questionRepository.save(existingQuestion);
            }
        }

        return questionRepository.findAllByQuiz(quiz);
    }

    public Iterable<Question> editQuestionValue(int quizOrderNum, int questionOrderNum, Map<String, String> questionInput) {
        Quiz quiz = findQuizByOrder(quizOrderNum);
        Question question = findQuestionByQuizAndOrder(quiz, questionOrderNum);

        String newQuestionText = questionInput.get("value");

        question.setValue(newQuestionText);
        questionRepository.save(question);

        return questionRepository.findAllByQuiz(quiz);
    }


    public Iterable<Answer> addAnswerToQuestion(int quizOrderNum, int questionOrderNum, int answerOrderNum, Map<String, String> answerInput) {
        if (answerOrderNum < 1) {
            throw new IllegalArgumentException(ErrorMessages.answerOrderNumTooLow(answerOrderNum));
        } else if (answerOrderNum > MAXIMUM_NUMBER_OF_ANSWERS) {
            throw new IllegalArgumentException(ErrorMessages.answerOrderNumExceedsMaximum(answerOrderNum));
        }

        Quiz quiz = findQuizByOrder(quizOrderNum);
        Question question = findQuestionByQuizAndOrder(quiz, questionOrderNum);

        String answerText = answerInput.get("value");

        int orderOfLastAnswer = getOrderNumOfLastAnswer(question);

        if (orderOfLastAnswer >= 5) {
            throw new IllegalArgumentException(ErrorMessages.questionIsAtMaximumAnswerLimit(quizOrderNum, questionOrderNum));
        } else if (answerOrderNum > orderOfLastAnswer + 1) {
            throw new IllegalArgumentException(ErrorMessages.answerOrderNumTooHigh(quizOrderNum, questionOrderNum, answerOrderNum));
        } else if (answerOrderNum == orderOfLastAnswer + 1) {
        } else {
            for (int i = orderOfLastAnswer; i >= answerOrderNum; i--) {
                Answer existingAnswer = answerRepository.findByQuestionAndOrder(question, i);
                existingAnswer.setOrder(i + 1);
                answerRepository.save(existingAnswer);
            }
        }

        answerRepository.save(new Answer(answerText, question, answerOrderNum));

        return answerRepository.findAllByQuestion(question);
    }

    private Quiz findQuizByOrder(int quizOrderNum) throws NoSuchElementException {
        Quiz quiz = quizRepository.findByOrder(quizOrderNum);
        if (quiz == null) {
            throw new NoSuchElementException(ErrorMessages.quizNotFound(quizOrderNum));
        }

        return quiz;
    }

    private Question findQuestionByQuizAndOrder(Quiz quiz, int questionOrderNum) throws NoSuchElementException {
        Question question = questionRepository.findByQuizAndOrder(quiz, questionOrderNum);
        if (question == null) {
            throw new NoSuchElementException(ErrorMessages.questionNotFound(quiz.getOrder(), questionOrderNum));
        }

        return question;
    }

    private int getOrderNumOfLastQuiz() {
        Quiz currentLastQuiz = quizRepository.findFirstByOrderByOrderDesc();
        int orderOfLastQuiz;
        if (currentLastQuiz == null) {
            orderOfLastQuiz = 0;
        } else {
            orderOfLastQuiz = currentLastQuiz.getOrder();
        }

        return orderOfLastQuiz;
    }

    private int getOrderNumOfLastQuestion(Quiz quiz) {
        Question currentLastQuestion = questionRepository.findFirstByQuizOrderByOrderDesc(quiz);
        int orderOfLastQuestion;
        if (currentLastQuestion == null) {
            orderOfLastQuestion = 0;
        } else {
            orderOfLastQuestion = currentLastQuestion.getOrder();
        }

        return orderOfLastQuestion;
    }

    private int getOrderNumOfLastAnswer(Question question) {
        Answer currentLastAnswer = answerRepository.findFirstByQuestionOrderByOrderDesc(question);
        int orderOfLastAnswer;
        if (currentLastAnswer == null) {
            orderOfLastAnswer = 0;
        } else {
            orderOfLastAnswer = currentLastAnswer.getOrder();
        }

        return orderOfLastAnswer;
    }
}
