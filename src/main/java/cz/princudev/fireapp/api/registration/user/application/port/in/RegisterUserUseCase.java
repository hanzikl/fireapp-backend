package cz.princudev.fireapp.api.registration.user.application.port.in;

import cz.princudev.fireapp.api.registration.user.domain.UserState;

public interface RegisterUserUseCase {

    UserState registerUser(RegisterUserCommand command);

}
