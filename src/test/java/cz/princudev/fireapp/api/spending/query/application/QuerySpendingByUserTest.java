package cz.princudev.fireapp.api.spending.query.application;

import cz.princudev.fireapp.api.spending.query.FindSpendingByUserService;
import cz.princudev.fireapp.api.spending.query.application.port.in.QuerySpendingByUserCommand;
import cz.princudev.fireapp.api.spending.query.application.port.out.QuerySpendingByUserPort;
import cz.princudev.fireapp.api.spending.query.application.port.out.QueryUserPort;
import cz.princudev.fireapp.api.spending.query.domain.SpendingCategory;
import cz.princudev.fireapp.api.spending.query.domain.SpendingState;
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

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class QuerySpendingByUserTest {

    @Mock
    private final QuerySpendingByUserPort querySpendingByUserPort = mock(QuerySpendingByUserPort.class);
    @Mock
    private final QueryUserPort queryUserPort = mock(QueryUserPort.class);

    @InjectMocks
    private final FindSpendingByUserService queryService = new FindSpendingByUserService(querySpendingByUserPort, queryUserPort);

    @Test
    void test_queryByUser() {

        TestUser user = new TestUser(15L, "Greg");

        List<SpendingState> expectedSpendingList = new ArrayList<>();
        expectedSpendingList.add(
                SimpleSpending.builder()
                        .amount(BigDecimal.TEN)
                        .id(251L)
                        .user(user)
                        .build());
        expectedSpendingList.add(
                SimpleSpending.builder()
                        .amount(BigDecimal.ONE)
                        .id(253L)
                        .user(user)
                        .build());

        when(queryUserPort.findById(eq(15L)))
                .thenReturn(user);

        when(querySpendingByUserPort.findAllByUser(eq(user)))
                .thenReturn(expectedSpendingList);

        List<SpendingState> result = queryService.findSpendingByUser(QuerySpendingByUserCommand.builder()
                .userId(15L)
                .build());

        Assertions.assertEquals(expectedSpendingList, result);
    }

    @Test
    void test_unknownUser() {


        Assertions.assertFalse(true, "todo");

    }

    @Getter
    @EqualsAndHashCode
    @RequiredArgsConstructor
    private static final class TestUser implements UserState {
        private final Long id;
        private final String name;
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