package pl.pg.eti.kask.laboratorium1.configuration;

import pl.pg.eti.kask.laboratorium1.user.entity.User;
import pl.pg.eti.kask.laboratorium1.user.service.UserService;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.context.Initialized;
import javax.enterprise.event.Observes;
import javax.inject.Inject;
import java.time.LocalDate;

@ApplicationScoped
public class InitializedData {

    private final UserService userService;

    @Inject
    public InitializedData(UserService userService) {
        this.userService = userService;
    }

    public void contextInitialized(@Observes @Initialized(ApplicationScoped.class) Object init) {
        init();
    }

    private synchronized void init() {
        User admin = User.builder()
                .id(1L)
                .login("admin")
                .name("Adam")
                .surname("Cormel")
                .birthday(LocalDate.of(1990, 10, 21))
                .isPatron(false)
                .sex("male")
                .avatarPath("")
                .build();

        User weronikaPiotrowska = User.builder()
                .id(2L)
                .login("weronika_piotrowska")
                .name("Weronika")
                .surname("Piotrowska")
                .birthday(LocalDate.of(1999, 8, 8))
                .isPatron(true)
                .sex("female")
                .avatarPath("")
                .build();

        User kasiaKowalska = User.builder()
                .id(3L)
                .login("kasiakowal")
                .name("Katarzyna")
                .surname("Kowalska")
                .birthday(LocalDate.of(2002, 1, 1))
                .isPatron(false)
                .sex("female")
                .avatarPath("")
                .build();

        userService.create(admin);
        userService.create(weronikaPiotrowska);
        userService.create(kasiaKowalska);
    }
}
