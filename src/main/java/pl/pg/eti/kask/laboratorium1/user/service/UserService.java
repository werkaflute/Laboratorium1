package pl.pg.eti.kask.laboratorium1.user.service;

import lombok.NoArgsConstructor;
import pl.pg.eti.kask.laboratorium1.user.entity.User;
import pl.pg.eti.kask.laboratorium1.user.repository.UserRepository;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.util.List;
import java.util.Optional;

@ApplicationScoped
@NoArgsConstructor
public class UserService {

    private UserRepository userRepository;

    @Inject
    public UserService (UserRepository userRepository){
        this.userRepository = userRepository;
    }

    public Optional<User> find(Long id){
        return userRepository.find(id);
    }

    public List<User> findAll() { return userRepository.findAll();}

    public void create(User user) {
        userRepository.create(user);
    }

    public void update(User user) {
        userRepository.update(user);
    }
}
