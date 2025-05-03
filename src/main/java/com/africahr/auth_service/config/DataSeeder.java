package com.africahr.auth_service.config;


import com.africahr.auth_service.model.Employee;
import com.africahr.auth_service.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
public class DataSeeder {

    @Bean
    CommandLineRunner seedAdmin(UserRepository repository) {
        return args -> {
            String adminEmail = "admin@company.com";

            // Only insert if not already present
            if (repository.findByEmail(adminEmail).isEmpty()) {
                Employee admin = new Employee();
                admin.setName("Admin User");
                admin.setEmail(adminEmail);
                admin.setDepartment("Administration");
                admin.setRole("ADMIN");
                admin.setAvatarUrl("https://example.com/avatar.png");
                
                // You should hash the password!
                String rawPassword = "admin123";
                String hashedPassword = new BCryptPasswordEncoder().encode(rawPassword);
                admin.setPassword(hashedPassword);

                repository.save(admin);
                System.out.println("✅ Admin user seeded.");
            } else {
                System.out.println("ℹ️ Admin user already exists.");
            }
        };
    }
}
