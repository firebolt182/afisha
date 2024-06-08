package org.javaacademy.afisha.repository;

import lombok.RequiredArgsConstructor;
import org.javaacademy.afisha.entity.Place;
import org.javaacademy.afisha.entity.Report;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class ReportRepository {
    private final JdbcTemplate jdbcTemplate;

    public List<Report> makeReport() {
        String sql = """
                select distinct ev.name "event_name",
                				ty.name "type_name",
                				count(*) "count",
                				sum(ti.price) "amount"
                	from application.ticket ti
                	join application.event ev
                	on ti.event_id = ev.id
                		join application.event_type ty on  ev.event_type_id = ty.id
                	where is_sold = true
                	group by ev.name, ty.name
                """;
        return jdbcTemplate.query(sql, this::reportRowMapper);
    }

    private Report reportRowMapper(ResultSet resultSet, int rowNum) throws SQLException {
        Report report = new Report();
        report.setName(resultSet.getString("event_name"));
        report.setType(resultSet.getString("type_name"));
        report.setCountSoldTickets(resultSet.getInt("count"));
        report.setAmountSoldTickets(BigDecimal.valueOf(resultSet.getInt("amount")));
        return report;
    }
}
