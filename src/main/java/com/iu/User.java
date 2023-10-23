package com.iu;

import jakarta.inject.Named;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Named
public class User {
    private Integer userId;
    private String userName;
    private String password;
    private String forename;
    private String lastname;
    private String email;

}
