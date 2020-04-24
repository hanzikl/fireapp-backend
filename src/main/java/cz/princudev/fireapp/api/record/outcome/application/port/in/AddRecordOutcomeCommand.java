package cz.princudev.fireapp.api.record.outcome.application.port.in;

import cz.princudev.fireapp.api.record.outcome.domain.UserState;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import java.math.BigDecimal;

@RequiredArgsConstructor
public class AddRecordOutcomeCommand {

    @NonNull
    @Getter
    private final UserState user;

    @NonNull
    @Getter
    private final BigDecimal amount;

    @Getter
    private final boolean splitToTeam;

}
