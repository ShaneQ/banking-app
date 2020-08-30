package ie.shanequaid.banking.problem;

import org.zalando.problem.AbstractThrowableProblem;
import org.zalando.problem.Status;

public class AccountBalanceProblem extends AbstractThrowableProblem {

    public AccountBalanceProblem() {
        super(null, "Account balance too low", Status.BAD_REQUEST);
    }
}
