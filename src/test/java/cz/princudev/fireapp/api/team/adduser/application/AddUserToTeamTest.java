package cz.princudev.fireapp.api.team.adduser.application;


import cz.princudev.fireapp.api.team.adduser.application.port.in.AddUserToTeamCommand;
import cz.princudev.fireapp.api.team.adduser.application.port.in.AddUserToTeamUseCase;
import cz.princudev.fireapp.api.team.adduser.application.port.out.FindTeamPort;
import cz.princudev.fireapp.api.team.adduser.application.port.out.FindUserPort;
import cz.princudev.fireapp.api.team.adduser.application.port.out.PersistTeamPort;
import cz.princudev.fireapp.api.team.adduser.domain.TeamState;
import cz.princudev.fireapp.api.team.adduser.domain.UserState;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Collections;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
class AddUserToTeamTest {

    @Mock
    private final PersistTeamPort persistTeamPort = mock(PersistTeamPort.class);
    @Mock
    private final FindTeamPort findTeamPort = mock(FindTeamPort.class);
    @Mock
    private final FindUserPort findUserPort = mock(FindUserPort.class);

    @InjectMocks
    private final AddUserToTeamUseCase addUserToTeamUseCase =
            new AddUserToTeamService(persistTeamPort, findTeamPort, findUserPort);

    @Test
    void test_addUserToTeam() {


        UserState user1 = new TestUser(5L);
        UserState user2 = new TestUser(12L);

        UserState foreignUser = new TestUser(256L);
        UserState userToAdd = new TestUser(13L);

        when(findUserPort.findUserById(13L)).thenReturn(userToAdd);
        when(findTeamPort.findTeamById(9L)).thenReturn(
                new TestTeam(9L, Stream.of(user1, user2).collect(Collectors.toSet())));

        when(findTeamPort.findTeamByUser(userToAdd)).thenReturn(null); // not in any team

        // mock persistence - return its argument
        when(persistTeamPort.persistTeam(any())).thenAnswer(it -> it.getArgument(0));

        TeamState result = addUserToTeamUseCase.addUserToTeam(new AddUserToTeamCommand(13L, 9L));

        TeamState expectedResult = new TestTeam(9L, Stream.of(user1, user2, userToAdd).collect(Collectors.toSet()));

        Assertions.assertEquals(result, expectedResult);
    }

    @Test
    void test_unknownUser() {

        // user does not exists
        when(findUserPort.findUserById(eq(3L))).thenReturn(null);

        // team exists
        when(findTeamPort.findTeamById(eq(145L)))
                .thenReturn(new TestTeam(145L, Collections.singleton(new TestUser(44L))));

        assertThrows(
                IllegalStateException.class,
                () -> addUserToTeamUseCase.addUserToTeam(new AddUserToTeamCommand(3L, 145L)),
                "when user unknown, exception must be thrown");

    }

    @Test
    void test_unknownTeam() {

        // user exists
        when(findUserPort.findUserById(eq(5L))).thenReturn(new TestUser(5L));

        // team does not exist
        when(findTeamPort.findTeamById(eq(1L))).thenReturn(null);

        assertThrows(
                IllegalStateException.class,
                () -> addUserToTeamUseCase.addUserToTeam(new AddUserToTeamCommand(5L, 1L)),
                "when team unknown, exception must be thrown");

    }

    @Test
    void test_userAlreadyInAnotherTeam() {

        // user does not exists
        TestUser user = new TestUser(1L);
        when(findUserPort.findUserById(eq(1L))).thenReturn(user);

        // team 2 exists
        when(findTeamPort.findTeamById(eq(2L)))
                .thenReturn(new TestTeam(2L, Collections.singleton(new TestUser(2L))));

        // user already in team 1
        when(findTeamPort.findTeamByUser(eq(user)))
                .thenReturn(new TestTeam(1L, Collections.singleton(user)));

        assertThrows(
                IllegalStateException.class,
                () -> addUserToTeamUseCase.addUserToTeam(new AddUserToTeamCommand(1L, 2L)),
                "when user already in another team, exception must be thrown");

    }

    @Test
    void test_userAlreadyInGivenTeam() {

        // user does not exists
        TestUser user1 = new TestUser(1L);
        TestUser user2 = new TestUser(2L);

        TestTeam team = new TestTeam(1L, Stream.of(user1, user2).collect(Collectors.toSet()));

        when(findUserPort.findUserById(eq(1L))).thenReturn(user1);
        when(findTeamPort.findTeamById(eq(1L))).thenReturn(team);

        // user already in this team
        when(findTeamPort.findTeamByUser(eq(user1))).thenReturn(team);

        assertThrows(
                IllegalStateException.class,
                () -> addUserToTeamUseCase.addUserToTeam(new AddUserToTeamCommand(1L, 1L)),
                "when user already in given team, exception must be thrown");

    }

    @Getter
    @EqualsAndHashCode
    @RequiredArgsConstructor
    private static final class TestUser implements UserState {
        private final Long id;
    }

    @Getter
    @EqualsAndHashCode
    @RequiredArgsConstructor
    private static final class TestTeam implements TeamState {
        private final Long id;
        private final Set<UserState> users;
    }


}
