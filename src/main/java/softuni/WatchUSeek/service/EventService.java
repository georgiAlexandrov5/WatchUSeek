package softuni.WatchUSeek.service;


import softuni.WatchUSeek.data.models.service.EventServiceModel;

import java.util.List;

public interface EventService {

    EventServiceModel createEvent(EventServiceModel eventServiceModel);

    EventServiceModel findEventById(String id);

    void deleteEvent(String id);

    List<EventServiceModel> findAllEvents();


}
