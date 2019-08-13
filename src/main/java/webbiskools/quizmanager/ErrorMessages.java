package webbiskools.quizmanager;

public class ErrorMessages {

    private static final String VALUE_NOT_IN_DB = " does not exist in the database";

    public static String quizNotFound(int quizNum) {
        return "Quiz number " + quizNum + VALUE_NOT_IN_DB;
    }

    public static String questionNotFound(int quizNum, int questionNum) {
        return "Question number " + questionNum + " of quiz number " + quizNum + VALUE_NOT_IN_DB;
    }
}
