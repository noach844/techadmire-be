package techadmire.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import techadmire.dto.AuthResponseDTO;
import techadmire.dto.LoginDTO;
import techadmire.dto.RegisterDTO;
import techadmire.models.UserEntity;
import techadmire.repositories.UsersRepository;
import techadmire.security.JWTGenerator;

@RestController()
@RequestMapping("auth")
public class AuthController {
    private UsersRepository usersRepository;
    private AuthenticationManager authenticationManager;
    private JWTGenerator jwtGenerator;
    private PasswordEncoder passwordEncoder;

    @Autowired
    public AuthController(UsersRepository usersRepository, AuthenticationManager authenticationManager,
                          PasswordEncoder passwordEncoder, JWTGenerator jwtGenerator) {
        this.usersRepository = usersRepository;
        this.authenticationManager = authenticationManager;
        this.passwordEncoder = passwordEncoder;
        this.jwtGenerator = jwtGenerator;
    }

    @PostMapping("login")
    public ResponseEntity<AuthResponseDTO> login(@RequestBody LoginDTO loginDTO) throws Exception {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginDTO.getUsername(), loginDTO.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);

        String token = jwtGenerator.generateToken(authentication);
        return new ResponseEntity<>(new AuthResponseDTO(token), HttpStatus.OK);
    }

    @PostMapping("register")
    public ResponseEntity<String> signup(@RequestBody RegisterDTO registerDTO) throws Exception {
        UserEntity userEntity =
                new UserEntity(registerDTO.getUsername(), registerDTO.getFirstname(), registerDTO.getLastname(), passwordEncoder.encode(registerDTO.getPassword()));
        usersRepository.save(userEntity);
        return new ResponseEntity<>("success", HttpStatus.OK);
    }
}
