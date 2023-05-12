package it.esempio.superherobatch.decider;

import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.job.flow.FlowExecutionStatus;
import org.springframework.batch.core.job.flow.JobExecutionDecider;

public class DecessoDecider implements JobExecutionDecider {
    public static final String DECEDUTO="yes";
    public static final String NESSUN_DECESSO="no";

    private String decesso;
    public DecessoDecider(String decesso) {

        this.decesso=decesso;
    }

    @Override
    public FlowExecutionStatus decide(JobExecution jobExecution, StepExecution stepExecution) {

        return decesso == "true" ? new FlowExecutionStatus(DECEDUTO) : new FlowExecutionStatus(NESSUN_DECESSO)  ;
    }
}
