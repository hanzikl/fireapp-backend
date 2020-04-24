package cz.princudev.fireapp.api.record.outcome.domain;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Builder
@EqualsAndHashCode
@ToString
public class Outcome implements OutcomeState {

    private final Long id;

    private final LocalDate date;

    private final OutcomeCategory category;

    private final UserState user;

    private final BigDecimal amount;

    private final boolean splitToTeam;

}
