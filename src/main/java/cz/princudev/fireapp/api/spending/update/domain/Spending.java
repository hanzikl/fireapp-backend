package cz.princudev.fireapp.api.spending.update.domain;

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
public class Spending implements SpendingState {

    private final Long id;

    private final LocalDate date;

    private final SpendingCategory category;

    private final UserState user;

    private final BigDecimal amount;

    private final boolean splitToTeam;

    public static SpendingBuilder fromState(SpendingState spendingState) {
        return Spending.builder()
                .id(spendingState.getId())
                .date(spendingState.getDate())
                .category(spendingState.getCategory())
                .user(spendingState.getUser())
                .amount(spendingState.getAmount())
                .splitToTeam(spendingState.isSplitToTeam());

    }

}
