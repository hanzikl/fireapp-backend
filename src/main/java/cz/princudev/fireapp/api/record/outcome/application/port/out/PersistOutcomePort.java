package cz.princudev.fireapp.api.record.outcome.application.port.out;

import cz.princudev.fireapp.api.record.outcome.domain.OutcomeState;

public interface PersistOutcomePort {

    OutcomeState persistOutcome(OutcomeState outcomeState);

}
