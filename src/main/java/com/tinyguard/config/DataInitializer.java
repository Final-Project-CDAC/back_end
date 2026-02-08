package com.tinyguard.config;

import com.tinyguard.entity.*;
import com.tinyguard.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DataInitializer implements CommandLineRunner {
    
    private final VaccineRepository vaccineRepository;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    
    @Override
    public void run(String... args) {
        initializeVaccines();
        initializeDefaultUsers();
    }
    
    private void initializeVaccines() {
        try {
            if (vaccineRepository.count() == 0) {
                vaccineRepository.save(createVaccine("HepB", "Birth", "Dose 1", "0-2 Weeks", 0));
                vaccineRepository.save(createVaccine("DTaP", "2 Months", "Dose 1", "6-10 Weeks", 60));
                vaccineRepository.save(createVaccine("Hib", "2 Months", "Dose 1", "6-10 Weeks", 60));
                vaccineRepository.save(createVaccine("PCV13", "2 Months", "Dose 1", "6-10 Weeks", 60));
                vaccineRepository.save(createVaccine("RV", "2 Months", "Dose 1", "6-10 Weeks", 60));
                vaccineRepository.save(createVaccine("IPV", "2 Months", "Dose 1", "6-10 Weeks", 60));
                vaccineRepository.save(createVaccine("DTaP", "4 Months", "Dose 2", "14-18 Weeks", 120));
                vaccineRepository.save(createVaccine("Hib", "4 Months", "Dose 2", "14-18 Weeks", 120));
                vaccineRepository.save(createVaccine("PCV13", "4 Months", "Dose 2", "14-18 Weeks", 120));
                vaccineRepository.save(createVaccine("RV", "4 Months", "Dose 2", "14-18 Weeks", 120));
                vaccineRepository.save(createVaccine("IPV", "4 Months", "Dose 2", "14-18 Weeks", 120));
                vaccineRepository.save(createVaccine("DTaP", "6 Months", "Dose 3", "22-26 Weeks", 180));
                vaccineRepository.save(createVaccine("Hib", "6 Months", "Dose 3", "22-26 Weeks", 180));
                vaccineRepository.save(createVaccine("PCV13", "6 Months", "Dose 3", "22-26 Weeks", 180));
                vaccineRepository.save(createVaccine("IPV", "6 Months", "Dose 3", "22-26 Weeks", 180));
                vaccineRepository.save(createVaccine("Influenza", "6 Months", "Annual", "Seasonal", 180));
                vaccineRepository.save(createVaccine("MMR", "12 Months", "Dose 1", "12-15 Months", 365));
                vaccineRepository.save(createVaccine("VAR", "12 Months", "Dose 1", "12-15 Months", 365));
                vaccineRepository.save(createVaccine("HepA", "12 Months", "Dose 1", "12-23 Months", 365));
            }
        } catch (Exception e) {
            throw new RuntimeException("Failed to initialize vaccines", e);
        }
    }
    
    private void initializeDefaultUsers() {
        try {
            if (userRepository.count() == 0) {
                User admin = new User();
                admin.setFullName("Admin User");
                admin.setEmail("admin@tinyguard.com");
                admin.setPassword(passwordEncoder.encode("admin123"));
                admin.setRole(User.Role.ADMIN);
                admin.setActive(true);
                userRepository.save(admin);
                
                User parent = new User();
                parent.setFullName("Sarah M.");
                parent.setEmail("parent@email.com");
                parent.setPassword(passwordEncoder.encode("parent123"));
                parent.setRole(User.Role.PARENT);
                parent.setActive(true);
                userRepository.save(parent);
            }
        } catch (Exception e) {
            throw new RuntimeException("Failed to initialize default users", e);
        }
    }
    
    private Vaccine createVaccine(String name, String milestone, String dose, String range, int days) {
        Vaccine vaccine = new Vaccine();
        vaccine.setName(name);
        vaccine.setAgeMilestone(milestone);
        vaccine.setDoseNumber(dose);
        vaccine.setDateRange(range);
        vaccine.setAgeInDays(days);
        vaccine.setDescription("Standard vaccination for " + milestone);
        return vaccine;
    }
}
