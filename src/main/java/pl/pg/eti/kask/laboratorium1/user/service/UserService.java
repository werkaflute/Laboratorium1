package pl.pg.eti.kask.laboratorium1.user.service;

import lombok.NoArgsConstructor;
import pl.pg.eti.kask.laboratorium1.user.entity.User;
import pl.pg.eti.kask.laboratorium1.user.repository.UserRepository;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
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

    public void getAvatar(FileInputStream inStream, OutputStream outStream) throws IOException {
        byte[] buffer = new byte[4096];
        int bytesRead = -1;
        while ((bytesRead = inStream.read(buffer)) != -1) {
            outStream.write(buffer, 0, bytesRead);
        }
    }

    public void saveAvatarFile(String filePath , InputStream inputStream) throws IOException{
        File file = new File(filePath);
        try (FileOutputStream outputStream = new FileOutputStream(file)) {
            int read;
            byte[] bytes = new byte[1024];
            while ((read = inputStream.read(bytes)) != -1) {
                outputStream.write(bytes, 0, read);
            }
        }
    }

}
