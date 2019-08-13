package webbiskools.quizmanager.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import webbiskools.quizmanager.model.Question;
import webbiskools.quizmanager.model.Quiz;

@RepositoryRestResource
public interface QuestionRepository extends CrudRepository<Question, Long> {
    Iterable<Question> findAllByQuiz(Quiz quiz);

    Question findByQuizAndOrder(Quiz quiz, int questionOrder);

}
