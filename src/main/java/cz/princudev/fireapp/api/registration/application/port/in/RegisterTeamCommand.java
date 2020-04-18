package cz.princudev.fireapp.api.registration.application.port.in;

import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class RegisterTeamCommand {

    @NonNull
    @Getter
    private final Long initialUserId;

}
