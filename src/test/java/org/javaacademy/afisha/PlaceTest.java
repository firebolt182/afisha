package org.javaacademy.afisha;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import lombok.RequiredArgsConstructor;
import org.hamcrest.Matchers;
import org.javaacademy.afisha.dto.PlaceDtoRq;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.annotation.DirtiesContext;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@RequiredArgsConstructor
public class PlaceTest {
    @Autowired
    private JdbcTemplate jdbcTemplate;
    private final String URL = "http://localhost:8080/place";

    @Test
    @DisplayName("Создание места")
    public void addPlaceSuccess() {
        PlaceDtoRq placeDtoRq = new PlaceDtoRq("test name",
                "test address,", "test city");
        RestAssured.given()
                .body(placeDtoRq)
                .contentType(ContentType.JSON)
                .log().all()
                .post(URL)
                .then()
                .log().all()
                .statusCode(201);
    }

    @Test
    @DisplayName("Вывод всех мест")
    public void findAllPlacesSuccess() {
        RestAssured.given()
                .log().all()
                .get(URL)
                .then()
                .log().all()
                .statusCode(200);

        String sql = "select count(*) from application.place";
        long count = takeNum(sql);
        Assertions.assertNotEquals(0, count);
    }

    @Test
    @DisplayName("Вывод одного места")
    public void findPlaceByIdSuccess() {
        String sql = "select id from application.place where name = 'test name'";
        int id = takeNum(sql);
        RestAssured.given()
                .log().all()
                .get(URL + "/" + id)
                .then()
                .log().all()
                .statusCode(200)
                .body("name", Matchers.equalTo("test name"))
                .body("address", Matchers.equalTo("test address,"))
                .body("city", Matchers.equalTo("test city"));


    }

    private Integer takeNum(String query) {
        int count = 0;
        try {
            String sql = query;
            count = jdbcTemplate.queryForObject(sql, Integer.class);
        }
        catch (EmptyResultDataAccessException e) {
        }
        return count;
    }
}
