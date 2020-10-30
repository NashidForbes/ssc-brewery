package guru.sfg.brewery.security.perms.custom.annotations.customer;

import org.springframework.security.access.prepost.PreAuthorize;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
@PreAuthorize("hasAnyAuthority('customer.update')")
public @interface CustomerUpdatePermission {
}
