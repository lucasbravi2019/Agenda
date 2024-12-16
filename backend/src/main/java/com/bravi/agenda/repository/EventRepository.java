package com.bravi.agenda.repository;

import com.bravi.agenda.dto.CalendarEventsDTO;
import com.bravi.agenda.dto.CalendarEventsUpdateDTO;

import java.util.List;
import java.util.Map;

public interface EventRepository {

    Map<Integer, CalendarEventsDTO> findEventsByYearAndMonth(Integer year, Integer month);

    List<CalendarEventsUpdateDTO.CalendarEvent> findEventsByYearAndMonthAndDay(Integer year, Integer month, Integer day);

    void createEvents(List<CalendarEventsUpdateDTO.CalendarEvent> calendarEvents, Integer year, Integer month, Integer day);

    void updateEvents(List<CalendarEventsUpdateDTO.CalendarEvent> calendarEvents, Integer year, Integer month);

    void deleteEventsByIds(List<Integer> calendarEventIds);

}
