package com.hendisantika.auth.springbootgoogleauthentication.controller;

import com.hendisantika.auth.springbootgoogleauthentication.domain.AuthenticationStatus;
import com.hendisantika.auth.springbootgoogleauthentication.domain.User;
import com.hendisantika.auth.springbootgoogleauthentication.service.TotpService;
import com.hendisantika.auth.springbootgoogleauthentication.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Optional;

/**
 * Created by IntelliJ IDEA.
 * Project : springboot-google-authentication
 * User: hendisantika
 * Email: hendisantika@gmail.com
 * Telegram : @hendisantika34
 * Date: 2019-02-27
 * Time: 06:38
 */
@RestController
@RequestMapping(value = "/authenticate/", method = RequestMethod.POST)
public class AuthenticationRestController {
    @Value("${2fa.enabled}")
    private boolean isTwoFaEnabled;
    @Autowired
    private UserService userService;
    @Autowired
    private TotpService totpService;

    @PostMapping("{login}/{password}")
    public AuthenticationStatus authenticate(@PathVariable String login, @PathVariable String password) {
        Optional<User> user = userService.findUser(login, password);

        if (!user.isPresent()) {
            return AuthenticationStatus.FAILED;
        }
        if (!isTwoFaEnabled) {
            UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(login, password);
            SecurityContextHolder.getContext().setAuthentication(authentication);
            return AuthenticationStatus.AUTHENTICATED;
        } else {
            SecurityContextHolder.getContext().setAuthentication(null);
            return AuthenticationStatus.REQUIRE_TOKEN_CHECK;
        }
    }

    @GetMapping("token/{login}/{password}/{token}")
    public AuthenticationStatus tokenCheck(@PathVariable String login, @PathVariable String password, @PathVariable String token) {
        Optional<User> user = userService.findUser(login, password);

        if (!user.isPresent()) {
            return AuthenticationStatus.FAILED;
        }

        if (!totpService.verifyCode(token, user.get().getSecret())) {
            return AuthenticationStatus.FAILED;
        }

        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(user.get().getLogin(), user.get().getPassword(), new ArrayList<>());
        SecurityContextHolder.getContext().setAuthentication(authentication);

        return AuthenticationStatus.AUTHENTICATED;
    }

    @PostMapping("/logout")
    public void logout() {
        SecurityContextHolder.clearContext();
    }
}
