package com.bravi.agenda.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CalendarEventsDTO {

    private Integer id;
    private String eventDescription;
    private Integer day;

}
