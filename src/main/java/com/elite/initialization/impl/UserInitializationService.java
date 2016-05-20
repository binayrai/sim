package com.elite.initialization.impl;


import com.elite.domain.User;
import com.elite.dto.enums.UserRole;
import com.elite.initialization.InitializationService;
import com.elite.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.EnumSet;

@Service
@Profile("default")
public class UserInitializationService implements InitializationService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Transactional(rollbackFor = Throwable.class)
    @Override
    public void doImport() throws Exception {
        if (userRepository.count() == 0) {
            User admin = new User();
            admin.setUsername("admin@teamecho.at");
            admin.setPassword(passwordEncoder.encode("admin"));
            admin.setRoles(EnumSet.of(UserRole.ROLE_ADMIN));
            admin.setEnabled(true);
            admin.setCreatedDate(new Date());
            admin.setLastModifiedDate(new Date());

            userRepository.save(admin);


            User user = new User();
            user.setUsername("user@teamecho.at");
            user.setPassword(passwordEncoder.encode("user"));
            user.setRoles(EnumSet.of(UserRole.ROLE_USER));
            user.setEnabled(true);
            user.setCreatedDate(new Date());
            user.setLastModifiedDate(new Date());
            userRepository.save(user);
        }
    }

    @Override
    public int getOrder() {
        return HIGHEST_PRECEDENCE;
    }
}
