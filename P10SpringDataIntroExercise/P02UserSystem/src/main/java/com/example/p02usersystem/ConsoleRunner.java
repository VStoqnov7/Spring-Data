package com.example.p02usersystem;

import com.example.p02usersystem.entities.User;
import com.example.p02usersystem.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Component
public class ConsoleRunner implements CommandLineRunner {
    private final UserRepository userRepository;

    @Autowired
    public ConsoleRunner(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        // Проверка дали вече има потребители в базата данни
        if (userRepository.count() == 0) {
            // Създаване на няколко потребителя
            User user1 = new User("Ventsislav1", "pAssword_1222", "info@softuni-bulgaria.org",10,"Venci1","Stoqnov1");
            User user2 = new User("Ventsislav2", "pAssword_2333", "kiki@hotmail.co.uk",12,"Venci2","Stoqnov2");
            User user3 = new User("Ventsislav2", "pAssword_3444", "s.peterson@mail.uu.net",13,"Venci3","Stoqnov3");

            // Запазване на потребителите в базата данни
            userRepository.saveAll(Arrays.asList(user1, user2, user3));

            System.out.println("Added users to the database.");
        } else {
            System.out.println("Database already contains users.");
        }
    }
}