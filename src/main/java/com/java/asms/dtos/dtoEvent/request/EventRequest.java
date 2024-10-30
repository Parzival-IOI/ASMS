package com.java.asms.dtos.dtoEvent.request;

import com.java.asms.models.Event;
import com.java.asms.models.User;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class EventRequest {
    private String title;
    private String description;
    private String location;
    private String category;
    private long makerId;
    private long checkerId;
    public void requestEvent(Event event) {
        event.setTitle(this.title);
        event.setDescription(this.description);
        event.setLocation(this.location);
        event.setCategory(this.category);

        if (event.getMaker() != null) {
            event.getMaker().setId(this.makerId);
        } else {
            User maker = new User();
            maker.setId(this.makerId);
            event.setMaker(maker);
        }

        if (event.getChecker() != null) {
            event.getChecker().setId(this.checkerId);
        } else {
            User checker = new User();
            checker.setId(this.checkerId);
            event.setChecker(checker);
        }
    }

}
