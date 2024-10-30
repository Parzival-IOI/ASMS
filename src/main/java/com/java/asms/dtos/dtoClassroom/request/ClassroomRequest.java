package com.java.asms.dtos.dtoClassroom.request;

import com.java.asms.models.Classroom;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class ClassroomRequest {
    private String name;
    private String description;
    private String schedule;
    public void requestClassroom(Classroom classroom) {
        classroom.setName(this.name);
        classroom.setDescription(this.description);
        classroom.setSchedule(this.schedule);
    }

}
