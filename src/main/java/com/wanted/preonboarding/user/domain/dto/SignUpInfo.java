package com.wanted.preonboarding.user.domain.dto;

import java.sql.Date;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SignUpInfo {
    private String id;
    private String name;
    private String password;
    private String passwordCheck;
    private String email;
    private String phoneNumber;
    private Date birthday;

}
