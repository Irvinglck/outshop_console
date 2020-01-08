package com.lck.outshop.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class LogConfig {
    private static final Logger logger=  LoggerFactory.getLogger(LogConfig.class);
    @Bean
    public Person logMethod() {
        logger.info("==========print log==========");
        return new Person();
    }

}
