package com.concurrency.endpoint;

import com.concurrency.entity.CalculateResponse;
import com.concurrency.usecase.CalculatesSumSubtractionDivisionAndMultiplication;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;

@RestController
@Tag(name = "Calculate")
@RequiredArgsConstructor
@RequestMapping(path = "/calculate", produces = MediaType.APPLICATION_JSON_VALUE)
public class CalculateValuesController {

    private final CalculatesSumSubtractionDivisionAndMultiplication calculateValues;

    @GetMapping(value = "/{value1}/{value2}")
    @Operation(summary = "Calcula a soma, subtração, divisão e multiplicação dos dois valores informados")
    public CalculateResponse calculate(@PathVariable final BigDecimal value1, @PathVariable final BigDecimal value2) {
        return calculateValues.execute(value1, value2);
    }

}
