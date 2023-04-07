package rentcar.rentcar.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    CustomUserDetailsService customUserDetailsService;

    @Autowired
    public SecurityConfig(CustomUserDetailsService customUserDetailsService) {
        this.customUserDetailsService = customUserDetailsService;
    }

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(customUserDetailsService).passwordEncoder(passwordEncoder());
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        return http.csrf().disable()
                .authorizeRequests()
                .antMatchers("/reg/user", "/swagger-ui/index.html").permitAll()
                .antMatchers("/car/free", "/car/start/{id}", "/car/end").hasAnyRole("USER", "ADMIN")
                .antMatchers("/cr/update", "/cr/delete/{id}").hasAnyRole("USER", "ADMIN")
                .antMatchers("/user/update", "/user/delete", "/user/fine", "/user/cr", "/user/rh").hasAnyRole("USER", "ADMIN")
                .antMatchers("/reg/cr").hasAnyRole("USER", "ADMIN")
                .antMatchers("/dl/update/", "/dl/create", "/dl/delete").hasAnyRole("USER", "ADMIN")
                .antMatchers("/dl/{id}", "/dl/all").hasRole("ADMIN")
                .antMatchers("/cr/all").hasRole("ADMIN")
                .antMatchers("/car/{id}", "/car/update", "/car/delete/{id}", "/car/create").hasRole("ADMIN")
                .antMatchers("/cr/all", "/cr/{id}").hasRole("ADMIN")
                .antMatchers("/fine/**").hasRole("ADMIN")
                .antMatchers("/rh/**").hasRole("ADMIN")
                .antMatchers("/user/{id}", "/user/all").hasRole("ADMIN")
                .anyRequest().authenticated()
                .and()
                .httpBasic()
                .and().build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
