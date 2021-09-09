package web.dao;

import web.models.Role;

import java.util.List;
import java.util.Set;

public interface RoleDao {
  List<Role> getAllRoles();
  Role findRoles(String role);
  void save(Role role);
}
