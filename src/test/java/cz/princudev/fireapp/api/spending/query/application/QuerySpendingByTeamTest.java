package cz.princudev.fireapp.api.spending.query.application;

import cz.princudev.fireapp.api.spending.query.application.port.out.FindSpendingByUserPort;
import cz.princudev.fireapp.api.spending.query.application.port.out.FindTeamPort;
import cz.princudev.fireapp.api.spending.query.application.port.out.FindUserByTeamPort;
import cz.princudev.fireapp.api.spending.query.domain.SpendingCategory;
import cz.princudev.fireapp.api.spending.query.domain.SpendingState;
import cz.princudev.fireapp.api.spending.query.domain.TeamState;
import cz.princudev.fireapp.api.spending.query.domain.UserState;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class QuerySpendingByTeamTest {

    @Mock
    private final FindUserByTeamPort findUserByTeamPort = mock(FindUserByTeamPort.class);
    @Mock
    private final FindSpendingByUserPort findSpendingByUserPort = mock(FindSpendingByUserPort.class);
    @Mock
    private final FindTeamPort findTeamPort = mock(FindTeamPort.class);

    @InjectMocks
    private final FindSpendingByTeamService queryService =
            new FindSpendingByTeamService(findUserByTeamPort, findSpendingByUserPort, findTeamPort);

    @Test
    void test_queryByTeam() {

        List<UserState> userList = new ArrayList<>();
        // team user
        userList.add(new TestUser(1L));
        userList.add(new TestUser(2L));
        userList.add(new TestUser(3L));
        // foreign user
        userList.add(new TestUser(4L));

        Set<UserState> teamUsers = userList.stream().filter(user -> user.getId() < 4L).collect(Collectors.toSet());

        List<SpendingState> spendingList = new ArrayList<>();
        // team spending
        spendingList.add(SimpleSpending.builder().user(userList.get(0)).amount(new BigDecimal("15.50")).id(1L).build());
        spendingList.add(SimpleSpending.builder().user(userList.get(0)).amount(new BigDecimal("49")).id(2L).build());
        spendingList.add(SimpleSpending.builder().user(userList.get(0)).amount(new BigDecimal("287")).id(3L).build());
        spendingList.add(SimpleSpending.builder().user(userList.get(1)).amount(new BigDecimal("30")).id(4L).build());
        spendingList.add(SimpleSpending.builder().user(userList.get(1)).amount(new BigDecimal("19")).id(5L).build());
        spendingList.add(SimpleSpending.builder().user(userList.get(2)).amount(new BigDecimal("999.999")).id(6L).build());
        // foreign spending
        spendingList.add(SimpleSpending.builder().user(userList.get(3)).amount(new BigDecimal("-20")).id(7L).build());
        spendingList.add(SimpleSpending.builder().user(userList.get(3)).amount(new BigDecimal("432")).id(8L).build());

        TeamState team = new TestTeam(8L, teamUsers);

        when(findTeamPort.findById(eq(8L))).thenReturn(team);
        when(findUserByTeamPort.findUsersByTeam(eq(team))).thenReturn(teamUsers);

        for (int i = 0; i < 3; i++) {
            UserState user = userList.get(i);
            when(findSpendingByUserPort.findAllByUser(user)).thenReturn(
                    spendingList.stream().filter(it -> it.getUser().equals(user)).collect(Collectors.toList()));
        }

        List<SpendingState> result = queryService.findSpendingByTeam(8L);

        // spending must be queried only for team users
        verify(findSpendingByUserPort, never()).findAllByUser(eq(userList.get(3)));

        Assertions.assertEquals(
                spendingList.stream().filter(spending -> spending.getUser().getId() < 4).collect(Collectors.toList()),
                result);
    }

    @Test
    void test_unknownTeam() {

        when(findTeamPort.findById(eq(3L))).thenReturn(null);

        assertThrows(
                IllegalStateException.class,
                () -> queryService.findSpendingByTeam(3L),
                "when team does not exists, exception must be thrown");
    }

    @Getter
    @EqualsAndHashCode
    @RequiredArgsConstructor
    private static final class TestUser implements UserState {
        private final Long id;

        @Override
        public String getName() {
            return null;
        }
    }

    @Getter
    @EqualsAndHashCode
    @RequiredArgsConstructor
    private static final class TestTeam implements TeamState {

        private final Long id;
        private final Set<UserState> users;

    }

    @Getter
    @Builder
    @EqualsAndHashCode
    @ToString
    private static class SimpleSpending implements SpendingState {

        private final Long id;
        private final UserState user;
        private final BigDecimal amount;

        @Override
        public LocalDate getDate() {
            return null;
        }

        @Override
        public SpendingCategory getCategory() {
            return null;
        }

        @Override
        public boolean isSplitToTeam() {
            return false;
        }
    }


}