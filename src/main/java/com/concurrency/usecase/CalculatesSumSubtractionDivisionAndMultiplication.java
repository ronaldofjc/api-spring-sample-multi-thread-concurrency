package com.concurrency.usecase;

import com.concurrency.entity.Calculate;
import com.concurrency.entity.CalculateResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

@Slf4j
@Service
@RequiredArgsConstructor
public class CalculatesSumSubtractionDivisionAndMultiplication {

    private final CalculateSum calculateSum;
    private final CalculateSubtraction calculateSubtraction;
    private final CalculateDivision calculateDivision;
    private final CalculateMultiplication calculateMultiplication;

    public CalculateResponse execute(final BigDecimal value1, final BigDecimal value2) {
        final List<Callable<Calculate>> tasksToProcess = new ArrayList<>();
        List<Future<Calculate>> tasksResult = new ArrayList<>();
        List<Calculate> calculates = new ArrayList<>();

        final ExecutorService executor = Executors.newCachedThreadPool();

        setValuesOnTasks(value1, value2);
        addTasks(tasksToProcess);

        try {
            tasksResult = executor.invokeAll(tasksToProcess);
        } catch (final Exception e) {
            log.debug("executor.invokeAll abruptly interrupted. " + e);
        }

        executor.shutdown();

        while (!executor.isTerminated()) {
            try {
                executor.awaitTermination(1, TimeUnit.MINUTES);
            } catch (final Exception e) {
                log.debug("executor.awaitTermination abruptly interrupted. " + e);
            }
        }

        tasksResult.forEach(task -> {
            try {
                if (task.get() != null) {
                    calculates.add(task.get());
                }
            } catch (Exception e) {
                log.debug("resultTask.get() abruptly interrupted. " + e);
            }
        });

        return CalculateResponse.builder()
                .result(calculates)
                .build();
    }

    private void addTasks(List<Callable<Calculate>> tasksToProcess) {
        tasksToProcess.add(calculateSum);
        tasksToProcess.add(calculateSubtraction);
        tasksToProcess.add(calculateDivision);
        tasksToProcess.add(calculateMultiplication);
    }

    private void setValuesOnTasks(BigDecimal value1, BigDecimal value2) {
        calculateSum.setValue1(value1);
        calculateSum.setValue2(value2);
        calculateSubtraction.setValue1(value1);
        calculateSubtraction.setValue2(value2);
        calculateDivision.setValue1(value1);
        calculateDivision.setValue2(value2);
        calculateMultiplication.setValue1(value1);
        calculateMultiplication.setValue2(value2);
    }
}
