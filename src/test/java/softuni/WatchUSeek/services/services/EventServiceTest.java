package softuni.WatchUSeek.services.services;

import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import softuni.WatchUSeek.data.entities.Event;
import softuni.WatchUSeek.data.models.service.EventServiceModel;
import softuni.WatchUSeek.errors.EventNotFoundException;
import softuni.WatchUSeek.repositories.EventRepository;
import softuni.WatchUSeek.service.interfaces.EventService;
import softuni.WatchUSeek.services.base.ServiceTestBase;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class EventServiceTest extends ServiceTestBase {

    List<Event> events;
    EventServiceModel eventServiceModel;

    private final ModelMapper modelMapper;


    @MockBean
    EventRepository eventRepository;


    @Autowired
    EventService eventService;

    @Autowired
    public EventServiceTest(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    @BeforeEach
    protected void beforeEach() {
        Event event = new Event();

        events = new ArrayList<>();
        Mockito.when(eventRepository.findAll()).thenReturn(events);

        eventServiceModel = new EventServiceModel();
        eventServiceModel.setTitle("Meeting");
        eventServiceModel.setLocation("vkushti");
        eventServiceModel.setDescription("Anytime");
        eventServiceModel.setDate(LocalDate.now());
        eventServiceModel.setId("1");


    }

    @Test
    void createEvent_whenValidInput_shouldAdd() {

        Event event = this.modelMapper.map(eventServiceModel, Event.class);
        events.add(event);
        this.eventRepository.save(event);

        assertNotNull(eventRepository.findAll().size());
        assertEquals(events.get(0).getLocation(), eventServiceModel.getLocation());
        assertEquals(events.get(0).getTitle(), eventServiceModel.getTitle());

    }

    @Test
    void findEventById_whenInvalidId_shouldThrow() {
        String id = "2";
        Mockito.when(this.eventRepository.findById("1"))
                .thenReturn(Optional.empty());
        assertThrows(
                EventNotFoundException.class,
                () -> this.eventService.findEventById(id));

    }

    @Test
    void findAllEvents_whenEmpty_shouldReturnEmpty() {
        events.clear();

        List<EventServiceModel> events = eventService.findAllEvents();

        Assert.assertEquals(0, events.size());
    }

    @Test
    void findAllStraps_ShouldReturnCorrect() {
        Event event1 = new Event();
        Event event2 = new Event();

        event1.setId("1");
        event2.setId("2");
        events.add(event1);
        events.add(event2);

        List<EventServiceModel> eventServiceModels = eventService.findAllEvents();


        Assert.assertEquals(events.size(), eventServiceModels.size());
        Assert.assertEquals(events.get(0).getId(), eventServiceModels.get(0).getId());
        Assert.assertEquals(events.get(1).getId(), eventServiceModels.get(1).getId());
    }

    @Test
    void delete_whenExist_shouldDelete(){

        String id="1";
        Event strap=new Event();
        strap.setId(id);
        Mockito.when(eventRepository.findById(id)).thenReturn(Optional.of(strap)).thenThrow(RuntimeException.class);
        this.eventService.deleteEvent(id);
        assertThrows(RuntimeException.class,
                ()->this.eventService.deleteEvent(id));
    }

}
