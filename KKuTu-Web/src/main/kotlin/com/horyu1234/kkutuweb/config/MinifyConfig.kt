package com.horyu1234.kkutuweb.config

import com.horyu1234.kkutuweb.MinifyFilter
import org.springframework.boot.web.servlet.FilterRegistrationBean
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class MinifyConfig {
    @Bean
    fun filterRegistrationBean(): FilterRegistrationBean<*> {
        return FilterRegistrationBean(MinifyFilter())
    }
}
