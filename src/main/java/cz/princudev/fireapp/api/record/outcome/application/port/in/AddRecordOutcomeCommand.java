package cz.princudev.fireapp.api.record.outcome.application.port.in;

import cz.princudev.fireapp.api.record.outcome.domain.UserState;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@RequiredArgsConstructor
public class AddRecordOutcomeCommand {

    @NonNull
    private final UserState user;

    @NonNull
    private final LocalDate date;

    @NonNull
    private final BigDecimal amount;

    private final boolean splitToTeam;

}
