package ie.shanequaid.banking.problem;

import org.zalando.problem.AbstractThrowableProblem;
import org.zalando.problem.Status;

public class TransferProblem extends AbstractThrowableProblem {

    public TransferProblem(String message) {
        super(null, message, Status.BAD_REQUEST);
    }
}

