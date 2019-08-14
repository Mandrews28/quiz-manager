package webbiskools.quizmanager.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import webbiskools.quizmanager.model.Quiz;

@RepositoryRestResource
public interface QuizRepository extends CrudRepository<Quiz, Long> {
    Iterable<Quiz> findAllByOrderByOrderAsc();

    Quiz findByOrder(int order);

    Quiz findFirstByOrderByOrderDesc();
}
