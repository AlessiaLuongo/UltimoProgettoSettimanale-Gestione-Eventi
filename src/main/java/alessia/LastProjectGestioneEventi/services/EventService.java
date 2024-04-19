package alessia.LastProjectGestioneEventi.services;

import alessia.LastProjectGestioneEventi.entities.Event;
import alessia.LastProjectGestioneEventi.entities.User;
import alessia.LastProjectGestioneEventi.exceptions.BadRequestException;
import alessia.LastProjectGestioneEventi.payload.NewEventPayload;
import alessia.LastProjectGestioneEventi.repositories.EventDAO;
import alessia.LastProjectGestioneEventi.repositories.UserDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service

public class EventService {

    @Autowired
    private EventDAO eventDAO;

    @Autowired
    private UserDAO userDAO;


    public Page<Event> getAllEvents(int page, int size, String sortBy){
        if(size > 100) size = 100;
        Pageable pageable = PageRequest.of(page, size, Sort.by(sortBy));
        return this.eventDAO.findAll(pageable);
    }

    public Event save(NewEventPayload body) throws BadRequestException{
    this.eventDAO.findBytitle(body.title()).ifPresent(
            event -> {
                throw new BadRequestException("This Event " + body.title() + " is already created");

            }
    );
    Event newEvent = new Event(body.title(), body.description(), body.date(), body.location(), body.numberOfAttendees());
    return eventDAO.save(newEvent);
}


}