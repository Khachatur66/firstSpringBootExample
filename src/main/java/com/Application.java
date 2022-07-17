package com;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.util.StringUtils;

import java.util.Locale;

@EnableAsync
@SpringBootApplication
public class Application extends SpringBootServletInitializer {
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);

       /* String name = "Service location address";

        String s[] = name.split(" ");

        StringBuilder builder = new StringBuilder(s[0].toLowerCase());

        for (int i = 1; i < s.length; i++) {
            builder.append(StringUtils.capitalize(s[i]));
        }
        System.out.println(builder);

        System.out.println(System.currentTimeMillis());*/
    }
}
