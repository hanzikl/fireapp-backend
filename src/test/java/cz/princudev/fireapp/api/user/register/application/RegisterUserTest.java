package cz.princudev.fireapp.api.user.register.application;


import cz.princudev.fireapp.api.user.register.application.port.in.RegisterUserCommand;
import cz.princudev.fireapp.api.user.register.application.port.in.RegisterUserUseCase;
import cz.princudev.fireapp.api.user.register.application.port.out.PersistUserPort;
import cz.princudev.fireapp.api.user.register.domain.User;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
class RegisterUserTest {

    @Mock
    PersistUserPort persistUserPort = mock(PersistUserPort.class);

    @InjectMocks
    private final RegisterUserUseCase registerUserUseCase = new RegisterUserService(persistUserPort);

    @Test
    void test_registerUser() {

        registerUserUseCase.registerUser(new RegisterUserCommand("Karel"));

        User expectedUser = new User(null, "Karel");

        verify(persistUserPort).persistUser(eq(expectedUser));

    }

}
