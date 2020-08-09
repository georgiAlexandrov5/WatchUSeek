package softuni.WatchUSeek.web.controllers;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import softuni.WatchUSeek.data.models.binding.EventCreateBindingModel;

import softuni.WatchUSeek.data.models.binding.WatchCreateBindingModel;
import softuni.WatchUSeek.data.models.service.EventServiceModel;
import softuni.WatchUSeek.data.models.service.WatchServiceModel;
import softuni.WatchUSeek.repositories.EventRepository;
import softuni.WatchUSeek.service.EventService;

import java.security.Principal;
import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("/events")
public class EventController extends BaseController {

    private final ModelMapper mapper;
    private final EventService eventService;

    @Autowired
    public EventController(ModelMapper mapper, EventService eventService) {
        this.mapper = mapper;
        this.eventService = eventService;
    }


    @GetMapping("/add")
    @PageTitle("Add Event")
    public ModelAndView addEvent(@ModelAttribute(name = "model") EventCreateBindingModel model) {
        return view("event/add-event");
    }


    @PostMapping("/add")
    public ModelAndView addEventConfirm(@ModelAttribute(name = "model") EventCreateBindingModel model,
                                        Principal principal) {

        EventServiceModel eventServiceModel = this.mapper.map(model, EventServiceModel.class);

        this.eventService.createEvent(eventServiceModel);
        System.out.println(principal);

        return super.redirect("/events/all");
    }

    @GetMapping("/all")
    @PageTitle("All Events")
    public ModelAndView allEvents(ModelAndView modelAndView) {
        List<EventServiceModel> events = this.eventService.findAllEvents();
        modelAndView.addObject("events", events);
        return super.view("event/all-events", modelAndView);
    }

    @GetMapping("/details/{id}")
    @PreAuthorize("isAuthenticated()")
    @PageTitle("Event Details")
    public ModelAndView eventDetails(@PathVariable String id, ModelAndView modelAndView) {
        EventServiceModel event = this.eventService.findEventById(id);
        modelAndView.addObject("event", event);
        return super.view("event/details-event", modelAndView);
    }

    @PostMapping("/delete/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ModelAndView deleteEventConfirm(@PathVariable String id) {
        this.eventService.deleteEvent(id);

        return super.redirect("/events/all");
    }

}
