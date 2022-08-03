package com.angela.codingdojo.configuration;

import com.angela.codingdojo.model.Package;
import com.angela.codingdojo.service.PackageService;
import com.angela.codingdojo.service.UserService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.context.annotation.Bean;

@SpringBootConfiguration
public class Initialization {

    @Bean
    public CommandLineRunner initializePackages(PackageService pService) {
        return (args) -> {
            Package p = new Package();
            p.setName("Basic");
            p.setCost(10f);
            p.setAvailable(true);
            pService.save(p);

            p = new Package();
            p.setName("Premium");
            p.setCost(100f);
            p.setAvailable(true);
            pService.save(p);

            p = new Package();
            p.setName("Sports");
            p.setCost(50f);
            p.setAvailable(false);
            pService.save(p);

            p = new Package();
            p.setName("Ultra");
            p.setCost(75f);
            p.setAvailable(true);
            pService.save(p);
        };
    }

    @Bean
    public CommandLineRunner initializeUsers(UserService uService, PackageService pService) {
        return (args) -> {
            /*User u = new User();
            u.setUsername("admin");
            u.setEmail("admin@mail.com");
            u.setPassword("123123");
            uService.registerUser(u);

            u = new User();
            u.setUsername("user");
            u.setEmail("user@mail.com");
            u.setPassword("123123");
            u.setCurrentPackage(pService.findByName("Basic"));
            u.getNextDue();
            uService.registerUser(u);*/
        };
    }

}
