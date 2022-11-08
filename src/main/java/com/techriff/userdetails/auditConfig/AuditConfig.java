package com.techriff.userdetails.auditConfig;

import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import com.techriff.userdetails.service.AuditAwareImpl;
@EnableJpaAuditing
@Configuration
public class AuditConfig {
   
    @Bean
    public AuditorAware<String> auditorAware()
    {
        return new AuditAwareImpl();
    }
    
}
