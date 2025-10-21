package edu.dhbw.configuration_bean;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MyConfiguration {

    @Bean
    Author defineAuthor() {
        return new Author("Tobias", "Göttel");
    }

}
