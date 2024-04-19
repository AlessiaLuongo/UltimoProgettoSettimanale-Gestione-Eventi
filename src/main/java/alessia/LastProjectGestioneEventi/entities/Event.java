package alessia.LastProjectGestioneEventi.entities;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString

public class Event {
    @Id
    @GeneratedValue
    @Setter(AccessLevel.NONE)
    private UUID id;
    private String title;
    private String description;
    private LocalDate date;
    private String location;
    private int numberOfAttendees;
    @ManyToMany(mappedBy = "events")
    private List<User> users = new ArrayList<>();


    public Event(String title, String description, LocalDate date, String location, int numberOfAttendees) {
        this.title = title;
        this.description = description;
        this.date = date;
        this.location = location;
        this.numberOfAttendees = numberOfAttendees;
    }
}
