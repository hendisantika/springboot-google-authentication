package com.hendisantika.auth.springbootgoogleauthentication.service;

import com.hendisantika.auth.springbootgoogleauthentication.domain.User;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Created by IntelliJ IDEA.
 * Project : springboot-google-authentication
 * User: hendisantika
 * Email: hendisantika@gmail.com
 * Telegram : @hendisantika34
 * Date: 2019-02-27
 * Time: 06:36
 */
@Service
public class UserService {
    private static final List<User> users = new ArrayList<>();
    private static final int SECRET_SIZE = 10;

    @Value("${2fa.enabled}")
    private boolean isTwoFaEnabled;

    public User register(String login, String password) {
        User user = new User(login, password, generateSecret());
        users.add(user);

        return user;
    }

    public Optional<User> findUser(String login, String password) {
        return users.stream()
                .filter(user -> user.getLogin().equals(login) && user.getPassword().equals(password))
                .findFirst();
    }

    private String generateSecret() {
        return RandomStringUtils.random(SECRET_SIZE, true, true).toUpperCase();
    }
}