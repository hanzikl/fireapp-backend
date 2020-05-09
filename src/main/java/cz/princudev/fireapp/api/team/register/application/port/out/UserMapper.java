package cz.princudev.fireapp.api.team.register.application.port.out;

import cz.princudev.fireapp.api.team.register.domain.UserState;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
class UserMapper {

    static DbUser mapToEntity(UserState userState) {

        return DbUser.builder()
                .id(userState.getId())
                .name(userState.getName())
                .build();
    }

}
