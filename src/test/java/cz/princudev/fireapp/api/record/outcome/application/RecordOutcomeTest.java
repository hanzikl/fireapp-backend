package cz.princudev.fireapp.api.record.outcome.application;

import cz.princudev.fireapp.api.record.outcome.application.port.in.AddRecordOutcomeCommand;
import cz.princudev.fireapp.api.record.outcome.application.port.in.AddRecordOutcomeUseCase;
import cz.princudev.fireapp.api.record.outcome.application.port.out.PersistOutcomePort;
import cz.princudev.fireapp.api.record.outcome.domain.Outcome;
import cz.princudev.fireapp.api.record.outcome.domain.OutcomeCategory;
import cz.princudev.fireapp.api.record.outcome.domain.UserState;
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
class RecordOutcomeTest {

    @Mock
    private final PersistOutcomePort persistOutcomePort = mock(PersistOutcomePort.class);

    @InjectMocks
    private final AddRecordOutcomeUseCase addRecordOutcomeUseCase =
            new AddRecordOutcomeService(persistOutcomePort);

    @Test
    void test_addOutcomeNotSplitToTeam() {

        UserState user = new TestUser(12L);
        AddRecordOutcomeCommand command = AddRecordOutcomeCommand.builder()
                .user(user)
                .category(OutcomeCategory.ALCOHOL)
                .date(LocalDate.of(2018, 9, 11))
                .amount(new BigDecimal("12.50"))
                .splitToTeam(false)
                .build();

        addRecordOutcomeUseCase.addOutcomeRecord(command);

        Outcome expectedOutcome = Outcome.builder()
                .user(user)
                .category(OutcomeCategory.ALCOHOL)
                .date(LocalDate.of(2018, 9, 11))
                .amount(new BigDecimal("12.50"))
                .splitToTeam(false)
                .build();

        verify(persistOutcomePort).persistOutcome(eq(expectedOutcome));
    }

    @Test
    void test_addOutcomeSplitToTeam() {
        UserState user = new TestUser(7L);

        AddRecordOutcomeCommand command = AddRecordOutcomeCommand.builder()
                .user(user)
                .category(OutcomeCategory.HOLIDAY_AND_RELAX)
                .date(LocalDate.of(2020, 4, 30))
                .amount(new BigDecimal("843.00"))
                .splitToTeam(true)
                .build();

        addRecordOutcomeUseCase.addOutcomeRecord(command);

        Outcome expectedOutcome = Outcome.builder()
                .user(user)
                .category(OutcomeCategory.HOLIDAY_AND_RELAX)
                .date(LocalDate.of(2020, 4, 30))
                .amount(new BigDecimal("843.00"))
                .splitToTeam(true)
                .build();

        verify(persistOutcomePort).persistOutcome(eq(expectedOutcome));
    }

    @Test
    void test_addOutcomeNegativeAmount() {

        UserState user = new TestUser(1L);

        assertThrows(
                IllegalArgumentException.class,
                () -> addRecordOutcomeUseCase.addOutcomeRecord(
                        AddRecordOutcomeCommand.builder()
                                .user(user)
                                .category(OutcomeCategory.FUN)
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
