package org.javaacademy.afisha.repository;

import lombok.RequiredArgsConstructor;
import org.javaacademy.afisha.entity.Event;
import org.javaacademy.afisha.entity.Ticket;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;
import java.util.Optional;

//сохранить новую сущность, получить сущность по id, получить все сущности
@Repository
@RequiredArgsConstructor
public class TicketRepository {
    private final JdbcTemplate jdbcTemplate;

    public void saveTicket(int eventId, String clientEmail, BigDecimal price, boolean isSold) {
        String sql = """
            insert into application.ticket (event_id,
                                            client_email,
                                            price,
                                            is_sold) values(?, ?, ?, ?)
            """;
        jdbcTemplate.update(sql, eventId, clientEmail, price, isSold);
    }

    public Optional<Ticket> findTicketById(int id) {
        String sql = """
                select *
                from application.ticket where id = %d;
                """.formatted(id);
        return Optional.ofNullable(jdbcTemplate.queryForObject(sql, this::ticketRowMapper));
    }

    public List<Ticket> findAllTickets() {
        String sql = "select * from application.ticket";
        return jdbcTemplate.query(sql, this::ticketRowMapper);
    }

    public Optional<Ticket> findTicket(int eventTypeId) {
        String sql = """
                select * from application.ticket where id = (
                	select id from application.ticket
                		where event_id =
                			(select id from
                				application.event where event_type_id = %d limit 1)
                		AND is_sold = false limit 1);
                """.formatted(eventTypeId);
        return Optional.ofNullable(jdbcTemplate.queryForObject(sql, this::ticketRowMapper));
    }

    public void updateTicket(Integer id, String email) {
        String sql = """
                update application.ticket set client_email = '%s', is_sold = true
                	where id = %d;
                """.formatted(email, id);
        jdbcTemplate.execute(sql);
    }

    public void addTicketToMuseum(String email) {
        String sql = """
                insert into application.ticket(event_id, client_email, price, is_sold)
                	values((select ev.id from
                       application.event ev join application.event_type ty
                       on ev.event_type_id = ty.id
                       where ty.name = 'museum'),'%s', 20.0, true);
                """.formatted(email);
        jdbcTemplate.execute(sql);
    }

    private Ticket ticketRowMapper(ResultSet resultSet, int rowNum) throws SQLException {
        Ticket ticket = new Ticket();
        ticket.setId(resultSet.getInt("id"));
        ticket.setEventId(resultSet.getInt("event_id"));
        ticket.setClientEmail(resultSet.getString("client_email"));
        ticket.setPrice(resultSet.getBigDecimal("price"));
        ticket.setSold(resultSet.getBoolean("is_sold"));
        return ticket;
    }
}
