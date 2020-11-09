package guru.sfg.brewery.security.listeners;

import guru.sfg.brewery.domain.security.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.event.AuthenticationFailureBadCredentialsEvent;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class AuthenticationFailureBadCredentialsListener {

    @EventListener
    public void Listen(AuthenticationFailureBadCredentialsEvent event) {
        log.debug("Bad credentials for login.");
        if (event.getSource() instanceof UsernamePasswordAuthenticationToken) {
            UsernamePasswordAuthenticationToken token =
                    (UsernamePasswordAuthenticationToken) event.getSource();

            if ((token.getPrincipal() instanceof User) == false) {
                log.debug("Bad credentials for user name: " + token.getPrincipal().toString());
            }
            if (token.getDetails() instanceof WebAuthenticationDetails) {
                WebAuthenticationDetails details =
                        (WebAuthenticationDetails) token.getDetails();
                log.debug("Source IP: " + details.getRemoteAddress());
            }
        }


    }


}
