package com.play.multithreading.utils;

import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

// Required for accessing H2 console in a stand alone application
@Configuration
public class WebConfiguration {
    @Bean
    ServletRegistrationBean h2servletRegistration(){
        ServletRegistrationBean registrationBean = new ServletRegistrationBean( new org.h2.server.web.WebServlet());
        registrationBean.addUrlMappings("/console/*");
        return registrationBean;
    }
}
