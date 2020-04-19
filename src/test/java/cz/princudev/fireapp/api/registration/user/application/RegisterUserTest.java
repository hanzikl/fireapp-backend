package cz.princudev.fireapp.api.registration.user.application;


import cz.princudev.fireapp.api.registration.user.application.port.in.RegisterUserCommand;
import cz.princudev.fireapp.api.registration.user.application.port.in.RegisterUserUseCase;
import cz.princudev.fireapp.api.registration.user.application.port.out.PersistUserPort;
import cz.princudev.fireapp.api.registration.user.domain.UserState;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
class RegisterUserTest {

    @Mock
    PersistUserPort persistUserPort = mock(PersistUserPort.class);

    @InjectMocks
    private final RegisterUserUseCase registerUserUseCase = new RegisterUserService(persistUserPort);

    @Captor
    ArgumentCaptor<UserState> acUserState = ArgumentCaptor.forClass(UserState.class);

    @Test
    void test_registerUser() {

        registerUserUseCase.registerUser(new RegisterUserCommand("Karel"));

        verify(persistUserPort).persistUser(acUserState.capture());
        Assertions.assertEquals("Karel", acUserState.getValue().getName(), "persist user must be called with given user name");
    }

}
