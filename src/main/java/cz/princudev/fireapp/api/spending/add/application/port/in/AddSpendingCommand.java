package cz.princudev.fireapp.api.spending.add.application.port.in;

import cz.princudev.fireapp.api.spending.add.domain.SpendingCategory;
import cz.princudev.fireapp.api.spending.add.domain.UserState;
import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Builder
@RequiredArgsConstructor
public class AddSpendingCommand {

    @NonNull
    private final UserState user;

    @NonNull
    private final SpendingCategory category;

    @NonNull
    private final LocalDate date;

    @NonNull
    private final BigDecimal amount;

    private final boolean splitToTeam;

}
