package web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import web.models.Role;
import web.models.User;
import web.service.RoleService;
import web.service.UserService;

import javax.validation.Valid;
import java.util.HashSet;
import java.util.Set;

@Controller
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private RoleService roleService;


    private final UserService userService;

    public AdminController(UserService userService) {
        this.userService = userService;
    }


    @GetMapping()
    public String index(Model model) {
        model.addAttribute("people", userService.getAllUsers());
        return "adminIndex";
    }

    @GetMapping("/new")
    public String newPerson(Model model) {
        model.addAttribute("person", new User());
        return "new";
    }

    @PostMapping()
    public String create(@ModelAttribute("person") @Valid User user,
                         @RequestParam(value = "ADMIN", required = false) String ADMIN,
                         @RequestParam(value = "USER", required = false) String USER) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

        Set<Role> roles = new HashSet<>();
        if(ADMIN != null){
            roles.add(new Role(1,ADMIN));
        }
        if(USER != null){
            roles.add(new Role(2,USER));
        }
        if(ADMIN == null && USER == null ){
            roles.add(new Role(2,USER));
        }
        user.setPassword(encoder.encode(user.getPassword()));
        user.setRoles(roles);
        userService.addUser(user);
        return "redirect:/admin";
    }

    @GetMapping("/{id}/edit")
    public String edit(Model model, @PathVariable("id") int id) {
        model.addAttribute("person", userService.showUserById(id));
        model.addAttribute("roles", roleService.getAllRoles());
        return "edit";
    }


    @PutMapping("/{id}")
    public String update(@ModelAttribute("person") @Valid User user,
                         BindingResult bindingResult,
                         @RequestParam(value = "role", required = false) String[] AllRoles) {
        if (bindingResult.hasErrors()){
            return "edit";
        }
        User user1 = user;
        Set<Role> roles = new HashSet<>();
        for (String role : AllRoles) {
            roles.add(roleService.findRoles(role));
        }
        user1.setRoles(roles);
        userService.updateUser(user1);
        return "redirect:/admin";
    }

    @GetMapping("/{id}")
    public String show(@PathVariable("id") int id, Model model) {
        model.addAttribute("user", userService.showUserById(id));
        return "show";
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") int id) {
        userService.removeUser(id);
        return "redirect:/admin";
    }
}
