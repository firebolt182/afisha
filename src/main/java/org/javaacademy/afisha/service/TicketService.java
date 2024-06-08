package org.javaacademy.afisha.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.javaacademy.afisha.dto.TicketDto;
import org.javaacademy.afisha.entity.Ticket;
import org.javaacademy.afisha.repository.TicketRepository;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class TicketService {
    private final TicketRepository ticketRepository;

    public void buyTicket(String email, int eventTypeId) {
        switch (eventTypeId) {
            case 1 :
                byuTicketToMuseum(email);
                printBuyTicket();
                break;
            case 2, 3:
                updateTicket(email, eventTypeId);
                printBuyTicket();
                break;
        }
    }

    private void byuTicketToMuseum(String email) {
        ticketRepository.addTicketToMuseum(email);
    }

    private void printBuyTicket() {
        log.info("Билет куплен");
    }

    private void updateTicket(String email, int eventTypeId) {
        TicketDto ticketDto = convertToDto(ticketRepository.findTicket(eventTypeId).orElseThrow());
        ticketRepository.updateTicket(ticketDto.getId(), email);
    }

    private TicketDto convertToDto(Ticket ticket) {
        TicketDto ticketDto = new TicketDto();
        if (ticket != null) {
            ticketDto.setId(ticket.getId());
            ticketDto.setEventId(ticketDto.getEventId());
            ticketDto.setClientEmail(ticket.getClientEmail());
            ticketDto.setSold(ticket.isSold());
        }
        return ticketDto;
    }
}
