
@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private AuthenticationService authenticationService;

    public SecurityConfig(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) {
        auth.authenticationProvider(this.authenticationService);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // authorize requests and permit all for the sighup, css, and js
        http.authorizeRequests()
                .antMatchers("/signup", "/css/**", "/js/**").permitAll()
                .anyRequest().authenticated();

        // Generates a login form at /login and allows anyone to access it
        http.formLogin()
                .loginPage("/login")
                .permitAll();

        // Redirects successful logins to the /home page
        http.formLogin()
                .defaultSuccessUrl("/home", true);
    }

}