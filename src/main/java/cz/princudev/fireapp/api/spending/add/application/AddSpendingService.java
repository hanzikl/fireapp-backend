package cz.princudev.fireapp.api.spending.add.application;

import cz.princudev.fireapp.api.spending.add.application.port.in.AddSpendingCommand;
import cz.princudev.fireapp.api.spending.add.application.port.in.AddSpendingUseCase;
import cz.princudev.fireapp.api.spending.add.application.port.out.PersistSpendingPort;
import cz.princudev.fireapp.api.spending.add.domain.Spending;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
@Slf4j
public class AddSpendingService implements AddSpendingUseCase {

    private final PersistSpendingPort persistSpendingPort;

    public AddSpendingService(PersistSpendingPort persistSpendingPort) {

        this.persistSpendingPort = persistSpendingPort;
    }

    @Override
    public void addOutcomeRecord(AddSpendingCommand command) {

        if (command.getAmount().compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("amount must be non-negative");
        }

        Spending outcome = Spending.builder()
                .date(command.getDate())
                .category(command.getCategory())
                .amount(command.getAmount())
                .user(command.getUser())
                .splitToTeam(command.isSplitToTeam())
                .build();

        persistSpendingPort.persistSpending(outcome);
    }

}
