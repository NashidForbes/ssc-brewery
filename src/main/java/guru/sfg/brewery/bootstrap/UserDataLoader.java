package guru.sfg.brewery.bootstrap;


import guru.sfg.brewery.domain.security.Authority;
import guru.sfg.brewery.domain.security.User;
import guru.sfg.brewery.repositories.CustomerRepository;
import guru.sfg.brewery.repositories.security.AuthorityRepository;
import guru.sfg.brewery.repositories.security.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Slf4j
@RequiredArgsConstructor
@Component
public class UserDataLoader implements CommandLineRunner {
    private final AuthorityRepository authorityRepository;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    
    
    /**
     * Callback used to run the bean.
     *
     * @param args incoming main method arguments
     */
    @Override
    public void run(String... args) {
        if(authorityRepository.count() == 0){
            loadUserData();            
        }

    }

    private void loadUserData(){
        Authority adminAuth = Authority.builder()
                .role("ADMIN")
                .build();

        authorityRepository.save(adminAuth);

        Authority userAuth = Authority.builder()
                .role("USER")
                .build();

        authorityRepository.save(userAuth);

        Authority customerAuth = Authority.builder()
                .role("CUSTOMER")
                .build();

        authorityRepository.save(customerAuth);

        User springUser = User.builder()
                .authority(adminAuth)
                .username("spring")
                .password(passwordEncoder.encode("password"))
                .build();

        User userUser = User.builder()
                .authority(userAuth)
                .username("user")
                .password(passwordEncoder.encode("password"))
                .build();

        User scottUser = User.builder()
                .authority(customerAuth)
                .username("scott")
                .password(passwordEncoder.encode("password"))
                .build();

        userRepository.save(springUser);
        userRepository.save(userUser);
        userRepository.save(scottUser);

        log.debug("Users count " + userRepository.count());

    }
}
