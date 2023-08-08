package io.davorpatech.apps.springbootdemo;

import io.davorpatech.fwk.web.request.correlation.config.EnableRequestCorrelation;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@EnableRequestCorrelation
@SpringBootApplication
public class SpringBootDemoApplication
{
	public static void main(String[] args)
	{
		SpringApplication.run(SpringBootDemoApplication.class, args);
	}
}
