package cz.princudev.fireapp.api.spending.query.application.port.in;

import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@Getter
@Builder
@RequiredArgsConstructor
public class QuerySpendingByUserCommand {

    @NonNull
    Long userId;

}
