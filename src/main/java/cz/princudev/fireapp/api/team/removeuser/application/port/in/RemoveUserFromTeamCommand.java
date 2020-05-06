package cz.princudev.fireapp.api.team.removeuser.application.port.in;

import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class RemoveUserFromTeamCommand {

    @NonNull
    private final Long userId;

    @NonNull
    private final Long teamId;

}
