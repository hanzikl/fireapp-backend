package cz.princudev.fireapp.api.team.adduser.application.port.out;

import cz.princudev.fireapp.api.team.adduser.domain.UserState;

public interface FindUserPort {

    UserState findUserById(Long userId);
}
