package softuni.WatchUSeek.service.impl;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import softuni.WatchUSeek.data.entities.Event;
import softuni.WatchUSeek.data.models.service.EventServiceModel;
import softuni.WatchUSeek.errors.EntityNotSavedException;
import softuni.WatchUSeek.errors.EventNotFoundException;
import softuni.WatchUSeek.repositories.EventRepository;
import softuni.WatchUSeek.service.interfaces.EventService;
import softuni.WatchUSeek.service.validations.EventServiceValidator;

import java.util.List;
import java.util.stream.Collectors;

import static softuni.WatchUSeek.errors.Constants.EVENT_NOT_FOUND;
import static softuni.WatchUSeek.service.validations.ValidationConstants.ENTITY_NOT_SAVED;

@Service
public class EventServiceImpl implements EventService {

    private final ModelMapper mapper;
    private final EventRepository eventRepository;
    private final EventServiceValidator validator;

    @Autowired
    public EventServiceImpl(ModelMapper mapper, EventRepository eventRepository, EventServiceValidator validator) {
        this.mapper = mapper;
        this.eventRepository = eventRepository;

        this.validator = validator;
    }


    @Override
    public EventServiceModel createEvent(EventServiceModel eventServiceModel) {
        if (!validator.isValid(eventServiceModel)) {
            throw new EntityNotSavedException(ENTITY_NOT_SAVED);
        }
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
