package com.bravi.agenda.service.impl;

import com.bravi.agenda.dto.CalendarEventsDTO;
import com.bravi.agenda.dto.CalendarEventsUpdateDTO;
import com.bravi.agenda.repository.EventRepository;
import com.bravi.agenda.service.AgendaService;
import com.bravi.agenda.util.ListUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class AgendaServiceImpl implements AgendaService {

    private final EventRepository eventRepository;

    @Autowired
    public AgendaServiceImpl(EventRepository eventRepository) {
        this.eventRepository = eventRepository;
    }

    @Override
    public Map<Integer, CalendarEventsDTO> getEventsByYearAndMonth(Integer year, Integer month) {
        return eventRepository.findEventsByYearAndMonth(year, month);
    }

    @Override
    public void updateEventsByYearAndMonth(CalendarEventsUpdateDTO calendarEventsUpdate) {
        Integer day = calendarEventsUpdate.getDay();
        Set<CalendarEventsUpdateDTO.CalendarEvent> eventsByYearAndMonthAndDay = new HashSet<>(eventRepository
                .findEventsByYearAndMonthAndDay(calendarEventsUpdate.getYear(), calendarEventsUpdate.getMonth(), day));

        List<CalendarEventsUpdateDTO.CalendarEvent> newEvents = new ArrayList<>();
        List<CalendarEventsUpdateDTO.CalendarEvent> modifiedEvents = new ArrayList<>();
        Set<Integer> allEventsIds = new HashSet<>();

        for (CalendarEventsUpdateDTO.CalendarEvent event : calendarEventsUpdate.getEvents()) {
            Integer id = event.getId();
            if (id == null)
                newEvents.add(event);
            else {
                Optional<CalendarEventsUpdateDTO.CalendarEvent> eventToModifiy = eventsByYearAndMonthAndDay.stream()
                        .filter(existingEvent -> existingEvent.getId().equals(event.getId()))
                        .findFirst()
                        .map(existingEvent -> {
                            allEventsIds.add(event.getId());
                            existingEvent.setDescription(event.getDescription());
                            return existingEvent;
                        });

                eventToModifiy.ifPresent(modifiedEvents::add);
            }
        }

        List<Integer> eventIdsToDelete = eventsByYearAndMonthAndDay.stream()
                .map(CalendarEventsUpdateDTO.CalendarEvent::getId)
                .filter(event -> !allEventsIds.contains(event))
                .toList();

        eventRepository.createEvents(newEvents, calendarEventsUpdate.getYear(), calendarEventsUpdate.getMonth(), day);
        eventRepository.updateEvents(modifiedEvents, calendarEventsUpdate.getYear(), calendarEventsUpdate.getMonth());
        eventRepository.deleteEventsByIds(eventIdsToDelete);
    }
}
