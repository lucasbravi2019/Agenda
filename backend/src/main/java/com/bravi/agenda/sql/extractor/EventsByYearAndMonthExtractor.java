package com.bravi.agenda.sql.extractor;

import com.bravi.agenda.dto.CalendarEventsDTO;
import com.bravi.agenda.util.ListUtils;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EventsByYearAndMonthExtractor implements ResultSetExtractor<Map<Integer, List<CalendarEventsDTO>>> {

    @Override
    public Map<Integer, List<CalendarEventsDTO>> extractData(ResultSet rs) throws SQLException, DataAccessException {
        Map<Integer, List<CalendarEventsDTO>> eventsByDay = new HashMap<>();

        while (rs.next()) {
            int id = rs.getInt(1);
            String eventDescription = rs.getString(2);
            int day = rs.getInt(3);

            CalendarEventsDTO event = CalendarEventsDTO.builder()
                    .id(id)
                    .eventDescription(eventDescription)
                    .day(day)
                    .build();

            eventsByDay.merge(day, ListUtils.list(event), ListUtils::addAll);
        }

        return eventsByDay;
    }
}
