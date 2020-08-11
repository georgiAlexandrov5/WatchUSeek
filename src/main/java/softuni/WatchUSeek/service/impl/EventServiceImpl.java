package softuni.WatchUSeek.service.impl;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import softuni.WatchUSeek.data.entities.Event;
import softuni.WatchUSeek.data.models.service.EventServiceModel;
import softuni.WatchUSeek.errors.EventNotFoundException;
import softuni.WatchUSeek.repositories.EventRepository;
import softuni.WatchUSeek.service.EventService;

import java.util.List;
import java.util.stream.Collectors;

import static softuni.WatchUSeek.errors.Constants.EVENT_NOT_FOUND;

@Service
public class EventServiceImpl implements EventService {

    private final ModelMapper mapper;
    private final EventRepository eventRepository;

    @Autowired
    public EventServiceImpl(ModelMapper mapper, EventRepository eventRepository) {
        this.mapper = mapper;
        this.eventRepository = eventRepository;
    }


    @Override
    public EventServiceModel createEvent(EventServiceModel eventServiceModel) {
        Event event = this.mapper.map(eventServiceModel, Event.class);

        return this.mapper.map(this.eventRepository.save(event), EventServiceModel.class);

    }

    @Override
    public EventServiceModel findEventById(String id) {
        return this.eventRepository.findById(id)
                .map(c -> this.mapper.map(c, EventServiceModel.class))
                .orElseThrow(() -> new EventNotFoundException(EVENT_NOT_FOUND));
    }

    @Override
    public void deleteEvent(String id) {
        Event event = this.eventRepository.findById(id)
                .orElseThrow(() -> new EventNotFoundException(EVENT_NOT_FOUND));

        this.eventRepository.delete(event);
    }

    @Override
    public List<EventServiceModel> findAllEvents() {
        return this.eventRepository.findAll()
                .stream()
                .map(c -> this.mapper.map(c, EventServiceModel.class))
                .collect(Collectors.toList());
    }
}
