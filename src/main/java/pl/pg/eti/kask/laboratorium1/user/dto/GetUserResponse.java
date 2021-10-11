package pl.pg.eti.kask.laboratorium1.user.dto;

import lombok.*;
import pl.pg.eti.kask.laboratorium1.user.entity.User;

import java.time.LocalDate;
import java.util.function.Function;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@ToString
@EqualsAndHashCode
public class GetUserResponse {
    private Long id;

    private String login;

    private String name;

    private String surname;

    private LocalDate birthday;

    private boolean isPatron;

    private String sex;

    private String avatarPath;

    public static Function<User, GetUserResponse> entityToDtoMapper() {
        return user -> GetUserResponse.builder()
                .id(user.getId())
                .login(user.getLogin())
                .name(user.getName())
                .surname(user.getSurname())
                .birthday(user.getBirthday())
                .isPatron(user.isPatron())
                .sex(user.getSex())
                .avatarPath(user.getAvatarPath())
                .build();
    }
}
