package com.limpygnome.website.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;

@Configuration
@Order(1)
@ComponentScan({
        "com.limpygnome.website.config"
})
public class AppConfig
{

}
