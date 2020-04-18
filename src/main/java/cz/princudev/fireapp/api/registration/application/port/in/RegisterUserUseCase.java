package cz.princudev.fireapp.api.registration.application.port.in;

import cz.princudev.fireapp.api.registration.domain.UserState;

public interface RegisterUserUseCase {

    UserState registerUser(RegisterUserCommand command);

}
