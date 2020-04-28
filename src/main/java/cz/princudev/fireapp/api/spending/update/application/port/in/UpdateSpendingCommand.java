package cz.princudev.fireapp.api.spending.update.application.port.in;

import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import java.math.BigDecimal;

@Getter
@Builder
@RequiredArgsConstructor
public class UpdateSpendingCommand {

    @NonNull
    private final Long id;
    @NonNull
    private final BigDecimal amount;

}
