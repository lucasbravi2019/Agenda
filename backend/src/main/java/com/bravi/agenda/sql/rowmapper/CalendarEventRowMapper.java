package com.bravi.agenda.sql.rowmapper;

import com.bravi.agenda.dto.CalendarEventsDTO;
import com.bravi.agenda.dto.CalendarEventsUpdateDTO;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class CalendarEventRowMapper implements RowMapper<CalendarEventsUpdateDTO.CalendarEvent> {

    @Override
    public CalendarEventsUpdateDTO.CalendarEvent mapRow(ResultSet rs, int rowNum) throws SQLException {
        return CalendarEventsUpdateDTO.CalendarEvent.builder()
                .id(rs.getInt(1))
                .description(rs.getString(2))
                .build();
    }
}
