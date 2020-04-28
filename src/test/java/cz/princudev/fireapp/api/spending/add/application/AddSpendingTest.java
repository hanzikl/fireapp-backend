package cz.princudev.fireapp.api.spending.add.application;

import cz.princudev.fireapp.api.spending.add.application.port.in.AddSpendingCommand;
import cz.princudev.fireapp.api.spending.add.application.port.out.PersistSpendingPort;
import cz.princudev.fireapp.api.spending.add.application.port.in.AddSpendingUseCase;
import cz.princudev.fireapp.api.spending.add.domain.Spending;
import cz.princudev.fireapp.api.spending.add.domain.SpendingCategory;
import cz.princudev.fireapp.api.spending.add.domain.UserState;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
class AddSpendingTest {

    @Mock
    private final PersistSpendingPort persistSpendingPort = mock(PersistSpendingPort.class);

    @InjectMocks
    private final AddSpendingUseCase addSpendingUseCase =
            new AddSpendingService(persistSpendingPort);

    @Test
    void test_addOutcomeNotSplitToTeam() {

        UserState user = new TestUser(12L);
        AddSpendingCommand command = AddSpendingCommand.builder()
                .user(user)
                .category(SpendingCategory.ALCOHOL)
                .date(LocalDate.of(2018, 9, 11))
                .amount(new BigDecimal("12.50"))
                .splitToTeam(false)
                .build();

        addSpendingUseCase.addOutcomeRecord(command);

        Spending expectedOutcome = Spending.builder()
                .user(user)
                .category(SpendingCategory.ALCOHOL)
                .date(LocalDate.of(2018, 9, 11))
                .amount(new BigDecimal("12.50"))
                .splitToTeam(false)
                .build();

        verify(persistSpendingPort).persistSpending(eq(expectedOutcome));
    }

    @Test
    void test_addOutcomeSplitToTeam() {
        UserState user = new TestUser(7L);

        AddSpendingCommand command = AddSpendingCommand.builder()
                .user(user)
                .category(SpendingCategory.HOLIDAY_AND_RELAX)
                .date(LocalDate.of(2020, 4, 30))
                .amount(new BigDecimal("843.00"))
                .splitToTeam(true)
                .build();

        addSpendingUseCase.addOutcomeRecord(command);

        Spending expectedOutcome = Spending.builder()
                .user(user)
                .category(SpendingCategory.HOLIDAY_AND_RELAX)
                .date(LocalDate.of(2020, 4, 30))
                .amount(new BigDecimal("843.00"))
                .splitToTeam(true)
                .build();

        verify(persistSpendingPort).persistSpending(eq(expectedOutcome));
    }

    @Test
    void test_addOutcomeNegativeAmount() {

        UserState user = new TestUser(1L);

        assertThrows(
                IllegalArgumentException.class,
                () -> addSpendingUseCase.addOutcomeRecord(
                        AddSpendingCommand.builder()
                                .user(user)
                                .category(SpendingCategory.FUN)
                                .date(LocalDate.of(2020, 4, 30))
                                .amount(new BigDecimal("-1"))
                                .splitToTeam(true)
                                .build()),
                "negative amount - exception must be thrown");

    }

    @Getter
    @RequiredArgsConstructor
    private static final class TestUser implements UserState {
        private final Long id;
    }

}
