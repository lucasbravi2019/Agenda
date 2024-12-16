package com.bravi.agenda.service;

import com.bravi.agenda.dto.CalendarEventsDTO;
import com.bravi.agenda.dto.CalendarEventsUpdateDTO;

import java.util.Map;

public interface AgendaService {

    Map<Integer, CalendarEventsDTO> getEventsByYearAndMonth(Integer year, Integer month);

    void updateEventsByYearAndMonth(CalendarEventsUpdateDTO calendarEventsUpdate);
}
