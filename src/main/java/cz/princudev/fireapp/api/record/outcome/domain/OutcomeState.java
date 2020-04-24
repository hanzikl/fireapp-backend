package cz.princudev.fireapp.api.record.outcome.domain;

import java.math.BigDecimal;

public interface OutcomeState {

    Long getId();

    UserState getUser();

    BigDecimal getAmount();

    boolean isSplitToTeam();

}
