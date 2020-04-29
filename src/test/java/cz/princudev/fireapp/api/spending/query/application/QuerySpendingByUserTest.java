package cz.princudev.fireapp.api.spending.query.application;

import cz.princudev.fireapp.api.spending.query.application.port.out.FindSpendingByUserPort;
import cz.princudev.fireapp.api.spending.query.application.port.out.FindUserPort;
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

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class QuerySpendingByUserTest {

    @Mock
    private final FindSpendingByUserPort findSpendingByUserPort = mock(FindSpendingByUserPort.class);
    @Mock
    private final FindUserPort findUserPort = mock(FindUserPort.class);

    @InjectMocks
    private final FindSpendingByUserService queryService = new FindSpendingByUserService(findSpendingByUserPort, findUserPort);

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

        when(findUserPort.findById(eq(15L)))
                .thenReturn(user);

        when(findSpendingByUserPort.findAllByUser(eq(user)))
                .thenReturn(expectedSpendingList);

        List<SpendingState> result = queryService.findSpendingByUser(15L);

        Assertions.assertEquals(expectedSpendingList, result);
    }

    @Test
    void test_unknownUser() {

        when(findUserPort.findById(eq(15L)))
                .thenReturn(null);

        assertThrows(
                IllegalStateException.class,
                () -> queryService.findSpendingByUser(15L),
                "when user does not exists, exception must be thrown");
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