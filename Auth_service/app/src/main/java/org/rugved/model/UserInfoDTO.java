package org.rugved.model;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import org.rugved.entities.UserInfo;

@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class UserInfoDTO extends UserInfo {

    private String userName;
    private String lastname;
    private Long phoneNumber;
    private String email;
    private String address;
}