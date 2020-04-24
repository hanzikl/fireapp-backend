package cz.princudev.fireapp.api.record.outcome.application;

import cz.princudev.fireapp.api.record.outcome.application.port.in.AddRecordOutcomeCommand;
import cz.princudev.fireapp.api.record.outcome.application.port.in.AddRecordOutcomeUseCase;
import cz.princudev.fireapp.api.record.outcome.application.port.out.PersistOutcomePort;
import cz.princudev.fireapp.api.record.outcome.domain.Outcome;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
@Slf4j
public class AddRecordOutcomeService implements AddRecordOutcomeUseCase {

    private final PersistOutcomePort persistOutcomePort;

    public AddRecordOutcomeService(PersistOutcomePort persistOutcomePort) {

        this.persistOutcomePort = persistOutcomePort;
    }

    @Override
    public void addOutcomeRecord(AddRecordOutcomeCommand command) {

        if (command.getAmount().compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("amount must be non-negative");
        }

        Outcome outcome = Outcome.builder()
                .amount(command.getAmount())
                .user(command.getUser())
                .splitToTeam(command.isSplitToTeam())
                .build();

        persistOutcomePort.persistOutcome(outcome);
    }

}
