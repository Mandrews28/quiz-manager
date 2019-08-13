package webbiskools.quizmanager;

public class ErrorMessages {

    private static final String VALUE_NOT_IN_DB = "does not exist in the database";
    private static final String ORDER_NUM_TOO_HIGH = "is too high and out of sequence. If you want to add a question " +
            "to the end of the quiz, the order should equal the number of questions plus one";
    private static final String ORDER_NUM_TOO_LOW = "is too low. The question order cannot be 0 or below because the " +
            "first question has an order number of 1";


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
        return "The question number " + questionNum + " " + ORDER_NUM_TOO_LOW;
    }

    private static String quizAndQuestionNumErrorStart(int quizNum, int questionNum) {
        return "Question number " + questionNum + " of quiz number " + quizNum + " ";
    }
}
