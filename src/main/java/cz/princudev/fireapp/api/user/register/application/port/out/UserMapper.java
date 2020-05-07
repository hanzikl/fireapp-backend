package cz.princudev.fireapp.api.user.register.application.port.out;

import cz.princudev.fireapp.api.user.register.domain.UserState;
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
