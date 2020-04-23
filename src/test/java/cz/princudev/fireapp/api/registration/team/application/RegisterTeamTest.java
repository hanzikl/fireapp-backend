package cz.princudev.fireapp.api.registration.team.application;


import cz.princudev.fireapp.api.registration.team.application.port.in.RegisterTeamCommand;
import cz.princudev.fireapp.api.registration.team.application.port.in.RegisterTeamUseCase;
import cz.princudev.fireapp.api.registration.team.application.port.out.FindUserAndTeamPort;
import cz.princudev.fireapp.api.registration.team.application.port.out.PersistTeamPort;
import cz.princudev.fireapp.api.registration.team.domain.Team;
import cz.princudev.fireapp.api.registration.team.domain.TeamState;
import cz.princudev.fireapp.api.registration.team.domain.UserState;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Collections;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
class RegisterTeamTest {

    @Mock
    PersistTeamPort persistTeamPort = mock(PersistTeamPort.class);

    @Mock
    FindUserAndTeamPort findUserAndTeamPort = mock(FindUserAndTeamPort.class);

    @InjectMocks
    private final RegisterTeamUseCase registerTeamUseCase = new RegisterTeamService(findUserAndTeamPort, persistTeamPort); // TODO

    @Test
    void test_registerTeam() {

        // define data which should be flowing through ports
        TestUser existingUser = new TestUser(5L, "Karlos");
        Team expectedTeam = Team.builder()
                .userSet(Collections.singleton(existingUser))
                .build();
        Team persistedTeam = Team.builder()
                .id(23L)
                .userSet(Collections.singleton(existingUser))
                .build();

        // set ports data
        when(findUserAndTeamPort.findUser(eq(5L))).thenReturn(existingUser);
        when(persistTeamPort.persistTeam(eq(expectedTeam))).thenReturn(persistedTeam);

        // assert that result is as expected
        TeamState result = registerTeamUseCase.registerTeam(new RegisterTeamCommand(5L));
        Assert.assertEquals(persistedTeam, result);
    }

    @Test
    void test_NonExistingUserShouldThrowException() {

        when(findUserAndTeamPort.findUser(eq(1L))).thenReturn(null);

        assertThrows(
                IllegalStateException.class,
                () -> registerTeamUseCase.registerTeam(new RegisterTeamCommand(1L)),
                "not existing user exception must be thrown");
    }

    @Test
    void test_UserAlreadyInTeamShouldThrowException() {

        TestUser expectedUser = new TestUser(12L, "Andy");
        TestUser anotherUserInTeam = new TestUser(28L, "James");

        when(findUserAndTeamPort.findUser(eq(12L))).thenReturn(expectedUser);

        Team existingTeam = Team.builder()
                .id(1L)
                .userSet(Stream.of(expectedUser, anotherUserInTeam)
                        .collect(Collectors.toSet()))
                .build();

        when(findUserAndTeamPort.findUserTeam(expectedUser)).thenReturn(existingTeam);

        assertThrows(
                IllegalStateException.class,
                () -> registerTeamUseCase.registerTeam(new RegisterTeamCommand(12L)),
                "when user is already in team, exception must be thrown");
    }


    @Getter
    @RequiredArgsConstructor
    private static final class TestUser implements UserState {
        private final Long id;
        private final String name;
    }


}
