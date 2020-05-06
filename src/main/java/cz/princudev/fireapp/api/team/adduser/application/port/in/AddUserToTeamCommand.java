package cz.princudev.fireapp.api.team.adduser.application.port.in;

import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class AddUserToTeamCommand {

    @NonNull
    private final Long userId;

    @NonNull
    private final Long teamId;

}
