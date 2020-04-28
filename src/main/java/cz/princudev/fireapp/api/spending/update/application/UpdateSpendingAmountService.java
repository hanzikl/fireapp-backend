package cz.princudev.fireapp.api.spending.update.application;

import cz.princudev.fireapp.api.spending.update.application.port.in.UpdateSpendingAmountUseCase;
import cz.princudev.fireapp.api.spending.update.application.port.in.UpdateSpendingCommand;
import cz.princudev.fireapp.api.spending.update.application.port.out.PersistSpendingPort;
import cz.princudev.fireapp.api.spending.update.domain.Spending;
import cz.princudev.fireapp.api.spending.update.domain.SpendingState;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
@Slf4j
public class UpdateSpendingAmountService implements UpdateSpendingAmountUseCase {

    private final PersistSpendingPort persistSpendingPort;

    public UpdateSpendingAmountService(PersistSpendingPort persistSpendingPort) {

        this.persistSpendingPort = persistSpendingPort;
    }

    @Override
    public void updateSpendingAmount(UpdateSpendingCommand command) {

        SpendingState spendingState = persistSpendingPort.findSpendingById(command.getId());

        if (spendingState == null) {
            throw new IllegalStateException("not found");
        }

        if (command.getAmount().compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("amount must be non-negative");
        }

        SpendingState updatedSpending = Spending.fromState(spendingState)
                .amount(command.getAmount())
                .build();

        persistSpendingPort.persistSpending(updatedSpending);
    }

}
