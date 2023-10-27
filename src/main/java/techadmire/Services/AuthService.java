package techadmire.Services;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import techadmire.dto.AuthResponseDTO;
import techadmire.dto.LoginDTO;
import techadmire.dto.RegisterDTO;
import techadmire.models.UserEntity;
import techadmire.repositories.UsersRepository;
import techadmire.security.JWTGenerator;

import java.util.Date;
import java.util.Optional;

@Service
public class AuthService {
    private final UsersRepository usersRepository;
    private final AuthenticationManager authenticationManager;
    private final JWTGenerator jwtGenerator;
    private final PasswordEncoder passwordEncoder;

    public AuthService(UsersRepository usersRepository, AuthenticationManager authenticationManager, JWTGenerator jwtGenerator, PasswordEncoder passwordEncoder) {
        this.usersRepository = usersRepository;
        this.authenticationManager = authenticationManager;
        this.jwtGenerator = jwtGenerator;
        this.passwordEncoder = passwordEncoder;
    }

    public AuthResponseDTO authUser(LoginDTO loginDTO) throws Exception {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginDTO.getUsername(), loginDTO.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);

        String token = jwtGenerator.generateToken(authentication);
        Date expiration = jwtGenerator.getExpirationDateFromJWT(token);
        return new AuthResponseDTO(token, expiration);
    }

    public String createUser(RegisterDTO registerDTO) throws Exception {
        UserEntity userEntity =
                new UserEntity(registerDTO.getUsername(), registerDTO.getFirstname(), registerDTO.getLastname(), passwordEncoder.encode(registerDTO.getPassword()));
        usersRepository.save(userEntity);
        return "success";
    }

    public  Optional<UserEntity> findUser(String username){
        return usersRepository.findByUsername(username);
    }
}
