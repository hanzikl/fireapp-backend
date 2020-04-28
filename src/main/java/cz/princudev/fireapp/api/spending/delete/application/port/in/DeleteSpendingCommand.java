package cz.princudev.fireapp.api.spending.delete.application.port.in;

import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@Getter
@Builder
@RequiredArgsConstructor
public class DeleteSpendingCommand {

    @NonNull
    private final Long id;

}
