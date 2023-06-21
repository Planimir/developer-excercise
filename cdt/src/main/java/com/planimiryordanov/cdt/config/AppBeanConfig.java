package com.planimiryordanov.cdt.config;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author Planimir Yordanov
 */
@Configuration
public class AppBeanConfig {

    @Bean
    ModelMapper modelMapper(){
        return new ModelMapper();
    }
}
