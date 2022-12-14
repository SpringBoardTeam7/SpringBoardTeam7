package com.sparta.springboardteam7;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class SpringBoardTeam7Application {

    public static void main(String[] args) {
        SpringApplication.run(SpringBoardTeam7Application.class, args);
    }

}
