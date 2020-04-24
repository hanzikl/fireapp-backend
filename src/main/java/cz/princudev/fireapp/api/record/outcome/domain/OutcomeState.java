package cz.princudev.fireapp.api.record.outcome.domain;

import java.math.BigDecimal;
import java.time.LocalDate;

public interface OutcomeState {

    Long getId();

    LocalDate getDate();

    UserState getUser();

    BigDecimal getAmount();

    boolean isSplitToTeam();

}
