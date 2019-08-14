package webbiskools.quizmanager.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import webbiskools.quizmanager.model.Answer;
import webbiskools.quizmanager.model.Question;

@RepositoryRestResource
public interface AnswerRepository extends CrudRepository<Answer, Long> {
    Iterable<Answer> findAllByQuestion(Question question);

    Answer findByQuestionAndOrder(Question question, int answerOrder);

    Answer findFirstByQuestionOrderByOrderDesc(Question question);

    void deleteAllByQuestion(Question question);
}
