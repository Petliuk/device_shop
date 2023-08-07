package com.device.shop.security;
import org.springframework.security.access.prepost.PreAuthorize;
import java.lang.annotation.*;


@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@PreAuthorize("@securityService.canAccessOwnData(#userId)")
public @interface CanAccessOwnData {
}