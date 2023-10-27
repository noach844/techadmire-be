package techadmire.Services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import techadmire.dto.ApplicationDTO;
import techadmire.models.ApplicationEntity;
import techadmire.models.UserEntity;
import techadmire.repositories.ApplicationsRepository;
import techadmire.repositories.UsersRepository;
import techadmire.security.JWTGenerator;

import java.util.List;

@Service
public class ApplicationsService {
    private JWTGenerator jwtGenerator;
    private UsersRepository usersRepository;
    private ApplicationsRepository applicationsRepository;

    @Autowired
    public ApplicationsService(JWTGenerator jwtGenerator, UsersRepository usersRepository, ApplicationsRepository applicationsRepository) {
        this.jwtGenerator = jwtGenerator;
        this.usersRepository = usersRepository;
        this.applicationsRepository = applicationsRepository;
    }

    public String CreateApplication(ApplicationDTO applicationDTO){
        UserEntity user = new UserEntity();
        user.setId(applicationDTO.getUser_id());
        ApplicationEntity applicationEntity =
                new ApplicationEntity(applicationDTO.getFullname(), applicationDTO.getUniversity(), applicationDTO.getCourse(), user);
        applicationsRepository.save(applicationEntity);
        return "Success";
    }

    public List<ApplicationEntity> findApplicationsByUser(String username){
        UserEntity user = usersRepository.findByUsername(username).orElse(null);
        if(user != null){
            return user.getApplications();
        }

        return null;
    }
}
