package com.vaibhav.sociofy.security

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder

@Configuration
class SecurityConfig : WebSecurityConfigurerAdapter() {


    @Bean
    fun getPasswordEncoder(): PasswordEncoder = BCryptPasswordEncoder()


    override fun configure(http: HttpSecurity) {
        http
            .csrf().disable()
            .authorizeRequests()
            .antMatchers("/api/v1/**").permitAll()
            .anyRequest()
            .authenticated()

    }
}