package guru.sfg.brewery.bootstrap;

import guru.sfg.brewery.domain.security.Authority;
import guru.sfg.brewery.domain.security.Role;
import guru.sfg.brewery.domain.security.User;
import guru.sfg.brewery.repositories.security.AuthorityRepository;
import guru.sfg.brewery.repositories.security.RoleRepository;
import guru.sfg.brewery.repositories.security.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by jt on 6/21/20.
 */
@Slf4j
@RequiredArgsConstructor
@Component
public class UserDataLoader implements CommandLineRunner {

    private final AuthorityRepository authorityRepository;
    private final RoleRepository roleRepository;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    private void loadSecurityData() {
        // specify crud operations beer authorities
        Authority createBeer = authorityRepository.save(Authority.builder().permission(
                "beer.create").build());
        Authority updateBeer = authorityRepository.save(Authority.builder().permission(
                "beer.update").build());
        Authority readBeer = authorityRepository.save(Authority.builder().permission(
                "beer.read").build());
        Authority deleteBeer = authorityRepository.save(Authority.builder().permission(
                "beer.delete").build());
        
/*        Authority admin = authorityRepository.save(Authority.builder().role("ROLE_ADMIN").build());
        Authority userRole = authorityRepository.save(Authority.builder().role("ROLE_USER").build());
        Authority customer = authorityRepository.save(Authority.builder().role("ROLE_CUSTOMER").build());*/



        // specify crud operations customer authorities
        Authority createCustomer =
                authorityRepository.save(Authority.builder().permission(
                        "customer.create").build());
        Authority updateCustomer = authorityRepository.save(Authority.builder().permission(
                "customer.update").build());
        Authority readCustomer = authorityRepository.save(Authority.builder().permission(
                "customer.read").build());
        Authority deleteCustomer = authorityRepository.save(Authority.builder().permission(
                "customer.delete").build());

        // specify crud operations brewery authorities
        Authority createBrewery =
                authorityRepository.save(Authority.builder().permission(
                        "brewery.create").build());
        Authority updateBrewery = authorityRepository.save(Authority.builder().permission(
                "brewery.update").build());
        Authority readBrewery = authorityRepository.save(Authority.builder().permission(
                "brewery.read").build());
        Authority deleteBrewery = authorityRepository.save(Authority.builder().permission(
                "brewery.delete").build());

        // specify roles
        Role adminRole = roleRepository.save(Role.builder().name("ADMIN").build());
        Role customerRole = roleRepository.save(Role.builder().name("CUSTOMER").build());
        Role userRole = roleRepository.save(Role.builder().name("USER").build());

        // specify fine grained permissions
        /* To prevent
        /Caused by: java.lang.UnsupportedOperationException: null
        at java.base/java.util.ImmutableCollections.uoe(ImmutableCollections.java:72) ~[na:na]
        use HashSet*/
        adminRole.setAuthorities(new HashSet<>(Set.of(createBeer,updateBeer, readBeer, deleteBeer,
                createCustomer, updateCustomer, readCustomer,
                deleteCustomer,createBrewery, updateBrewery, readBrewery,
                deleteBrewery)));
        customerRole.setAuthorities(new HashSet<>(Set.of(readBeer, readCustomer,
                readBrewery)));
        userRole.setAuthorities(new HashSet<>(Set.of(readBeer)));
        
        
        roleRepository.saveAll(Arrays.asList(adminRole, customerRole, userRole));
        
        
        userRepository.save(User.builder()
                .username("spring")
                .password(passwordEncoder.encode("guru"))
                .role(adminRole)
                .build());

        userRepository.save(User.builder()
                .username("user")
                .password(passwordEncoder.encode("password"))
                .role(userRole)
                .build());

        userRepository.save(User.builder()
                .username("scott")
                .password(passwordEncoder.encode("tiger"))
                .role(customerRole)
                .build());

        log.debug("Users Loaded: " + userRepository.count());
    }

    @Transactional
    @Override
    public void run(String... args) throws Exception {
        if (authorityRepository.count() == 0) {
            loadSecurityData();
        }
    }


}
