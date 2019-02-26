package com.hendisantika.auth.springbootgoogleauthentication.controller;

import com.hendisantika.auth.springbootgoogleauthentication.domain.User;
import com.hendisantika.auth.springbootgoogleauthentication.service.UserService;
import org.apache.commons.codec.binary.Base32;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by IntelliJ IDEA.
 * Project : springboot-google-authentication
 * User: hendisantika
 * Email: hendisantika@gmail.com
 * Telegram : @hendisantika34
 * Date: 2019-02-27
 * Time: 06:37
 */
@RestController
@RequestMapping(value = "/user/", method = RequestMethod.POST)
public class UserRestController {
    @Value("${2fa.enabled}")
    private boolean isTwoFaEnabled;
    @Autowired
    private UserService userService;

    @RequestMapping(value = "/register/{login}/{password}", method = RequestMethod.POST)
    public String register(@PathVariable String login, @PathVariable String password) {
        User user = userService.register(login, password);
        String encodedSecret = new Base32().encodeToString(user.getSecret().getBytes());

        // This Base32 encode may usually return a string with padding characters - '='.
        // QR generator which is user (zxing) does not recognize strings containing symbols other than alphanumeric
        // So just remove these redundant '=' padding symbols from resulting string
        return encodedSecret.replace("=", "");
    }
}