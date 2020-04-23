package cz.princudev.fireapp.api.register.user.application.port.in;

import cz.princudev.fireapp.api.register.user.domain.UserState;

public interface RegisterUserUseCase {

    UserState registerUser(RegisterUserCommand command);

}
