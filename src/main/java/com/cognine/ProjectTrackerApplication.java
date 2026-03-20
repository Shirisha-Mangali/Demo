package com.cognine;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.ApplicationContext;

@SpringBootApplication
public class ProjectTrackerApplication {

	public static void main(String[] args) {
		SpringApplication.run(ProjectTrackerApplication.class, args);
	}
	// @Bean
    // public CommandLineRunner printBeans(ApplicationContext context) {
    //     return args -> {
    //         String[] beans = context.getBeanDefinitionNames();
    //         for (String bean : beans) {
    //             System.out.println(bean);
    //         }
    //     };
	// }

}
