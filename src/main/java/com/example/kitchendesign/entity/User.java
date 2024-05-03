package com.example.kitchendesign.entity;

import com.example.kitchendesign.entity.project.Draft;
import com.example.kitchendesign.entity.project.Project;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

@Entity
@Getter
@Setter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
//@Table(
//        name = "tb_user",
//uniqueConstraints = {@UniqueConstraint(columnNames = {"username", "email", "phone_number"})}
//)
@Table(name = "tb_user")
public class User {

    public interface LoginWithEmail{}
    public interface LoginWithUserName{}
    public interface LoginWithPhoneNumber{}


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;


    @Column(name = "username", nullable = false)
    private String username;


    @Column(name = "password", nullable = false)
    private String password;


    @Column(name = "email", nullable = false)
    private String email;


    @Column(name = "phone_number", nullable = false)
    private String phoneNumber;


    @ElementCollection(fetch = FetchType.EAGER)
    @Enumerated(EnumType.STRING)
    @Column(name = "role", nullable = false)
    private Set<Role> roles;


    @Column(name = "registration_date", nullable = false)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss.SSS")
    private LocalDateTime regDate;


    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "project_id", referencedColumnName = "id")
    private List<Project> projects;


}
