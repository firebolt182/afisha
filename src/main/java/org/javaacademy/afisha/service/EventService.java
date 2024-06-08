package org.javaacademy.afisha.service;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.javaacademy.afisha.dto.EventDto;
import org.javaacademy.afisha.dto.EventDtoRq;
import org.javaacademy.afisha.entity.Event;
import org.javaacademy.afisha.repository.EventRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.support.TransactionTemplate;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

//Создать сервис + контроллер, позволяющий создавать события и выдывать существующие события.
// При создании события (передается цена, дата, имя события и место),
// должны генерироваться 100 билетов для кино,
// 50 билетов для театра (email не заполнен, билет не продан).
// Для музея билеты не генерятся.
// События + билеты должны создаваться одной транзакцией (делать после прохождения транзакций).
@Service
@RequiredArgsConstructor
@Slf4j
public class EventService {
    private final EventRepository eventRepository;
    private final TransactionTemplate transactionTemplate;
    private static final Integer CINEMA_TICKETS_QTY = 100;
    private static final Integer THEATRE_TICKETS_QTY = 50;

    //цена, дата, имя события и место
    //                            BigDecimal price,
    //                            LocalDate date,
    //                            String eventName,
    //                            int placeId,
    //                            int eventTypeId

    /*@PostConstruct
    public void create() {
        createEvent(new EventDtoRq(
                BigDecimal.TEN, LocalDate.of(2024, 10, 10),
                "фонарный столб", 1, 2
        ));
        System.out.println("УСПЕХ");
    }*/

    public void createEvent(EventDtoRq eventDtoRq) {
        transactionTemplate.executeWithoutResult((transactionStatus -> {
            Object savepoint = transactionStatus.createSavepoint();
            try {
        saveChosenEvent(eventDtoRq);
            } catch (Exception e) {
                transactionStatus.rollbackToSavepoint(savepoint);
            }
        }));
    }

    public EventDto findEventById(int id) {
        return convertToDto(eventRepository.findEventById(id).orElseThrow());
    }

    public List<EventDto> findAllEvents() {
        return eventRepository.findAllEvents().stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    private void saveChosenEvent(EventDtoRq eventDtoRq) {
        switch (eventDtoRq.getEventTypeId()){
            case 1 :
                saveEvent(eventDtoRq);
                break;
            case 2 :
                saveEvent(eventDtoRq);
                eventRepository.callProcedureGenerateTickets(CINEMA_TICKETS_QTY,
                        eventRepository.findEventId(eventDtoRq.getEventName()),
                        eventDtoRq.getPrice());
                break;
            case 3 :
                saveEvent(eventDtoRq);
                eventRepository.callProcedureGenerateTickets(THEATRE_TICKETS_QTY,
                        eventRepository.findEventId(eventDtoRq.getEventName()),
                        eventDtoRq.getPrice());
                break;
        }
    }
    private void saveEvent(EventDtoRq eventDtoRq) {
        eventRepository.saveEvent(eventDtoRq.getEventName(),
                eventDtoRq.getEventTypeId(),
                eventDtoRq.getDate(),
                eventDtoRq.getPlaceId());
    }

    private EventDto convertToDto(Event event) {
        EventDto eventDto = new EventDto();
        eventDto.setId(event.getId());
        eventDto.setName(event.getName());
        eventDto.setEventTypeId(event.getEventTypeId());
        eventDto.setPlaceId(event.getPlaceId());
        eventDto.setEvent_date(event.getEvent_date());
        return eventDto;
    }
}
