package com.backend.helpdesk.configurations;

import com.backend.helpdesk.entity.RequestType;
import com.backend.helpdesk.entity.RoleEntity;
import com.backend.helpdesk.entity.Status;
import com.backend.helpdesk.entity.UserEntity;
import com.backend.helpdesk.repository.*;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Component
@Configuration
public class DataSeedingListener implements ApplicationListener<ContextRefreshedEvent> {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private StatusRepository statusRepository;

    @Autowired
    private CategoriesRepository categoriesRepository;

    @Autowired
    private SkillsRepository skillsRepository;

    @Autowired
    private RequestTypeRepository requestTypeRepository;

    private void addRoleIfMissing(String name) {
        if (!roleRepository.findByName(name).isPresent()) {
            roleRepository.save(new RoleEntity(name));
        }
    }

    private void addRequestTypeIfMissing(String name) {
        if (!requestTypeRepository.findByName(name).isPresent()) {
            requestTypeRepository.save(new RequestType(name));
        }
    }

    private void addUserIfMissing(String username, String password, String... roles) {
        if (!userRepository.findByEmail(username).isPresent()) {
            UserEntity user = new UserEntity(username, new BCryptPasswordEncoder().encode(password), "f", "l");
            user.setRoleEntities(new HashSet<>());

            for (String role : roles) {
                user.getRoleEntities().add(roleRepository.findByName(role).get());
            }

            userRepository.save(user);
        }
    }

    private void addStatusIfMissing(String name) {
        if (!statusRepository.findByName(name).isPresent()) {
            statusRepository.save(new Status(name));
        }
    }

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {

        UserEntity userEntitydelete = new UserEntity();
        userEntitydelete = userRepository.findByEmail("thangle@novahub.vn").get();
        userRepository.delete(userEntitydelete);

        UserEntity userEntity = new UserEntity();
        userEntity.setEmail("thangle@novahub.vn");
        userEntity.setFirstName("Thang");
        userEntity.setLastName("Le");
        Set<RoleEntity> roleEntities = new HashSet<>();
        roleEntities.add(roleRepository.findByName("ROLE_ADMIN").get());
        roleEntities.add(roleRepository.findByName("ROLE_EMPLOYEES").get());
        roleEntities.add(roleRepository.findByName("ROLE_SECRETARY").get());
        userEntity.setRoleEntities(roleEntities);
        userRepository.save(userEntity);

        addRoleIfMissing("ROLE_ADMIN");
        addRoleIfMissing("ROLE_EMPLOYEES");
        addRoleIfMissing("ROLE_SECRETARY");

        addUserIfMissing("lunachris1208@gmail.com", "lunachris1208@gmail.com", "ROLE_ADMIN");
        addUserIfMissing("bkdn.ntdat@gmail.com", "bkdn.ntdat@gmail.com", "ROLE_EMPLOYEES");
        addUserIfMissing("abc@gmail.com", "abc@gmail.com", "ROLE_SECRETARY");

        addStatusIfMissing("APPROVED");
        addStatusIfMissing("PENDING");
        addStatusIfMissing("REJECTED");

        if (signingKey == null || signingKey.length() == 0) {
            String jws = Jwts.builder()
                    .setSubject("HelpDesk")
                    .signWith(SignatureAlgorithm.HS256, "helpdeskAPI").compact();

            System.out.println("Use this jwt key:");
            System.out.println("jwt-key=" + jws);
        }

    }

    @Value("${jwt-key}")
    private String signingKey;
}