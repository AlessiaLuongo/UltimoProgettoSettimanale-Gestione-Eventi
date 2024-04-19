package alessia.LastProjectGestioneEventi.controllers;

import alessia.LastProjectGestioneEventi.entities.Event;
import alessia.LastProjectGestioneEventi.entities.User;
import alessia.LastProjectGestioneEventi.payload.NewEventPayload;
import alessia.LastProjectGestioneEventi.services.EventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/events")
public class EventController {

    @Autowired
    EventService eventService;

    @GetMapping
    public Page<Event> getAllEvents(@RequestParam(defaultValue = "0") int page,
                                   @RequestParam(defaultValue = "10") int size,
                                   @RequestParam(defaultValue = "id") String sortBy){
        return this.eventService.getAllEvents(page, size, sortBy);
    }

    @PostMapping()
    @PreAuthorize("hasAuthority('EVENTPLANNER')")
    @ResponseStatus(HttpStatus.CREATED)
    public Event saveEvent(@RequestBody NewEventPayload body){
        return this.eventService.save(body);
    }

    @GetMapping("/{eventId}")
    public Event findSingleEvent(@PathVariable UUID eventId){
        return this.eventService.findEventById(eventId);
    }

    @PutMapping("/{eventId}")
    @PreAuthorize("hasAuthority('EVENTPLANNER')")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public Event findSingleEventAndUpdate(@PathVariable UUID eventId, @RequestBody Event body){
        return this.eventService.findEventByIdAndUpdate(eventId, body);
    }

    @DeleteMapping("/{eventId}")
    @PreAuthorize("hasAuthority('EVENTPLANNER')")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void findSingleEventAndDelete(@PathVariable UUID eventId){
        this.eventService.findEventByIdAndDelete(eventId);
    }
}
