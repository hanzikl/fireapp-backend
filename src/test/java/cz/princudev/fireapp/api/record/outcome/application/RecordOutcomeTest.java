package cz.princudev.fireapp.api.record.outcome.application;

import cz.princudev.fireapp.api.record.outcome.application.port.in.AddRecordOutcomeCommand;
import cz.princudev.fireapp.api.record.outcome.application.port.in.AddRecordOutcomeUseCase;
import cz.princudev.fireapp.api.record.outcome.application.port.out.PersistOutcomePort;
import cz.princudev.fireapp.api.record.outcome.domain.Outcome;
import cz.princudev.fireapp.api.record.outcome.domain.UserState;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.math.BigDecimal;

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
        AddRecordOutcomeCommand command = new AddRecordOutcomeCommand(user, new BigDecimal("12.50"), false);

        addRecordOutcomeUseCase.addOutcomeRecord(command);

        Outcome expectedOutcome = Outcome.builder()
                .user(user)
                .amount(new BigDecimal("12.50"))
                .splitToTeam(false)
                .build();

        verify(persistOutcomePort).persistOutcome(eq(expectedOutcome));
    }

    @Test
    void test_addOutcomeSplitToTeam() {
        UserState user = new TestUser(7L);
        AddRecordOutcomeCommand command = new AddRecordOutcomeCommand(user, new BigDecimal("843.00"), true);

        addRecordOutcomeUseCase.addOutcomeRecord(command);

        Outcome expectedOutcome = Outcome.builder()
                .user(user)
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
                () -> addRecordOutcomeUseCase.addOutcomeRecord(new AddRecordOutcomeCommand(user, new BigDecimal("-1"), true)),
                "negative amount - exception must be thrown");

    }




        @Getter
    @RequiredArgsConstructor
    private static final class TestUser implements UserState {
        private final Long id;
    }

}
