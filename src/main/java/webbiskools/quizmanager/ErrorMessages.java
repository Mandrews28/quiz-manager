package webbiskools.quizmanager;

import webbiskools.quizmanager.service.QuizService;

public class ErrorMessages {

    private static final String VALUE_NOT_IN_DB = "does not exist in the database";
    private static final String ORDER_NUM_TOO_HIGH = "is too high and out of sequence. The order number cannot be " +
            "more than one larger than the current maximum as order numbers should be sequential";
    private static final String ORDER_NUM_TOO_LOW = "is too low. The order value cannot be 0 or below because the " +
            "values start at 1 and increase incrementally";


    public static String quizNotFound(int quizNum) {
        return quizNumErrorStart(quizNum) + VALUE_NOT_IN_DB;
    }

    public static String questionNotFound(int quizNum, int questionNum) {
        return quizAndQuestionNumErrorStart(quizNum, questionNum) + VALUE_NOT_IN_DB;
    }

    public static String answerNotFound(int quizNum, int questionNum, int answerNum) {
        return quizAndQuestionAndAnswerNumErrorStart(quizNum, questionNum, answerNum) + VALUE_NOT_IN_DB;
    }

    public static String quizOrderNumTooHigh(int quizNum) {
        return quizNumErrorStart(quizNum) + ORDER_NUM_TOO_HIGH;
    }

    public static String questionOrderNumTooHigh(int quizNum, int questionNum) {
        return quizAndQuestionNumErrorStart(quizNum, questionNum) + ORDER_NUM_TOO_HIGH;
    }

    public static String answerOrderNumTooHigh(int quizNum, int questionNum, int answerNum) {
        return quizAndQuestionAndAnswerNumErrorStart(quizNum, questionNum, answerNum) + ORDER_NUM_TOO_HIGH;
    }

    public static String quizOrderNumTooLow(int quizNum) {
        return quizNumErrorStart(quizNum) + ORDER_NUM_TOO_LOW;
    }

    public static String questionOrderNumTooLow(int questionNum) {
        return questionNumErrorStart(questionNum) + ORDER_NUM_TOO_LOW;
    }

    public static String answerOrderNumTooLow(int answerNum) {
        return answerNumErrorStart(answerNum) + ORDER_NUM_TOO_LOW;
    }

    public static String answerOrderNumExceedsMaximum(int answerNum) {
        return answerNumErrorStart(answerNum) + "exceeds the maximum number of answers that a question can have. " +
                "The maximum is " + QuizService.MAXIMUM_NUMBER_OF_ANSWERS;
    }

    public static String questionIsAtMaximumAnswerLimit(int quizNum, int questionNum) {
        return quizAndQuestionNumErrorStart(quizNum, questionNum) + "already has the maximum " +
                "number of answers of " + QuizService.MAXIMUM_NUMBER_OF_ANSWERS + ". Unable to add another answer";
    }

    public static String questionIsAtMinimumAnswerLimit(int quizNum, int questionNum) {
        return quizAndQuestionNumErrorStart(quizNum, questionNum) + "has the minimum number of answers of " +
                QuizService.MINIMUM_NUMBER_OF_ANSWERS + ". Unable to delete an answer";
    }


    private static String quizNumErrorStart(int quizNum) {
        return "The quiz number " + quizNum + " ";
    }

    private static String questionNumErrorStart(int questionNum) {
        return "The question number " + questionNum + " ";
    }

    private static String answerNumErrorStart(int answerNum) {
        return "The answer number " + answerNum + " ";
    }

    private static String quizAndQuestionNumErrorStart(int quizNum, int questionNum) {
        return "Question number " + questionNum + " of quiz number " + quizNum + " ";
    }

    private static String quizAndQuestionAndAnswerNumErrorStart(int quizNum, int questionNum, int answerNum) {
        return "Answer number " + answerNum + " of question number " + questionNum + " of quiz number " + quizNum + " ";
    }
}
