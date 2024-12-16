package com.bravi.agenda.repository.impl;

import com.bravi.agenda.constant.QueryType;
import com.bravi.agenda.dto.CalendarEventsDTO;
import com.bravi.agenda.dto.CalendarEventsUpdateDTO;
import com.bravi.agenda.repository.EventRepository;
import com.bravi.agenda.sql.Query;
import com.bravi.agenda.sql.executor.NativeQueryExecutor;
import com.bravi.agenda.sql.extractor.EventsByYearAndMonthExtractor;
import com.bravi.agenda.sql.rowmapper.CalendarEventRowMapper;
import com.bravi.agenda.util.DateUtils;
import com.bravi.agenda.util.MapBuilder;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Slf4j
@Repository
public class EventRepositoryImpl implements EventRepository {

    public static final String FIND_EVENTS_BY_YEAR_AND_MONTH_AND_DAY = "event.findEventsByYearAndMonthAndDay";
    public static final String FIND_EVENTS_BY_YEAR_AND_MONTH = "event.findEventsByYearAndMonth";
    public static final String YEAR = "year";
    public static final String MONTH = "month";
    public static final String DAY = "day";
    public static final String INSERT_EVENT = "event.insertEvent";
    public static final String UPDATE_EVENT_BY_ID = "event.updateEventById";
    public static final String EVENT_IDS = "eventIds";
    public static final String DESCRIPTION = "description";
    public static final String UPDATED = "updated";
    public static final String ID = "id";
    public static final String CREATED = "created";
    public static final String DELETE_EVENT_BY_IDS = "event.deleteEventByIds";
    private final NativeQueryExecutor nativeQueryExecutor;

    @Autowired
    public EventRepositoryImpl(NativeQueryExecutor nativeQueryExecutor) {
        this.nativeQueryExecutor = nativeQueryExecutor;
    }

    @Override
    public Map<Integer, CalendarEventsDTO> findEventsByYearAndMonth(Integer year, Integer month) {
        return (Map<Integer, CalendarEventsDTO>) Query.newQuery(FIND_EVENTS_BY_YEAR_AND_MONTH, QueryType.SELECT, nativeQueryExecutor)
                .param(YEAR, year)
                .param(MONTH, month)
                .resultSetExtractor(new EventsByYearAndMonthExtractor())
                .getResult();
    }

    @Override
    public List<CalendarEventsUpdateDTO.CalendarEvent> findEventsByYearAndMonthAndDay(Integer year, Integer month, Integer day) {
        return (List<CalendarEventsUpdateDTO.CalendarEvent>) Query.newQuery(FIND_EVENTS_BY_YEAR_AND_MONTH_AND_DAY, QueryType.SELECT, nativeQueryExecutor)
                .param(YEAR, year)
                .param(MONTH, month)
                .param(DAY, day)
                .rowMapper(new CalendarEventRowMapper())
                .getResult();
    }

    @Override
    public void createEvents(List<CalendarEventsUpdateDTO.CalendarEvent> calendarEvents, Integer year, Integer month, Integer day) {
        if (CollectionUtils.isEmpty(calendarEvents)) return;

        Map<String, String>[] batchParams = calendarEvents.stream()
                .map(event -> {
                    String dateTime = DateUtils.dateTimeNow();
                    return MapBuilder.newMap(DESCRIPTION, event.getDescription())
                            .add(DAY, day.toString())
                            .add(MONTH, month.toString())
                            .add(YEAR, year.toString())
                            .add(CREATED, dateTime)
                            .add(UPDATED, dateTime)
                            .get();
                }).toArray(Map[]::new);

        Query.newQuery(INSERT_EVENT, QueryType.MODIFICATION, nativeQueryExecutor)
                .batchParams(batchParams)
                .batchUpdate();
    }

    @Override
    public void updateEvents(List<CalendarEventsUpdateDTO.CalendarEvent> calendarEvents, Integer year, Integer month) {
        if (CollectionUtils.isEmpty(calendarEvents)) return;

        Map<String, String>[] batchParams = calendarEvents.stream()
                .map(event -> MapBuilder.newMap(DESCRIPTION, event.getDescription())
                        .add(UPDATED, DateUtils.dateTimeNow())
                        .add(ID, event.getId().toString())
                        .get()).toArray(Map[]::new);

        Query.newQuery(UPDATE_EVENT_BY_ID, QueryType.MODIFICATION, nativeQueryExecutor)
                .batchParams(batchParams)
                .batchUpdate();
    }

    @Override
    public void deleteEventsByIds(List<Integer> calendarEventIds) {
        if (CollectionUtils.isEmpty(calendarEventIds)) return;

        Query.newQuery(DELETE_EVENT_BY_IDS, QueryType.MODIFICATION, nativeQueryExecutor)
                .param(EVENT_IDS, calendarEventIds)
                .execute();
    }
}
