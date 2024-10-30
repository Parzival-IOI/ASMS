package com.java.asms.dtos.dtoUser.request;

import com.java.asms.enums.UserRole;
import com.java.asms.models.User;
import jakarta.persistence.Column;
import lombok.*;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class UserRequest {
    private String firstName;
    private String lastName;
    private Date dob;
    private UserRole role;
    private String phone;
    private String email;
    private String nationalId;
    private String address;
    public void requestUser(User user) {
        user.setFirstName(this.firstName);
        user.setLastName(this.lastName);
        user.setDob(this.dob);
        user.setRole(this.role);
        user.setPhone(this.phone);
        user.setEmail(this.email);
        user.setNationalId(this.nationalId);
        user.setAddress(this.address);
    }


}
