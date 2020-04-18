package cz.princudev.fireapp.api.registration.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class User implements UserState {

    private final Long id;
    private final String name;

}
