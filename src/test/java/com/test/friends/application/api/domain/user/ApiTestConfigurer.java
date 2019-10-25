package com.test.friends.application.api.domain.user;

import com.test.friends.FriendsApplication;
import org.springframework.boot.test.context.SpringBootTest;

import java.lang.annotation.*;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
@SpringBootTest(
    classes = FriendsApplication.class,
    webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public @interface ApiTestConfigurer {}
