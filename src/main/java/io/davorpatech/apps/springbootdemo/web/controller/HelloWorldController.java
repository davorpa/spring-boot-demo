package io.davorpatech.apps.springbootdemo.web.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
class HelloWorldController
{
    @GetMapping
    String index()
    {
        return "Hello World!";
    }

    @GetMapping("/salute")
    String salute()
    {
        return "Kaixo!";
    }

    @GetMapping("/farewell")
    String farewell()
    {
        return "さようなら!";
    }
}
