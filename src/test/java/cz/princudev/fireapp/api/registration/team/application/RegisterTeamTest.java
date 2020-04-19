package cz.princudev.fireapp.api.registration.team.application;


import cz.princudev.fireapp.api.registration.team.application.port.in.RegisterTeamCommand;
import cz.princudev.fireapp.api.registration.team.application.port.in.RegisterTeamUseCase;
import cz.princudev.fireapp.api.registration.team.application.port.out.FindUserAndTeamPort;
import cz.princudev.fireapp.api.registration.team.application.port.out.PersistTeamPort;
import cz.princudev.fireapp.api.registration.team.domain.TeamState;
import cz.princudev.fireapp.api.registration.team.domain.UserState;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
class RegisterTeamTest {

    @Mock
    PersistTeamPort persistTeamPort = mock(PersistTeamPort.class);

    @Mock
    FindUserAndTeamPort findUserAndTeamPort = mock(FindUserAndTeamPort.class);

    @InjectMocks
    private final RegisterTeamUseCase registerTeamUseCase = new RegisterTeamService(findUserAndTeamPort, persistTeamPort); // TODO

    @Captor
    ArgumentCaptor<TeamState> acTeamState = ArgumentCaptor.forClass(TeamState.class);

    @Test
    void test_registerTeam() {

        UserState expectedUser = new UserState() {
            @Override
            public Long getId() {
                return 5L;
            }

            @Override
            public String getName() {
                return "Karlos";
            }
        };

        when(findUserAndTeamPort.findUser(eq(5L))).thenReturn(expectedUser);

        registerTeamUseCase.registerTeam(new RegisterTeamCommand(5L));

        verify(persistTeamPort).persistTeam(acTeamState.capture());

        Set<UserState> expectedUserSet = new HashSet<>();
        expectedUserSet.add(expectedUser);

        Assert.assertEquals("team must have one given user",
                expectedUserSet,
                acTeamState.getValue().getUsers());
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

        UserState expectedUser = new UserState() {
            @Override
            public Long getId() {
                return 12L;
            }
            @Override
            public String getName() {
                return "Andy";
            }
        };

        when(findUserAndTeamPort.findUser(eq(12L))).thenReturn(expectedUser);

        TeamState existingTeam = new TeamState() {
            @Override
            public Long getId() {
                return 1L;
            }

            @Override
            public Set<UserState> getUsers() {
                return Collections.singleton(expectedUser);
            }
        };

        when(findUserAndTeamPort.findUserTeam(expectedUser)).thenReturn(existingTeam);

        assertThrows(
                IllegalStateException.class,
                () -> registerTeamUseCase.registerTeam(new RegisterTeamCommand(12L)),
                "when user is already in team, exception must be thrown");
    }



}
