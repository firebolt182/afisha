package org.javaacademy.afisha;

import io.restassured.RestAssured;
import org.hamcrest.Matchers;
import org.javaacademy.afisha.dto.EventDtoRq;
import org.javaacademy.afisha.repository.EventTypeRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.annotation.DirtiesContext;

import java.math.BigDecimal;
import java.time.LocalDate;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class EventTest {
    @Autowired
    EventTypeRepository eventTypeRepository;
    @Autowired
    JdbcTemplate jdbcTemplate;

    private static final String URL = "http://localhost:8080/event";


    @Test
    @DisplayName("Создание события")
    public void addEventSuccess() {
        eventTypeRepository.saveEventType("test event");
        String eventTypeSql = "select id from application.event_type where name = 'test event' limit 1";
        int eventTypeId = takeNum(eventTypeSql);
        System.out.println(eventTypeId);
        String placeSql = "select id from application.place where name = 'test name'";
        int placeId = takeNum(placeSql);
        EventDtoRq eventDtoRq = new EventDtoRq(BigDecimal.TEN, LocalDate.now(),
                "test event", placeId, eventTypeId);
        RestAssured.given()
                .body(eventDtoRq)
                .log().all()
                .post(URL)
                .then()
                .log().all()
                .statusCode(201);
    }

    @Test
    @DisplayName("Вывод всех событий")
    public void findAllEventsSuccess() {
        RestAssured.given()
                .log().all()
                .get(URL)
                .then()
                .log().all()
                .statusCode(200);

        String sql = "select count(*) from application.event";
        long count = takeNum(sql);
        Assertions.assertNotEquals(0, count);
    }

    @Test
    @DisplayName("Вывод одного события")
    public void findEventByIdSuccess() {
        String sql = "select id from application.event where name = 'test event'";
        int id = takeNum(sql);
        RestAssured.given()
                .log().all()
                .get(URL + "/" + id)
                .then()
                .log().all()
                .statusCode(200)
                .body("name", Matchers.equalTo("test event"))
                .body("price", Matchers.equalTo("10.0"));
    }


    private Integer takeNum(String query) {
        int number = 0;
        try {
            String sql = query;
            number = jdbcTemplate.queryForObject(sql, Integer.class);
        }
        catch (EmptyResultDataAccessException e) {
        }
        return number;
    }
}
