package cz.princudev.fireapp.api.spending.query.domain;

import java.math.BigDecimal;
import java.time.LocalDate;

public interface SpendingState {

    Long getId();

    LocalDate getDate();

    SpendingCategory getCategory();

    UserState getUser();

    BigDecimal getAmount();

    boolean isSplitToTeam();

}
