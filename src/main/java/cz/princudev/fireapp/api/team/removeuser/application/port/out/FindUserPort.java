package cz.princudev.fireapp.api.team.removeuser.application.port.out;

import cz.princudev.fireapp.api.team.removeuser.domain.UserState;

public interface FindUserPort {

    UserState findUserById(Long userId);
}
