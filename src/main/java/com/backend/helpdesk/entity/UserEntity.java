package com.backend.helpdesk.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.util.Date;
import java.util.Set;


@Data
@Entity(name="users")
@NoArgsConstructor
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @Column
    private String email;

    @Column
    private String password;

    @Column
    private String firstName;

    private String lastName;

    private int age;

    private Date birthday;

    private String sex;

    private String address;

    private Date startingDay;

    @Column(nullable = false)
    private boolean enable;

    @Lob
    @Column(name = "avatar")
    @Type(type = "org.hibernate.type.BinaryType")
    private byte[] avatar;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "user_role",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    private Set<RoleEntity> roleEntities;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "user_skills",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "skill_id")
    )
    private Set<SkillsEntity> skillsEntities;

    public UserEntity(String email, String password, String firstName, String lastName) {
        this.email = email;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.enable = true;
    }
}
