package cz.princudev.fireapp.api.registration.application;


import cz.princudev.fireapp.api.registration.application.port.in.RegisterUserCommand;
import cz.princudev.fireapp.api.registration.application.port.in.RegisterUserUseCase;
import cz.princudev.fireapp.api.registration.application.port.out.PersistUserPort;
import cz.princudev.fireapp.api.registration.domain.UserState;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import static org.mockito.Mockito.mock;

@RunWith(MockitoJUnitRunner.class)
class RegisterUserTest {

    @Mock
    PersistUserPort persistUserPort = mock(PersistUserPort.class);

    @InjectMocks
    private final RegisterUserUseCase registerUserUseCase = new RegisterUserService(persistUserPort);

    @Test
    void test_registerUser() {

        UserState expectedCalledUserState = new TestUser(null, "Karel");

        Mockito.when(persistUserPort.persistUser(expectedCalledUserState)).thenReturn(new TestUser(1L, "Karel"));

        UserState newUser = registerUserUseCase.
                registerUser(new RegisterUserCommand("Karel"));

        Mockito.verify(persistUserPort).persistUser(expectedCalledUserState);

        Assertions.assertNotNull(newUser, "new user must be created");
        Assertions.assertNotNull(newUser.getId(), "new user id must not be null");
        Assertions.assertEquals("Karel", newUser.getName());
    }

    @Data
    @AllArgsConstructor
    private static class TestUser implements UserState {

        private Long id;
        private String name;

    }

}
