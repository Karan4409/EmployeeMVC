package com.example.EmployeeMvc.Enitity;

import jakarta.persistence.*;
import org.springframework.web.bind.annotation.ModelAttribute;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "users")
public class AppUser {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String username;
    private String password;


    public long getId() {
        return id;
    }

    public void setId(long id) {
        id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "user_roles", // join table
            joinColumns = @JoinColumn(name = "user_id"),          // FK to AppUser
            inverseJoinColumns = @JoinColumn(name = "role_id")    // FK to Role
    )
    private Set<Role> roles = new HashSet<>();

    // Constructors
    public AppUser() {}
    public AppUser(String username, String password) {
        this.username = username;
        this.password = password;
    }

    // Helper methods for sync
    public void addRole(Role role) {
        roles.add(role);
        role.getUsers().add(this);
    }

    public void removeRole(Role role) {
        roles.remove(role);
        role.getUsers().remove(this);

    }

        public Set<Role> getRoles() { return roles; }
        public void setRoles(Set<Role> roles) { this.roles = roles;
    }

    // Users :
    // Karan : 1234
    // Rahul : rahul@999 - > $2a$10$BSJtxIf.iYuGXhdTF4RNr.WAZ6qMlAPzNuGL0/mRZd6Qk5o.mPrQO
    // siddhu : sid@967 -> $2a$10$nBV/b1Nydb6R4JdtfyemGeT1NX/fjfm0MpV88WCx2/W86R65bcflW
    // Ronak : rony@143 -> $2a$10$Tdh6aHsGUbWKk0b.96khNe6JECCJmH1FKuCL6youPPPO/C6JNNeDW

}
