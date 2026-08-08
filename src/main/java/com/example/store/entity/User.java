package com.example.store.entity;

import com.example.store.enums.Role;
import jakarta.persistence.*;

@Entity
@Table(name = "users")

public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true, nullable = false)
    private String email;
    private String password;
    private String firstName;
    private String lastName;
    @Enumerated(EnumType.STRING)
    private Role role;

    protected User() {
    }

    private User(String email, String password, String firstName, String lastName, Role role) {
        this.email = email;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.role = role;
    }

    public static User createCustomer(String email, String password, String firstName, String lastName) {
        return new User(email, password, firstName, lastName, Role.CUSTOMER);
    }

    public static User createAdmin(String email, String password, String firstName, String lastName) {
        return new User(email, password, firstName, lastName, Role.ADMIN);
    }

    public Long getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getLastName() {
        return lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public Role getRole() {
        return role;
    }

    public void setFirstName(String firstName) {
        if (firstName == null || firstName.isBlank()){
            throw new IllegalArgumentException("First Name cannot be null or empty");
        }
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        if (lastName == null || lastName.isBlank()){
            throw new IllegalArgumentException("Last Name cannot be null or empty");
        }
        this.lastName = lastName;
    }

    public void changePassword(String password) {
        if (password == null || password.isBlank()){
            throw new IllegalArgumentException("Password cannot be null or empty");
        }
        this.password = password;
    }
}