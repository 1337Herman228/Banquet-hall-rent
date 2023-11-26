//package com.bankets.bankets.config;
//
//import com.bankets.bankets.models.Roles;
//import com.bankets.bankets.models.Users;
//import com.bankets.bankets.repo.RolesRepository;
//import com.bankets.bankets.repo.UsersRepository;
////import com.bankets.bankets.service.UserService;
//import jakarta.transaction.Transactional;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.core.authority.SimpleGrantedAuthority;
//import org.springframework.security.core.context.SecurityContextHolder;
//import org.springframework.security.core.userdetails.User;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.security.core.userdetails.UsernameNotFoundException;
//import org.springframework.security.provisioning.InMemoryUserDetailsManager;
//import org.springframework.security.web.SecurityFilterChain;
//
//import java.util.List;
//import java.util.stream.Collectors;
//import java.util.stream.StreamSupport;
//
//@Configuration
//@EnableWebSecurity
//public class SecurityConfig {
//
//    @Autowired
//    private RolesRepository rolesRepository;
//
//    @Autowired
//    private UsersRepository usersRepository;
//
//
//    @Bean
//    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
//        http
//                .authorizeHttpRequests((requests) -> requests
//                        .requestMatchers("/", "/home").authenticated()
//                        .anyRequest().authenticated()
//                )
//                .formLogin((form) -> form
//                        .loginPage("/login")
//                        .permitAll()
//                )
//                .logout((logout) -> logout.permitAll());
//
//        return http.build();
//    }
//
//    @Bean
//    public UserDetailsService userDetailsService( ) {
//
//        UserDetails user =
//                User.withDefaultPasswordEncoder()
//                        .username("123")
//                        .password("123")
//                        .roles("USER")
//                        .build();
//
//        return new InMemoryUserDetailsManager(user);
//    }
//
//
//}
//
////import com.bankets.bankets.service.UserService;
////import jakarta.servlet.DispatcherType;
////import org.springframework.beans.factory.annotation.Autowired;
////import org.springframework.context.annotation.Bean;
////import org.springframework.security.authentication.AuthenticationManager;
////import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
////import org.springframework.security.config.Customizer;
////import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
////import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
////import org.springframework.security.config.annotation.web.builders.HttpSecurity;
////import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
////import org.springframework.security.core.userdetails.User;
////import org.springframework.security.core.userdetails.UserDetails;
////import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
////import org.springframework.security.crypto.password.PasswordEncoder;
////import org.springframework.security.provisioning.InMemoryUserDetailsManager;
////import org.springframework.security.provisioning.JdbcUserDetailsManager;
////import org.springframework.security.web.SecurityFilterChain;
////
////import javax.sql.DataSource;
////
////
////@EnableWebSecurity
////public class SecurityConfig {
////
//////    private DataSource dataSource;
//////    @Autowired
//////    public SecurityConfig(DataSource dataSource) { this.dataSource = dataSource; }
//////
//////    @Bean
//////    public JdbcUserDetailsManager user(PasswordEncoder encoder) {
//////        UserDetails admin = User.builder()
//////                .username("123")
//////                .password(encoder.encode("123"))
//////                .roles("ADMIN")
//////                .authorities("ADMIN")
//////                .build();
//////        JdbcUserDetailsManager jdbcUserDetailsManager = new JdbcUserDetailsManager(dataSource);
//////        jdbcUserDetailsManager.createUser(admin);
//////        return jdbcUserDetailsManager;
//////    }
//////    @Bean
//////    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
//////        http
//////                .authorizeHttpRequests(
//////                        (authorize) -> authorize
//////                                .dispatcherTypeMatchers(DispatcherType.FORWARD, DispatcherType.ERROR).permitAll()
//////                                .requestMatchers("/").hasAuthority("ADMIN")
//////                                .requestMatchers("/").hasAuthority("USER")
//////                                .anyRequest().authenticated())
//////                .httpBasic(Customizer.withDefaults())
//////                .formLogin(Customizer.withDefaults());
//////        return http.build();
//////    }
////
//////    private UserService userService;
////
////    @Bean
////    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
////        http
////                .authorizeHttpRequests((requests) -> requests
////                        .requestMatchers("/", "/about").permitAll()
////                        .anyRequest().authenticated()
////                )
////                .formLogin((form) -> form
////                        .loginPage("/login")
////                        .permitAll()
////                        .loginProcessingUrl("/login")
////                        .defaultSuccessUrl("/", true)
////                )
////                .logout((logout) -> logout.permitAll());
//////        http
//////                .authorizeRequests()
//////                .anyRequest().authenticated()
//////                .requestMatchers("/").authenticated() // написать пути, которые требуют аутентификации
//////                .requestMatchers("/admin").hasRole("Администратор")
//////                .anyRequest().permitAll();
////
////
////        return http.build();
////    }
////
////
//////
//////    @Bean
//////    public DaoAuthenticationProvider daoAuthenticationProvider() {
//////        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
//////        authenticationProvider.setPasswordEncoder(passwordEncoder());
//////        authenticationProvider.setUserDetailsService(userService);
//////        return authenticationProvider;
//////    }
//////
//////    @Bean
//////    public BCryptPasswordEncoder passwordEncoder() {
//////        return new BCryptPasswordEncoder();
//////    }
//////
//////    @Bean
//////    public AuthenticationManager authenticationManager (AuthenticationConfiguration authenticationConfiguration) throws Exception {
//////        return authenticationConfiguration.getAuthenticationManager();
//////    }
////}
//
