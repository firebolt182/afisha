package org.javaacademy.afisha;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class ReportControllerTest {
    private static final String URL = "http://localhost:8080/event";

    @Test
    @DisplayName("Вывод отчета")
    public void makeReportSuccess() {
        RestAssured.given()
                .log().all()
                .contentType(ContentType.JSON)
                .get(URL)
                .then()
                .log().all()
                .statusCode(200);
    }
}
