package io.davorpatech.apps.springbootdemo.service;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

public class CalculatorService {
    public double add(double num1, double num2) {
        return num1 + num2;
    }

    public double substract(double num1, double num2) {
        return num1 - num2;
    }

    public double multiply(double num1, double num2) {
        return num1 * num2;
    }

    public double divide(double num1, double num2) {
        if (Double.compare(num2, 0) == 0) {
            throw new ArithmeticException("Division by zero");
        }
        return num1 / num2;
    }
}
