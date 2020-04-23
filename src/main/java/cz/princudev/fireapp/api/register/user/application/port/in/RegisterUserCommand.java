package cz.princudev.fireapp.api.register.user.application.port.in;

import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class RegisterUserCommand {

    @NonNull
    private final String name;


}
