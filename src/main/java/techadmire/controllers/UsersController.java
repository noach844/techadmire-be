package techadmire.controllers;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import techadmire.dto.UserDTO;
import techadmire.models.User;
import techadmire.repositories.UsersRepository;

@RestController()
@RequestMapping("users")
public class UsersController {
    private final UsersRepository usersRepository;

    public UsersController(UsersRepository usersRepository){
        this.usersRepository = usersRepository;
    }

    @PostMapping("/login")
    public boolean login() throws Exception {
        return false;
    }

    @PostMapping("/signup")
    public User signup(@RequestBody UserDTO userDTO) throws Exception {
        User user = new User(userDTO.getUsername(), userDTO.getFirstname(), userDTO.getLastname(), userDTO.getPassword());
        return this.usersRepository.save(user);
    }
}
