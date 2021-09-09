package web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import web.models.Role;
import web.models.User;
import web.service.RoleService;
import web.service.UserService;

import javax.annotation.PostConstruct;
import java.util.HashSet;
import java.util.Set;

@Component
public class DataInitializer {
    private UserService userService;
    private RoleService roleService;

    @Autowired
    public DataInitializer(UserService userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }

    @PostConstruct
    public void Init() {
        Role role1 = new Role(1, "ROLE_ADMIN");
        Role role2 = new Role(2, "ROLE_USER");

        roleService.save(role1);
        roleService.save(role2);


        Set<Role> admin = new HashSet<>();
        Set<Role> user = new HashSet<>();

        admin.add(role1);
        admin.add(role2);

        user.add(role2);

        User user1 = new User(24, "shirshov", "$2a$12$PoPNV8pbDXaqeVJ2wY.yIuvzePp2tiIQAwsO7MM4FG4VZ1KmZDCrG", "admin");
        user1.setRoles(admin);
        userService.addUser(user1);

        User user2 = new User(30, "userov", "$2a$12$5sAqoUKIXcpF8cYuY5LRMuSmq8ZB5zc4s9DT62bUiTrJG.fDRzD6C", "user");
        user2.setRoles(user);
        userService.addUser(user2);
    }
}
