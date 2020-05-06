package cz.princudev.fireapp.api.user.register.application;

import cz.princudev.fireapp.api.user.register.application.port.in.RegisterUserCommand;
import cz.princudev.fireapp.api.user.register.application.port.in.RegisterUserUseCase;
import cz.princudev.fireapp.api.user.register.application.port.out.PersistUserPort;
import cz.princudev.fireapp.api.user.register.domain.User;
import cz.princudev.fireapp.api.user.register.domain.UserState;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
class RegisterUserService implements RegisterUserUseCase {

    private final PersistUserPort persistUserPort;

    RegisterUserService(PersistUserPort persistUserPort) {
        this.persistUserPort = persistUserPort;
    }

    @Override
    public UserState registerUser(RegisterUserCommand command) {

        User user = new User(null, command.getName());

        return persistUserPort.persistUser(user);
    }

}
