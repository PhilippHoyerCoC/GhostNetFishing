package com.iu.model;

import java.util.List;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "user")
public class User {

    @Id
    @GeneratedValue
    @Column(name="ID")
    private Integer userId;
    @Column(name="USERNAME")
    private String userName;
    @Column(name="PASSWORD")
    private String password;
    @Column(name="FORENAME")
    private String forename;
    @Column(name="LASTNAME")
    private String lastname;
    @Column(name="EMAIL")
    private String email;
    @Embedded
    @Column(name="PHONE")
    private PhoneNumber phone;
    
    @OneToMany(mappedBy="user")
    @JoinColumn(name="GHOSTNET_ID")
    private List<GhostNet> ghostNets;

    public User() {
        //Needed by JPA
    }

    @Override
    public String toString() {
        return "User{" +
                "userId=" + userId +
                ", userName=" + userName +
                ", password=" + password +
                ", forename=" + forename +
                ", lastname=" + lastname +
                ", email=" + email +
                ", phone=" + phone +
                '}';
    }

}
