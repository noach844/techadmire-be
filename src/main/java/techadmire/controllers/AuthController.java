package techadmire.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import techadmire.dto.AuthResponseDTO;
import techadmire.dto.LoginDTO;
import techadmire.dto.RegisterDTO;
import techadmire.models.UserEntity;
import techadmire.repositories.UsersRepository;
import techadmire.security.CustomUserDetailsService;
import techadmire.security.JWTGenerator;

import java.util.Date;
import java.util.Optional;

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
        Date expiration = jwtGenerator.getExpirationDateFromJWT(token);
        return new ResponseEntity<>(new AuthResponseDTO(token, expiration), HttpStatus.OK);
    }

    @PostMapping("register")
    public ResponseEntity<String> signup(@RequestBody RegisterDTO registerDTO) throws Exception {
        UserEntity userEntity =
                new UserEntity(registerDTO.getUsername(), registerDTO.getFirstname(), registerDTO.getLastname(), passwordEncoder.encode(registerDTO.getPassword()));
        usersRepository.save(userEntity);
        return new ResponseEntity<>("success", HttpStatus.OK);
    }

    @GetMapping("user-details")
    public  ResponseEntity<Optional<UserEntity>> getUser(@RequestHeader("Authorization") String authorizationHeader){
        String username =jwtGenerator.getUsernameFromJWT(authorizationHeader.split(" ")[1]);
        Optional<UserEntity> user = usersRepository.findByUsername(username);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }
}
