package io.davorpatech.apps.springbootdemo.web.controller;

import io.davorpatech.apps.springbootdemo.services.CalculatorService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Objects;

@RestController
@RequestMapping("/calculator")
public class CalculatorController
{
    private final CalculatorService calculatorService;

    /**
     * Constructs a new {@link CalculatorController} with the given arguments.
     *
     * @param calculatorService the calculator service, never {@code null}
     */
    public CalculatorController(
            final CalculatorService calculatorService)
    {
        this.calculatorService = Objects.requireNonNull(
                calculatorService, "CalculatorService must not be null!");
    }

    @GetMapping("/add")
    public double add(
            @RequestParam double num1,
            @RequestParam double num2)
    {
        return calculatorService.add(num1, num2);
    }

    @GetMapping("/substract")
    public double substract(
            @RequestParam double num1,
            @RequestParam double num2)
    {
        return calculatorService.substract(num1, num2);
    }

    @GetMapping("/multiply")
    public double multiply(
            @RequestParam double num1,
            @RequestParam double num2)
    {
        return calculatorService.multiply(num1, num2);
    }

    @GetMapping("/divide")
    public double divide(
            @RequestParam double dividend,
            @RequestParam double divisor)
    {
        return calculatorService.divide(dividend, divisor);
    }
}
