package cz.princudev.fireapp.api.team.removeuser.application;


import cz.princudev.fireapp.api.team.removeuser.application.port.in.RemoveUserFromTeamCommand;
import cz.princudev.fireapp.api.team.removeuser.application.port.in.RemoveUserFromTeamUseCase;
import cz.princudev.fireapp.api.team.removeuser.application.port.out.FindTeamPort;
import cz.princudev.fireapp.api.team.removeuser.application.port.out.FindUserPort;
import cz.princudev.fireapp.api.team.removeuser.application.port.out.PersistTeamPort;
import cz.princudev.fireapp.api.team.removeuser.domain.TeamState;
import cz.princudev.fireapp.api.team.removeuser.domain.UserState;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
class RemoveUserFromTeamCommandTest {

    @Mock
    private final PersistTeamPort persistTeamPort = mock(PersistTeamPort.class);
    @Mock
    private final FindTeamPort findTeamPort = mock(FindTeamPort.class);
    @Mock
    private final FindUserPort findUserPort = mock(FindUserPort.class);

    @InjectMocks
    private final RemoveUserFromTeamUseCase removeUserFromTeamUseCase =
            new RemoveUserFromTeamService(persistTeamPort, findTeamPort, findUserPort);

    @Test
    void test_removeUserFromTeam() {

        UserState user1 = new TestUser(1L);
        UserState user2 = new TestUser(2L);
        UserState userToRemove = new TestUser(3L);

        when(findUserPort.findUserById(3L)).thenReturn(userToRemove);
        when(findTeamPort.findTeamById(1L)).thenReturn(
                new TestTeam(1L, Stream.of(user1, user2, userToRemove).collect(Collectors.toSet())));

        // mock persistence - return its argument
        when(persistTeamPort.persistTeam(any())).thenAnswer(it -> it.getArgument(0));

        TeamState result = removeUserFromTeamUseCase.removeUserFromTeam(new RemoveUserFromTeamCommand(3L, 1L));

        TeamState expectedResult = new TestTeam(1L, Stream.of(user1, user2).collect(Collectors.toSet()));

        Assertions.assertEquals(result, expectedResult);
    }


    @Test
    void test_teamWithLastUserDeletedAfterUserRemoval() {

        TestUser lastUser = new TestUser(1L);

        TestTeam testTeam = new TestTeam(1L, Collections.singleton(lastUser));

        when(findTeamPort.findTeamById(eq(1L))).thenReturn(testTeam);
        when(findUserPort.findUserById(eq(1L))).thenReturn(lastUser);

        removeUserFromTeamUseCase.removeUserFromTeam(new RemoveUserFromTeamCommand(1L, 1L));

        verify(persistTeamPort).deleteTeam(eq(testTeam));
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
                () -> removeUserFromTeamUseCase.removeUserFromTeam(new RemoveUserFromTeamCommand(3L, 145L)),
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
                () -> removeUserFromTeamUseCase.removeUserFromTeam(new RemoveUserFromTeamCommand(5L, 1L)),
                "when team unknown, exception must be thrown");

    }

    @Test
    void test_userNotInGivenTeam() {

        TestUser user1 = new TestUser(1L);
        TestUser user2 = new TestUser(2L);

        TestTeam team = new TestTeam(1L, Stream.of(user2).collect(Collectors.toSet()));

        when(findUserPort.findUserById(eq(1L))).thenReturn(user1);
        when(findTeamPort.findTeamById(eq(1L))).thenReturn(team);

        // user not in this team
        when(findTeamPort.findTeamByUser(eq(user1))).thenReturn(team);

        assertThrows(
                IllegalStateException.class,
                () -> removeUserFromTeamUseCase.removeUserFromTeam(new RemoveUserFromTeamCommand(1L, 1L)),
                "when user not present in given team, exception must be thrown");

    }

    @Getter
    @ToString
    @EqualsAndHashCode
    @RequiredArgsConstructor
    private static final class TestUser implements UserState {
        private final Long id;
    }

    @Getter
    @ToString
    @EqualsAndHashCode
    private static final class TestTeam implements TeamState {
        private final Long id;
        private final Set<UserState> users;

        private TestTeam(Long id, Set<UserState> users) {
            this.id = id;
            // defensive copy, also ensures modifiable set
            this.users = new HashSet<>(users);
        }
    }


}
