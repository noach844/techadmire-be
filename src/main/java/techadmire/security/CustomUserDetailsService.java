package techadmire.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import techadmire.models.UserEntity;
import techadmire.repositories.UsersRepository;
import java.util.Collections;

@Service
public class CustomUserDetailsService implements UserDetailsService {
    private UsersRepository usersRepository;

    @Autowired
    public CustomUserDetailsService(UsersRepository usersRepository){
        this.usersRepository = usersRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException{
        UserEntity userEntity = usersRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException("user not found!"));
        return new User(userEntity.getUsername(), userEntity.getPassword(), Collections.emptyList());
    }

}
