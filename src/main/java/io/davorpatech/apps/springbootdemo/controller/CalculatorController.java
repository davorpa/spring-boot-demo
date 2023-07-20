package io.davorpatech.apps.springbootdemo.controller;

import io.davorpatech.apps.springbootdemo.service.CalculatorService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController()
@RequestMapping("calculator")
public class CalculatorController {

    private CalculatorService calculatorService = new CalculatorService();

    @GetMapping("add")
    public double add(
            @RequestParam double num1,
            @RequestParam double num2) {
        return calculatorService.add(num1, num2);
    }

    @GetMapping("substract")
    public double substract(
            @RequestParam double num1,
            @RequestParam double num2) {
        return calculatorService.substract(num1, num2);
    }

    @GetMapping("multiply")
    public double multiply(
            @RequestParam double num1,
            @RequestParam double num2) {
        return calculatorService.multiply(num1, num2);
    }

    @GetMapping("divide")
    public double divide(
            @RequestParam double num1,
            @RequestParam double num2) {
        return calculatorService.divide(num1, num2);
    }
}
