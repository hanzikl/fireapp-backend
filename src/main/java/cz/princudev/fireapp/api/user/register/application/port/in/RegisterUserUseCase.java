package cz.princudev.fireapp.api.user.register.application.port.in;

import cz.princudev.fireapp.api.user.register.domain.UserState;

public interface RegisterUserUseCase {

    UserState registerUser(RegisterUserCommand command);

}
