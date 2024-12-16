package com.bravi.agenda.controller;

import com.bravi.agenda.dto.CalendarEventsDTO;
import com.bravi.agenda.dto.CalendarEventsUpdateDTO;
import com.bravi.agenda.service.AgendaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

import static com.bravi.agenda.constant.Endpoints.AGENDA;

@RestController
@RequestMapping(AGENDA)
public class AgendaController {

    private final AgendaService agendaService;

    @Autowired
    public AgendaController(AgendaService agendaService) {
        this.agendaService = agendaService;
    }

    @GetMapping
    public ResponseEntity<Map<Integer, CalendarEventsDTO>> getEventsByYearAndMonth(@RequestParam Integer year,
                                                                                   @RequestParam Integer month) {
        return ResponseEntity.ok(agendaService.getEventsByYearAndMonth(year, month));
    }

    @PutMapping
    public ResponseEntity<Void> updateEventsByYearAndMonth(@RequestBody CalendarEventsUpdateDTO calendarEventsUpdate) {
        agendaService.updateEventsByYearAndMonth(calendarEventsUpdate);
        return ResponseEntity.noContent().build();
    }

}
