package com.concurrency.usecase;


import com.concurrency.entity.Calculate;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.concurrent.Callable;

@Slf4j
@Setter
@Service
public class CalculateDivision implements Callable<Calculate> {

    BigDecimal value1;
    BigDecimal value2;

    @Override
    public Calculate call() {
        log.info("Process Division. Value 1: {} % Value 2: {}", value1, value2);
        return Calculate.builder()
                .threadName(Thread.currentThread().getName())
                .operation("Divisão")
                .result(value1.divide(value2)).build();
    }
}
