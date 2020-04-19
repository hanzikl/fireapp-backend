package cz.princudev.fireapp.api.registration.user.application.port.out;

import cz.princudev.fireapp.api.registration.user.domain.UserState;

public interface PersistUserPort {

    UserState persistUser(UserState userState);

}
