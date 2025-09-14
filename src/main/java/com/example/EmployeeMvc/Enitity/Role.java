package com.example.EmployeeMvc.Enitity;

import jakarta.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "roles")
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;  // e.g., "ROLE_ADMIN"

    @ManyToMany(mappedBy = "roles")
    private Set<AppUser> users = new HashSet<>();

    // Constructors
    public Role() {}
    public Role(String name) { this.name = name; }

    // Getters & setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public Set<AppUser> getUsers() { return users; }
    public void setUsers(Set<AppUser> users) { this.users = users; }
}

//ROLE_ADMIN → Full access (manage users, roles, employees).

//ROLE_MANAGER → Can manage employees, but not users/roles.

//ROLE_HR → Can add/update employee details, but not delete.

// ROLE_USER → Basic access, only view own profile or limited employee data.

// ROLE_GUEST → Very limited access, maybe just login.