package pl.pg.eti.kask.laboratorium1.user.entity;

import lombok.*;
import lombok.experimental.SuperBuilder;
import pl.pg.eti.kask.laboratorium1.user.Role;

import java.io.Serializable;
import java.time.LocalDate;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@ToString
@EqualsAndHashCode
public class User implements Serializable {

    private Long id;

    private String login;

    private String name;

    private String surname;

    private Role role;

    private LocalDate birthday;

    private boolean isPatron;

    private String sex;

    private String avatarPath;

}
