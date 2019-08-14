package webbiskools.quizmanager;

import webbiskools.quizmanager.service.QuizService;

public class ErrorMessages {

    private static final String VALUE_NOT_IN_DB = "does not exist in the database";
    private static final String ORDER_NUM_TOO_HIGH = "is too high and out of sequence. If you want to add a question " +
            "to the end of the quiz, the order should equal the number of questions plus one";
    private static final String ORDER_NUM_TOO_LOW = " is too low. The order value cannot be 0 or below because the " +
            "values start at 1 and increase incrementally";


    public static String quizNotFound(int quizNum) {
        return "Quiz number " + quizNum + " " + VALUE_NOT_IN_DB;
    }

    public static String questionNotFound(int quizNum, int questionNum) {
        return quizAndQuestionNumErrorStart(quizNum, questionNum) + VALUE_NOT_IN_DB;
    }

    public static String questionOrderNumTooHigh(int quizNum, int questionNum) {
        return quizAndQuestionNumErrorStart(quizNum, questionNum) + ORDER_NUM_TOO_HIGH;
    }

    public static String questionOrderNumTooLow(int questionNum) {
        return "The question number " + questionNum + ORDER_NUM_TOO_LOW;
    }

    public static String answerOrderNumTooHigh(int quizNum, int questionNum, int answerNum) {
        return quizAndQuestionAndAnswerNumErrorStart(quizNum, questionNum, answerNum) + ORDER_NUM_TOO_HIGH;
    }

    public static String answerOrderNumTooLow(int answerNum) {
        return "The answer number " + answerNum + ORDER_NUM_TOO_LOW;
    }

    public static String answerOrderNumExceedsMaximum(int answerNum) {
        return "The answer number " + answerNum + " exceeds the maximum number of answers that a question can have. " +
                "The maximum is " + QuizService.MAXIMUM_NUMBER_OF_ANSWERS;
    }

    public static String questionIsAtMaximumAnswerLimit(int quizNum, int questionNum) {
        return quizAndQuestionNumErrorStart(quizNum, questionNum) + "already has the maxmimum " +
                "number of answers of " + QuizService.MAXIMUM_NUMBER_OF_ANSWERS + ". Unable to add another answer";
    }


    private static String quizAndQuestionNumErrorStart(int quizNum, int questionNum) {
        return "Question number " + questionNum + " of quiz number " + quizNum + " ";
    }

    private static String quizAndQuestionAndAnswerNumErrorStart(int quizNum, int questionNum, int answerNum) {
        return "Answer number " + answerNum + " of question number " + questionNum + " of quiz number " + quizNum + " ";
    }
}
