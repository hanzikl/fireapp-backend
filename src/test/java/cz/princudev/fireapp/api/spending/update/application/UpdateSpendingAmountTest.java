package cz.princudev.fireapp.api.spending.update.application;

import cz.princudev.fireapp.api.spending.update.application.port.in.UpdateSpendingAmountUseCase;
import cz.princudev.fireapp.api.spending.update.application.port.in.UpdateSpendingCommand;
import cz.princudev.fireapp.api.spending.update.application.port.out.PersistSpendingPort;
import cz.princudev.fireapp.api.spending.update.domain.Spending;
import cz.princudev.fireapp.api.spending.update.domain.SpendingCategory;
import cz.princudev.fireapp.api.spending.update.domain.SpendingState;
import cz.princudev.fireapp.api.spending.update.domain.UserState;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
class UpdateSpendingAmountTest {

    @Mock
    private final PersistSpendingPort persistSpendingPort = Mockito.mock(PersistSpendingPort.class);

    @InjectMocks
    private final UpdateSpendingAmountUseCase updateSpendingAmountUseCase =
            new UpdateSpendingAmountService(persistSpendingPort);

    @Test
    void test_UpdateOutcomeToNegativeAmountShouldFail() {

        SpendingState spendingState = Spending.builder()
                .id(1L)
                .amount(BigDecimal.ONE)
                .category(SpendingCategory.CAR_AND_TRANSPORT)
                .splitToTeam(true)
                .date(LocalDate.of(2012, 6, 3))
                .user(new TestUser(12L))
                .build();

        when(persistSpendingPort.findSpendingById(1L)).thenReturn(spendingState);

        Assertions.assertThrows(
                IllegalArgumentException.class,
                () -> updateSpendingAmountUseCase.updateSpendingAmount(
                        UpdateSpendingCommand.builder()
                                .id(1L)
                                .amount(new BigDecimal("-1"))
                                .build()),
                "negative amount - exception must be thrown");

    }

    @Test
    void test_UpdateOutcomeToGivenAmount() {

        SpendingState spendingState = Spending.builder()
                .id(1L)
                .amount(new BigDecimal("1353.23"))
                .category(SpendingCategory.CLOTHING)
                .splitToTeam(false)
                .date(LocalDate.of(2010, 6, 4))
                .user(new TestUser(13L))
                .build();

        SpendingState expectedSpending = Spending.builder()
                .id(1L)
                .amount(new BigDecimal("2400"))
                .category(SpendingCategory.CLOTHING)
                .splitToTeam(false)
                .date(LocalDate.of(2010, 6, 4))
                .user(new TestUser(13L))
                .build();

        when(persistSpendingPort.findSpendingById(1L)).thenReturn(spendingState);

        updateSpendingAmountUseCase.updateSpendingAmount(
                UpdateSpendingCommand.builder()
                        .id(1L)
                        .amount(new BigDecimal("2400"))
                        .build());

        verify(persistSpendingPort).persistSpending(eq(expectedSpending));

    }

    @Test
    void test_updateNonExistingSpending() {

        Assertions.assertThrows(
                IllegalStateException.class,
                () -> updateSpendingAmountUseCase.updateSpendingAmount(
                        UpdateSpendingCommand.builder()
                                .id(1L)
                                .amount(new BigDecimal("123"))
                                .build()),
                "non existing - exception must be thrown");
    }


    @Getter
    @RequiredArgsConstructor
    @EqualsAndHashCode
    private static final class TestUser implements UserState {
        private final Long id;
    }

}
