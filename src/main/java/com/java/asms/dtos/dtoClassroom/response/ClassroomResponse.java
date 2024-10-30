package com.java.asms.dtos.dtoClassroom.response;

import com.java.asms.models.Classroom;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class ClassroomResponse {
    private long id;
    private String name;
    private String description;
    private String schedule;
    public void responseClassroom(Classroom classroom) {
        this.id = classroom.getId();
        this.name = classroom.getName();
        this.description = classroom.getDescription();
        this.schedule = classroom.getSchedule();
    }
}

