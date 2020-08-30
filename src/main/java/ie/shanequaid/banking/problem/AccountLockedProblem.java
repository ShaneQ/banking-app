package ie.shanequaid.banking.problem;

import org.zalando.problem.AbstractThrowableProblem;
import org.zalando.problem.Status;

public class AccountLockedProblem extends AbstractThrowableProblem {

    public AccountLockedProblem() {
        super(null, "Debit Account Locked", Status.UNAUTHORIZED);
    }

}
