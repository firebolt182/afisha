package org.javaacademy.afisha.repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.javaacademy.afisha.entity.EventType;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

@Repository
@RequiredArgsConstructor
public class EventTypeRepository {
    private final JdbcTemplate jdbcTemplate;

    public void saveEventType(String name) {
        String sql = "insert into application.event_type (name) values(?)";
        jdbcTemplate.update(sql, name);
    }

    public Optional<EventType> findEventTypeById(int id) {
        String sql = """
                select *
                from application.event_type where id = %d;
                """.formatted(id);
        return Optional.ofNullable(jdbcTemplate.queryForObject(sql, this::eventTypeRowMapper));
    }

    public List<EventType> findAllTypes() {
        String sql = "select * from application.event_type";
        return jdbcTemplate.query(sql, this::eventTypeRowMapper);
    }

    public EventType eventTypeRowMapper(ResultSet resultSet, int rowNum) throws SQLException {
        EventType eventType = new EventType();
        eventType.setId(resultSet.getInt("id"));
        eventType.setName(resultSet.getString("name"));
        return eventType;
    }
}
