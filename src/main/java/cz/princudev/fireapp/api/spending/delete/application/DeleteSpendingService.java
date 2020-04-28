package cz.princudev.fireapp.api.spending.delete.application;

import cz.princudev.fireapp.api.spending.delete.application.port.in.DeleteSpendingCommand;
import cz.princudev.fireapp.api.spending.delete.application.port.in.DeleteSpendingUseCase;
import cz.princudev.fireapp.api.spending.delete.application.port.out.PersistSpendingPort;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class DeleteSpendingService implements DeleteSpendingUseCase {

    private final PersistSpendingPort persistSpendingPort;

    public DeleteSpendingService(PersistSpendingPort persistSpendingPort) {

        this.persistSpendingPort = persistSpendingPort;
    }

    @Override
    public void deleteSpending(DeleteSpendingCommand command) {

        persistSpendingPort.deleteSpending(command.getId());
    }

}
