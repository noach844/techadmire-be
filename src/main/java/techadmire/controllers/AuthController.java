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
import techadmire.Services.AuthService;
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
    private final AuthService authService;
    private final JWTGenerator jwtGenerator;

    @Autowired
    public AuthController(AuthService authService, JWTGenerator jwtGenerator) {
        this.authService = authService;
        this.jwtGenerator = jwtGenerator;
    }

    @PostMapping("login")
    public ResponseEntity<AuthResponseDTO> login(@RequestBody LoginDTO loginDTO) throws Exception {
        AuthResponseDTO res = this.authService.authUser(loginDTO);
        return new ResponseEntity<>(res, HttpStatus.OK);
    }

    @PostMapping("register")
    public ResponseEntity<String> signup(@RequestBody RegisterDTO registerDTO) throws Exception {
        String res = this.authService.createUser(registerDTO);
        return new ResponseEntity<>(res, HttpStatus.OK);
    }

    @GetMapping("user-details")
    public  ResponseEntity<Optional<UserEntity>> getUser(@RequestHeader("Authorization") String authorizationHeader){
        String username =jwtGenerator.getUsernameFromJWT(authorizationHeader.substring(7, authorizationHeader.length()));
        Optional<UserEntity> user = this.authService.findUser(username);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }
}
