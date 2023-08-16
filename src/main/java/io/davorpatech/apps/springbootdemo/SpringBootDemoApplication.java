package io.davorpatech.apps.springbootdemo;

import io.davorpatech.fwk.validation.config.EnableValidatedGroups;
import io.davorpatech.fwk.web.request.correlation.config.EnableRequestCorrelation;
import io.davorpatech.fwk.web.servlet.error.attributes.config.EnableExtensibleErrorAttributes;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@EnableValidatedGroups
@EnableExtensibleErrorAttributes
@EnableRequestCorrelation
@SpringBootApplication
public class SpringBootDemoApplication
{
	public static void main(String[] args)
	{
		SpringApplication.run(SpringBootDemoApplication.class, args);
	}
}
