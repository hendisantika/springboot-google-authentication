package com.hendisantika.auth.springbootgoogleauthentication.domain;

/**
 * Created by IntelliJ IDEA.
 * Project : springboot-google-authentication
 * User: hendisantika
 * Email: hendisantika@gmail.com
 * Telegram : @hendisantika34
 * Date: 2019-02-27
 * Time: 06:35
 */
public class User {
    private String login;
    private String password;
    private String secret;

    public User(String login, String password, String secret) {
        this.login = login;
        this.password = password;
        this.secret = secret;
    }

    public String getLogin() {
        return login;
    }

    public String getPassword() {
        return password;
    }

    public String getSecret() {
        return secret;
    }
}
