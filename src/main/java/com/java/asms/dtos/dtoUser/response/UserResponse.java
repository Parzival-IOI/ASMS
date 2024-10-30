package com.java.asms.dtos.dtoUser.response;

import com.java.asms.enums.UserRole;
import com.java.asms.models.User;
import lombok.*;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class UserResponse {
    private long id;
    private String firstName;
    private String lastName;
    private Date dob;
    private UserRole role;
    private String phone;
    private String email;
    private String nationalId;
    private String address;
    public void responseUser(User user) {
        this.id = user.getId();
        this.firstName = user.getFirstName();
        this.lastName = user.getLastName();
        this.dob = user.getDob();
        this.role = user.getRole();
        this.phone = user.getPhone();
        this.email = user.getEmail();
        this.nationalId = user.getNationalId();
        this.address = user.getAddress();
    }

}
