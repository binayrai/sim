package com.elite;

import org.springframework.aop.framework.Advised;
import org.springframework.beans.factory.ListableBeanFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.authentication.RememberMeAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.servlet.configuration.EnableWebMvcSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.access.intercept.FilterSecurityInterceptor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import java.lang.reflect.Method;
import java.util.Collection;

@Configuration
@EnableWebMvcSecurity
@Order(SecurityProperties.ACCESS_OVERRIDE_ORDER)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    private UserDetailsService userDetailsService;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private ListableBeanFactory listableBeanFactory;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // read the @Secured annotations on our controllers and add the matcher with our authorities (=roles)
        Collection<Object> controllers = listableBeanFactory.getBeansWithAnnotation(Controller.class).values();
        for (Object c : controllers) {
            Class<?> clazz = (c instanceof Advised) ? ((Advised) c).getTargetClass() : c.getClass();
            String startingUrlPart = "";
            if (clazz.isAnnotationPresent(RequestMapping.class)) {
                final String[] url = clazz.getAnnotation(RequestMapping.class).value();
                if (url.length != 1) {
                    throw new RuntimeException("Invalid " + RequestMapping.class.getName() + "annotation on class "
                            + clazz.getName() + ": length must be 1 but is " + url.length);
                }
                startingUrlPart = url[0];
            }
            for (Method method : clazz.getMethods()) {
                if (method.isAnnotationPresent(RequestMapping.class) && method.isAnnotationPresent(Secured.class)) {
                    final String[] url = method.getAnnotation(RequestMapping.class).value();
                    if (url.length != 1) {
                        throw new RuntimeException("Invalid " + RequestMapping.class.getName() + "annotation on class "
                                + clazz.getName() + " for method " + method.getName()
                                + ": length must be 1 but is " + url.length);
                    }
                    final String pattern = startingUrlPart + url[0];
                    final String[] authorities = method.getAnnotation(Secured.class).value();
                    http.authorizeRequests().antMatchers(pattern).hasAnyAuthority(authorities);
                }
            }
        }

        HttpSecurity security = http.authorizeRequests()
                .antMatchers("/css/**", "/js/**", "/images/**", "/webjars/**").permitAll()
                .antMatchers("/confirm/**").permitAll()
                .antMatchers("/forgetpassword/**").permitAll()
                .antMatchers("/register/**").permitAll()
                .antMatchers("/login/**").permitAll()
                .anyRequest().authenticated()
                .and();
        security = security.formLogin()
                .loginPage("/login")


                .permitAll()
                .and();
        security.logout().invalidateHttpSession(false).logoutSuccessUrl("/login?logout")
                .permitAll();
        security.sessionManagement().sessionAuthenticationErrorUrl("/login?error");

        http.sessionManagement()
                .invalidSessionUrl("/login?invalidsession");
        http.rememberMe();
        security.csrf().disable();
        security.headers().frameOptions().disable();
    }

    @Bean
    public RememberMeAuthenticationProvider rememberMeAuthenticationProvider() {
        return new RememberMeAuthenticationProvider("teamecho-rms");
    }

    @Autowired
    public void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder);
    }

    @Override
    public void configure(final WebSecurity web) throws Exception {
        // necessary to override this method, so that the th:sec tag is working
        final HttpSecurity http = getHttp();
        web.postBuildAction(() -> web.securityInterceptor(http.getSharedObject(FilterSecurityInterceptor.class)));
    }
}
