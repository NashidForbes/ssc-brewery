package guru.sfg.brewery.security;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.DelegatingPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by jt on 6/17/20.
 */
public class SfgPasswordEncoderFactories {

    public static PasswordEncoder createDelegatingPasswordEncoder() {
        String encodingId = "bcrypt";
        Map<String, PasswordEncoder> encoders = new HashMap<>();
        encoders.put(encodingId, new BCryptPasswordEncoder());
        encoders.put("ldap",
                new org.springframework.security.crypto.password.LdapShaPasswordEncoder());
        encoders.put("noop",
                org.springframework.security.crypto.password.NoOpPasswordEncoder
                        .getInstance());
        encoders.put("sha256",
                new org.springframework.security.crypto.password.StandardPasswordEncoder());
/*      You be the developer
        Security and compliance has determined the strength of 10 is no longer sufficient
        Recommendation is to use strength of 15 to prevent brute force attacks
        Update Spring Security to use BCrypt with strength of 15
        Use new key - bcrypt15
        Update password for user scott only - others will be migrated at a future date*/
        encoders.put("bcrypt15",
                new BCryptPasswordEncoder(15));
        return new DelegatingPasswordEncoder(encodingId, encoders);
    }

    //don't instantiate class
    private SfgPasswordEncoderFactories() {
    }
}
