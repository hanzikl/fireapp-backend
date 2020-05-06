package cz.princudev.fireapp.api.user.register.application.port.out;

import cz.princudev.fireapp.api.user.register.domain.UserState;

public interface PersistUserPort {

    UserState persistUser(UserState userState);

}
