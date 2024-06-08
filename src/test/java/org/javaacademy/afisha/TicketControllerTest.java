package org.javaacademy.afisha;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import lombok.RequiredArgsConstructor;
import org.javaacademy.afisha.service.TicketService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@RequiredArgsConstructor
public class TicketControllerTest {
    @Autowired
    TicketService ticketService;
    private static final String BASE_URL = "http://localhost:8080";
    private static final String BASE_URL_POSTFIX = "/ticket";


    @Test
    @DisplayName("Вывод покупка билета")
    public void buyTicketSuccess() {
        RestAssured.given()
                .queryParam("email", "test@mail.ru")
                .queryParam("eventTypeId", 2)
                .contentType(ContentType.HTML)
                .log().all()
                .post(BASE_URL + BASE_URL_POSTFIX)
                .then()
                .statusCode(201);
    }
}
