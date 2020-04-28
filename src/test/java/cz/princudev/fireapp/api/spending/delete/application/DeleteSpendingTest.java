package cz.princudev.fireapp.api.spending.delete.application;

import cz.princudev.fireapp.api.spending.delete.application.port.in.DeleteSpendingCommand;
import cz.princudev.fireapp.api.spending.delete.application.port.in.DeleteSpendingUseCase;
import cz.princudev.fireapp.api.spending.delete.application.port.out.PersistSpendingPort;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
class DeleteSpendingTest {

    @Mock
    private final PersistSpendingPort persistSpendingPort = Mockito.mock(PersistSpendingPort.class);

    @InjectMocks
    private final DeleteSpendingUseCase deleteSpendingUseCase =
            new DeleteSpendingService(persistSpendingPort);

    @Test
    void test_delete() {

        deleteSpendingUseCase.deleteSpending(
                DeleteSpendingCommand.builder()
                        .id(1L)
                        .build());

        verify(persistSpendingPort).deleteSpending(eq(1L));

    }

}
