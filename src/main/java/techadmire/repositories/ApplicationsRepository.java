package techadmire.repositories;

import org.springframework.data.repository.CrudRepository;
import techadmire.models.ApplicationEntity;

import java.util.List;


public interface ApplicationsRepository extends CrudRepository<ApplicationEntity, Integer> {
    List<ApplicationEntity> findByUser_Id(Long userId);
}
