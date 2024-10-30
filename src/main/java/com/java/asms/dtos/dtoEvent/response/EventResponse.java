package com.java.asms.dtos.dtoEvent.response;

import com.java.asms.dtos.dtoUser.response.UserResponse;
import com.java.asms.models.Event;
import com.java.asms.models.User;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class EventResponse {
    private long id;
    private String title;
    private String description;
    private String location;
    private String category;
    private UserResponse maker;
    private UserResponse checker;
    public void responseEvent(Event event) {
        this.id = event.getId();
        this.title = event.getTitle();
        this.description = event.getDescription();
        this.location = event.getLocation();
        this.category = event.getCategory();

        if (event.getMaker() != null) {
            System.out.println("Maker exists: " + event.getMaker());
            UserResponse makerResponse = new UserResponse();
            makerResponse.responseUser(event.getMaker());
            this.maker = makerResponse;
        } else {
            System.out.println("Maker is null for event ID: " + event.getId());
        }

        if (event.getChecker() != null) {
            System.out.println("Checker exists: " + event.getChecker());
            UserResponse checkerResponse = new UserResponse();
            checkerResponse.responseUser(event.getChecker());
            this.checker = checkerResponse;
        } else {
            System.out.println("Checker is null for event ID: " + event.getId());
        }
    }

}
