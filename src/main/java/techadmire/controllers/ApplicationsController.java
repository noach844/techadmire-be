package techadmire.controllers;

import jakarta.servlet.ServletException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import techadmire.Services.ApplicationsService;
import techadmire.dto.ApplicationDTO;
import techadmire.dto.RegisterDTO;
import techadmire.models.ApplicationEntity;
import techadmire.models.UserEntity;
import techadmire.repositories.ApplicationsRepository;
import techadmire.repositories.UsersRepository;
import techadmire.security.JWTGenerator;

import java.util.List;
import java.util.Optional;

@RestController()
@RequestMapping("applications")
public class ApplicationsController {

    private final ApplicationsService applicationsService;
    private final JWTGenerator jwtGenerator;

    @Autowired
    public ApplicationsController(ApplicationsService applicationsService, JWTGenerator jwtGenerator) {
        this.applicationsService = applicationsService;
        this.jwtGenerator = jwtGenerator;
    }

    @PostMapping("/")
    public ResponseEntity<String> postApplication(@RequestBody ApplicationDTO applicationDTO) throws Exception {
       String res = this.applicationsService.CreateApplication(applicationDTO);
       return new ResponseEntity<>(res, HttpStatus.OK);
    }

    @GetMapping("/")
    public ResponseEntity<List<ApplicationEntity>> getApplications(@RequestHeader("Authorization") String authorizationHeader) {
        String username = jwtGenerator.getUsernameFromJWT(authorizationHeader.substring(7, authorizationHeader.length()));
        List<ApplicationEntity> res = this.applicationsService.findApplicationsByUser(username);
        return new ResponseEntity<>(res, HttpStatus.OK);
    }
}
