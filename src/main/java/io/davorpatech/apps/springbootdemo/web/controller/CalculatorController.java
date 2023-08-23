package io.davorpatech.apps.springbootdemo.web.controller;

import io.davorpatech.apps.springbootdemo.services.CalculatorService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Objects;

@RestController
@RequestMapping("/calculator")
class CalculatorController
{
    private final CalculatorService calculatorService;

    /**
     * Constructs a new {@link CalculatorController} with the given arguments.
     *
     * @param calculatorService the calculator service, never {@code null}
     */
    CalculatorController(
            final CalculatorService calculatorService)
    {
        this.calculatorService = Objects.requireNonNull(
                calculatorService, "CalculatorService must not be null!");
    }

    @GetMapping("/add")
    double add(
            @RequestParam double num1,
            @RequestParam double num2)
    {
        return calculatorService.add(num1, num2);
    }

    @GetMapping("/substract")
    double substract(
            @RequestParam double num1,
            @RequestParam double num2)
    {
        return calculatorService.substract(num1, num2);
    }

    @GetMapping("/multiply")
    double multiply(
            @RequestParam double num1,
            @RequestParam double num2)
    {
        return calculatorService.multiply(num1, num2);
    }

    @GetMapping("/divide")
    double divide(
            @RequestParam double dividend,
            @RequestParam double divisor)
    {
        return calculatorService.divide(dividend, divisor);
    }
}
