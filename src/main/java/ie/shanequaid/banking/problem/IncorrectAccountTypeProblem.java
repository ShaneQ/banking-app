package ie.shanequaid.banking.problem;

import org.zalando.problem.AbstractThrowableProblem;
import org.zalando.problem.Status;

public class IncorrectAccountTypeProblem extends AbstractThrowableProblem {
    public IncorrectAccountTypeProblem(String message) {
        super(null, message, Status.BAD_REQUEST);
    }
}
