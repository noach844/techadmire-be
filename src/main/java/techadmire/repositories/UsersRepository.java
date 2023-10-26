package techadmire.repositories;

import org.springframework.data.repository.CrudRepository;
import techadmire.models.UserEntity;

import java.util.Optional;

public interface UsersRepository extends CrudRepository<UserEntity, Integer> {
    Optional<UserEntity> findByUsername(String username);
}
