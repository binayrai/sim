package com.elite;

import com.elite.thymeleaf.dialect.CredibleDialect;
import nz.net.ultraq.thymeleaf.LayoutDialect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.core.env.Environment;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@EnableAutoConfiguration
@Configuration
@ComponentScan
@EnableTransactionManagement
@EnableGlobalMethodSecurity(securedEnabled = true, prePostEnabled = true)
public class Application {
    @Autowired
    private Environment env;
    public static void main(String[] args) throws Throwable {
        SpringApplication.run(Application.class, args);
    }

    @Bean
    public LayoutDialect layoutDialect() {
        return new LayoutDialect();
    }

    @Bean
    public CredibleDialect credibleDialect() {
        return new CredibleDialect();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
    @Bean
    public ReloadableResourceBundleMessageSource messageSource() {
        ReloadableResourceBundleMessageSource source = new ReloadableResourceBundleMessageSource();
        source.setFallbackToSystemLocale(false);
        source.setBasenames("classpath:ValidationMessages",
                "classpath:common",
                "classpath:login",
                "classpath:user",
                "classpath:menu");

        if (env.acceptsProfiles("dev")) {
            source.setCacheSeconds(0);
        } else {
            source.setCacheSeconds(-1);
        }

        return source;
    }


}
