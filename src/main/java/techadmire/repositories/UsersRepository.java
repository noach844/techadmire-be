package techadmire.repositories;

import org.springframework.data.repository.CrudRepository;
import techadmire.models.User;

public interface UsersRepository extends CrudRepository<User, Integer> {
}
