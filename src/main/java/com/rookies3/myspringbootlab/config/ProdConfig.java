package com.rookies3.myspringbootlab.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
@Profile("prod")
public class ProdConfig {

    @Bean // myEnvironment 메서드를 실행하여 MyEnvironment를 스프링 빈으로 등록
    public MyEnvironment myEnvironment() {
        return MyEnvironment.builder().mode("운영환경").build();
    }
}
