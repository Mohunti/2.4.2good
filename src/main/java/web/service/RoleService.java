package web.service;

import web.models.Role;

import java.util.List;

public interface RoleService {
    List<Role> getAllRoles();
    Role findRoles(String role);

    void save(Role role);
}
