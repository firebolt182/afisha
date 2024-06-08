package org.javaacademy.afisha.repository;

import lombok.RequiredArgsConstructor;
import org.javaacademy.afisha.entity.Place;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class PlaceRepository {
    private final JdbcTemplate jdbcTemplate;

    public void savePlace(String name, String address, String city) {
        String sql = "insert into application.place (name, address, city) values(?, ?, ?)";
        jdbcTemplate.update(sql, name, address, city);
    }

    public Optional<Place> findPlaceById(int id) {
        String sql = """
                select *
                from application.place where id = %d;
                """.formatted(id);
        return Optional.ofNullable(jdbcTemplate.queryForObject(sql, this::placeRowMapper));
    }

    public List<Place> findAllPlaces() {
        String sql = "select * from application.place";
        return jdbcTemplate.query(sql, this::placeRowMapper);
    }

    public Place placeRowMapper(ResultSet resultSet, int rowNum) throws SQLException {
        Place place = new Place();
        place.setId(resultSet.getInt("id"));
        place.setName(resultSet.getString("name"));
        place.setAddress(resultSet.getString("address"));
        place.setCity(resultSet.getString("city"));
        return place;
    }
}
