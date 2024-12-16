package com.bravi.agenda.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CalendarEventsUpdateDTO {

    private Integer year;
    private Integer month;
    private Integer day;

    @Builder.Default
    private List<CalendarEvent> events = new ArrayList<>();

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class CalendarEvent {

        private Integer id;
        private String description;

    }

}
