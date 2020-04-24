package cz.princudev.fireapp.api.record.outcome.domain;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import java.math.BigDecimal;

@Getter
@Builder
@EqualsAndHashCode
@ToString
public class Outcome implements OutcomeState {

    private final Long id;

    private final UserState user;

    private final BigDecimal amount;

    private final boolean splitToTeam;

}
