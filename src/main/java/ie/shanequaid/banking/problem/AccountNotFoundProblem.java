package ie.shanequaid.banking.problem;

import org.zalando.problem.AbstractThrowableProblem;
import org.zalando.problem.Status;

public class AccountNotFoundProblem extends AbstractThrowableProblem {

    public AccountNotFoundProblem() {
        super(null, "account not found", Status.NOT_FOUND);
    }

}
