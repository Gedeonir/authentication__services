package com.africahr.auth_service.service;

import com.africahr.auth_service.model.Employee;
import java.util.List;


public interface UserService {
    Employee registerUser(Employee user);
    List<Employee> getAllUsers();
    Employee getUserById(String id);
    Employee updateUser(String id, Employee updatedUser);
    void deleteUser(String id);
}
