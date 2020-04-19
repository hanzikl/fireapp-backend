package cz.princudev.fireapp.api.registration.application.port.out;

import cz.princudev.fireapp.api.registration.domain.UserState;

public interface PersistUserPort {

    UserState persistUser(UserState userState);

}
