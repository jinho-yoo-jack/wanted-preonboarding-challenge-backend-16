package com.wanted.preonboarding.user.domain.dto;

import java.sql.Date;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserInfo {
    private String id;
    private String name;
    private String email;
    private String phoneNumber;
    private Date birthday;
}
