package org.javaacademy.afisha.repository;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.javaacademy.afisha.entity.Event;
import org.javaacademy.afisha.entity.EventType;
import org.javaacademy.afisha.entity.Place;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCallback;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.support.TransactionTemplate;

import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

//сохранить новую сущность, получить сущность по id, получить все сущности
@Repository
@RequiredArgsConstructor
public class EventRepository {
    private final JdbcTemplate jdbcTemplate;
    private final TransactionTemplate transactionTemplate;

    public void saveEvent(String name, int eventTypeId, LocalDate eventDate, int placeId) {
        String sql = """
            insert into application.event (name,
                                           event_type_id,
                                           event_date,
                                           place_id) values(?, ?, ?, ?)
            """;
        jdbcTemplate.update(sql, name, eventTypeId, eventDate, placeId);
    }

    public Optional<Event> findEventById(int id) {
        String sql = """
                select *
                from application.event where id = %d;
                """.formatted(id);
        return Optional.ofNullable(jdbcTemplate.queryForObject(sql, this::eventRowMapper));
    }

    public List<Event> findAllEvents() {
        String sql = "select * from application.event";
        return jdbcTemplate.query(sql, this::eventRowMapper);
    }

    public Event eventRowMapper(ResultSet resultSet, int rowNum) throws SQLException {
        Event event = new Event();
        event.setId(resultSet.getInt("id"));
        event.setName(resultSet.getString("name"));
        event.setEventTypeId(resultSet.getInt("event_type_id"));
        event.setEvent_date(resultSet.getTimestamp("event_date").
                toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
        event.setPlaceId(resultSet.getInt("place_id"));
        return event;
    }

    public Integer findEventId(String eventName) {
        String sql = "select id from application.event where name = '%s'".formatted(eventName);
        List<Integer> idList = (List<Integer>) jdbcTemplate.execute(sql, (PreparedStatementCallback<Object>) preparedStatement -> {
            ResultSet resultSet = preparedStatement.executeQuery();
            List<Integer> ids = new LinkedList<>();
            while (resultSet.next()) {
                ids.add(resultSet.getInt("id"));
            }
            resultSet.close();
            return ids;
        });
        return idList.get(0);
    }

    public void callProcedureGenerateTickets(int qty,
                                             int eventId,
                                             BigDecimal price) {
        jdbcTemplate.execute(createProcedureGenerateTickets());
        jdbcTemplate.execute("call application.create_tickets(%d, %d, %s)".formatted(qty,
                eventId,
                price.toPlainString()));
    }

    private String createProcedureGenerateTickets() {
        return """
                create or replace procedure application.create_tickets(qty int,
                                                                       event_id int,
                                                                       price numeric)
                language plpgsql
                as $$
                declare i int;
                begin
                    foreach i in array(select array(select * from generate_series(1, qty)))
                    loop
                    insert into application.ticket (event_id, price, is_sold) values
                    (event_id, price, false);
                    end loop;
                end;
                $$
                """;
    }
}
