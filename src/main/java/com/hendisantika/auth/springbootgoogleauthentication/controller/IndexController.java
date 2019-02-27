package com.hendisantika.auth.springbootgoogleauthentication.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

/**
 * Created by IntelliJ IDEA.
 * Project : springboot-google-authentication
 * User: hendisantika
 * Email: hendisantika@gmail.com
 * Telegram : @hendisantika34
 * Date: 2019-02-27
 * Time: 07:19
 */

@RestController
public class IndexController {

    @GetMapping("secured")
    String index() {
        return "secured";
    }

    @GetMapping("/tes")
    String index2() {
        return "Test Google Authentication! " + new Date();
    }
}
